package com.example.efetskovich.realmexample.models;

import com.example.efetskovich.realmexample.ormlite.ORMLiteFactory;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;

/**
 * @author e.fetskovich on 10/13/17.
 */

@DatabaseTable(tableName = "users")
public class User {

    @DatabaseField(generatedId = true, dataType = DataType.INTEGER, columnName = "user_id")
    private int userId;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = "name")
    private String name;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = "address")
    private String address;

    @DatabaseField(dataType = DataType.DATE, columnName = "creation_date")
    private Date creationDate;

    @DatabaseField(foreign = true)
    private Bag bag;

    @ForeignCollectionField(eager = true)
    private ForeignCollection<Roles> rolesList;

    public User() {
        creationDate = new Date();
    }

    public User(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Collection<Roles> getRolesList() {
        return rolesList;
    }

    public Bag getBag() {
        return bag;
    }

    public void setBag(Bag bag) {
        this.bag = bag;
    }

    public void addRole(Roles value) throws SQLException {
        value.setUser(this);
        ORMLiteFactory.getHelper().getRolesDao().create(value);
    }

    public void removeRole(Roles value) throws SQLException {
        ORMLiteFactory.getHelper().getRolesDao().delete(value);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", creationDate=" + creationDate +
                ", bag=" + bag.getBagName() + '\'' +
                '}';
    }
}
