package com.dragon.wtudragondesign.bean;

/**
 * Created by dragon on 2018/4/26.
 * 用户信息的实体 option + s
 */

public class UserDataEntity {


    /**
     * id : 21
     * userName : dragon
     * password : 4dee4b07c5b2b2fab7d23ec3d775bc92
     * reputation : 60
     * vip : true
     */

    private int id;
    private String userName;
    private String password;
    private int reputation;
    private boolean vip;

    private String nickName;
    private String sign;

    private String phone;
    private String headImg;


    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getReputation() {
        return reputation;
    }

    public void setReputation(int reputation) {
        this.reputation = reputation;
    }

    public boolean isVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }
}
