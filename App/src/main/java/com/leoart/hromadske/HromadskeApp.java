package com.leoart.hromadske;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.leoart.hromadske.model.FullPost;
import com.leoart.hromadske.model.Post;
import com.leoart.hromadske.orm.DBHelper;
import com.leoart.hromadske.orm.DataBaseHelper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Bogdan on 15.12.13.
 */
public class HromadskeApp extends Application {
    public static String Tag = "HromadskeApp";
    public static Context context;
    public static HromadskeApp appInstance;
    public static File CASH_DIR;
    public static final long CASH_SIZE = 30 * 1024 * 1024; //50Mb

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(Tag, "Started UaEnergyApp");

        context = getApplicationContext();
        CASH_DIR = context.getCacheDir();

        installCash();
        setAppInstance(this);
        // parseVideoPost("http://hromadske.tv/video/");
    }

    public void setAppInstance(HromadskeApp hromadskeApp) {
        appInstance = hromadskeApp;
    }

    public static HromadskeApp getAppInstance() {
        return appInstance;
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static void installCash() {
        Log.d(Tag, "Installing HTTP cash...");
        try {
            File httpCashDir = new File(HromadskeApp.CASH_DIR, "http");
            long httpCashDirSize = HromadskeApp.CASH_SIZE;
            HttpResponseCache.install(httpCashDir, httpCashDirSize);
        } catch (IOException e) {
            Log.d(Tag, "Error while installing cash: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static void uninstallCash() {
        Log.d(Tag, "Uninstalling cash...");
        HttpResponseCache cash = HttpResponseCache.getInstalled();
        if (cash != null) {
            try {
                cash.delete();
            } catch (IOException e) {
                Log.d(Tag, "Error while uninstalling cash: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }


    //TODO
    @Override
    public void onLowMemory() {
        Log.d(Tag, "On Low Memory Called");
        //TODO
    }

    @Override
    public void onTerminate() {
        Log.d(Tag, "Terminating App");
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
        super.onTerminate();

        Log.d(Tag, "App terminated");
    }


    public void clearDB() {
        DataBaseHelper db = HromadskeApp.getDatabaseHelper();
        try {
            Dao<Post, Integer> postsDao = db.getDao(Post.class);
            DeleteBuilder<Post, Integer> deletePostsDao = postsDao.deleteBuilder();
            deletePostsDao.delete();
        } catch (SQLException e) {
            Log.d(Tag, "Error while getting dao: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(Tag, "App Configuration Changed");
    }

    public static ExecutorService getThreadExecutor() {
        if (threadExecutor == null) {
            threadExecutor = Executors.newCachedThreadPool();
        }
        return threadExecutor;
    }

    public static DataBaseHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(HromadskeApp.context, DataBaseHelper.class);
        }
        return databaseHelper;
    }


    static class PostsAsyncParser extends AsyncTask<String, Void, Void> {

        Elements links = null;
        Elements images = null;
        Elements titles = null;
        Elements descriptions = null;
        Elements dates = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... urls) {
            Document doc = null;
            try {
                doc = Jsoup.connect(urls[0]).get();
            } catch (IOException ex) {

            }

            titles = doc.getElementsByClass("episode_name");
            images = doc.getElementsByTag("img");

            links = doc.getElementsByTag("a");

            descriptions = doc.getElementsByClass("episode_description");
            dates = doc.getElementsByClass("episode_date");

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            HromadskeApp.setDates(dates);
            HromadskeApp.setTitles(titles);
            HromadskeApp.setImages(images);
            HromadskeApp.setLinks(links);
            HromadskeApp.setDescriptions(descriptions);
        }
    }


    public static void parseVideoPost(final String url) {

        // new Thread(new Task(url)).start();

        PostsAsyncParser parser = new PostsAsyncParser();
        parser.execute(url);

       /* threadExecutor = Executors.newCachedThreadPool();
        getThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                Document doc = null;
                try {
                    doc = Jsoup.connect(url).get();
                } catch (IOException ex) {

                }

                Elements titles = doc.getElementsByClass("episode_name");



                HromadskeApp.getAppInstance().setLinks(doc.getElementsByTag("a"));
                HromadskeApp.getAppInstance().setImages(doc.getElementsByTag("img"));
                HromadskeApp.getAppInstance().setTitles(doc.getElementsByClass("episode_name"));
                HromadskeApp.getAppInstance().setDescriptions(doc.getElementsByClass("episode_description"));
                HromadskeApp.getAppInstance().setDates(doc.getElementsByClass("episode_date"));

                Log.d(Tag, "TITLES = " + getAppInstance().getTitles());
            }
        });*/


    }

    public static Elements getLinks() {
        return links;
    }

    public static Elements getImages() {
        return images;
    }

    public static Elements getTitles() {
        return titles;
    }

    public static Elements getDescriptions() {
        return descriptions;
    }

    public static Elements getDates() {
        return dates;
    }

    public static void setLinks(Elements links) {
        HromadskeApp.links = links;
    }

    public static void setImages(Elements images) {
        HromadskeApp.images = images;
    }

    public static void setTitles(Elements titles) {
        HromadskeApp.titles = titles;
    }

    public static void setDescriptions(Elements descriptions) {
        HromadskeApp.descriptions = descriptions;
    }

    public static void setDates(Elements dates) {
        HromadskeApp.dates = dates;
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

    private static Elements links = null;
    private static Elements images = null;
    private static Elements titles = null;
    private static Elements descriptions = null;
    private static Elements dates = null;

}