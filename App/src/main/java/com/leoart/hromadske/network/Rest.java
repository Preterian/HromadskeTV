package com.leoart.hromadske.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.appcompat.R;
import android.util.Log;

import com.leoart.hromadske.HromadskeApp;

/**
 * Created by Bogdan on 16.12.13.
 */
public class Rest {


    private static final String TAG = "Rest";

    //TODO
    public static boolean isNetworkOnline(){
        boolean status = false;
        try{
        ConnectivityManager cm = (ConnectivityManager) HromadskeApp.context.getSystemService(Context.CONNECTIVITY_SERVICE);
            //mobile
            //1
            NetworkInfo.State mobile = null;
            if(cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE != null)){
                mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
            }

            //wifi
            //1
            NetworkInfo.State wifi = null;
            if(cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI) != null){
                wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
            }

            if(wifi != null){
                Log.d(TAG, "Wifi state: " + wifi);
            }
            if(mobile != null){
                Log.d(TAG, "3G state: " + mobile);
            }

            if(mobile == NetworkInfo.State.CONNECTED || mobile == NetworkInfo.State.CONNECTING){
                status = true;
            }
            if(wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING){
                status = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return status;
    }
}
