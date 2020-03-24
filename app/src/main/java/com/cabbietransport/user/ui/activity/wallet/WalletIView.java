package com.cabbietransport.user.ui.activity.wallet;

import com.cabbietransport.user.data.network.TelrResponse;
import com.cabbietransport.user.data.network.model.TelrAddWallet;
import com.appoets.paytmpayment.PaytmObject;
import com.cabbietransport.user.base.MvpView;
import com.cabbietransport.user.data.network.model.AddWallet;
import com.cabbietransport.user.data.network.model.BrainTreeResponse;

public interface WalletIView extends MvpView {
    void onSuccess(AddWallet object);

    void onSuccess(TelrAddWallet object);

    void onSuccess(PaytmObject object);

    void onSuccess(TelrResponse response);

    void onSuccess(BrainTreeResponse response);
    void onError(Throwable e);
}
