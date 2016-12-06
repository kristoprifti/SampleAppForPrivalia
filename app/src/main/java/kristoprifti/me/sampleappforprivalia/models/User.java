package kristoprifti.me.sampleappforprivalia.models;

import java.io.Serializable;

/**
 * Created by Kristi on 12/5/2016.
 */

public class User implements Serializable {

    private String mUsername;
    private String mBio;
    private String mWebsite;
    private String mProfilePicture;
    private String mFullName;
    private int mPosts;
    private int mFollowers;
    private int mFollowing;

    public User(String username, String bio, String website, String profilePicture, String fullName, int posts, int followers, int following) {
        mUsername = username;
        mBio = bio;
        mWebsite = website;
        mProfilePicture = profilePicture;
        mFullName = fullName;
        mPosts = posts;
        mFollowers = followers;
        mFollowing = following;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getBio() {
        return mBio;
    }

    public void setBio(String bio) {
        mBio = bio;
    }

    public String getWebsite() {
        return mWebsite;
    }

    public void setWebsite(String website) {
        mWebsite = website;
    }

    public String getProfilePicture() {
        return mProfilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        mProfilePicture = profilePicture;
    }

    public String getFullName() {
        return mFullName;
    }

    public void setFullName(String fullName) {
        mFullName = fullName;
    }

    public int getPosts() {
        return mPosts;
    }

    public void setPosts(int posts) {
        mPosts = posts;
    }

    public int getFollowers() {
        return mFollowers;
    }

    public void setFollowers(int followers) {
        mFollowers = followers;
    }

    public int getFollowing() {
        return mFollowing;
    }

    public void setFollowing(int following) {
        mFollowing = following;
    }

    @Override
    public String toString() {
        return "User{" +
                "mUsername='" + mUsername + '\'' +
                ", mBio='" + mBio + '\'' +
                ", mWebsite='" + mWebsite + '\'' +
                ", mProfilePicture='" + mProfilePicture + '\'' +
                ", mFullName='" + mFullName + '\'' +
                ", mPosts=" + mPosts +
                ", mFollowers=" + mFollowers +
                ", mFollowing=" + mFollowing +
                '}';
    }
}
