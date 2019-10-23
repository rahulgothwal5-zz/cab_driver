package com.oxycab.provider.ui.activity.notification;


import com.oxycab.provider.base.BasePresenter;
import com.oxycab.provider.data.network.APIClient;
import com.oxycab.provider.data.network.model.Notification;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NotificationPresenter<V extends NotificationIView> extends BasePresenter<V> implements NotificationIPresenter<V> {

    @Override
    public void notifications() {
        Observable modelObservable = APIClient.getAPIClient().notifications();
        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> getMvpView().onSuccess((List<Notification>) trendsResponse),
                        throwable -> getMvpView().onError((Throwable) throwable));

    }
}
