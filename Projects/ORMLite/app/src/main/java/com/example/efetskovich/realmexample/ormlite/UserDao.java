package com.example.efetskovich.realmexample.ormlite;


import com.example.efetskovich.realmexample.models.User;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

/**
 * @author e.fetskovich on 10/13/17.
 */

public class UserDao extends BaseDaoImpl<User, String> {

    protected UserDao(ConnectionSource connectionSource,
                      Class<User> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<User> getAllRoles() throws SQLException {
        return this.queryForAll();
    }

    public List<User> getGoalByName(String name)  throws SQLException{
        QueryBuilder<User, String> queryBuilder = queryBuilder();
        queryBuilder.where().eq("name", name);
        PreparedQuery<User> preparedQuery = queryBuilder.prepare();
        List<User> goalList =query(preparedQuery);
        return goalList;
    }

}