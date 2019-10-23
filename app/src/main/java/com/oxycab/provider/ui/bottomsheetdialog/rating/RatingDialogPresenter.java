package com.oxycab.provider.ui.bottomsheetdialog.rating;

import com.oxycab.provider.base.BasePresenter;
import com.oxycab.provider.data.network.APIClient;
import com.oxycab.provider.data.network.model.Rating;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RatingDialogPresenter<V extends RatingDialogIView> extends BasePresenter<V> implements RatingDialogIPresenter<V> {

    @Override
    public void rate(HashMap<String, Object> obj, Integer id) {
        getMvpView().showLoading();
        Observable modelObservable = APIClient.getAPIClient().ratingRequest(obj, id);
        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer() {
                               @Override
                               public void accept(Object trendsResponse) throws Exception {
                                   getMvpView().hideLoading();
                                   RatingDialogPresenter.this.getMvpView().onSuccess((Rating) trendsResponse);
                               }
                           },
                        new Consumer() {
                            @Override
                            public void accept(Object throwable) throws Exception {
                                getMvpView().hideLoading();
                                getMvpView().onError((Throwable) throwable);
                            }
                        });

    }
}
