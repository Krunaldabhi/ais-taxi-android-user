package com.cabbietransport.user.ui.activity.location_pick;

import com.cabbietransport.user.base.MvpView;
import com.cabbietransport.user.data.network.model.AddressResponse;

/**
 * Created by santhosh@appoets.com on 19-05-2018.
 */
public interface LocationPickIView extends MvpView {

    void onSuccess(AddressResponse address);

    void onError(Throwable e);
}
