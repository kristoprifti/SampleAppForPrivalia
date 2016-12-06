package kristoprifti.me.sampleappforprivalia.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import kristoprifti.me.sampleappforprivalia.R;
import kristoprifti.me.sampleappforprivalia.models.UserActivity;
import kristoprifti.me.sampleappforprivalia.utils.RoundedImageView;

/**
 * Created by Kristi on 12/6/2016.
 */

public class UserDetailActivity extends BaseActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_detail_activity);
        setupToolbar(true);

        Intent intent = getIntent();
        UserActivity activity = (UserActivity) intent.getSerializableExtra(USER_ACTIVITY_TRANSFER);
        if(activity != null){
            TextView userName = (TextView) findViewById(R.id.userName);
            userName.setText(activity.getUserName());

            TextView activityLocation = (TextView) findViewById(R.id.userLocation);
            activityLocation.setText(activity.getPictureLocation());

            TextView nrOfComments = (TextView) findViewById(R.id.nrOfComments);
            nrOfComments.setText(Integer.toString(activity.getComments()));

            TextView nrOfLikes = (TextView) findViewById(R.id.nrOfLikesTextView);
            nrOfLikes.setText(Integer.toString(activity.getLikes()));

            RoundedImageView userProfilePic = (RoundedImageView) findViewById(R.id.userProfilePicture);
            Picasso.with(this).load(activity.getUserProfilePic())
                    .error(R.mipmap.ic_launcher)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(userProfilePic);

            ImageView userActivityPic = (ImageView) findViewById(R.id.userActivityPicture);
            Picasso.with(this).load(activity.getPictureUrl())
                    .error(R.mipmap.ic_launcher)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(userActivityPic);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
