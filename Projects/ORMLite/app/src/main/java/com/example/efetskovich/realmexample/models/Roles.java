package com.example.efetskovich.realmexample.models;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author e.fetskovich on 10/13/17.
 */

@DatabaseTable(tableName = "roles")
public class Roles {

    @DatabaseField(generatedId = true, columnName = "role_id")
    private int roleId;

    @DatabaseField(dataType = DataType.STRING, columnName = "role_name")
    private String name;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Roles{" +
                "roleId=" + roleId +
                ", name='" + name + '\'' +
                ", user=" + user +
                '}';
    }
}
