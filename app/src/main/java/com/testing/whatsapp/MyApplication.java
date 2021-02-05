package com.testing.whatsapp;

import androidx.multidex.MultiDexApplication;

public class MyApplication extends MultiDexApplication {
    private String filters;

    public String getFilters() {
        return filters;
    }

    public void setFilters(String filters) {
        this.filters = filters;
    }
}
