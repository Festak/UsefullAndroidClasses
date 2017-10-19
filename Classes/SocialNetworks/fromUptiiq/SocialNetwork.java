package com.euclid.uptiiq.extras.socialNetwork;

import org.json.JSONObject;

public interface SocialNetwork {

    void requestLogin();

    JSONObject getUserDetails();

    void requestToPostMessege();

    void requestToPostPhoto();

    void requestToPostVideo();

    void requestFriendsList();

    void logout();

}
