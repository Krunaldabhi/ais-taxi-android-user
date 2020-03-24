package com.cabbietransport.user.ui.activity.upcoming_trip_detail;

import com.cabbietransport.user.base.MvpPresenter;

public interface UpcomingTripDetailsIPresenter<V extends UpcomingTripDetailsIView> extends MvpPresenter<V> {

    void getUpcomingTripDetails(Integer requestId);
}
