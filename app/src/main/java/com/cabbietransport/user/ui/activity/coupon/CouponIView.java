package com.cabbietransport.user.ui.activity.coupon;

import com.cabbietransport.user.base.MvpView;
import com.cabbietransport.user.data.network.model.PromoResponse;

public interface CouponIView extends MvpView {
    void onSuccess(PromoResponse object);

    void onError(Throwable e);
}
