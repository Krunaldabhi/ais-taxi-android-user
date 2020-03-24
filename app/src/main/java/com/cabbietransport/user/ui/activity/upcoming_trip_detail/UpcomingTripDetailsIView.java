package com.cabbietransport.user.ui.activity.upcoming_trip_detail;

import com.cabbietransport.user.base.MvpView;
import com.cabbietransport.user.data.network.model.Datum;

import java.util.List;

public interface UpcomingTripDetailsIView extends MvpView {

    void onSuccess(List<Datum> upcomingTripDetails);

    void onError(Throwable e);
}
