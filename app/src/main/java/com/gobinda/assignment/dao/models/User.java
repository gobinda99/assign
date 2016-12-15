package com.gobinda.assignment.dao.models;

import com.gobinda.assignment.dao.DaoUtils;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;

/**
 * Created by gobinda on 5/9/16.
 */
@DatabaseTable(tableName = "users")
public class User {

    @DatabaseField(generatedId = true, columnName = "id")
    protected long mId;

    @DatabaseField(columnName = "email", columnDefinition = "TEXT NOT NULL UNIQUE ON CONFLICT IGNORE")
    protected String mEmail;

    @DatabaseField(columnName = "name")
    protected String mName;

    @DatabaseField(columnName = "password", canBeNull = false)
    protected String mPassword;

    public User() {
    }

    public User(String email, String name, String password) {
        mEmail = email;
        mName = name;
        mPassword = password;
    }

    public long getId() {
        return mId;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public static User getUser(String email) {
        try {
            return DaoUtils.getByFieldValue("email", email, User.class);
        } catch (SQLException e) {

        }
        return null;
    }

    public static int saveUser(User user) {
        try {
            return DaoUtils.create(user);
        } catch (SQLException e) {
        }
        return -1;
    }


}
