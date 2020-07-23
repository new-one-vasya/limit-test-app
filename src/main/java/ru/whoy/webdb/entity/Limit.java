package ru.whoy.webdb.entity;


import ru.whoy.webdb.entity.sub.Method;
import ru.whoy.webdb.entity.sub.Resource;
import ru.whoy.webdb.entity.sub.Verdict;

public class Limit {

    private Resource resource;
    private Verdict verdict;
    private Method method;
    private long value;

    public Limit(Resource resource, Verdict verdict, Method method, long value) {
        this.resource = resource;
        this.verdict = verdict;
        this.method = method;
        this.value = value;
    }

    public Resource getResource() {
        return resource;
    }

    public Verdict getVerdict() {
        return verdict;
    }

    public Method getMethod() {
        return method;
    }

    public long getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Limit{" +
                "resource=" + resource +
                ", verdict=" + verdict +
                ", method=" + method +
                ", value=" + value +
                '}';
    }
}
