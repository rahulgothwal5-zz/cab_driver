package com.oxycab.provider.ui.bottomsheetdialog.invoice_flow;

import com.oxycab.provider.base.BasePresenter;
import com.oxycab.provider.data.network.APIClient;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class InvoiceDialogPresenter<V extends InvoiceDialogIView> extends BasePresenter<V> implements InvoiceDialogIPresenter<V> {

    @Override
    public void statusUpdate(HashMap<String, Object> obj, Integer id) {
        Observable modelObservable = APIClient.getAPIClient().updateRequest(obj, id);
        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> getMvpView().onSuccess(trendsResponse),
                        throwable -> getMvpView().onError((Throwable) throwable));

    }
}
