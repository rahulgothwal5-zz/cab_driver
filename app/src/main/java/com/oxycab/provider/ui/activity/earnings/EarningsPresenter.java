package com.oxycab.provider.ui.activity.earnings;

import com.oxycab.provider.base.BasePresenter;
import com.oxycab.provider.data.network.APIClient;
import com.oxycab.provider.data.network.model.EarningsList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class EarningsPresenter<V extends EarningsIView> extends BasePresenter<V> implements EarningsIPresenter<V> {

    @Override
    public void getEarnings() {
        Observable modelObservable = APIClient.getAPIClient().getEarnings();
        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> getMvpView().onSuccess((EarningsList) trendsResponse),
                        throwable -> getMvpView().onError((Throwable) throwable));
    }
}
