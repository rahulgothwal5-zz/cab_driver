package com.oxycab.provider.ui.activity.earnings;


import com.oxycab.provider.base.MvpPresenter;

public interface EarningsIPresenter<V extends EarningsIView> extends MvpPresenter<V> {
    void getEarnings();
}
