package com.testing.firebase.analytic.click;

import android.content.DialogInterface;

import com.testing.firebase.analytic.Analytics;

public abstract class AnalyticDismissListener extends AnalyticClickListener implements DialogInterface.OnDismissListener {

    public AnalyticDismissListener(Analytics analytics) {
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
