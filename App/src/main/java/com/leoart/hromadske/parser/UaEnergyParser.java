package com.leoart.hromadske.parser;

import android.util.Log;

import com.leoart.hromadske.HromadskeApp;
import com.leoart.hromadske.model.FullPost;
import com.leoart.hromadske.model.Post;

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
 * Created with IntelliJ IDEA.
 * User: Bogdan
 * Date: 18.11.13
 * Time: 21:55
 * To change this template use File | Settings | File Templates.
 */
public class UaEnergyParser {

    public static final String TAG = "PARSER";

    public static  void ParseFullPost(final String url){

        new Thread(new Runnable() {
            public void run() {

                Document doc = null;
                try {
                    doc = Jsoup.connect(url).get();
                } catch (IOException ex) {
                    Logger.getLogger(UaEnergyParser.class.getName()).log(Level.SEVERE, null, ex);
                }

                //parsing post-title
                Element postTitle = doc.getElementById("left-page-col").select("h2").first();

                //parsing post-author
                Element postAuthor = doc.getElementById("post-info").select("p").first();

                //parsing post-date
                Element postDate = doc.getElementById("post-info").select("p").last();

                // parsing post-body
                Element content = doc.getElementById("post-body");

                Element postBody = content.select("p").first();


                FullPost fullPost = new FullPost();


                fullPost.setPostDate(postDate.text());
                fullPost.setPostTitle(postTitle.text());
                fullPost.setPostBody(postBody.text());

                HromadskeApp.setFullPost(fullPost);

                Log.d(TAG, fullPost.getPostDate());


            }
        }).start();

    }




    public static void main(String[] args) {

        String url = "http://ua-energy.org/post/view/1/3";
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException ex) {
            Logger.getLogger(UaEnergyParser.class.getName()).log(Level.SEVERE, null, ex);
        }


        Element content = doc.getElementById("post-list");

        // parsing posts links
        Elements links = content.getElementsByTag("a");
        // parsing posts info
        Elements info = content.select("p.info");
        // parsing posts dates
        Elements dates = content.select("div.postlist-date");

        System.out.println(links.size());
        System.out.println(info.size());

        int len = info.size();
        ArrayList<Post> posts = new ArrayList<Post>(len);
      /*  for(int  i = 0; i < len; i++){
            Post post = new Post();
            post.setLink(links.get(i).attr("href"));
            post.setLinkText(links.get(i).text());
            post.setInfo(info.get(i).text());
            posts.add(post);
        }


       /* for(int i =0; i < len; i++){
            System.out.println(posts.get(i).getLink());
            System.out.println(posts.get(i).getLinkText());
            System.out.println(posts.get(i).getInfo());
            System.out.println();
        }
         */


        // parsing news info
        Elements infoS = content.getElementsByTag("p");

        String date = null;
        int j = 0;
        for(int  i = 0; i < len; i++){
            Post post = new Post();
            if(isThisDateValid(infoS.get(i).text(),"dd.MM.yyyy" )){
                date = infoS.get(i).text();
            }
            post.setLink(links.get(i).attr("href"));
            post.setLinkText(links.get(i).text());
            post.setInfo(info.get(i).text());
            post.setDate(date);
            posts.add(post);
        }

        for(int i =0; i < len; i++){
            System.out.println(posts.get(i).getLink());
            System.out.println(posts.get(i).getLinkText());
            System.out.println(posts.get(i).getInfo());
            System.out.println(posts.get(i).getDate());
            System.out.println();
        }



    }



    public static boolean isThisDateValid(String dateToValidate, String dateFromat){

        if(dateToValidate == null){
            return false;
        }

        if(dateToValidate.length()!=10){
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
        sdf.setLenient(false);

        try {

            //if not valid, it will throw ParseException
            Date date = sdf.parse(dateToValidate);
            System.out.println(date);

        } catch (ParseException e) {

            e.printStackTrace();
            return false;
        }

        return true;
    }


}
