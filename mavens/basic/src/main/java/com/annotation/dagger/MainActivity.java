package com.annotation.dagger;

import javax.inject.Inject;

public class MainActivity {

    @Inject
    Apple apple;

    public static void main(String[] args) {
        // If compilation fails, see README.md

        MainActivity mainActivity = new MainActivity();

        DaggerMainComponent.create().inject(mainActivity);
        mainActivity.apple.say();
        
    }
}
