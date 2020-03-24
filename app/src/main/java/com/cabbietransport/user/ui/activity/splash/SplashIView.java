package com.cabbietransport.user.ui.activity.splash;

import com.cabbietransport.user.base.MvpView;
import com.cabbietransport.user.data.network.model.CheckVersion;
import com.cabbietransport.user.data.network.model.Service;
import com.cabbietransport.user.data.network.model.User;

import java.util.List;

public interface SplashIView extends MvpView {

    void onSuccess(List<Service> serviceList);

    void onSuccess(User user);

    void onError(Throwable e);

    void onSuccess(CheckVersion checkVersion);
}
