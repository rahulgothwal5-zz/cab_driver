package com.oxycab.provider.ui.bottomsheetdialog.cancel;

import com.oxycab.provider.base.MvpView;

public interface CancelDialogIView extends MvpView {

    void onSuccessCancel(Object object);
    void onError(Throwable e);
}
