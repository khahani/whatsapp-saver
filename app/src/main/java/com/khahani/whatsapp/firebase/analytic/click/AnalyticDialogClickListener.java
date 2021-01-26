package com.khahani.whatsapp.firebase.analytic.click;

import android.content.DialogInterface;

import com.khahani.whatsapp.firebase.analytic.Analytics;

public abstract class AnalyticDialogClickListener extends AnalyticClickListener
        implements DialogInterface.OnClickListener {

    protected AnalyticDialogClickListener(Analytics analytics) {
        super(analytics);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        trackClick();
        this.click(dialog, which);
    }

    @Override
    protected TrackClick.Type getType() {
        return TrackClick.Type.DialogButton;
    }

    protected abstract void click(DialogInterface dialog, int which);
}
