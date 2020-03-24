package com.cabbietransport.user.ui.fragment.book_ride;

import com.cabbietransport.user.base.MvpView;
import com.cabbietransport.user.data.network.model.PromoResponse;


public interface BookRideIView extends MvpView {
    void onSuccess(Object object);

    void onError(Throwable e);

    void onSuccessCoupon(PromoResponse promoResponse);
}
