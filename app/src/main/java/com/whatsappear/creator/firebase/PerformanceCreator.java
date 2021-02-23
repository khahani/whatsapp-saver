package com.whatsappear.creator.firebase;

import com.khahani.usecase_firebase.BuildConfig;
import com.khahani.usecase_firebase.Creator;
import com.khahani.usecase_firebase.performance.NullPerformance;
import com.khahani.usecase_firebase.performance.Performance;

public class PerformanceCreator extends Creator<Performance> {
    @Override
    public Performance factoryMethod() {
        if (BuildConfig.DEBUG) {
            return new NullPerformance();
        }
        try {
            return (Performance) Class.forName("com.khahani.firebase.performance.PerformanceImpl")
                    .newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return new NullPerformance();
        }
    }
}
