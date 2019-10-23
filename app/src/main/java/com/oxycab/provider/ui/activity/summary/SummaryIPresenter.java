package com.oxycab.provider.ui.activity.summary;


import com.oxycab.provider.base.MvpPresenter;

public interface SummaryIPresenter<V extends SummaryIView> extends MvpPresenter<V> {
    void getSummary();
}
