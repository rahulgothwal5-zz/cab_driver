package com.oxycab.provider.ui.bottomsheetdialog.invoice_flow;

import com.oxycab.provider.base.MvpPresenter;

import java.util.HashMap;

public interface InvoiceDialogIPresenter<V extends InvoiceDialogIView> extends MvpPresenter<V> {
    void statusUpdate(HashMap<String, Object> obj, Integer id);
}
