package com.cabbietransport.user.ui.activity.main;

import com.cabbietransport.user.base.MvpPresenter;

import java.util.HashMap;

public interface MainIPresenter<V extends MainIView> extends MvpPresenter<V> {

    void getUserInfo();

    void logout(String id);

    void checkStatus();

    void getSavedAddress();

    void getProviders(HashMap<String, Object> params);

    void getNavigationSettings();

    void updateDestination(HashMap<String, Object> obj);

    void payment(HashMap<String, Object> obj);

    void cancelRequest(HashMap<String, Object> params);

}
