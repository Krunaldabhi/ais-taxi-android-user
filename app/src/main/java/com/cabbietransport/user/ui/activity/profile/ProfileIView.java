package com.cabbietransport.user.ui.activity.profile;

import com.cabbietransport.user.base.MvpView;
import com.cabbietransport.user.data.network.model.User;

public interface ProfileIView extends MvpView {

    void onSuccess(User user);

    void onUpdateSuccess(User user);

    void onError(Throwable e);

    void onSuccessPhoneNumber(Object object);

    void onVerifyPhoneNumberError(Throwable e);
}
