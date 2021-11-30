package com.annotation.dagger;

@dagger.Module
public class MainModule {
    //第二步 使用Provider 注解 实例化对象
    @dagger.Provides
    Apple providerApple() {
        return new Apple();
    }
}
