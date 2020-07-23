package ru.whoy.webdb.entity.sub;

import java.util.HashMap;
import java.util.Map;

public class OperationResult {

    private String verdict;
    private String message;

    private static Map<Verdict, OperationResult> map;

    static {
        map = new HashMap<>();
        map.put(Verdict.NONE, new OperationResult("Success", "Успешная операция"));
        map.put(Verdict.CONFIRM, new OperationResult("Warning", "Необходимо дополнительнео подтверждение операции"));
        map.put(Verdict.ERROR, new OperationResult("Error", "Операция не может быть выполнена"));
    }

    private OperationResult(String verdict, String message) {
        this.verdict = verdict;
        this.message = message;
    }

    public static OperationResult getResult(Verdict verdict) {
        return map.get(verdict);
    }

    public String getVerdict() {
        return verdict;
    }

    public String getMessage() {
        return message;
    }
}
