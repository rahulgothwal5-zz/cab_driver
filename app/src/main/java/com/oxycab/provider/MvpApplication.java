package com.oxycab.provider;

import android.app.Application;
import android.location.Location;
import android.support.multidex.MultiDex;

import com.facebook.stetho.Stetho;
import com.oxycab.provider.common.ConnectivityReceiver;
import com.oxycab.provider.common.SharedHelper;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public class MvpApplication extends Application {

    public static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    public static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 2;
    public static float DEFAULT_ZOOM = 15;
    public static Location mLastKnownLocation;
    public static final int PERMISSIONS_REQUEST_PHONE = 4;
    public static final int PICK_LOCATION_REQUEST_CODE = 3;

    private static MvpApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        mInstance = this;
        MultiDex.install(this);

    }

    public static synchronized MvpApplication getInstance() {
        return mInstance;
    }

    public static void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

    public static NumberFormat getNumberFormat() {
        String currencyCode = SharedHelper.getKey(getInstance(), "currency_code", "INR");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
        numberFormat.setCurrency(Currency.getInstance(currencyCode));
        numberFormat.setMinimumFractionDigits(2);
        return numberFormat;
    }

}
