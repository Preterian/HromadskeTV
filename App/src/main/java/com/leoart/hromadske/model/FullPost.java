package com.leoart.hromadske.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Bogdan on 08.12.13.
 */
@DatabaseTable(tableName = "full_post")
public class FullPost {

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostBody() {
        return postBody;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getVideo_url(){return video_url;}

    public void setVideo_url(String video_url){this.video_url = video_url;}

    @DatabaseField(id = true)
    private int id;

    @DatabaseField(canBeNull = false, columnName = "post_title")
    private String postTitle;
    @DatabaseField(canBeNull = false, columnName = "post_body")
    private String postBody;
    @DatabaseField(canBeNull = false, columnName = "post_date")
    private String postDate;
    @DatabaseField(canBeNull = false, columnName = "video_url")
    private String video_url;
}
