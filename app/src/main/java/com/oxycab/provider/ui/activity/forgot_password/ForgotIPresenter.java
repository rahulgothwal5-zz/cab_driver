package com.oxycab.provider.ui.activity.forgot_password;

import com.oxycab.provider.base.MvpPresenter;

import java.util.HashMap;

public interface ForgotIPresenter<V extends ForgotIView> extends MvpPresenter<V> {
    void forgot(HashMap<String, Object> obj);
}
