package com.cabbietransport.user.ui.activity.location_pick;

import com.cabbietransport.user.base.MvpPresenter;

public interface LocationPickIPresenter<V extends LocationPickIView> extends MvpPresenter<V> {
    void address();
}
