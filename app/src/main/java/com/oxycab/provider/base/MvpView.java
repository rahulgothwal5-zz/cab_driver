package com.oxycab.provider.base;

import android.app.Activity;

public interface MvpView {
    Activity activity();
    void showLoading();
    void hideLoading();
    void onError(Throwable e);
}
