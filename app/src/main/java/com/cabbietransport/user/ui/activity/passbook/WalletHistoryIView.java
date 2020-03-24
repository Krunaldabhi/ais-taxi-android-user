package com.cabbietransport.user.ui.activity.passbook;

import com.cabbietransport.user.base.MvpView;
import com.cabbietransport.user.data.network.model.WalletResponse;

public interface WalletHistoryIView extends MvpView {
    void onSuccess(WalletResponse response);

    void onError(Throwable e);
}
