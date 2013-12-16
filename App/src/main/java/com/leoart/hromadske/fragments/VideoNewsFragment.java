package com.leoart.hromadske.fragments;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;

import com.j256.ormlite.dao.Dao;
import com.leoart.hromadske.CursorAdapter.PostsGridCursorAdapter;
import com.leoart.hromadske.HromadskeApp;
import com.leoart.hromadske.R;
import com.leoart.hromadske.model.Post;
import com.leoart.hromadske.network.Rest;

import java.sql.SQLException;

/**
 * Created by Bogdan on 16.12.13.
 */
public class VideoNewsFragment extends Fragment {
    private static final String TAG = "PostsFragment";
    final String LOG_TAG = "myLogs";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        limitPostsPerPage = 16;
        try {
            HromadskeApp.getDatabaseHelper().parsePosts("http://hromadske.tv/video/", limitPostsPerPage);
            postsDao = HromadskeApp.getDatabaseHelper().getPostsDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        view = inflater.inflate(R.layout.posts_grid, container, false);

        gridView = (GridView) view.findViewById(R.id.post_grid);

        mAdapter = new PostsGridCursorAdapter(HromadskeApp.context, getCursor());
        gridView.setAdapter(mAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long ID) {

                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {

                       /* if (!Rest.isNetworkOnline()) {
                            return;
                        }*/

                        Cursor cursor = mAdapter.getCursor();
                        cursor.moveToPosition(position);

                        int id = cursor.getInt(cursor.getColumnIndex("id"));
                        Post post;
                        try {
                            post = postsDao.queryForId(id);
                            if (post != null) {
                                Log.d(TAG, "Some post was choosed = "
                                        + post.getLinkText());


                                FragmentManager myFragmentManager = getFragmentManager();

                                FullPostFragment fragment = new FullPostFragment();


                                Bundle bundle = new Bundle();
                                bundle.putString("postUrl", post.getLink());
                                bundle.putString("postTitle", post.getLinkText());
                                bundle.putString("postDate", post.getDate());

                                fragment.setArguments(bundle);

                                FragmentTransaction fragmentTransaction = myFragmentManager
                                        .beginTransaction();
                                fragmentTransaction.replace(R.id.flContent, fragment);
                                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();


                            }
                        } catch (SQLException e) {
                            Log.e(TAG, "SQL Error: " + e.getMessage());
                            e.printStackTrace();
                        }
                    }

                };
                HromadskeApp.getThreadExecutor().execute(runnable);
            }

        });

        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                // TODO Auto-generated method stub
                Log.e("GridView", "firstVisibleItem" + firstVisibleItem + "\nLastVisibleItem" + totalItemCount);
            }
        });

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

    private boolean loading = false;
    private boolean loadedAll = false;

    private PostsGridCursorAdapter mAdapter;
    private GridView gridView;
    private View view;
    private Dao<Post, Integer> postsDao;
    private int limitPostsPerPage;

    protected Cursor getCursor() {
        Cursor cursor = HromadskeApp.getDatabaseHelper().getReadableDatabase().query(Post.TABLE_NAME, new String[]{"id", "link", "link_text", "link_info", "date", "video_image_url"}, null, null, null, null, null, null);
        return cursor;
    }
}
