package com.khahani.appodeal;

import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.appodeal.ads.Appodeal;
import com.explorestack.consent.Consent;
import com.explorestack.consent.ConsentForm;
import com.explorestack.consent.ConsentFormListener;
import com.explorestack.consent.ConsentInfoUpdateListener;
import com.explorestack.consent.ConsentManager;
import com.explorestack.consent.exception.ConsentManagerException;
import com.khahani.usecase_firebase.OnCompletionListener;

public class AppodealInitializer implements Runnable {
    @Nullable
    private ConsentForm consentForm;
    private final Activity activity;
    private boolean hasConsent;
    private OnCompletionListener completionListener;
    private String appodealAppKey;

    public boolean hasConsent() {
        return hasConsent;
    }

    public AppodealInitializer(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void run() {
        try {
            resolveUserConsent();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Requesting Consent from European Users using Stack ConsentManager (https://wiki.appodeal.com/en/android/consent-manager).
    private void resolveUserConsent() {
        // Note: YOU MUST SPECIFY YOUR APPODEAL SDK KET HERE
        appodealAppKey = activity.getString(R.string.appodeal_key);
        ConsentManager consentManager = ConsentManager.getInstance(activity);
        // Requesting Consent info update
        consentManager.requestConsentInfoUpdate(
                appodealAppKey,
                new ConsentInfoUpdateListener() {
                    @Override
                    public void onConsentInfoUpdated(Consent consent) {
                        Consent.ShouldShow consentShouldShow =
                                consentManager.shouldShowConsentDialog();
                        // If ConsentManager return Consent.ShouldShow.TRUE, than we should show consent form
                        if (consentShouldShow == Consent.ShouldShow.TRUE) {
                            showConsentForm();
                        } else {
                            if (consent.getStatus() == Consent.Status.UNKNOWN) {
                                // Start our main activity with default Consent value
                                consentIsReady();
                            } else {
                                boolean hasConsent = consent.getStatus() == Consent.Status.PERSONALIZED;
                                // Start our main activity with resolved Consent value
                                consentIsReady();
                            }
                        }
                    }

                    @Override
                    public void onFailedToUpdateConsentInfo(ConsentManagerException e) {
                        // Start our main activity with default Consent value
                        consentIsReady();
                    }
                });
    }

    // Displaying ConsentManger Consent request form
    private void showConsentForm() {
        if (consentForm == null) {
            consentForm = new ConsentForm.Builder(activity)
                    .withListener(new ConsentFormListener() {
                        @Override
                        public void onConsentFormLoaded() {
                            // Show ConsentManager Consent request form
                            consentForm.showAsActivity();
                        }

                        @Override
                        public void onConsentFormError(ConsentManagerException error) {
                            if (BuildConfig.DEBUG) {
                                Toast.makeText(
                                        activity,
                                        "Consent form error: " + error.getReason(),
                                        Toast.LENGTH_SHORT
                                ).show();
                            }
                            // Start our main activity with default Consent value
                            consentIsReady();
                        }

                        @Override
                        public void onConsentFormOpened() {
                            //ignore
                        }

                        @Override
                        public void onConsentFormClosed(Consent consent) {
                            boolean hasConsent = consent.getStatus() == Consent.Status.PERSONALIZED;
                            // Start our main activity with resolved Consent value
                            consentIsReady();
                        }
                    }).build();
        }
        // If Consent request form is already loaded, then we can display it, otherwise, we should load it first
        if (consentForm.isLoaded()) {
            consentForm.showAsActivity();
        } else {
            consentForm.load();
        }
    }

    public void showUpdateConsentForm() {
        showConsentForm();
    }


    // Start our main activity with resolved Consent value
    private void consentIsReady() {
        Consent.Status consentStatus = ConsentManager.getInstance(activity).getConsentStatus();
        this.hasConsent = consentStatus == Consent.Status.PERSONALIZED
                || consentStatus == Consent.Status.PARTLY_PERSONALIZED;

        initSdk();

        if (completionListener != null)
            completionListener.onCompleted(this.hasConsent);
    }

    private void initSdk() {
        Appodeal.initialize(activity, appodealAppKey, Appodeal.BANNER_BOTTOM | Appodeal.INTERSTITIAL, this.hasConsent);
    }

    public void setCompletionListener(OnCompletionListener completionListener) {
        this.completionListener = completionListener;
    }
}
