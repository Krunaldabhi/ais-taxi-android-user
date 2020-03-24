package com.cabbietransport.user.ui.activity.card;

import com.cabbietransport.user.base.MvpPresenter;


public interface CarsIPresenter<V extends CardsIView> extends MvpPresenter<V> {
    void card();
}
