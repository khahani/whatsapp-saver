package com.khahani.usecase_firebase.analytic.click;

import android.content.DialogInterface;

import com.khahani.usecase_firebase.analytic.AnalyticsBase;

public abstract class AnalyticDismissListener extends AnalyticClickListener implements DialogInterface.OnDismissListener {

    public AnalyticDismissListener(AnalyticsBase analytics) {
        super(analytics);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        trackClick();
        dismiss(dialog);
    }

    @Override
    protected TrackClick.Type getType() {
        return TrackClick.Type.DialogDismiss;
    }

    protected abstract void dismiss(DialogInterface dialog);
}
