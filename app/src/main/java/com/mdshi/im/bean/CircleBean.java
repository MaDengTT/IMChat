package com.mdshi.im.bean;

import com.mdshi.common.db.entity.UserEntity;

/**
 * Created by MaDeng on 2018/10/19.
 */
public class CircleBean {

    /**
     * Id : 2
     * userId : 10016
     * contentText : 12345
     * imgUrls : 5456465.jpg,7878.jpg
     * createTime : 2018-10-18T18:09:34.000Z
     * user_id : 10016
     * userInfo : {"userID":10016,"userName":"18538008584","date":"2018-10-17T19:40:02.000Z","email":null,"phone":"18538008584","avatar":"group1/M00/00/00/wKgABFvIAJKATRvQAAArb-JBPio492.jpg","id":10016}
     */

    private int Id;
    private long userId;
    private String contentText;
    private String imgUrls;
    private String createTime;
    private int user_id;
    private UserEntity userInfo;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public String getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(String imgUrls) {
        this.imgUrls = imgUrls;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public UserEntity getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserEntity userInfo) {
        this.userInfo = userInfo;
    }

}
