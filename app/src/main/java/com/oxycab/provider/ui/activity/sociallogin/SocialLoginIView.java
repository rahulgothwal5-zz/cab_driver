package com.oxycab.provider.ui.activity.sociallogin;

import com.oxycab.provider.base.MvpView;
import com.oxycab.provider.data.network.model.Token;

public interface SocialLoginIView extends MvpView {
    void onSuccess(Token token);
    void onError(Throwable e);
}
