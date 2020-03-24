package com.cabbietransport.user.ui.activity.passbook;

import com.cabbietransport.user.base.MvpPresenter;

public interface WalletHistoryIPresenter<V extends WalletHistoryIView> extends MvpPresenter<V> {
    void wallet();
}
