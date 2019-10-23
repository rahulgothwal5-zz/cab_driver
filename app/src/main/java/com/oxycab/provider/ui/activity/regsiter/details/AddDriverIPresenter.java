package com.oxycab.provider.ui.activity.regsiter.details;

import com.oxycab.provider.base.MvpPresenter;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface AddDriverIPresenter<V extends AddDriverIView> extends MvpPresenter<V> {
    void register(@PartMap Map<String, RequestBody> params, @Part List<MultipartBody.Part> file);
}
