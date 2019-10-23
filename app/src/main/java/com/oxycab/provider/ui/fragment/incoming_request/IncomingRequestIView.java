package com.oxycab.provider.ui.fragment.incoming_request;

import com.oxycab.provider.base.MvpView;

public interface IncomingRequestIView extends MvpView {
    void onSuccessAccept(Object responseBody);
    void onSuccessCancel(Object object);
    void onError(Throwable e);
}
