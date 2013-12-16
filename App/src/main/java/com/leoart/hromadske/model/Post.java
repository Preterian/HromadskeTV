package com.leoart.hromadske.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created with IntelliJ IDEA.
 * User: Bogdan
 * Date: 18.11.13
 * Time: 21:56
 * To change this template use File | Settings | File Templates.
 */
@DatabaseTable(tableName = "post")
public class Post {
    public static final String TABLE_NAME = "post";

    @DatabaseField(id = true)
    private int id;
    @DatabaseField(canBeNull = false, columnName = "link")
    private String link;
    @DatabaseField(canBeNull = false, columnName = "link_text")
    private String linkText;
    @DatabaseField(canBeNull = false, columnName = "link_info")
    private String info;
    @DatabaseField(canBeNull = false, columnName = "date")
    private String date;
    @DatabaseField(canBeNull = false, columnName = "video_image_url")
    private String videoImageUrl;

    public Post() {

    }

    public String getVideoImageUrl() {
        return videoImageUrl;
    }

    public void setVideoImageUrl(String videoImageUrl) {
        this.videoImageUrl = videoImageUrl;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLinkText() {
        return linkText;
    }

    public void setLinkText(String linkText) {
        this.linkText = linkText;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setId(int id){
        this.id = id;
    }

}