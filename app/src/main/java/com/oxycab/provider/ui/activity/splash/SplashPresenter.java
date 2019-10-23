package com.oxycab.provider.ui.activity.splash;

import android.os.Handler;

import com.oxycab.provider.base.BasePresenter;

public class SplashPresenter<V extends SplashIView> extends BasePresenter<V> implements SplashIPresenter<V> {

    @Override
    public void handlerCall() {
        new Handler().postDelayed(() -> getMvpView().redirectHome(), 5000); //Timer is in ms here.
    }
}
