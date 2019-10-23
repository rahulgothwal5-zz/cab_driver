package com.oxycab.provider.ui.fragment.upcoming;


import com.oxycab.provider.base.MvpPresenter;

public interface UpcomingTripIPresenter<V extends UpcomingTripIView> extends MvpPresenter<V> {
    void getUpcoming();
}
