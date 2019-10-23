package com.oxycab.provider.ui.activity.regsiter;

import com.oxycab.provider.base.MvpPresenter;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface RegisterIPresenter<V extends RegisterIView> extends MvpPresenter<V> {
    void verifyMobileAlreadyExits(Object mobile);
    void sendOTP(Object obj);

}
