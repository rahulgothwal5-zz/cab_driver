package com.oxycab.provider.ui.activity.reset_password;

import com.oxycab.provider.base.MvpPresenter;

import java.util.HashMap;

public interface ResetIPresenter<V extends ResetIView> extends MvpPresenter<V> {
    void reset(HashMap<String, Object> obj);
}
