package com.cabbietransport.user.ui.activity.notification_manager;

import com.cabbietransport.user.base.MvpView;
import com.cabbietransport.user.data.network.model.NotificationManager;

import java.util.List;

public interface NotificationManagerIView extends MvpView {

    void onSuccess(List<NotificationManager> notificationManager);

    void onError(Throwable e);

}