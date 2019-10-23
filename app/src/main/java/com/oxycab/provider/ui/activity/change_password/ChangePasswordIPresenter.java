package com.oxycab.provider.ui.activity.change_password;

import com.oxycab.provider.base.MvpPresenter;

import java.util.HashMap;

public interface ChangePasswordIPresenter<V extends ChangePasswordIView> extends MvpPresenter<V> {
    void changePassword(HashMap<String, Object> obj);
}
