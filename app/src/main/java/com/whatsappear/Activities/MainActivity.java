package com.whatsappear.Activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.khahani.usecase_firebase.analytic.LogEvent;
import com.khahani.usecase_firebase.analytic.screen.TrackScreen;
import com.whatsappear.Adapters.PagerAdapter;
import com.whatsappear.R;
import com.whatsappear.dialog.NotificationServiceAlertDialog;
import com.whatsappear.share.AppodealPrivacyPolicy;
import com.whatsappear.share.RateAndReview;
import com.whatsappear.share.ShareTheApp;


public class MainActivity extends BaseActivity {

    private NotificationServiceAlertDialog notificationServiceAlertDialog;

    public boolean isNotificationEnabled() {
        return notificationServiceAlertDialog.getStatus() == NotificationServiceAlertDialog.Status.Enabled;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();
        setupViewPager();
    }

    @Override
    protected TrackScreen initTrackScreen(LogEvent logger) {
        return new TrackScreen(logger, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        notificationServiceAlertDialog = new NotificationServiceAlertDialog(this);
        notificationServiceAlertDialog.run();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupViewPager() {
        ViewPager viewPager = findViewById(R.id.viewpager);
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.tablayout);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainscreen_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.share) {
            ShareTheApp shareTheApp = new ShareTheApp(this);
            shareTheApp.run();
        } else if (item.getItemId() == R.id.privacy) {
            AppodealPrivacyPolicy privacyPolicy = new AppodealPrivacyPolicy(this);
            privacyPolicy.run();
        } else if (item.getItemId() == R.id.rate) {
            RateAndReview rateAndReview = new RateAndReview(this);
            rateAndReview.run();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public String getScreenName() {
        return "Main screen";
    }

    @Override
    public String getClassName() {
        return MainActivity.class.getName();
    }
}
