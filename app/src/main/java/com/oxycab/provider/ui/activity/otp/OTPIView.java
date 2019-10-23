package com.oxycab.provider.ui.activity.otp;

import com.oxycab.provider.base.MvpView;
import com.oxycab.provider.data.network.model.MyOTP;

public interface OTPIView extends MvpView {
    void onSuccess(MyOTP otp);
    void onError(Throwable e);
}
