package com.oxycab.provider.ui.activity.past_detail;


import com.oxycab.provider.base.MvpView;
import com.oxycab.provider.data.network.model.HistoryDetail;

public interface PastTripDetailIView extends MvpView {
    void onSuccess(HistoryDetail historyDetail);
    void onError(Throwable e);
}
