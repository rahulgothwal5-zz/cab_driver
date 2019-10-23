package com.oxycab.provider.ui.activity.help;

import com.oxycab.provider.base.MvpView;
import com.oxycab.provider.data.network.model.Help;

public interface HelpIView extends MvpView {
    void onSuccess(Help object);
    void onError(Throwable e);
}
