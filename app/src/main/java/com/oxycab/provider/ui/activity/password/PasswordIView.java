package com.oxycab.provider.ui.activity.password;

import com.oxycab.provider.base.MvpView;
import com.oxycab.provider.data.network.model.User;

public interface PasswordIView extends MvpView {
    void onSuccess(User object);
    void onError(Throwable e);
}
