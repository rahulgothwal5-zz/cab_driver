package com.oxycab.provider.ui.activity.splash;

import com.oxycab.provider.base.MvpPresenter;

public interface SplashIPresenter<V extends SplashIView> extends MvpPresenter<V> {
    void handlerCall();
}
