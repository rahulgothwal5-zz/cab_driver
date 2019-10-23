package com.oxycab.provider.ui.fragment.status_flow;

import com.oxycab.provider.base.MvpView;

public interface StatusFlowIView extends MvpView {
    void onSuccess(Object object);
    void onError(Throwable e);
}
