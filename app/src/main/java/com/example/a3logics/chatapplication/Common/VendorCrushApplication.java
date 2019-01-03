package com.example.a3logics.chatapplication.Common;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;

import com.afollestad.materialdialogs.MaterialDialog;


/**
 * Created by Rajeev on 30/11/17.
 *
 * overall initialize the application
 */

public class VendorCrushApplication extends Application{
    private static VendorCrushApplication _instance;
    private static MaterialDialog mProgressDialog;


    @Override
    public void onCreate() {
        super.onCreate();
        //Fabric.with(this, new Crashlytics());
        _instance=this;
    }
    public static Context getInstances(){
        return _instance;
    }

    /**
     *--  Check for Internet Connectivity ----------
     **/
    public static boolean isInternetAvailable() {

        ConnectivityManager conMgr = (ConnectivityManager) _instance.getSystemService(Context.CONNECTIVITY_SERVICE);

        // ARE WE CONNECTED TO THE NET
        if (null != conMgr && conMgr.getActiveNetworkInfo() != null

                && conMgr.getActiveNetworkInfo().isAvailable()

                && conMgr.getActiveNetworkInfo().isConnected()) {

            return true;

        } else {
            return false;
        }


    }

    public static void logout(){

    }


}
