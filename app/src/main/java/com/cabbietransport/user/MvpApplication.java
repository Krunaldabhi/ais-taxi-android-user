package com.cabbietransport.user;

import android.content.Context;
import androidx.multidex.MultiDex;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.facebook.stetho.Stetho;
import com.cabbietransport.user.common.ConnectivityReceiver;
import com.cabbietransport.user.common.LocaleHelper;
import com.cabbietransport.user.data.network.model.Datum;
import com.telr.mobile.sdk.TelrApplication;

import java.util.HashMap;

import io.fabric.sdk.android.Fabric;

//import com.facebook.stetho.Stetho;

public class MvpApplication extends TelrApplication {

    private static MvpApplication mInstance;

    public static boolean canGoToChatScreen;
    public static boolean isChatScreenOpen;

    public static boolean isCash = true;
    public static boolean isCard = true;
    public static boolean isPayumoney;
    public static boolean isPaypal;
    public static boolean isPaytm;
    public static boolean isTelr;
    public static boolean isPaypalAdaptive;
    public static boolean isBraintree;
    public static boolean openChatFromNotification = true;

    public static HashMap<String, Object> RIDE_REQUEST = new HashMap<>();
    public static Datum DATUM = null;
    public static boolean showOTP = true;

    public static synchronized MvpApplication getInstance() {
        return mInstance;
    }

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        MvpApplication.context = getApplicationContext();

        mInstance = this;

        CrashlyticsCore core = new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build();
        Crashlytics crashlytics = new Crashlytics.Builder().core(core).build();
        Fabric.with(this, crashlytics, new Crashlytics());

        if (BuildConfig.DEBUG)
            Stetho.initializeWithDefaults(this);

        MultiDex.install(this);
    }

    public static Context getContext(){
        return MvpApplication.context;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase, "en"));
        MultiDex.install(newBase);
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

}
