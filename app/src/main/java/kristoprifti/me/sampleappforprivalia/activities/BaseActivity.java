package kristoprifti.me.sampleappforprivalia.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import kristoprifti.me.sampleappforprivalia.R;

/**
 * Created by Kristi on 12/5/2016.
 */

class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    static final String USER_ACTIVITY_TRANSFER = "USER_ACTIVITY_TRANSFER";

    void setupToolbar(boolean enableHome){
        Log.d(TAG, "activateToolbar: starts");
        ActionBar actionBar = getSupportActionBar();
        if(actionBar == null){
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            if(toolbar != null){
                setSupportActionBar(toolbar);
                actionBar = getSupportActionBar();
            }
        }

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(enableHome);
        }
    }
}
