package com.oxycab.provider.ui.activity.main;

import com.oxycab.provider.base.MvpView;
import com.oxycab.provider.data.network.model.OTPResponse;
import com.oxycab.provider.data.network.model.TripResponse;
import com.oxycab.provider.data.network.model.User;

public interface MainIView extends MvpView {
    void onSuccess(User user);
    void onSuccessLogout(Object object);
    void onSuccess(TripResponse tripResponse);
    void onSuccessProviderAvailable(Object object);
    void onSuccessFCM(Object object);
    void onSuccessLocationUpdate(TripResponse tripResponse);

    void onSuccessServerLocationUpdate(Object object);
    void onSuccessInstant(OTPResponse object);

    void onSuccessInstantNow(Object object);

}
