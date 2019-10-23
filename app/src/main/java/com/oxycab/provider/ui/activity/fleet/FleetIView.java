package com.oxycab.provider.ui.activity.fleet;


import com.oxycab.provider.base.MvpView;
import com.oxycab.provider.data.network.model.Vehicle;

import java.util.List;

/**
 * Created by santhosh@appoets.com on 19-05-2018.
 */
public interface FleetIView extends MvpView {
    void onSuccessVehicle(List<Vehicle> vehicles);
    void onSuccessVehicle(Object object);
}
