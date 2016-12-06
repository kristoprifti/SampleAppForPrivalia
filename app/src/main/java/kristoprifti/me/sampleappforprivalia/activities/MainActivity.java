package kristoprifti.me.sampleappforprivalia.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
import kristoprifti.me.sampleappforprivalia.utils.RoundedImageView;

public class MainActivity extends BaseActivity implements GetJSONInstagramData.OnDataAvailable,
        RecyclerItemClickListener.OnRecyclerClickListener {

    private static final String TAG = "MainActivity";
    private InstagramRecyclerViewAdapter mInstagramRecyclerViewAdapter;
    private TextView mPosts, mFollowers, mFollowing, mUserName, mDescription;
    private RoundedImageView mProfilePicture;

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

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, this));

        mInstagramRecyclerViewAdapter = new InstagramRecyclerViewAdapter(this, new ArrayList<UserActivity>());
        recyclerView.setAdapter(mInstagramRecyclerViewAdapter);

        Log.d(TAG, "onCreate: ends");
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: starts");
        super.onResume();

        GetJSONInstagramData getJSONInstagramData = new GetJSONInstagramData(this,
                "https://api.instagram.com/v1/users/self/media/recent/?access_token=199917231.4a7c055.1ae97dee8190479495a2ec857aa8e936",
                "https://api.instagram.com/v1/users/self/?access_token=199917231.4a7c055.1ae97dee8190479495a2ec857aa8e936");

        try {
            getJSONInstagramData.sendGetRequest();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "onResume: ends");
    }

    @Override
    public void onDataAvailable(final List<UserActivity> userActivities, final User userInfo) {
        Log.d(TAG, "onDataAvailable: starts");

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                populateUserInfoData(userInfo);
                mInstagramRecyclerViewAdapter.loadNewData(userActivities);
            }
        });

        Log.d(TAG, "onDataAvailable: ends");
    }

    @SuppressLint("SetTextI18n")
    private void populateUserInfoData(User userInfo) {
        if(getSupportActionBar() != null)
            getSupportActionBar().setTitle(userInfo.getUsername());

        mPosts.setText(Integer.toString(userInfo.getPosts()));
        mFollowing.setText(Integer.toString(userInfo.getFollowing()));
        mFollowers.setText(Integer.toString(userInfo.getFollowers()));
        mUserName.setText(userInfo.getFullName());
        mDescription.setText(userInfo.getBio() + "\n" + userInfo.getWebsite());
        Picasso.with(this).load(userInfo.getProfilePicture())
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher)
                .into(mProfilePicture);
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.d(TAG, "onItemClick: starts");
        Intent intent = new Intent(this, UserDetailActivity.class);
        intent.putExtra(USER_ACTIVITY_TRANSFER, mInstagramRecyclerViewAdapter.getUserActivity(position));
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view, int position) {
        Log.d(TAG, "onItemLongClick: starts");
        Toast.makeText(MainActivity.this, "Long tap at position " + position, Toast.LENGTH_SHORT).show();
    }
}
