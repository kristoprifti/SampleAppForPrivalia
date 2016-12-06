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
 */

class GetInstagramData {
    private static final String TAG = "GetInstagramData";
    private OkHttpClient client = new OkHttpClient();

    private final OnDownloadComplete mCallback;

    interface OnDownloadComplete{
        void onDownloadComplete(ArrayList<String> data);
    }

    GetInstagramData(OnDownloadComplete callBack) {
        mCallback = callBack;
    }

    void runInSameThread(String profileInfoUrl, String recentActivityUrl) throws IOException {
        sendHttpRequest(profileInfoUrl, recentActivityUrl);
    }

    private void sendHttpRequest(String profileInfoUrl, String recentActivityUrl) throws IOException {
        Request requestProfileInfo = new Request.Builder()
                .url(profileInfoUrl)
                .build();
        final Request requestRecentActivityInfo = new Request.Builder()
                .url(recentActivityUrl)
                .build();

        client.newCall(requestProfileInfo).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: Profile info request failed with message " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String profileInfoData = response.body().string();
                client.newCall(requestRecentActivityInfo).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d(TAG, "onFailure: Recent activity request failed with message " + e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String activityInfoData = response.body().string();
                        ArrayList<String> userDetails = new ArrayList<>();
                        userDetails.add(profileInfoData);
                        userDetails.add(activityInfoData);
                        mCallback.onDownloadComplete(userDetails);
                    }
                });
            }
        });
    }
}
