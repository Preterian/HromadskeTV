package com.leoart.hromadske.network;

import android.util.Log;

import com.leoart.hromadske.HromadskeApp;
import com.leoart.hromadske.model.Post;

import java.sql.SQLException;

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



             /*  int newLimit = limit;
                if(limit> HromadskeApp.getInstance().getTitles().size()){
                    newLimit=DataSingleton.getTitles().size();
                }

                Log.d(TAG, "Items Limit = " + limit);

                for(int  i = page; i < newLimit; i++){
                    Post post = new Post();
                    post.setId(i);
                    post.setLink(DataSingleton.getInstance().getLinks().get(i + 9).attr("href"));
                    post.setVideoImageUrl(DataSingleton.getInstance().getImages().get(i+2).attr("src"));
                    post.setLinkText(DataSingleton.getInstance().getTitles().get(i).text());
                    post.setInfo(DataSingleton.getInstance().getDescriptions().get(i).text());
                    post.setDate(DataSingleton.getInstance().getDates().get(i).text());
                    try {
                        HromadskeApp.getDatabaseHelper().getDao(Post.class).createOrUpdate(post);
                    } catch (SQLException e) {
                        Log.d(TAG, "Error loading data to dataBase: " + e.getMessage());
                        e.printStackTrace();
                    }

                }*/
            }
        });
    }
}
