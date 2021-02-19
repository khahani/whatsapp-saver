package com.testing.whatsapp.dialog;

import android.app.Activity;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.appcompat.app.AlertDialog;

import com.khahani.usecase_firebase.analytic.click.AnalyticDialogClickListener;
import com.testing.whatsapp.Activities.BaseActivity;
import com.testing.whatsapp.Activities.BaseActivityValidator;
import com.testing.whatsapp.R;

public class NotificationServiceAlertDialog implements Runnable {
    private static final String ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";
    private static final String ACTION_NOTIFICATION_LISTENER_SETTINGS = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS";
    private final Activity activity;

    public NotificationServiceAlertDialog(Activity activity) {
        new BaseActivityValidator(activity).run();
        this.activity = activity;
    }

    private void setUpNotificationService() {
        if (!isNotificationServiceEnabled())
            build().show();
    }

    private AlertDialog build() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setTitle(R.string.notification_listener_service);
        alertDialogBuilder.setMessage(R.string.notification_listener_service_explanation);
        alertDialogBuilder.setPositiveButton(R.string.allow, new AnalyticDialogClickListener(((BaseActivity) activity).getAnalytic()) {
            @Override
            protected String getName() {
                return "Allow";
            }

            @Override
            protected String getId() {
                return activity.getString(R.string.grant_access_to_notif);
            }

            @Override
            protected void click(DialogInterface dialog, int which) {
                activity.startActivity(new Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS));
            }
        });
        alertDialogBuilder.setCancelable(false);
        return (alertDialogBuilder.create());
    }

    private boolean isNotificationServiceEnabled() {
        String pkgName = activity.getPackageName();
        final String flat = Settings.Secure.getString(activity.getContentResolver(),
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

    public Status getStatus() {
        if (isNotificationServiceEnabled())
            return Status.Enabled;
        else
            return Status.Disabled;
    }

    public enum Status {
        Enabled,
        Disabled
    }

    @Override
    public void run() {
        setUpNotificationService();
    }
}