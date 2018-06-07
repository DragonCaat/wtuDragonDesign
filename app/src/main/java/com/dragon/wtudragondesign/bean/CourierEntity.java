package com.dragon.wtudragondesign.bean;

/**
 * Created by dragon on 2018/3/20.
 */

public class CourierEntity {
    /**
     * id : 1
     * title : 第二条发布信息更新
     * content : 测试第二条数据更新
     * amount : 100
     * imgSrc : /files/image/publish/2018/4/18/15240194343512162133.jpg
     * publisherId : 3
     * reciverId : 3
     * publishTime : 2018-04-18 10:32
     * publisherUserName : jason
     * publisherUserHeadImg : /files/image/head/2018/4/17/152395732294428526819.jpg
     * reciverUserName : jason
     * reciverUserHeadImg : /files/image/head/2018/4/17/152395732294428526819.jpg
     * recived : true
     */

    private int id;
    private String title;
    private String content;
    private int amount;
    private String imgSrc;
    private int publisherId;
    private int reciverId;
    private String publishTime;
    private String publisherUserName;
    private String publisherUserHeadImg;
    private String reciverUserName;
    private String reciverUserHeadImg;
    private boolean recived;

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

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    public int getReciverId() {
        return reciverId;
    }

    public void setReciverId(int reciverId) {
        this.reciverId = reciverId;
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

    public String getPublisherUserHeadImg() {
        return publisherUserHeadImg;
    }

    public void setPublisherUserHeadImg(String publisherUserHeadImg) {
        this.publisherUserHeadImg = publisherUserHeadImg;
    }

    public String getReciverUserName() {
        return reciverUserName;
    }

    public void setReciverUserName(String reciverUserName) {
        this.reciverUserName = reciverUserName;
    }

    public String getReciverUserHeadImg() {
        return reciverUserHeadImg;
    }

    public void setReciverUserHeadImg(String reciverUserHeadImg) {
        this.reciverUserHeadImg = reciverUserHeadImg;
    }

    public boolean isRecived() {
        return recived;
    }

    public void setRecived(boolean recived) {
        this.recived = recived;
    }
}
