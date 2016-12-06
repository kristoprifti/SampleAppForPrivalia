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
 */

public class InstagramRecyclerViewAdapter extends RecyclerView.Adapter<InstagramRecyclerViewAdapter.InstagramImageViewHolder>{
    private static final String TAG = "InstagramRecyclerView";

    private List<UserActivity> mPicturesList;
    private Context mContext;

    public InstagramRecyclerViewAdapter(Context context, List<UserActivity> picturesList) {
        mContext = context;
        mPicturesList = picturesList;
    }

    @Override
    public InstagramImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: new view requested");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_activity_row, parent, false);
        return new InstagramImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InstagramImageViewHolder holder, int position) {
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

    @Override
    public int getItemCount() {
        return ((mPicturesList != null) && (mPicturesList.size() != 0) ? mPicturesList.size() : 1);
    }

    public void loadNewData(List<UserActivity> newPhotos){
        mPicturesList = newPhotos;
        notifyDataSetChanged();

    }

    public UserActivity getUserActivity(int position){
        return ((mPicturesList != null) && (mPicturesList.size() != 0) ? mPicturesList.get(position) : null);
    }

    static class InstagramImageViewHolder extends RecyclerView.ViewHolder{
        private static final String TAG = "InstagramImageViewHolder";
        ImageView thumbnail;

        InstagramImageViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "IGImageViewHolder:start");
            this.thumbnail = (ImageView) itemView.findViewById(R.id.userActivityPicture);
        }
    }
}
