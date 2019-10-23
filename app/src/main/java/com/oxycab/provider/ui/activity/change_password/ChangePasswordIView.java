package com.oxycab.provider.ui.activity.change_password;

import com.oxycab.provider.base.MvpView;

public interface ChangePasswordIView extends MvpView {
    void onSuccess(Object object);
    void onError(Throwable e);
}
