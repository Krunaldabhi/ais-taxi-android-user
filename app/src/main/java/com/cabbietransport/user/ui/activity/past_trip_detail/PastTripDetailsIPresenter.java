package com.cabbietransport.user.ui.activity.past_trip_detail;

import com.cabbietransport.user.base.MvpPresenter;

public interface PastTripDetailsIPresenter<V extends PastTripDetailsIView> extends MvpPresenter<V> {

    void getPastTripDetails(Integer requestId);
}
