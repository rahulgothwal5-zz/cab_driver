package com.oxycab.provider.ui.activity.summary;


import com.oxycab.provider.base.MvpView;
import com.oxycab.provider.data.network.model.Summary;

public interface SummaryIView extends MvpView {
    void onSuccess(Summary object);
    void onError(Throwable e);
}
