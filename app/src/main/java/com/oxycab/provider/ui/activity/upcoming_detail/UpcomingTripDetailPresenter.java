package com.oxycab.provider.ui.activity.upcoming_detail;

import com.oxycab.provider.base.BasePresenter;
import com.oxycab.provider.data.network.APIClient;
import com.oxycab.provider.data.network.model.HistoryDetail;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UpcomingTripDetailPresenter<V extends UpcomingTripDetailIView> extends BasePresenter<V> implements UpcomingTripDetailIPresenter<V> {

    @Override
    public void getUpcomingDetail(Object request_id) {
        Observable modelObservable = APIClient.getAPIClient().getUpcomingDetail(request_id);
        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> getMvpView().onSuccess((HistoryDetail) trendsResponse),
                        throwable -> getMvpView().onError((Throwable) throwable));

    }
}
