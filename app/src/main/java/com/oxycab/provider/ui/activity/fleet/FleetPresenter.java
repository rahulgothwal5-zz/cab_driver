package com.oxycab.provider.ui.activity.fleet;

import com.oxycab.provider.base.BasePresenter;
import com.oxycab.provider.data.network.APIClient;
import com.oxycab.provider.data.network.model.Vehicle;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by santhosh@appoets.com on 02-05-2018.
 */
public class FleetPresenter<V extends FleetIView> extends BasePresenter<V> implements FleetIPresenter<V> {


    @Override
    public void vehicles() {

        Observable modelObservable = APIClient.getAPIClient().vehicles();

        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer() {
                               @Override
                               public void accept(Object trendsResponse) throws Exception {
                                   FleetPresenter.this.getMvpView().onSuccessVehicle((List<Vehicle>) trendsResponse);
                               }
                           },
                        new Consumer() {
                            @Override
                            public void accept(Object throwable) throws Exception {
                                FleetPresenter.this.getMvpView().onError((Throwable) throwable);
                            }
                        });
    }

    @Override
    public void setVehicle(Object vehicleId) {
        Observable modelObservable = APIClient.getAPIClient().setVehicle(vehicleId);
        getMvpView().showLoading();
        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer() {
                               @Override
                               public void accept(Object trendsResponse) throws Exception {
                                   getMvpView().hideLoading();
                                   FleetPresenter.this.getMvpView().onSuccessVehicle((Object) trendsResponse);
                               }
                           },
                        new Consumer() {
                            @Override
                            public void accept(Object throwable) throws Exception {
                                getMvpView().hideLoading();
                                FleetPresenter.this.getMvpView().onError((Throwable) throwable);
                            }
                        });
    }

}
