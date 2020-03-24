package com.cabbietransport.user.ui.activity.social;

import com.cabbietransport.user.base.MvpView;
import com.cabbietransport.user.data.network.model.Token;

/**
 * Created by santhosh@appoets.com on 19-05-2018.
 */
public interface SocialIView extends MvpView {
    void onSuccess(Token token);

    void onError(Throwable e);
}
