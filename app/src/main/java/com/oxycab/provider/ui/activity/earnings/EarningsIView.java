package com.oxycab.provider.ui.activity.earnings;


import com.oxycab.provider.base.MvpView;
import com.oxycab.provider.data.network.model.EarningsList;

public interface EarningsIView extends MvpView {
    void onSuccess(EarningsList earningsLists);
    void onError(Throwable e);
}
