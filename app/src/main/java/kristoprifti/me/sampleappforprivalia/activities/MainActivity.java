package kristoprifti.me.sampleappforprivalia.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import kristoprifti.me.sampleappforprivalia.R;
import kristoprifti.me.sampleappforprivalia.adapters.InstagramRecyclerViewAdapter;
import kristoprifti.me.sampleappforprivalia.adapters.RecyclerItemClickListener;
import kristoprifti.me.sampleappforprivalia.models.User;
import kristoprifti.me.sampleappforprivalia.models.UserActivity;
import kristoprifti.me.sampleappforprivalia.network.GetJSONInstagramData;
import kristoprifti.me.sampleappforprivalia.utils.InternetConnectionCheck;
import kristoprifti.me.sampleappforprivalia.utils.RoundedImageView;

// we implement OnDataAvailable to override the onDataAvailable method
// we implement RecyclerItemClikListener to override the method onItemClick for each recyclerview item
public class MainActivity extends BaseActivity implements GetJSONInstagramData.OnDataAvailable,
        RecyclerItemClickListener.OnRecyclerClickListener {

    private static final String TAG = "MainActivity";

    //This information is confidential as it includes the token of my INSTAGRAM account. For purpose of testing im letting it here...Please dont share it :)
    private static final String USER_TOKEN = "199917231.4a7c055.1ae97dee8190479495a2ec857aa8e936";
    private static final String USER_INFO = "https://api.instagram.com/v1/users/self/media/recent/?access_token=";
    private static final String USER_ACTIVITY = "https://api.instagram.com/v1/users/self/?access_token=";

    private InstagramRecyclerViewAdapter mInstagramRecyclerViewAdapter;
    private TextView mPosts, mFollowers, mFollowing, mUserName, mDescription;
    private RoundedImageView mProfilePicture;
    private ConstraintLayout mConstraintLayout;
    private ProgressBar mProgressBar;

    private InternetConnectionCheck internetConnectionCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar(false);

        mPosts = (TextView) findViewById(R.id.nrOfPostsTextView);
        mFollowers = (TextView) findViewById(R.id.nrOfFollowersTextView);
        mFollowing = (TextView) findViewById(R.id.nrOfFollowingsTextView);
        mUserName = (TextView) findViewById(R.id.userName);
        mDescription = (TextView) findViewById(R.id.userDescription);
        mProfilePicture = (RoundedImageView) findViewById(R.id.userProfilePicture);
        mConstraintLayout = (ConstraintLayout) findViewById(R.id.content_main);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        internetConnectionCheck = new InternetConnectionCheck();

        //initialize the recyclerview and set a staggered grid layout manager to it
        //with two columns and a vertical position
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, this));

        //initialize the adapter and assign the empty arraylist to it
        mInstagramRecyclerViewAdapter = new InstagramRecyclerViewAdapter(this, new ArrayList<UserActivity>());
        recyclerView.setAdapter(mInstagramRecyclerViewAdapter);
        recyclerView.setNestedScrollingEnabled(true);

        validateInternetConnection();

        Log.d(TAG, "onCreate: ends");
    }

    private void validateInternetConnection(){
        try {
            if(internetConnectionCheck.hasActiveInternetConnection()){
                runGetRequest();
            } else {
                mConstraintLayout.setVisibility(View.INVISIBLE);
                mProgressBar.setVisibility(View.INVISIBLE);
                Snackbar snackbar = Snackbar
                        .make(mConstraintLayout, "No Internet Connection!", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                validateInternetConnection();
                            }
                        });
                snackbar.show();
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    private void runGetRequest(){
        //display progress bar to the user until the data is fetched and processed
        mConstraintLayout.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);

        //create an instance of GetJSONInstagramData class and pass the two URLs to its constructor
        GetJSONInstagramData getJSONInstagramData = new GetJSONInstagramData(this,
                USER_INFO.concat(USER_TOKEN),
                USER_ACTIVITY.concat(USER_TOKEN));

        //call the method to send the get request and get the data
        try {
            getJSONInstagramData.sendGetRequest();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: starts");
        super.onResume();
        Log.d(TAG, "onResume: ends");
    }

    //method that is called when the data are processed in the GetInstagramJSONData class
    @Override
    public void onDataAvailable(final List<UserActivity> userActivities, final User userInfo) {
        Log.d(TAG, "onDataAvailable: starts");

        //run on ui thread because only the main activity instance can modify the views and this
        //method is using the interface of the GetInstagramJSONData class
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                populateUserInfoData(userInfo);
                mInstagramRecyclerViewAdapter.loadNewData(userActivities);
                mConstraintLayout.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        });

        Log.d(TAG, "onDataAvailable: ends");
    }

    //this method puts the results in the view controls such as TextViews and ImageViews
    @SuppressLint("SetTextI18n")
    private void populateUserInfoData(User userInfo) {
        if(getSupportActionBar() != null)
            getSupportActionBar().setTitle(userInfo.getUsername());

        mPosts.setText(Integer.toString(userInfo.getPosts()));
        mFollowing.setText(Integer.toString(userInfo.getFollowing()));
        mFollowers.setText(Integer.toString(userInfo.getFollowers()));
        mUserName.setText(userInfo.getFullName());
        mDescription.setText(userInfo.getBio());
        if(!userInfo.getWebsite().equals(""))
            mDescription.append("\n" + userInfo.getWebsite());
        Picasso.with(this).load(userInfo.getProfilePicture())
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher)
                .into(mProfilePicture);
    }

    //here i make use of the interface from RecyclerViewItemClickListener in order to handle the
    //item click on each item of the recyclerview and start a new UserDetailActivity
    //i pass the serializable model of UserActivity to this Activity
    @Override
    public void onItemClick(View view, int position) {
        Log.d(TAG, "onItemClick: starts");
        Intent intent = new Intent(this, UserDetailActivity.class);
        intent.putExtra(USER_ACTIVITY_TRANSFER, mInstagramRecyclerViewAdapter.getUserActivity(position));
        startActivity(intent);
    }
}
