package com.leoart.hromadske.CursorAdapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.leoart.hromadske.R;
import com.leoart.hromadske.VolleySingleton;


/**
 * Created by Bogdan on 16.12.13.
 */
public class PostsGridCursorAdapter extends BaseAdapter {

    public PostsGridCursorAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;

        defaultThumb = BitmapFactory.decodeResource(context.getResources(), R.drawable.abc_ab_bottom_solid_dark_holo);

        RequestQueue queue = VolleySingleton.getInstance(context).getRequestQueue();
        //or
        mImageLoader = VolleySingleton.getInstance(context).getImageLoader();
    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    static class ViewHolder{
        //public ImageView thumb;
        public NetworkImageView videoImg;
        public TextView postDate;
        public TextView postTitle;
        public TextView postDescription;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = li.inflate(R.layout.video_post_grid_cell, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.videoImg = (NetworkImageView) convertView.findViewById(R.id.grid_video_img);
            viewHolder.postDate = (TextView) convertView.findViewById(R.id.grid_date);
            viewHolder.postTitle = (TextView) convertView.findViewById(R.id.grid_title);
            viewHolder.postDescription = (TextView) convertView.findViewById(R.id.grid_description);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        getCursor().moveToPosition(position);

        String date = getCursor().getString(getCursor().getColumnIndex("date"));
        String title = getCursor().getString(getCursor().getColumnIndex("link_text"));
        String description = getCursor().getString(getCursor().getColumnIndex("link_info"));
        String videoImage_url = getCursor().getString(getCursor().getColumnIndex("video_image_url"));

        if(date!= null && !date.equals("")){
            viewHolder.postDate.setText(date);
        }else {
            viewHolder.postDate.setText("");
        }

        if(title!=null && !title.equals("")){
            viewHolder.postTitle.setText(title);
        }else {
            viewHolder.postDate.setText("");
        }

        if(description != null && !description.equals("")){
            viewHolder.postDescription.setText(description);
        }else {
            viewHolder.postDescription.setText("");
        }

        if(videoImage_url!= null && !videoImage_url.equals("")){
            viewHolder.videoImg.setImageUrl(videoImage_url, mImageLoader);
        }

        return convertView;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Cursor getCursor() {
        return cursor;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    private Context context;
    private Cursor cursor;
    private Bitmap defaultThumb;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
}
