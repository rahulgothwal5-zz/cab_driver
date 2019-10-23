package com.oxycab.provider.ui.activity.forgot_password;

import com.oxycab.provider.base.MvpView;
import com.oxycab.provider.data.network.model.ForgotResponse;

public interface ForgotIView extends MvpView {
    void onSuccess(ForgotResponse forgotResponse);
    void onError(Throwable e);
}
