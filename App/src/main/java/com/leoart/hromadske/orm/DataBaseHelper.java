package com.leoart.hromadske.orm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.leoart.hromadske.R;
import com.leoart.hromadske.model.Post;
import com.leoart.hromadske.parser.UaEnergyParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Bogdan on 06.12.13.
 */
public class DataBaseHelper extends OrmLiteSqliteOpenHelper {

    // name of the database file for your application -- change to something
    // appropriate for your app
    private static final String DATABASE_NAME = "hromadske.db";
    // any time you make changes to your database objects, you may have to
    // increase the database version
    private static final int DATABASE_VERSION = 2;

    private static String DB_PATH = "/data/data/com.leoart.android.hromadske/databases/";

    private static final String TAG = "DBHelper";

    // the DAO object we use to access the SimpleData table
    private Dao<Post, Integer> postsDao = null;

    private final Context myContext;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION,
                R.raw.ormlite_config);
        myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {

        try {
            TableUtils.createTable(connectionSource, Post.class);
        } catch (SQLException e) {
            Log.e(TAG, "Error while creating DB: " + e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {

        try {
            TableUtils.dropTable(connectionSource, Post.class, true);

            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(TAG, "Error while upgrading DB: " + e.getMessage());
            e.printStackTrace();
        }

    }


    /**
     * Returns the Database Access Object (DAO) for our SimpleData class. It will create it or just give the cached
     * value.
     */
    public Dao<Post, Integer> getPostsDao() throws SQLException {
        if (postsDao == null) {
            postsDao = getDao(Post.class);
        }
        return postsDao;
    }

    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();
        postsDao = null;
    }


    public void parsePosts(final String url, final int limit){
        new Thread(new Runnable() {
            public void run() {
                try {
                   parseData(url, limit);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }catch (SQLException e1){
                    e1.printStackTrace();
                }
            }
        }).start();
    }



    public  void parseData(String url, int limit) throws SQLException, IOException {

        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException ex) {
            Logger.getLogger(DataBaseHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

        Elements links = doc.getElementsByTag("a");
        Elements images = doc.getElementsByTag("img");
        Elements titles = doc.getElementsByClass("episode_name");
        Elements descriptions = doc.getElementsByClass("episode_description");
        Elements dates = doc.getElementsByClass("episode_date");
        Log.d(TAG, "Items Limit = " + limit);
        Log.d(TAG, "URL = " + url);
        for(int  i = 0; i < limit; i++){
            Post post = new Post();
            post.setId(i);
            post.setLink(links.get(i + 9).attr("href"));
            post.setVideoImageUrl(images.get(i+2).attr("src"));
            post.setLinkText(titles.get(i).text());
            post.setInfo(descriptions.get(i).text());
            post.setDate(dates.get(i).text());
            postsDao.create(post);
        }
    }
}