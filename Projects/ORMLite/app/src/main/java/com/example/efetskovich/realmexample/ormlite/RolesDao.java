package com.example.efetskovich.realmexample.ormlite;

import com.example.efetskovich.realmexample.models.Roles;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

/**
 * @author e.fetskovich on 10/13/17.
 */

public class RolesDao extends BaseDaoImpl<Roles, String> {
    protected RolesDao(ConnectionSource connectionSource,
                     Class<Roles> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<Roles> getAllRoles() throws SQLException {
        return this.queryForAll();
    }

}
