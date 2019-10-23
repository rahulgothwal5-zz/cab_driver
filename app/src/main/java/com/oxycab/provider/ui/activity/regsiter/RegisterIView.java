package com.oxycab.provider.ui.activity.regsiter;

import com.oxycab.provider.base.MvpView;
import com.oxycab.provider.data.network.model.MyOTP;
import com.oxycab.provider.data.network.model.User;

public interface RegisterIView extends MvpView {
    void onSuccess(MyOTP otp);
    void onError(Throwable e);
}
