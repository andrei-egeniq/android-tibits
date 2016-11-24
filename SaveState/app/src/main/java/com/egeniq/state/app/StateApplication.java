package com.egeniq.state.app;

import android.app.Application;
import android.util.Log;

import java.io.IOException;

/**
 * Created by andrei on 24/11/16.
 */

public class StateApplication extends Application {

    private static final String TAG = StateApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            StateCache.read(StateApplication.this);
        } catch (IOException e) {
            //non important data is lost
            Log.e(TAG, e.getMessage(), e);
        }
    }

    @Override
    public void onTrimMemory(int level) {
        //user has moved on, app is no longer in foreground
        if(level >= TRIM_MEMORY_UI_HIDDEN) {
            try {
                StateCache.write(StateApplication.this);
            } catch (IOException e) {
                //non important data is lost
                Log.e(TAG, e.getMessage(), e);
            }
        }
    }
}
