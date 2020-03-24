package com.cabbietransport.user.ui.activity.past_trip_detail;

import com.cabbietransport.user.base.MvpView;
import com.cabbietransport.user.data.network.model.Datum;

import java.util.List;

public interface PastTripDetailsIView extends MvpView {

    void onSuccess(List<Datum> pastTripDetails);

    void onError(Throwable e);
}
