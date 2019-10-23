package com.oxycab.provider.ui.activity.profile;

import com.oxycab.provider.base.MvpView;
import com.oxycab.provider.data.network.model.Panic;
import com.oxycab.provider.data.network.model.User;

public interface ProfileIView extends MvpView {
    void onSuccess(User user);
    void onSuccessUser(User user);
    void onError(Throwable e);

    void onSuccessPanic(Panic panic);
}
