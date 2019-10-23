package com.oxycab.provider.base;

import android.app.Activity;

import com.oxycab.provider.MvpApplication;

public interface MvpPresenter<V extends MvpView> {

    Activity activity();

    MvpApplication appContext();

    void attachView(V mvpView);

    void detachView();

}
