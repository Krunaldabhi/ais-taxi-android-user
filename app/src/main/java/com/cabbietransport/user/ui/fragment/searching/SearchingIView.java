package com.cabbietransport.user.ui.fragment.searching;

import com.cabbietransport.user.base.MvpView;

public interface SearchingIView extends MvpView {
    void onSuccess(Object object);

    void onError(Throwable e);
}
