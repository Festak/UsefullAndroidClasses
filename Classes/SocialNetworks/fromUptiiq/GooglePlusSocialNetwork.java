package com.euclid.uptiiq.extras.socialNetwork;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.euclid.uptiiq.R;
import com.euclid.uptiiq.constants.SharedPreferenceConstants;
import com.euclid.uptiiq.utils.SharedPreferenceHelper;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONObject;

public class GooglePlusSocialNetwork implements SocialNetwork {

    private Activity activity;
    private GoogleApiClient mGoogleApiClient;
    private Intent signInIntent;

    public GooglePlusSocialNetwork(Activity activity) {
        this.activity = activity;
        String serverId = activity.getString(R.string.google_server_id);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(serverId)
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(activity).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();
        mGoogleApiClient.connect();
    }

    @Override
    public void requestLogin() {
        signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        activity.startActivityForResult(signInIntent, 1231);
    }

    @Override
    public JSONObject getUserDetails() {
        return null;
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
        if (mGoogleApiClient.isConnected()) {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient);
        }
    }


    public void saveGoogleTokenId(GoogleSignInAccount account) {
        String tokenId = account.getIdToken();
        saveGoogleTokenToPref(tokenId);
    }

    public void saveGoogleToken(final GoogleSignInAccount account) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String scope = "oauth2:" + Scopes.PROFILE;
                try {
                    initToken(account, scope);

                } catch (Exception e) {
                    Log.e("Google", "saveGoogleToken: " + e);
                }
            }
        };
        AsyncTask.execute(runnable);
    }

    private void initToken(GoogleSignInAccount account, String scope) throws Exception {

        String token = null;

        if (account.getAccount() != null) {

            token = GoogleAuthUtil.getToken(
                    activity.getApplicationContext(),
                    account.getAccount(),
                    scope,
                    new Bundle()
            );
        }

        saveGoogleTokenToPref(token);
    }

    private void saveGoogleTokenToPref(String token) {
        if (token != null) {
            SharedPreferenceHelper.writePreference(
                    activity.getApplicationContext(),
                    SharedPreferenceConstants.GOOGLE_TOKEN,
                    token
            );
        }
    }

}
