package com.oxycab.provider.ui.activity.fleet;


import com.oxycab.provider.base.MvpPresenter;

/**
 * Created by santhosh@appoets.com on 19-05-2018.
 */
public interface FleetIPresenter<V extends FleetIView> extends MvpPresenter<V> {
    void vehicles();
    void setVehicle(Object vehicleId);
}
