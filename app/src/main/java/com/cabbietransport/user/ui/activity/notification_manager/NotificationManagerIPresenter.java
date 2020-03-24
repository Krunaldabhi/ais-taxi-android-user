package com.cabbietransport.user.ui.activity.notification_manager;

import com.cabbietransport.user.base.MvpPresenter;

public interface NotificationManagerIPresenter<V extends NotificationManagerIView> extends MvpPresenter<V> {
    void getNotificationManager();
}
