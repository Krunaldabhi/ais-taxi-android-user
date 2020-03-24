package com.cabbietransport.user.ui.activity.invite_friend;

import com.cabbietransport.user.base.MvpView;
import com.cabbietransport.user.data.network.model.User;

public interface InviteFriendIView extends MvpView {

    void onSuccess(User user);

    void onError(Throwable e);

}
