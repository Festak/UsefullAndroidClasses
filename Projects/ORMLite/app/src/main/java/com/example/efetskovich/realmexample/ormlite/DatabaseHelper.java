package com.example.efetskovich.realmexample.ormlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.efetskovich.realmexample.models.Bag;
import com.example.efetskovich.realmexample.models.Roles;
import com.example.efetskovich.realmexample.models.User;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * @author e.fetskovich on 10/13/17.
 */

public class DatabaseHelper  extends OrmLiteSqliteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getSimpleName();

    //имя файла базы данных который будет храниться в /data/data/APPNAME/DATABASE_NAME.db
    private static final String DATABASE_NAME ="myappname.db";

    //с каждым увеличением версии, при нахождении в устройстве БД с предыдущей версией будет выполнен метод onUpgrade();
    private static final int DATABASE_VERSION = 7;

    //ссылки на DAO соответсвующие сущностям, хранимым в БД
    private UserDao userDao = null;
    private BagDao bagDao = null;
    private RolesDao rolesDao = null;

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Выполняется, когда файл с БД не найден на устройстве
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource){
        try
        {
            TableUtils.createTable(connectionSource, Bag.class);
            TableUtils.createTable(connectionSource, Roles.class);
            TableUtils.createTable(connectionSource, User.class);
        }
        catch (SQLException e){
            Log.e(TAG, "error creating DB " + DATABASE_NAME);
            throw new RuntimeException(e);
        }
    }

    //Выполняется, когда БД имеет версию отличную от текущей
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVer,
                          int newVer){
        try{
            //Так делают ленивые, гораздо предпочтительнее не удаляя БД аккуратно вносить изменения
            TableUtils.dropTable(connectionSource, Roles.class, true);
            TableUtils.dropTable(connectionSource, Bag.class, true);
            TableUtils.dropTable(connectionSource, User.class, true);
            onCreate(db, connectionSource);
        }
        catch (SQLException e){
            Log.e(TAG,"error upgrading db "+DATABASE_NAME+"from ver "+oldVer);
            throw new RuntimeException(e);
        }
    }

    public UserDao getUserDao(){
        if(userDao == null){
            try {
                userDao = new UserDao(getConnectionSource(), User.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return userDao;
    }

    public BagDao getBagDao() throws SQLException{
        if(bagDao == null){
            bagDao = new BagDao(getConnectionSource(), Bag.class);
        }
        return bagDao;
    }

    public RolesDao getRolesDao() throws SQLException{
        if(rolesDao == null){
            rolesDao = new RolesDao(getConnectionSource(), Roles.class);
        }
        return rolesDao;
    }

    //выполняется при закрытии приложения
    @Override
    public void close(){
        super.close();
        userDao = null;
        bagDao = null;
        rolesDao = null;
    }
}