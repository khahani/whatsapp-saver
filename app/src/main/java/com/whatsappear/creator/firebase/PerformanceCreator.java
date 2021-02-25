package com.whatsappear.creator.firebase;

import com.khahani.usecase_firebase.performance.NullPerformance;
import com.khahani.usecase_firebase.performance.Performance;

public class PerformanceCreator extends ReleaseModuleActivator<Performance> {

    @Override
    protected Performance getNullModule() {
        return new NullPerformance();
    }

    @Override
    protected Performance getReleaseModule() {
        try {
            return (Performance) Class.forName("com.khahani.firebase.performance.PerformanceImpl")
                    .newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return new NullPerformance();
        }
    }
}
