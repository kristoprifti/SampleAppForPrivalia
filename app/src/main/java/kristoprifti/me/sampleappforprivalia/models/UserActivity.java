package kristoprifti.me.sampleappforprivalia.models;

import java.io.Serializable;

/**
 * Created by Kristi on 12/5/2016.
 * this is a serializable model for the UserActivity
 * it contains of variables for each user activity information and also getters and setters
 * it also contains a constructor which is used to initialize the model
 */

public class UserActivity implements Serializable{

    private String mPictureLocation;
    private int mComments;
    private String mUserName;
    private int mLikes;
    private String mUserProfilePic;
    private String mPictureUrl;

    public UserActivity(String pictureLocation, int comments, String userName, int likes, String userProfilePic, String pictureUrl) {
        mPictureLocation = pictureLocation;
        mComments = comments;
        mUserName = userName;
        mLikes = likes;
        mUserProfilePic = userProfilePic;
        mPictureUrl = pictureUrl;
    }

    public String getPictureLocation() {
        return mPictureLocation;
    }

    public void setPictureLocation(String pictureLocation) {
        mPictureLocation = pictureLocation;
    }

    public int getComments() {
        return mComments;
    }

    public void setComments(int comments) {
        mComments = comments;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public int getLikes() {
        return mLikes;
    }

    public void setLikes(int likes) {
        mLikes = likes;
    }

    public String getUserProfilePic() {
        return mUserProfilePic;
    }

    public void setUserProfilePic(String userProfilePic) {
        mUserProfilePic = userProfilePic;
    }

    public String getPictureUrl() {
        return mPictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        mPictureUrl = pictureUrl;
    }

    @Override
    public String toString() {
        return "UserActivity{" +
                "mPictureLocation='" + mPictureLocation + '\'' +
                ", mComments=" + mComments +
                ", mUserName='" + mUserName + '\'' +
                ", mLikes=" + mLikes +
                ", mUserProfilePic='" + mUserProfilePic + '\'' +
                ", mPictureUrl='" + mPictureUrl + '\'' +
                '}';
    }
}
