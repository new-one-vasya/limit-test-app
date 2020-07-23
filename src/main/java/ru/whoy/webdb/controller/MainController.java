package ru.whoy.webdb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.whoy.webdb.entity.Limit;
import ru.whoy.webdb.entity.Operation;
import ru.whoy.webdb.entity.User;
import ru.whoy.webdb.entity.sub.OperationResult;
import ru.whoy.webdb.service.LimitService;
import ru.whoy.webdb.service.LifeHolder;

import java.util.List;
import java.util.Map;

@RestController
public class MainController {

    @Autowired
    private LifeHolder lifeHolder;

    @Autowired
    private LimitService limitService;

    @RequestMapping("/other/getTestUser")
    public User getTestUser() {
        return lifeHolder.getTestUser();
    }

    @RequestMapping("/users")
    public List<User> getUsers() {
        return lifeHolder.getUsers();
    }

    @RequestMapping("/users/{id}")
    public User getUser(@PathVariable String id) {
        return lifeHolder.getUser(id);
    }

    @RequestMapping("/limits")
    public List<Limit> getLimits() {
        return limitService.getLimits();
    }


    @RequestMapping("/users/{id}/operations")
    public List<Operation> getUserOperations(@PathVariable String id) {
        return lifeHolder.getUserOperations(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/users/{id}/operations")
    public OperationResult makeOperation(@RequestBody Operation operation, @PathVariable String id) {
        return lifeHolder.makeOperation(id, operation);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/users/{id}/operations/confirm")
    public OperationResult confirmOperation(@RequestBody Operation operation, @PathVariable String id) {
        return lifeHolder.confirmOperation(id, operation);
    }

    @RequestMapping("/users/{id}/remain")
    public Map<String, Long> getUsersRemain(@PathVariable String id) {
        return lifeHolder.getRemain(id);
    }

    @RequestMapping("/users/{id}/currentValues")
    public Map<String, Long> getCurrentValues(@PathVariable String id) {
        return lifeHolder.getCurrentValues(id);
    }





























//    @RequestMapping(method = RequestMethod.POST, value = "/echoOp")
//    public Operation echoOp(@RequestBody Operation operation) {
//        if (operation.getResource() == Resource.BOTH)
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
//        return operation;
//    }

}
