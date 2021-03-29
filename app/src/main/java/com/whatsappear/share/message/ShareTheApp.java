package com.whatsappear.share.message;

import android.app.Activity;
import android.content.Intent;

import com.khahani.usecase_firebase.analytic.click.TrackClick;
import com.whatsappear.Activities.BaseActivity;
import com.whatsappear.R;

public abstract class ShareTheApp implements Runnable {
    protected final Activity activity;

    public ShareTheApp(Activity activity) {
        this.activity = activity;
    }

    private void trackClick() {
        TrackClick trackClick = new TrackClick(((BaseActivity) activity).getAnalytic(),
                "share_app", "share app", TrackClick.Type.ToolbarButton);
        trackClick.run();
    }

    private void shareApp() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, activity.getString(R.string.app_name));
            String shareMessage = activity.getString(R.string.share_message);
            shareMessage += getMarketSiteUrl() + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            activity.startActivity(Intent.createChooser(shareIntent, activity.getString(R.string.choose_one)));
        } catch (Exception e) {
            //e.toString();
        }
    }

    protected abstract String getMarketSiteUrl();

    @Override
    public void run() {
        trackClick();
        shareApp();
    }
}
