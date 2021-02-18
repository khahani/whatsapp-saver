package com.khahani.appodeal;

import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.appodeal.ads.Appodeal;
import com.appodeal.ads.UserSettings;
import com.appodeal.ads.utils.Log;
import com.explorestack.consent.Consent;
import com.explorestack.consent.ConsentForm;
import com.explorestack.consent.ConsentFormListener;
import com.explorestack.consent.ConsentInfoUpdateListener;
import com.explorestack.consent.ConsentManager;
import com.explorestack.consent.exception.ConsentManagerException;
import com.khahani.usecase_firebase.OnCompletionListener;

public class AppodealInitializer implements Runnable {
    private final boolean DEFAULT_CONSENT = true;
    @Nullable
    private ConsentForm consentForm;
    private final Activity activity;
    private boolean hasConsent;
    private OnCompletionListener completionListener;
    private String appodealAppKey;

    public String getAppodealAppKey() {
        return appodealAppKey;
    }

    public boolean hasConsent() {
        return hasConsent;
    }

    public AppodealInitializer(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void run() {
        try {
            enableLogs();
            resolveUserConsent();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void enableLogs() {
        if (BuildConfig.DEBUG) {
            Appodeal.setTesting(true);
            Appodeal.setLogLevel(Log.LogLevel.verbose);
        } else {
            Appodeal.setTesting(false);
            Appodeal.setLogLevel(Log.LogLevel.none);
        }
    }

    // Requesting Consent from European Users using Stack ConsentManager (https://wiki.appodeal.com/en/android/consent-manager).
    private void resolveUserConsent() {
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
                                hasConsent = DEFAULT_CONSENT;
                            } else {
                                hasConsent = consent.getStatus() == Consent.Status.PERSONALIZED;
                            }
                            consentIsReady();
                        }
                    }

                    @Override
                    public void onFailedToUpdateConsentInfo(ConsentManagerException e) {
                        hasConsent = DEFAULT_CONSENT;
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
                            hasConsent = DEFAULT_CONSENT;
                            consentIsReady();
                        }

                        @Override
                        public void onConsentFormOpened() {
                            //ignore
                        }

                        @Override
                        public void onConsentFormClosed(Consent consent) {
                            hasConsent = consent.getStatus() == Consent.Status.PERSONALIZED;
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

    // Start our main activity with resolved Consent value
    private void consentIsReady() {
        //khahani: determine the correct one
        Appodeal.setUserAge(25);
        Appodeal.setUserGender(UserSettings.Gender.MALE);
        Appodeal.initialize(activity, appodealAppKey, Appodeal.NONE, this.hasConsent);

        if (completionListener != null)
            completionListener.onCompleted(this.hasConsent);
    }

    public void setCompletionListener(OnCompletionListener completionListener) {
        this.completionListener = completionListener;
    }

    public void showUpdateConsentForm() {
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
                        }

                        @Override
                        public void onConsentFormOpened() {
                            //ignore
                        }

                        @Override
                        public void onConsentFormClosed(Consent consent) {
                            hasConsent =
                                    consent.getStatus() == Consent.Status.PERSONALIZED &&
                                            consent.getStatus() != Consent.Status.NON_PERSONALIZED;
                            // Update Appodeal SDK Consent value with resolved Consent value
                            Appodeal.updateConsent(hasConsent);
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
}
