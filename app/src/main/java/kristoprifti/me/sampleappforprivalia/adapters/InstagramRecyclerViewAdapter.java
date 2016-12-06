package kristoprifti.me.sampleappforprivalia.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import kristoprifti.me.sampleappforprivalia.R;
import kristoprifti.me.sampleappforprivalia.models.UserActivity;

/**
 * Created by Kristi on 12/5/2016.
 * this is the adapter that is used from the recycler view to adapt the data into different view holders
 * viewholders are created inside this adapter class
 * adapter takes the data from the datasource which is the arraylist and packages the data into viewholders
 * these viewholders are sent to the recyclerview
 */

//extending the viewholder to tell to the adapter which viewholder to use
public class InstagramRecyclerViewAdapter extends RecyclerView.Adapter<InstagramRecyclerViewAdapter.InstagramImageViewHolder>{
    private static final String TAG = "InstagramRecyclerView";

    private List<UserActivity> mPicturesList;
    private Context mContext;

    //constructor of the adapter
    public InstagramRecyclerViewAdapter(Context context, List<UserActivity> picturesList) {
        mContext = context;
        mPicturesList = picturesList;
    }

    //this method creates the viewholder and attaches the layout to it
    @Override
    public InstagramImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: new view requested");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_activity_row, parent, false);
        return new InstagramImageViewHolder(view);
    }

    //this method is called everytime the adapter calls the viewholder and here we have to populate
    //the fields with current data for each iteration
    @Override
    public void onBindViewHolder(InstagramImageViewHolder holder, int position) {
        //since we are displaying only one image in the imageview here we check for the image
        //if image is empty we just display a random image in our case the app icon
        if(mPicturesList == null || mPicturesList.size() == 0){
            holder.thumbnail.setImageResource(R.mipmap.ic_launcher);
        } else {
            UserActivity userActivity = mPicturesList.get(position);
            Log.d(TAG, "onBindViewHolder: " + userActivity.getPictureLocation() + " --> " + position);
            Picasso.with(mContext).load(userActivity.getPictureUrl())
                    .error(R.mipmap.ic_launcher)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(holder.thumbnail);
        }
    }

    //this method returns the number of the items in the recyclerview
    @Override
    public int getItemCount() {
        return ((mPicturesList != null) && (mPicturesList.size() != 0) ? mPicturesList.size() : 1);
    }

    //this method is called by MainActivity to load more data when the data is available from the server
    public void loadNewData(List<UserActivity> newPhotos){
        mPicturesList = newPhotos;
        notifyDataSetChanged();
    }

    //this method returns the current model of UserActivity that the user has pressed
    //we need this model to pass it to the UserDetail Activity to display all the info it holds
    public UserActivity getUserActivity(int position){
        return ((mPicturesList != null) && (mPicturesList.size() != 0) ? mPicturesList.get(position) : null);
    }

    //new static class for the viewholder which is extending the ViewHolder
    static class InstagramImageViewHolder extends RecyclerView.ViewHolder{
        private static final String TAG = "InstagramImageViewHolder";
        ImageView thumbnail;

        //setting up the viewholder. each view that is inside the viewholder will be declared here
        InstagramImageViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "IGImageViewHolder:start");
            this.thumbnail = (ImageView) itemView.findViewById(R.id.userActivityPicture);
        }
    }
}
