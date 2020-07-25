package com.sideeg.saloonapp.networking;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;


import com.sideeg.saloonapp.utility.LocalSession;


import androidx.multidex.MultiDex;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyApplication extends Application {

    private static Context context;
    private static LocalSession sessionManager;
    private static MyApplication instance;
    String token;

    @Override
    public void onCreate() {
        super.onCreate();

        if(instance == null){
            instance = this;
        }

        try{
            context = getApplicationContext();
            sessionManager=new LocalSession(this);



        }catch (Exception eee){
            Log.d("ours" , eee.getMessage());
            eee.printStackTrace();
        }

//        saveToken();
     }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }

    public static MyApplication getInstance(){
        return instance;
    }

    public static boolean hasNetwork(){
        return instance.isNetworkConnected();
    }

    private boolean isNetworkConnected(){
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }



}
