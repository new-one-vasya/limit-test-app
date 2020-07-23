package ru.whoy.webdb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.whoy.webdb.entity.Limit;
import ru.whoy.webdb.entity.Operation;
import ru.whoy.webdb.entity.OperationsInfo;
import ru.whoy.webdb.entity.User;
import ru.whoy.webdb.entity.sub.OperationResult;
import ru.whoy.webdb.entity.sub.Resource;
import ru.whoy.webdb.entity.sub.Verdict;

import java.util.*;

@Service
public class LifeHolder {

    private Map<String, User> users = new HashMap<>();
    private Map<String, List<Operation>> operations = new HashMap<>();
    private static final User INVALID_USER = new User("-1", "Invalid User");

    @Autowired
    LimitService limitService;

    public User getTestUser() {
        User user = new User();
        users.put(user.getId(), user);
        return user;
    }

    public List<User> getUsers() {
        return Collections.unmodifiableList(new ArrayList<>(users.values()));
    }

    public List<Operation> getUserOperations(String id) {
        return operations.getOrDefault(id, Collections.emptyList());
    }

    public User getUser(String id) {
        return users.getOrDefault(id, INVALID_USER);
    }

    private void validateInput(User user, Operation operation) {
        validateInput(user);
        if (operation.getValue() <= 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "less than zero");
        if (! (operation.getResource() == Resource.ACCOUNT || operation.getResource() == Resource.CARD) )
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "unknown resource");
    }

    private void validateInput(User user) {
        if (user == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "user not found");
    }

    public OperationResult makeOperation(String userId, Operation operation) {
        User user = users.get(userId);
        validateInput(user, operation);

        operations.putIfAbsent(userId, new ArrayList<>());

        Map<String, Long> operationsInfo = OperationsInfo.getCurrentValues(operations.get(userId));

        Verdict verdict = getSimpleOperationVerdict(operationsInfo, operation);

        if (verdict == Verdict.NONE)
            operations.get(userId).add(operation);

        return OperationResult.getResult(verdict);
    }

    public OperationResult confirmOperation(String userId, Operation operation) {
        User user = users.get(userId);
        validateInput(user, operation);

        operations.putIfAbsent(userId, new ArrayList<>());

        Map<String, Long> operationsInfo = OperationsInfo.getCurrentValues(operations.get(userId));

        Verdict verdict = getSimpleOperationVerdict(operationsInfo, operation);

        if (verdict == Verdict.NONE || verdict == Verdict.CONFIRM) {
            operations.get(userId).add(operation);
            verdict = Verdict.NONE;
        }

        return OperationResult.getResult(verdict);
    }

    private Verdict getSimpleOperationVerdict(Map<String, Long> operationsInfo, Operation operation) {
        Verdict currentVerdict = Verdict.NONE;

        for (Limit limit : limitService.getLimits()) {
            if (currentVerdict == Verdict.CONFIRM && limit.getVerdict() == Verdict.CONFIRM)
                continue;

            Long accumulated = operationsInfo.get(limit.getResource() + ":" + limit.getMethod());

            if (accumulated == null)
                accumulated = 0L;

            switch (limit.getMethod()) {
                case SUM:
                    if (limit.getValue() <= accumulated + operation.getValue())
                        currentVerdict = getStrongestVerdict(currentVerdict, limit.getVerdict());
                    break;
                case COUNT:
                    if (limit.getValue() <= accumulated + 1)
                        currentVerdict = getStrongestVerdict(currentVerdict, limit.getVerdict());
                    break;
            }

            if (currentVerdict == Verdict.ERROR)
                break;
        }

        return currentVerdict;
    }

    private Verdict getStrongestVerdict(Verdict currentVerdict, Verdict verdict) {
        return currentVerdict.ordinal() < verdict.ordinal() ? currentVerdict : verdict;
    }


    public Map<String, Long> getCurrentValues(String userId) {
        operations.putIfAbsent(userId, new ArrayList<>());
        return OperationsInfo.getCurrentValues(this.operations.get(userId));
    }


    public Map<String, Long> getRemain(String userId) {
        User user = users.get(userId);
        validateInput(user);

        operations.putIfAbsent(userId, new ArrayList<>());

        Map<String, Long> operationsInfo = OperationsInfo.getCurrentValues(operations.get(userId));
        List<Limit> limits = limitService.getLimits();

        Map<String, Long> answer = new HashMap<>();

        for (Limit limit : limits) {
            String compositeString = limit.getResource() + ":" + limit.getMethod();
            Long accumulated = operationsInfo.get(compositeString);

            if (accumulated == null)
                accumulated = 0L;

            if (limit.getVerdict() == Verdict.ERROR) {
                answer.put(compositeString, limit.getValue() - accumulated);
            }
        }

        return answer;
    }
}
