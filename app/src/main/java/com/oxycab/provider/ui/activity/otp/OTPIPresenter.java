package com.oxycab.provider.ui.activity.otp;

import com.oxycab.provider.base.MvpPresenter;


public interface OTPIPresenter<V extends OTPIView> extends MvpPresenter<V> {
    void sendOTP(Object obj);
    void sendVoiceOTP(Object obj);
}
