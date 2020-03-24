package com.cabbietransport.user.ui.fragment.invoice;

import com.cabbietransport.user.data.network.TelrResponse;
import com.appoets.paytmpayment.PaytmObject;
import com.cabbietransport.user.base.MvpView;
import com.cabbietransport.user.data.network.model.BrainTreeResponse;
import com.cabbietransport.user.data.network.model.CheckSumData;
import com.cabbietransport.user.data.network.model.Message;

public interface InvoiceIView extends MvpView {
    void onSuccess(Message message);

    void onSuccess(Object o);

    void onSuccessPayment(Object o);

    void onSuccess(TelrResponse response);

    void onError(Throwable e);

    void onSuccess(BrainTreeResponse response);

    void onPayumoneyCheckSumSucess(CheckSumData checkSumData);

    void onPayTmCheckSumSucess(PaytmObject payTmResponse);
}
