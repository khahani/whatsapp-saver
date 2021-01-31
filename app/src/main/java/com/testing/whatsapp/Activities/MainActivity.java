package com.testing.whatsapp.Activities;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.khahani.usecase_firebase.analytic.LogEvent;
import com.khahani.usecase_firebase.analytic.click.AnalyticDialogClickListener;
import com.khahani.usecase_firebase.analytic.click.AnalyticDismissListener;
import com.khahani.usecase_firebase.analytic.click.TrackClick;
import com.khahani.usecase_firebase.analytic.screen.TrackScreen;
import com.testing.whatsapp.Adapters.PagerAdapter;
import com.testing.whatsapp.BuildConfig;
import com.testing.whatsapp.R;


public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    private static final String ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";
    private static final String ACTION_NOTIFICATION_LISTENER_SETTINGS = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS";

    private AlertDialog enableNotificationListenerAlertDialog;

    private Toolbar toolbar;

    private ViewPager viewPager;
    private TabLayout tabLayout;

    private PagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.App_Dark);
        }
        setContentView(R.layout.activity_main);
        initialize();
        setUpViewPager();
        setUpNotifService();
    }

    @Override
    protected TrackScreen initTrackScreen(LogEvent logger) {
        return new TrackScreen(logger, this);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private boolean isNotificationServiceEnabled() {
        String pkgName = getPackageName();
        final String flat = Settings.Secure.getString(getContentResolver(),
                ENABLED_NOTIFICATION_LISTENERS);
        if (!TextUtils.isEmpty(flat)) {
            final String[] names = flat.split(":");
            for (String name : names) {
                final ComponentName cn = ComponentName.unflattenFromString(name);
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.getPackageName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private AlertDialog buildNotificationServiceAlertDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(R.string.notification_listener_service);
        alertDialogBuilder.setMessage(R.string.notification_listener_service_explanation);
        alertDialogBuilder.setPositiveButton(R.string.yes, new AnalyticDialogClickListener(getAnalytic()) {
            @Override
            protected String getName() {
                return "Yes";
            }

            @Override
            protected String getId() {
                return "grant_access_to_notification_service";
            }

            @Override
            protected void click(DialogInterface dialog, int which) {
                startActivity(new Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS));
            }
        });
        alertDialogBuilder.setNegativeButton(R.string.no, new AnalyticDialogClickListener(getAnalytic()) {
            @Override
            protected String getName() {
                return "No";
            }

            @Override
            protected String getId() {
                return getString(R.string.revoke_access_to_notification_service);
            }

            @Override
            protected void click(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setOnDismissListener(new AnalyticDismissListener(getAnalytic()) {

            @Override
            protected String getName() {
                return "Dismiss";
            }

            @Override
            protected String getId() {
                return getString(R.string.revoke_access_to_notification_service);
            }

            @Override
            protected void dismiss(DialogInterface dialog) {
                dialog.dismiss();
            }
        });
        return (alertDialogBuilder.create());
    }

    private void setUpNotifService() {
        if (!isNotificationServiceEnabled()) {
            enableNotificationListenerAlertDialog = buildNotificationServiceAlertDialog();
            enableNotificationListenerAlertDialog.show();
        }
    }

    private void initialize() {

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewpager);

    }

    private void setUpViewPager() {

        adapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


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
            trackClick();
            shareApp();
        }
        return super.onOptionsItemSelected(item);
    }

    private void trackClick() {
        TrackClick trackClick = new TrackClick(getAnalytic(), "share_app", "share app", TrackClick.Type.ToolbarButton);
        trackClick.run();
    }

    private void shareApp() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            String shareMessage = getString(R.string.share_message);
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, getString(R.string.choose_one)));
        } catch (Exception e) {
            //e.toString();
        }
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
