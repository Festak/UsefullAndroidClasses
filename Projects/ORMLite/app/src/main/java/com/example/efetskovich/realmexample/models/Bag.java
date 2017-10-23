package com.example.efetskovich.realmexample.models;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author e.fetskovich on 10/13/17.
 */

@DatabaseTable(tableName = "bags")
public class Bag {

    @DatabaseField(generatedId = true, columnName = "bag_id")
    private int bagId;

    @DatabaseField(dataType = DataType.STRING, columnName = "bag_name")
    private String bagName;

    public int getBagId() {
        return bagId;
    }

    public void setBagId(int bagId) {
        this.bagId = bagId;
    }

    public String getBagName() {
        return bagName;
    }

    public void setBagName(String bagName) {
        this.bagName = bagName;
    }

    @Override
    public String toString() {
        return "Bag{" +
                "bagId=" + bagId +
                ", bagName='" + bagName + '\'' +
                '}';
    }
}
