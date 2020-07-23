package ru.whoy.webdb.entity;

import java.util.UUID;

public class User {

    private static int GLOBAL_ID = 0;
    private static String GLOBAL_NAME = "User";

    private String id;
    private String name;

    public User() {
        id = UUID.randomUUID().toString();
        name = GLOBAL_NAME + ++GLOBAL_ID;
    }

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    @Deprecated
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @Deprecated
    public void setName(String name) {
        this.name = name;
    }
}
