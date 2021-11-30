package com.annotation.dagger;

import javax.inject.Inject;

public class Apple {

    @Inject
    Apple() {
        System.out.println("apple");
    }

    public void say() {
        System.out.println("apple say");
    }
}
