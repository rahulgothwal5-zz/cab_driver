package com.oxycab.provider.ui.activity.help;


import com.oxycab.provider.base.BasePresenter;
import com.oxycab.provider.data.network.APIClient;
import com.oxycab.provider.data.network.model.Help;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HelpPresenter<V extends HelpIView> extends BasePresenter<V> implements HelpIPresenter<V> {
    @Override
    public void getHelp() {
        Observable modelObservable = APIClient.getAPIClient().getHelp();
        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> getMvpView().onSuccess((Help) trendsResponse),
                        throwable -> getMvpView().onError((Throwable) throwable));

    }
}
