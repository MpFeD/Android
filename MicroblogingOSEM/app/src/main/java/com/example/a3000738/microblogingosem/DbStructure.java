package com.example.a3000738.microblogingosem;

/**
 * Created by Max Uchiha on 18/01/2017.
 */

public class DbStructure {
    public static final String DB_NAME = "twitter.db";
    public static final String TABLE = "status_table";
    public static final int DB_VERSION = 1;

    /* Column names of our database table */

    public static final String ID = "_id";
    public static final String USER = "user";
    public static final String MESSAGE = "message";
    public static final String CREATED_AT = "created_at";
}
