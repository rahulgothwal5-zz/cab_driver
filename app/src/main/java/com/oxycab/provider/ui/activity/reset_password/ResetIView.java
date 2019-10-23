package com.oxycab.provider.ui.activity.reset_password;

import com.oxycab.provider.base.MvpView;

public interface ResetIView extends MvpView{
    void onSuccess(Object object);
    void onError(Throwable e);
}
