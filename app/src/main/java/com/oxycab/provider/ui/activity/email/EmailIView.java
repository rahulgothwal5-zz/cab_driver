package com.oxycab.provider.ui.activity.email;

import com.oxycab.provider.base.MvpView;
import com.oxycab.provider.data.network.model.User;

public interface EmailIView extends MvpView {
    void onSuccess(User token);
    void onError(Throwable e);
}
