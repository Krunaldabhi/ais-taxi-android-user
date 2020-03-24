package com.cabbietransport.user.telr;

/**
 * @author Murtuza Vhora (msvhora)
 * @authorEmail Murtazavhora@gmail.com
 * @authorContactNumber +91 90334-94252 / +91 82000-44565
 */

import android.content.Context;
import android.content.Intent;
import android.location.Geocoder;
import android.os.Build;
import android.util.Log;


import androidx.appcompat.app.AppCompatActivity;

import com.cabbietransport.user.BuildConfig;
import com.cabbietransport.user.R;
import com.cabbietransport.user.common.Constants;
import com.cabbietransport.user.data.SharedHelper;
import com.cabbietransport.user.data.network.model.User;
import com.google.gson.Gson;
import com.telr.mobile.sdk.activty.WebviewActivity;
import com.telr.mobile.sdk.entity.request.payment.App;
import com.telr.mobile.sdk.entity.request.payment.Billing;
import com.telr.mobile.sdk.entity.request.payment.MobileRequest;
import com.telr.mobile.sdk.entity.request.payment.Name;
import com.telr.mobile.sdk.entity.request.payment.Tran;
import com.telr.mobile.sdk.entity.response.status.StatusResponse;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class TelrHelper {
    public static final String KEY = "74nj-vNR32^3fLXv";
    public static final String STORE_ID = "22707";
    public static final String MODE = "0";
    public static final String DEFAULT_COUNTRY_CODE = "US";
    public static final String DEFAULT_CURRENCY_CODE = "USD";

    public static final String TELR_AMOUNT_KEY = "telr_amount";
    public static final String TELR_TIPS_KEY = "telr_tips";
    public static final String TELR_CANCELLED_TRANSACTION_KEY = "is_cancelled";
    public static final String TELR_CANCEL_REASON_KEY = "cancel_reason";
    public static final String CARD_LAST_FOUR = "card_last_four";
    public static final String TELR_SUCCESS_STATUS_CODE = "A";

    public static final boolean isSecurityEnabled = false;      // Mark false to test on simulator, True to test on actual device and Production

    public static void initializePayment(Context context, String amount, String callbackActivity, String lastTransactionId) {
        SharedHelper.putKey(context.getApplicationContext(), TELR_AMOUNT_KEY, amount);

        Intent intent = new Intent(context, WebviewActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);

        if((lastTransactionId != null && (lastTransactionId.equals("") || lastTransactionId.equals("-1"))) || lastTransactionId == null) {
            intent.putExtra(WebviewActivity.EXTRA_MESSAGE, getMobileRequest(context, amount));
        }
        else {
            intent.putExtra(WebviewActivity.EXTRA_MESSAGE, getMobileRequestWithContAuth(context, amount, lastTransactionId));
        }
        intent.putExtra(WebviewActivity.SUCCESS_ACTIVTY_CLASS_NAME, callbackActivity);
        intent.putExtra(WebviewActivity.FAILED_ACTIVTY_CLASS_NAME, callbackActivity);
        intent.putExtra(WebviewActivity.IS_SECURITY_ENABLED, isSecurityEnabled);
        context.startActivity(intent);
        ((AppCompatActivity)context).finish();
    }

    public static HashMap<String, Object> getTelrResponse(Context context, Object object) {
        String amount = SharedHelper.getKey(context.getApplicationContext(), TELR_AMOUNT_KEY, "0");

        if (object instanceof StatusResponse) {
            StatusResponse status = (StatusResponse) object;

            if (status.getAuth() != null) {
                status.getAuth().getStatus();   // Authorisation status. A indicates an authorised transaction. H also indicates an authorised transaction, but where the transaction has been placed on hold. Any other value indicates that the request could not be processed.
                if (status.getAuth().getStatus().equals(TELR_SUCCESS_STATUS_CODE)) {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("amount", amount);
                    hashMap.put("payment_mode", Constants.PaymentMode.TELR);
                    hashMap.put("user_type", "user");
                    hashMap.put("transaction_response", status.getAuth().getTranref());

                    // Set Card last 4 digits
                    String cardLastFour = status.getAuth().getCardlast4();
                    if(cardLastFour != null && cardLastFour.length() > 0)
                        SharedHelper.putKey(context, CARD_LAST_FOUR, status.getAuth().getCardlast4());

                    return hashMap;
                }
            }
        }else if (object instanceof String){
            String errorMessage = (String) object;
            Log.e("TelrError", "getTelrResponse: " + errorMessage);
        }
        return null;
    }

    // This example used for the first payment, or with new card details.
    private static MobileRequest getMobileRequest(Context context, String amount) {
        User user = new Gson().fromJson(SharedHelper.getKey(context, "userInfo"), User.class);
        String countryCode = DEFAULT_COUNTRY_CODE; //getCountryCode(context);
        String currencyCode = DEFAULT_CURRENCY_CODE; //getCurrencyCode(countryCode);

        MobileRequest mobile = new MobileRequest();
        mobile.setStore(STORE_ID);                       // Store ID
        mobile.setKey(KEY);                              // Authentication Key : The Authentication Key will be supplied by Telr as part of the Mobile API setup process after you request that this integration type is enabled for your account. This should not be stored permanently within the App.

        App app = new App();
        app.setId(BuildConfig.APPLICATION_ID);                          // Application installation ID
        app.setName(context.getString(R.string.app_name));                    // Application name
        app.setUser(user.getEmail());                           // Application user ID : Your reference for the customer/user that is running the App. This should relate to their account within your systems.
        app.setVersion(BuildConfig.VERSION_NAME);                         // Application version
        app.setSdk(Build.VERSION.BASE_OS);
        mobile.setApp(app);

        Tran tran = new Tran();
        tran.setTest(MODE);                              // Test mode : Test mode of zero indicates a live transaction. If this is set to any other value the transaction will be treated as a test.
        tran.setType("auth");                           /* Transaction type
                                                            'auth'   : Seek authorisation from the card issuer for the amount specified. If authorised, the funds will be reserved but will not be debited until such time as a corresponding capture command is made. This is sometimes known as pre-authorisation.
                                                            'sale'   : Immediate purchase request. This has the same effect as would be had by performing an auth transaction followed by a capture transaction for the full amount. No additional capture stage is required.
                                                            'verify' : Confirm that the card details given are valid. No funds are reserved or taken from the card.
                                                        */
        tran.setClazz("paypage");                       // Transaction class only 'paypage' is allowed on mobile, which means 'use the hosted payment page to capture and process the card details'
        tran.setCartid(String.valueOf(new BigInteger(128, new Random()))); //// Transaction cart ID : An example use of the cart ID field would be your own transaction or order reference.
        tran.setDescription("Cabbie Telr Payment Gateway.");         // Transaction description
        tran.setCurrency("AED");                        // Transaction currency : Currency must be sent as a 3 character ISO code. A list of currency codes can be found at the end of this document. For voids or refunds, this must match the currency of the original transaction.
        tran.setAmount(amount);                         // Transaction amount : The transaction amount must be sent in major units, for example 9 dollars 50 cents must be sent as 9.50 not 950. There must be no currency symbol, and no thousands separators. Thedecimal part must be separated using a dot.
        tran.setLangauge("en");                        // (Optinal) default is en -> English
        mobile.setTran(tran);

        Billing billing = new Billing();

        Name name = new Name();
        name.setFirst(user.getFirstName());                          // Forename : the minimum required details for a transaction to be processed
        name.setLast(user.getLastName());                          // Surname : the minimum required details for a transaction to be processed
        name.setTitle("Mr");
        billing.setName(name);
        billing.setEmail(user.getEmail());
        billing.setPhone(user.getMobile());                // Phone number, required if enabled in your merchant dashboard.
        mobile.setBilling(billing);
        return mobile;
    }

    private static String getCountryCode(Context context) {

        double lat = Double.valueOf(SharedHelper.getKey(context, "latitude", "-33.8523341"));
        double lng = Double.valueOf(SharedHelper.getKey(context, "longitude", "151.2106085"));

        Geocoder gcd = new Geocoder(context, Locale.getDefault());
        List<android.location.Address> addresses = null;
        try {
            addresses = gcd.getFromLocation(lat, lng, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (addresses.size() > 0) {
            return addresses.get(0).getCountryCode();
        }
        return DEFAULT_COUNTRY_CODE;
    }

    private static String getCurrencyCode(String countryCode) {
        Locale locale = null;
        Currency currency = null;
        try {
            locale = new Locale("", countryCode);
            currency = Currency.getInstance(locale);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        if (currency != null) {
            return currency.getCurrencyCode();
        }

        return DEFAULT_CURRENCY_CODE;
    }

    private static MobileRequest getMobileRequestWithContAuth(Context context, String amount, String reference) {
        User user = new Gson().fromJson(SharedHelper.getKey(context, "userInfo"), User.class);
        String countryCode = DEFAULT_COUNTRY_CODE; //getCountryCode(context);
        String currencyCode = DEFAULT_CURRENCY_CODE; //getCurrencyCode(countryCode);

        MobileRequest mobile = new MobileRequest();
        mobile.setStore(STORE_ID);                       // Store ID
        mobile.setKey(KEY);                              // Authentication Key : The Authentication Key will be supplied by Telr as part of the Mobile API setup process after you request that this integration type is enabled for your account. This should not be stored permanently within the App.
        App app = new App();
        app.setId(BuildConfig.APPLICATION_ID);                          // Application installation ID
        app.setName(context.getString(R.string.app_name));                    // Application name
        app.setUser(user.getEmail());                           // Application user ID : Your reference for the customer/user that is running the App. This should relate to their account within your systems.
        app.setVersion(BuildConfig.VERSION_NAME);                         // Application version
        app.setSdk(Build.VERSION.BASE_OS);
        mobile.setApp(app);

        Tran tran = new Tran();
        tran.setTest(MODE);                              // Test mode : Test mode of zero indicates a live transaction. If this is set to any other value the transaction will be treated as a test.
        tran.setType("sale");                           /* Transaction type
                                                            'auth'   : Seek authorisation from the card issuer for the amount specified. If authorised, the funds will be reserved but will not be debited until such time as a corresponding capture command is made. This is sometimes known as pre-authorisation.
                                                            'sale'   : Immediate purchase request. This has the same effect as would be had by performing an auth transaction followed by a capture transaction for the full amount. No additional capture stage is required.
                                                            'verify' : Confirm that the card details given are valid. No funds are reserved or taken from the card.
                                                        */
        tran.setClazz("cont");
        tran.setCartid(String.valueOf(new BigInteger(128, new Random()))); //// Transaction cart ID : An example use of the cart ID field would be your own transaction or order reference.
        tran.setDescription("Cabbie Telr Payment Gateway.");           // Transaction description
        tran.setCurrency("AED");                        // Transaction currency : Currency must be sent as a 3 character ISO code. A list of currency codes can be found at the end of this document. For voids or refunds, this must match the currency of the original transaction.
        tran.setAmount(amount);                         // Transaction amount : The transaction amount must be sent in major units, for example 9 dollars 50 cents must be sent as 9.50 not 950. There must be no currency symbol, and no thousands separators. Thedecimal part must be separated using a dot.
        tran.setRef(reference);
        mobile.setTran(tran);

        return mobile;

    }

}
