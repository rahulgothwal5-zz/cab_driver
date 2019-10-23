package com.oxycab.provider.ui.activity.past_detail;


import com.oxycab.provider.base.MvpPresenter;

public interface PastTripDetailIPresenter<V extends PastTripDetailIView> extends MvpPresenter<V> {
    void getPastTripDetail(Object request_id);
}
