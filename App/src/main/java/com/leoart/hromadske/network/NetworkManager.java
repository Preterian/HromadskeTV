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
    public static void getPostsAsync(final int page, final  int limit){

        HromadskeApp.getThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {

                int newLimit = limit;
                if(limit>HromadskeApp.getTitles().size()){
                    newLimit=HromadskeApp.getTitles().size();
                }

                Log.d(TAG, "Items Limit = " + limit);

                for(int  i = page; i < newLimit; i++){
                    Post post = new Post();
                    post.setId(i);
                    post.setLink(HromadskeApp.getLinks().get(i + 9).attr("href"));
                    post.setVideoImageUrl(HromadskeApp.getImages().get(i+2).attr("src"));
                    post.setLinkText(HromadskeApp.getTitles().get(i).text());
                    post.setInfo(HromadskeApp.getDescriptions().get(i).text());
                    post.setDate(HromadskeApp.getDates().get(i).text());
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
