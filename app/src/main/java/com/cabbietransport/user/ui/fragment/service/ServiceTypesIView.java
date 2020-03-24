package com.cabbietransport.user.ui.fragment.service;

import com.cabbietransport.user.base.MvpView;
import com.cabbietransport.user.data.network.model.Service;

import java.util.List;

public interface ServiceTypesIView extends MvpView {

    void onSuccess(List<Service> serviceList);

    void onError(Throwable e);

    void onSuccess(Object object);
}
