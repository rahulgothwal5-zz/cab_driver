package com.oxycab.provider.ui.activity.notification;


import com.oxycab.provider.base.MvpView;
import com.oxycab.provider.data.network.model.Notification;

import java.util.List;

public interface NotificationIView extends MvpView {
    void onSuccess(List<Notification> notifications);
}
