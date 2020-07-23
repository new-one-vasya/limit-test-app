package ru.whoy.webdb.entity;

import ru.whoy.webdb.entity.sub.Method;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.whoy.webdb.entity.sub.Resource.*;
import static ru.whoy.webdb.entity.sub.Method.*;


public class OperationsInfo {

    public static final String ACCOUNT_SUM = ACCOUNT + ":" + SUM;
    public static final String CARD_SUM = CARD + ":" + SUM;
    public static final String BOTH_SUM = BOTH + ":" + SUM;
    public static final String ACCOUNT_COUNT = ACCOUNT + ":" + COUNT;
    public static final String CARD_COUNT = CARD + ":" + COUNT;
    public static final String BOTH_COUNT = BOTH + ":" + COUNT;

    public static Map<String, Long> getCurrentValues(List<Operation> operations) {
        Map<String, Long> currentValues = new HashMap<>();
        for (Operation operation : operations) {
            if (operation.getResource() == ACCOUNT) {
                currentValues.compute(ACCOUNT_COUNT, (k, v) -> v == null ? 1 : v + 1);
                currentValues.compute(ACCOUNT_SUM, (k, v) -> v == null ? operation.getValue() : v + operation.getValue());
            }
            if (operation.getResource() == CARD) {
                currentValues.compute(CARD_COUNT, (k, v) -> v == null ? 1 : v + 1);
                currentValues.compute(CARD_SUM, (k, v) -> v == null ? operation.getValue() : v + operation.getValue());
            }
            currentValues.compute(BOTH_COUNT, (k, v) -> v == null ? 1 : v + 1);
            currentValues.compute(BOTH_SUM, (k, v) -> v == null ? operation.getValue() : v + operation.getValue());
        }
        return currentValues;
    }
}
