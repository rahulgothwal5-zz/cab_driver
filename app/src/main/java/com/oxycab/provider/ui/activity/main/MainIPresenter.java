package com.oxycab.provider.ui.activity.main;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonObject;
import com.oxycab.provider.base.MvpPresenter;

import java.util.HashMap;

public interface MainIPresenter<V extends MainIView> extends MvpPresenter<V> {

    void getProfile();
    void logout(HashMap<String, Object> obj);
    void getTrip(HashMap<String, Object> params);
    void providerAvailable(HashMap<String, Object> obj);
    void sendFCM(JsonObject jsonObject);
    void getTripLocationUpdate(HashMap<String, Object> params);
    void instantRideEstimateFare(HashMap<String, Object> obj);

    void instantRideSendRequest(HashMap<String, Object> obj);

}
