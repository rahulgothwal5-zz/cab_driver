package com.oxycab.provider.ui.activity.regsiter.details;

import com.oxycab.provider.base.MvpView;
import com.oxycab.provider.data.network.model.MyOTP;
import com.oxycab.provider.data.network.model.User;

public interface AddDriverIView extends MvpView {
    void onSuccess(User user);
    void onError(Throwable e);
}
