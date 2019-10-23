package com.oxycab.provider.ui.activity.location_pick;


import com.oxycab.provider.base.MvpView;
import com.oxycab.provider.data.network.model.AddressResponse;

/**
 * Created by santhosh@appoets.com on 19-05-2018.
 */
public interface LocationPickIView extends MvpView {

    void onSuccess(AddressResponse address);
    void onError(Throwable e);
}
