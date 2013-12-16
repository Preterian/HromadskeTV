package com.leoart.hromadske;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.leoart.hromadske.model.FullPost;
import com.leoart.hromadske.orm.DataBaseHelper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Bogdan on 15.12.13.
 */
public class HromadskeApp extends Application {
    public static String Tag = "UaEnergyApp";
    public static Context context;


    @Override
    public void onCreate(){
        super.onCreate();
        Log.d(Tag, "Started UaEnergyApp");

        context = getApplicationContext();
    }

    @Override
    public void onTerminate(){
        Log.d(Tag, "Terminating App");
        if(databaseHelper != null){
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
        super.onTerminate();

        Log.d(Tag, "App terminated");
    }


    public void clearDB(){

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        Log.d(Tag, "App Configuration Changed");
    }

    public static ExecutorService getThreadExecutor() {
        if(threadExecutor == null){
            threadExecutor = Executors.newCachedThreadPool();
        }
        return threadExecutor;
    }

    public static DataBaseHelper getDatabaseHelper() {
        if(databaseHelper == null){
            databaseHelper = OpenHelperManager.getHelper(HromadskeApp.context, DataBaseHelper.class);
        }
        return databaseHelper;
    }

    public static void setDatabaseHelper(DataBaseHelper databaseHelper) {
        HromadskeApp.databaseHelper = databaseHelper;
    }

    private static DataBaseHelper databaseHelper;

    private static ExecutorService threadExecutor;

    private static FullPost fullPost = new FullPost();

    public static FullPost getFullPost() {
        return fullPost;
    }

    public static void setFullPost(FullPost fullPostN) {
        fullPost = fullPostN;
    }

    public String getFullPostUrl() {
        return fullPostUrl;
    }

    public void setFullPostUrl(String fullPostUrl) {
        this.fullPostUrl = fullPostUrl;
    }

    private String fullPostUrl;
}