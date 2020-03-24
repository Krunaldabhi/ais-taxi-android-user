package com.cabbietransport.user.ui.activity.setting;

import com.cabbietransport.user.base.MvpView;
import com.cabbietransport.user.data.network.model.AddressResponse;

public interface SettingsIView extends MvpView {

    void onSuccessAddress(Object object);

    void onLanguageChanged(Object object);

    void onSuccess(AddressResponse address);

    void onError(Throwable e);
}
