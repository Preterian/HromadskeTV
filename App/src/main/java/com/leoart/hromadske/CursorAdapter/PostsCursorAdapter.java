package com.leoart.hromadske.CursorAdapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.leoart.hromadske.R;

/**
 * Created by Bogdan on 06.12.13.
 */
public class PostsCursorAdapter extends BaseAdapter {

    public PostsCursorAdapter(Context context, Cursor cursor){
        this.setContext(context);
        this.setCursor(cursor);
    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder{
        public TextView postDate;
        public TextView postTitle;
        public TextView postInfo;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder vh;

      //  if(convertView == null){
            LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.post_cell, parent, false);

            vh = new ViewHolder();
            vh.postDate = (TextView) convertView.findViewById(R.id.post_date);
            vh.postInfo = (TextView) convertView.findViewById(R.id.post_info);
            vh.postTitle = (TextView) convertView.findViewById(R.id.post_title);
       // }else{
       //     vh = (ViewHolder) convertView.getTag();
        //}

        getCursor().moveToPosition(position);

        String postTitle = getCursor().getString(getCursor().getColumnIndex("link_text"));
        String postInfo = getCursor().getString(getCursor().getColumnIndex("link_info"));
        String postDate = getCursor().getString(getCursor().getColumnIndex("date"));

        if(!postInfo.equals("") && !postTitle.equals("") && !postDate.equals("")){
            vh.postInfo.setText(postInfo);
            vh.postTitle.setText(postTitle);
            vh.postDate.setText(postDate);
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
}
