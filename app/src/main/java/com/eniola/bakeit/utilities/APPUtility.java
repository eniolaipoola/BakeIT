package com.eniola.bakeit.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class APPUtility {

    private NetworkInfo networkInfo;
    public boolean isInternetAvailable(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager != null){
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return  networkInfo != null && networkInfo.isConnected();
    }
}
