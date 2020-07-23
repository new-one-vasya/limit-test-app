package ru.whoy.webdb.service;

import org.springframework.stereotype.Service;
import ru.whoy.webdb.entity.Limit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.whoy.webdb.entity.sub.Method.COUNT;
import static ru.whoy.webdb.entity.sub.Method.SUM;
import static ru.whoy.webdb.entity.sub.Resource.ACCOUNT;
import static ru.whoy.webdb.entity.sub.Resource.BOTH;
import static ru.whoy.webdb.entity.sub.Resource.CARD;
import static ru.whoy.webdb.entity.sub.Verdict.CONFIRM;
import static ru.whoy.webdb.entity.sub.Verdict.ERROR;

@Service
public class LimitService {

    private final List<Limit> limits = new ArrayList<>(Arrays.asList(
            new Limit(BOTH, ERROR, SUM, 1000L),
            new Limit(CARD, CONFIRM, SUM, 400L),
            new Limit(CARD, ERROR, SUM, 500L),
            new Limit(ACCOUNT, CONFIRM, SUM, 400L),
            new Limit(ACCOUNT, ERROR, COUNT, 10L)
    ));

    public List<Limit> getLimits() {
        return new ArrayList<>(limits);
    }

}
