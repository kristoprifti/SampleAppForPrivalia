package kristoprifti.me.sampleappforprivalia.adapters;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Kristi on 12/5/2016.
 * this class extends the SimpleOnItemTouchListener
 * https://developer.android.com/reference/android/support/v7/widget/RecyclerView.SimpleOnItemTouchListener.html
 * http://www.littlerobots.nl/blog/Handle-Android-RecyclerView-Clicks/
 */

public class RecyclerItemClickListener extends RecyclerView.SimpleOnItemTouchListener{
    private static final String TAG = "RecyclerItemClickListen";

    public interface OnRecyclerClickListener{
        void onItemClick(View view, int position);
    }

    private final OnRecyclerClickListener mListener;
    private final GestureDetectorCompat mGestureDetector;

    //constructor that gets context, recyclerview and the interface variable
    public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnRecyclerClickListener listener) {
        mListener = listener;
        //initialize the gesture detector and determine what action to take when the touch happens on the recyclerview
        mGestureDetector = new GestureDetectorCompat(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                //for every single tap event we get the child in the recyclerview and call the interface to
                //take this action in the Main Actiity class
                Log.d(TAG, "onSingleTapUp: starts");
                View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if(childView != null && mListener != null){
                    Log.d(TAG, "onSingleTapUp: calling listener.onItemClick");
                    mListener.onItemClick(childView, recyclerView.getChildAdapterPosition(childView));
                }
                return true;
            }
        });
    }

    //this method handles the interception of the touch event. it checks if the touch is intercepted
    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        Log.d(TAG, "onInterceptTouchEvent: starts");
        if(mGestureDetector != null){
            boolean result = mGestureDetector.onTouchEvent(e);
            Log.d(TAG, "onInterceptTouchEvent: returned: " + result);
            return result;
        } else {
            Log.d(TAG, "onInterceptTouchEvent: returned: false");
            return false;
        }
    }
}
