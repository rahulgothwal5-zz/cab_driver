package com.oxycab.provider.ui.activity.upcoming_detail;


import com.oxycab.provider.base.MvpPresenter;

public interface UpcomingTripDetailIPresenter<V extends UpcomingTripDetailIView> extends MvpPresenter<V> {
    void getUpcomingDetail(Object request_id);
}
