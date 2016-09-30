package com.egeniq.castcolor;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.MediaRouteActionProvider;
import android.support.v7.app.MediaRouteButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.libraries.cast.companionlibrary.cast.VideoCastManager;
import com.thebluealliance.spectrum.SpectrumDialog;

public class MainActivity extends AppCompatActivity {

    private VideoCastManager mVideoCastManager;

    private Drawable mExternalRouteEnabledDrawable;

    private MediaRouteButton mLayoutMediaRouteButton;
    private MediaRouteActionProvider mMenuMediaRouteActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));

        mVideoCastManager = VideoCastManager.getInstance();
        mExternalRouteEnabledDrawable = ColorCastUtil.getMediaRouteButtonDrawable(MainActivity.this);

        //MediaRouterButton in layout
        mLayoutMediaRouteButton = (MediaRouteButton)findViewById(R.id.media_route_button);
        mVideoCastManager.addMediaRouterButton(mLayoutMediaRouteButton);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        //MediaRouterButton in action bar
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mVideoCastManager.addMediaRouterButton(menu, R.id.media_route_menu_item);

        MenuItem mediaRouteMenuItem = menu.findItem(R.id.media_route_menu_item);
        mMenuMediaRouteActionProvider = (MediaRouteActionProvider)
                MenuItemCompat.getActionProvider(mediaRouteMenuItem);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.color_menu_item) {
            showColorDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showColorDialog() {
        new SpectrumDialog.Builder(MainActivity.this)
                .setColors(R.array.demo_colors)
                .setOnColorSelectedListener(new SpectrumDialog.OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(boolean positiveResult, @ColorInt int color) {
                        if (positiveResult) {
                            tintMediaRouterButtons(color);
                        }
                    }
                }).build().show(getSupportFragmentManager(), "color_dialog");
    }

    private void tintMediaRouterButtons(@ColorInt int color) {
        DrawableCompat.setTint(mExternalRouteEnabledDrawable, color);

        mLayoutMediaRouteButton.setRemoteIndicatorDrawable(mExternalRouteEnabledDrawable);

        if (mMenuMediaRouteActionProvider.getMediaRouteButton() != null) {
            mMenuMediaRouteActionProvider.getMediaRouteButton().setRemoteIndicatorDrawable(mExternalRouteEnabledDrawable);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Start scan for cast devices
        mVideoCastManager.incrementUiCounter();
    }

    @Override
    protected void onPause() {
        super.onPause();

        //Stop scan
        mVideoCastManager.decrementUiCounter();
    }
}
