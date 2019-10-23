package com.oxycab.provider.ui.bottomsheetdialog.invoice_flow;

import com.oxycab.provider.base.MvpView;

public interface InvoiceDialogIView extends MvpView {
    void onSuccess(Object object);
    void onError(Throwable e);
}
