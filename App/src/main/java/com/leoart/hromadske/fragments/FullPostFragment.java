package com.leoart.hromadske.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leoart.hromadske.R;
import com.leoart.hromadske.model.FullPost;
import com.leoart.hromadske.parser.UaEnergyParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Bogdan on 08.12.13.
 */
public class FullPostFragment extends Fragment {


    final String LOG_TAG = "FullPostFRAGMENT";

    TextView postDateView;
    TextView postAuthorView;
    TextView postTitleView;
    TextView postBodyView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.full_post, container, false);


        postAuthorView = (TextView) view.findViewById(R.id.full_post_author);
        postDateView = (TextView) view.findViewById(R.id.full_post_date);
        postTitleView = (TextView) view.findViewById(R.id.full_post_title);
        postBodyView = (TextView) view.findViewById(R.id.full_post_aritcle);

        //String url = null;
        Bundle extras = getArguments();
        //if (extras != null) {
        final String url = "http://hromadske.tv/".concat(extras.getString("postUrl"));
        final String postTitle = extras.getString("postTitle");
        final String postDate = extras.getString("postDate");
        // }

        Log.d(LOG_TAG, url);

        new Thread(new Runnable() {
            public void run() {


                Document doc = null;
                try {
                    doc = Jsoup.connect(url).get();
                } catch (IOException ex) {
                    Logger.getLogger(UaEnergyParser.class.getName()).log(Level.SEVERE, null, ex);
                }

                Elements videoSrc = doc.getElementsByTag("iframe");


              /*  for(int i = 0; i < videoSrc.size(); i++){
                    System.out.println(videoSrc.get(i).attr("src"));
                }*/


                Elements description = doc.getElementsByClass("video_text");
                //System.out.println(description.get(0).text());

                final String postBody = description.get(0).text();

//                fullPost.setVideo_url(videoSrc.get(0).attr("src"));

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        // if(fullPost.getPostDate() != null && !fullPost.getPostDate().equals(""))
                        postDateView.setText(postDate);

                        // if(fullPost.getPostTitle() != null && !fullPost.getPostTitle().equals(""))
                        postTitleView.setText(postTitle);

                        // if(fullPost.getPostBody() != null && !fullPost.getPostBody().equals(""))
                        postBodyView.setText(postBody);


                    }
                });

            }
        }).start();


        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(LOG_TAG, "Fragment1 onAttach");
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "Fragment1 onCreate");
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(LOG_TAG, "Fragment1 onActivityCreated");
    }

    public void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "Fragment1 onStart");
    }

    public void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "Fragment1 onResume");
    }

    public void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "Fragment1 onPause");
    }

    public void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "Fragment1 onStop");
    }

    public void onDestroyView() {
        super.onDestroyView();
        Log.d(LOG_TAG, "Fragment1 onDestroyView");
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "Fragment1 onDestroy");
    }

    public void onDetach() {
        super.onDetach();
        Log.d(LOG_TAG, "Fragment1 onDetach");
    }

}
