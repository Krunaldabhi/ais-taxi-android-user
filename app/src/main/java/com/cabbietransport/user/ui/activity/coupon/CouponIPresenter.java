package com.cabbietransport.user.ui.activity.coupon;

import com.cabbietransport.user.base.MvpPresenter;

public interface CouponIPresenter<V extends CouponIView> extends MvpPresenter<V> {
    void coupon();
}
