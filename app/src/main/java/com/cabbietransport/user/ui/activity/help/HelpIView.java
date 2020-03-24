package com.cabbietransport.user.ui.activity.help;

import com.cabbietransport.user.base.MvpView;
import com.cabbietransport.user.data.network.model.Help;

public interface HelpIView extends MvpView {

    void onSuccess(Help help);

    void onError(Throwable e);
}
