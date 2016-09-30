package com.egeniq.castcolor;

import android.app.Application;

import com.google.android.gms.cast.CastMediaControlIntent;
import com.google.android.libraries.cast.companionlibrary.cast.CastConfiguration;
import com.google.android.libraries.cast.companionlibrary.cast.VideoCastManager;

/**
 * Created by andrei on 30/09/16.
 */

public class CastColorApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //minimal setup
        CastConfiguration castConfiguration = new CastConfiguration.Builder(CastMediaControlIntent.DEFAULT_MEDIA_RECEIVER_APPLICATION_ID)
                .build();
        VideoCastManager.initialize(CastColorApplication.this, castConfiguration);
    }
}
