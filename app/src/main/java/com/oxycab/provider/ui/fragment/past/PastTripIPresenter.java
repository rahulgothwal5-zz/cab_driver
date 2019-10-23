package com.oxycab.provider.ui.fragment.past;


import com.oxycab.provider.base.MvpPresenter;

public interface PastTripIPresenter<V extends PastTripIView> extends MvpPresenter<V> {
    void getHistory();
}
