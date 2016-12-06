package kristoprifti.me.sampleappforprivalia.network;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Kristi on 12/5/2016.
 * This class downloads the raw data from any url that we pass by using the
 * synchronous OKHTTP library
 *
 */

class GetInstagramData {
    private static final String TAG = "GetInstagramData";
    //new OKHttp client object
    private OkHttpClient client = new OkHttpClient();

    //variable that stores the callback
    private final OnDownloadComplete mCallback;

    //callback object has to implement the interface in order to make sure it calls the OnDownloadComplete listener
    //we pass the data retrieved from the HTTP in the method of the interface
    interface OnDownloadComplete{
        void onDownloadComplete(ArrayList<String> data);
    }

    //constructor with a callback as an argument from the class that calls it
    GetInstagramData(OnDownloadComplete callBack) {
        mCallback = callBack;
    }

    //this function gets the endpoint url as an argument and processes them by sending an http call
    //to the endpoint. two different requests are created and added in a queue in order to be
    //called synchronously and the results are two json objects retrieved and added to an arraylist in
    //the form of a string
    void sendHttpRequest(String profileInfoUrl, String recentActivityUrl) throws IOException {
        //first request for the profile info
        Request requestProfileInfo = new Request.Builder()
                .url(profileInfoUrl)
                .build();

        //second request for the activity info
        final Request requestRecentActivityInfo = new Request.Builder()
                .url(recentActivityUrl)
                .build();

        //new call initialized and callbacks are implemented to handle the callback when its received
        client.newCall(requestProfileInfo).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: Profile info request failed with message " + e.getMessage());
            }

            //if the first request is successful this means that the endpoint is active and we can also do the other call
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String profileInfoData = response.body().string();
                client.newCall(requestRecentActivityInfo).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d(TAG, "onFailure: Recent activity request failed with message " + e.getMessage());
                    }

                    //after two successful callbacks we add the result to the array list and then
                    //using the interface method we
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String activityInfoData = response.body().string();
                        ArrayList<String> userDetails = new ArrayList<>();
                        userDetails.add(profileInfoData);
                        userDetails.add(activityInfoData);

                        //check for a valid callback before notifying the other class that uses the interface
                        //that the download is complete and passing the result to it
                        if(mCallback != null)
                            mCallback.onDownloadComplete(userDetails);
                    }
                });
            }
        });
    }
}
