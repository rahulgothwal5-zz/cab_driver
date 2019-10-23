package com.oxycab.provider.ui.activity.notification;


import com.oxycab.provider.base.MvpPresenter;

public interface NotificationIPresenter<V extends NotificationIView> extends MvpPresenter<V> {
    void notifications();
}
