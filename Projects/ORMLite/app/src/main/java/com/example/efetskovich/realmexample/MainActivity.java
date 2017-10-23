package com.example.efetskovich.realmexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.efetskovich.realmexample.models.Bag;
import com.example.efetskovich.realmexample.models.Roles;
import com.example.efetskovich.realmexample.models.User;
import com.example.efetskovich.realmexample.ormlite.ORMLiteFactory;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            List<User> users = ORMLiteFactory.getHelper().getUserDao().getGoalByName("Jeka");
            for(User user: users){
                ORMLiteFactory.getHelper().getBagDao().refresh(user.getBag());
                Log.i(TAG, "onCreate: "+user.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void testWithAddAndRead(){
        User user = new User();
        user.setName("Jeka");
        user.setAddress("Minsk");

        Bag bag = new Bag();
        bag.setBagName("Jeka's Bag");

        user.setBag(bag);

        Roles role = new Roles();
        role.setName("admin");
        Roles secondRole =new Roles();
        secondRole.setName("user");

        try {
            ORMLiteFactory.getHelper().getBagDao().create(bag);
            ORMLiteFactory.getHelper().getUserDao().create(user);

            user.addRole(role);
            user.addRole(secondRole);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            List<User> list = ORMLiteFactory.getHelper().getUserDao().getAllRoles();
            for(User userTest: list){
                Collection<Roles> rolesList = userTest.getRolesList();
                if(rolesList != null){
                    for(Roles roleTest: rolesList){
                        Log.i(TAG, "onCreate: "+roleTest.toString());
                    }
                }

                ORMLiteFactory.getHelper().getBagDao().refresh(userTest.getBag());
                Log.i(TAG, "onCreate: "+userTest.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
