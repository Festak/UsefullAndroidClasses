package com.example.efetskovich.realmexample.ormlite;

import com.example.efetskovich.realmexample.models.Bag;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

/**
 * @author e.fetskovich on 10/13/17.
 */

public class BagDao extends BaseDaoImpl<Bag, String> {
    protected BagDao(ConnectionSource connectionSource,
                      Class<Bag> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<Bag> getAllRoles() throws SQLException {
        return this.queryForAll();
    }
}
