package ru.whoy.webdb.entity;

import ru.whoy.webdb.entity.sub.Resource;

public class Operation {

    private long value;
    private Resource resource;

    public Operation() {
    }

    public Operation(long value, Resource resource) {
        this.value = value;
        this.resource = resource;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }
}
