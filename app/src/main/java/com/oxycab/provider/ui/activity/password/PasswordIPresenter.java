package com.oxycab.provider.ui.activity.password;

import com.oxycab.provider.base.MvpPresenter;

import java.util.HashMap;

public interface PasswordIPresenter<V extends PasswordIView> extends MvpPresenter<V> {
    void login(HashMap<String, Object> obj);
}
