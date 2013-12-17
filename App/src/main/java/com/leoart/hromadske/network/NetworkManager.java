package com.leoart.hromadske.network;

import android.util.Log;

import com.j256.ormlite.stmt.UpdateBuilder;
import com.leoart.hromadske.HromadskeApp;
import com.leoart.hromadske.model.Post;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Bogdan on 17.12.13.
 */
public class NetworkManager {


    private static final String TAG = "NetworkManager";

    /*
        @param url - this is video or interview url.
         */
    public static void getPostsAsync(final String url, final int page, final  int limit){

        HromadskeApp.getThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {

                Document doc = null;
                try {
                    doc = Jsoup.connect(url).get();
                } catch (IOException ex) {

                }

                Elements links = doc.getElementsByTag("a");
                Elements images = doc.getElementsByTag("img");
                Elements titles = doc.getElementsByClass("episode_name");
                Elements descriptions = doc.getElementsByClass("episode_description");
                Elements dates = doc.getElementsByClass("episode_date");

                int newLimit = limit;
                if(limit>titles.size()){
                    newLimit=titles.size();
                }

                Log.d(TAG, "Items Limit = " + limit);
                Log.d(TAG, "URL = " + url);


                for(int  i = page; i < newLimit; i++){
                    Post post = new Post();
                    post.setId(i);
                    post.setLink(links.get(i + 9).attr("href"));
                    post.setVideoImageUrl(images.get(i+2).attr("src"));
                    post.setLinkText(titles.get(i).text());
                    post.setInfo(descriptions.get(i).text());
                    post.setDate(dates.get(i).text());
                    try {
                        HromadskeApp.getDatabaseHelper().getDao(Post.class).createOrUpdate(post);
                    } catch (SQLException e) {
                        Log.d(TAG, "Error loading data to dataBase: " + e.getMessage());
                        e.printStackTrace();
                    }
                    // postsDao.create(post);
                }
            }
        });
    }
}
