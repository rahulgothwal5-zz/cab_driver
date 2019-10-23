package com.oxycab.provider.ui.activity.upcoming_detail;


import com.oxycab.provider.base.MvpView;
import com.oxycab.provider.data.network.model.HistoryDetail;

public interface UpcomingTripDetailIView extends MvpView {
    void onSuccess(HistoryDetail historyDetail);
    void onError(Throwable e);
}
