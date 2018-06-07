package com.dragon.wtudragondesign.bean;

/**
 * Created by dragon on 2018/4/21.
 *
 */

public class CourierDetailEntity {

    /**
     * id : 13
     * title : 求一本思修书，要2016版的
     * content : 最近上课需要一本思修书，2016版的
     * amount : 10
     * publisherId : 22
     * publishTime : 2018-04-21 17:45
     * publisherUserName : 123456
     * recived : false
     * publisherUserHeadImg : /files/image/head/2018/4/28/152489233176293401091.jpg
     */

    private int id;
    private String title;
    private String content;
    private int amount;
    private int publisherId;
    private String publishTime;
    private String publisherUserName;
    private boolean recived;

    private String publisherUserHeadImg;


    public String getPublisherUserHeadImg() {
        return publisherUserHeadImg;
    }

    public void setPublisherUserHeadImg(String publisherUserHeadImg) {
        this.publisherUserHeadImg = publisherUserHeadImg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getPublisherUserName() {
        return publisherUserName;
    }

    public void setPublisherUserName(String publisherUserName) {
        this.publisherUserName = publisherUserName;
    }

    public boolean isRecived() {
        return recived;
    }

    public void setRecived(boolean recived) {
        this.recived = recived;
    }
}
