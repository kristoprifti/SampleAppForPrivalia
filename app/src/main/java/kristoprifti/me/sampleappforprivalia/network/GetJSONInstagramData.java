package kristoprifti.me.sampleappforprivalia.network;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import kristoprifti.me.sampleappforprivalia.models.User;
import kristoprifti.me.sampleappforprivalia.models.UserActivity;

/**
 * Created by Kristi on 12/5/2016.
 * this class is responsible for processing the data after its retrieved from the HTTP get request
 * it uses models to store the data and pass them between intents as serializable content
 */

//this class implements an interface of GetInstagramData class which gets the data from the server
// when the data is successfully downloaded the method OnDownloadComplete is overriden and is called in this class
public class GetJSONInstagramData implements GetInstagramData.OnDownloadComplete {
    private static final String TAG = "GetJSONInstagramData";

    private List<UserActivity> mPicturesList = null;
    private User mUser = null;

    private String mUserActivityUrl;
    private String mUserInfoUrl;

    //variable that holds the callback for the constructor
    private final OnDataAvailable mCallBack;

    //interface which is used by MainActivity to make sure that it calls the OnDataAvailable method
    //once the downloading is done. Here we pass the list of pictures that the user has posted and the info for the
    //profile information
    public interface OnDataAvailable{
        void onDataAvailable(List<UserActivity> userActivities, User userInfo);
    }

    //constructor with callback and two json objects in form of strings coming from the GetInstagramData class
    public GetJSONInstagramData(OnDataAvailable callBack, String userActivityUrl, String userInfoUrl) {
        Log.d(TAG, "GetJSONInstagramData called");
        mUserActivityUrl = userActivityUrl;
        mUserInfoUrl = userInfoUrl;
        mCallBack = callBack;
    }

    //this method creates an instance of GetInstagramData class and calls the method sendHttpRequest
    public void sendGetRequest() throws IOException {
        GetInstagramData getRawData = new GetInstagramData(this);
        getRawData.sendHttpRequest(mUserInfoUrl, mUserActivityUrl);
    }

    //implementation of the interface from GetInstagramData
    //when download is complete this method is called with two json objects as strings and is processing
    //the data to prepare them for the MainActivity class and the adapter
    @Override
    public void onDownloadComplete(ArrayList<String> data) {
        Log.d(TAG, "onDownloadComplete: starts");

        mPicturesList = new ArrayList<>();

        try{
            JSONObject userInfoData = new JSONObject(data.get(0));
            setupUserData(userInfoData);

            JSONObject userActivityData = new JSONObject(data.get(1));
            JSONArray userActivityArray = userActivityData.getJSONArray("data");
            setupUserActivity(userActivityArray);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "onDownloadComplete: Error processing Json data " + e.getMessage());
        }

        if(mCallBack != null){
            mCallBack.onDataAvailable(mPicturesList, mUser);
        }
    }

    //this method extracts the data from the user information JSONObject and saves them in the User model
    private void setupUserData(JSONObject userData) throws JSONException {
        String username = userData.getJSONObject("data").getString("username");
        String bio = userData.getJSONObject("data").getString("bio");
        String website = userData.getJSONObject("data").getString("website");
        String profilePicture = userData.getJSONObject("data").getString("profile_picture");
        String fullName = userData.getJSONObject("data").getString("full_name");
        int posts = userData.getJSONObject("data").getJSONObject("counts").getInt("media");
        int followers = userData.getJSONObject("data").getJSONObject("counts").getInt("followed_by");
        int followings = userData.getJSONObject("data").getJSONObject("counts").getInt("follows");

        mUser = new User(username, bio, website, profilePicture, fullName, posts, followers, followings);
    }

    //this method extracts the data from the user activity JSON Object and stores them in an array list of
    //UserActivity models because here we have the last 20 pictures posted by the user
    private void setupUserActivity(JSONArray userActivityArray) throws JSONException {
        for(int i = 0; i < userActivityArray.length(); i++){
            JSONObject userActivity = userActivityArray.getJSONObject(i);

            String pictureLocation = "";
            if(userActivity.has("location") && userActivity.get("location") != null){
                pictureLocation = userActivity.getJSONObject("location").getString("name");
            }
            int comments = userActivity.getJSONObject("comments").getInt("count");
            String userName = userActivity.getJSONObject("user").getString("username");
            int likes = userActivity.getJSONObject("likes").getInt("count");
            String profilePicUrl = userActivity.getJSONObject("user").getString("profile_picture");
            String pictureUrl = userActivity.getJSONObject("images").getJSONObject("standard_resolution").getString("url");

            UserActivity userActivityObject = new UserActivity(pictureLocation, comments, userName, likes, profilePicUrl, pictureUrl);
            mPicturesList.add(userActivityObject);
        }
    }
}
