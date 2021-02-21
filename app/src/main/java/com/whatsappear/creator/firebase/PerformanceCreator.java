package com.whatsappear.creator.firebase;

import com.khahani.firebase.performance.PerformanceImpl;
import com.khahani.usecase_firebase.Creator;
import com.khahani.usecase_firebase.performance.Performance;

public class PerformanceCreator extends Creator<Performance> {
    @Override
    public Performance factoryMethod() {
        //return new NullPerformance();
        return new PerformanceImpl();
    }
}
