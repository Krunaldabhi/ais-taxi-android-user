package com.cabbietransport.user.ui.activity.login;

import com.cabbietransport.user.base.MvpView;
import com.cabbietransport.user.data.network.model.ForgotResponse;
import com.cabbietransport.user.data.network.model.Token;

public interface LoginIView extends MvpView {
    void onSuccess(Token token);

    void onSuccess(ForgotResponse object);

    void onError(Throwable e);
}
