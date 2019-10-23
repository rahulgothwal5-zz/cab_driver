package com.oxycab.provider.ui.fragment.past;


import com.oxycab.provider.base.MvpView;
import com.oxycab.provider.data.network.model.HistoryList;

import java.util.List;

public interface PastTripIView extends MvpView {
    void onSuccess(List<HistoryList> historyList);
    void onError(Throwable e);
}
