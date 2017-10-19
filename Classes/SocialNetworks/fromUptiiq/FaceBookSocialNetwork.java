package com.euclid.uptiiq.extras.socialNetwork;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.euclid.uptiiq.utils.AppController;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FaceBookSocialNetwork implements SocialNetwork {

    private static final String TAG = "FaceBookSocialNetwork";
    
    private Activity activity;

    private ArrayList<String> permissions;

    private CallbackManager callbackManager;

    private boolean publishPermissionsRequested = false;

    private LoginResult readResultSuccess;

    private SocialCallback myCallbackClass;

    public FaceBookSocialNetwork(Activity activity, ArrayList<String> permissions) {
        this.activity = activity;
        this.permissions = permissions;
        if (!FacebookSdk.isInitialized()) {
            Log.i(TAG, "FaceBookSocialNetwork: not initialized");
            FacebookSdk.sdkInitialize(activity.getApplicationContext(), 1230);
            AppEventsLogger.activateApp(activity.getApplication());
            callbackManager = CallbackManager.Factory.create();

        } else {
            Log.i(TAG, "FaceBookSocialNetwork: initialized");
            callbackManager = CallbackManager.Factory.create();
        }

    }

    public FaceBookSocialNetwork(Activity activity, ArrayList<String> permissions, FacebookSdk.InitializeCallback callback){
        this.activity = activity;
        this.permissions = permissions;
        if (!FacebookSdk.isInitialized()) {
            Log.i(TAG, "FaceBookSocialNetwork: not initialized");
            FacebookSdk.sdkInitialize(activity.getApplicationContext(), 1230, callback);
            AppEventsLogger.activateApp(activity.getApplication());
            callbackManager = CallbackManager.Factory.create();

        } else {
            Log.i(TAG, "FaceBookSocialNetwork: initialized");
            callbackManager = CallbackManager.Factory.create();
            callback.onInitialized();
        }
    }

    @Override
    public void requestLogin() {
        LoginManager.getInstance().logInWithReadPermissions(activity, permissions);
        LoginManager.getInstance().registerCallback(getCallbackManager(), new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AppController.getHelperSharedPreference().writePreference(activity, "loginRequest", "Success");
            }

            @Override
            public void onCancel() {
                AppController.getHelperSharedPreference().writePreference(activity, "loginRequest", "failure");
                LoginManager.getInstance().logOut();
//                Toast.makeText(activity, "User details not Available", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                AppController.getHelperSharedPreference().writePreference(activity, "loginRequest", "failure");
                LoginManager.getInstance().logOut();
                Toast.makeText(activity, "Failed to Login", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void requestPublish() {
        List<String> publishPermissions = Arrays.asList("publish_actions,email");
        LoginManager.getInstance().logInWithPublishPermissions(activity, publishPermissions);
        LoginManager.getInstance().registerCallback(getCallbackManager(), new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AppController.getHelperSharedPreference().writePreference(activity, "publishRequest", "Success");
            }

            @Override
            public void onCancel() {
                AppController.getHelperSharedPreference().writePreference(activity, "publishRequest", "Success");
            }

            @Override
            public void onError(FacebookException error) {
                AppController.getHelperSharedPreference().writePreference(activity, "publishRequest", "Success");
            }

        });
    }

    @Override
    public JSONObject getUserDetails() {
        return null;
    }


    public void getUserDetails(AccessToken currentAccessToken) {
        if (currentAccessToken != null && !currentAccessToken.isExpired()) {
            final GraphRequest request = GraphRequest.newMeRequest(currentAccessToken, new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    if (myCallbackClass != null && object != null) {
                        myCallbackClass.callbackReturn(object);
                    }
                }
            });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email,gender,birthday");
            request.setParameters(parameters);
            request.executeAsync();
        }
    }

    public void getDetails(AccessToken currentAccessToken) {
        if (currentAccessToken != null && !currentAccessToken.isExpired()) {
               /* make the API call */
            new GraphRequest(AccessToken.getCurrentAccessToken(), "/{user-id}/accounts", null, HttpMethod.GET,
                    new GraphRequest.Callback() {
                        public void onCompleted(GraphResponse response) {
                            Log.e("onCompleted", " " + response.getJSONObject());
                        }
                    }
            ).executeAsync();
        }
    }


    @Override
    public void requestToPostMessege() {

    }

    @Override
    public void requestToPostPhoto() {

    }

    @Override
    public void requestToPostVideo() {

    }

    @Override
    public void requestFriendsList() {

    }

    @Override
    public void logout() {
       LoginManager.getInstance().logOut();
    }


    public CallbackManager getCallbackManager() {
        return callbackManager;
    }

    public void registerCallback(SocialCallback callbackClass) {
        myCallbackClass = callbackClass;
    }

}
