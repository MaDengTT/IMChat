package com.mdshi.common.db.bean;

import java.util.Date;

/**
 * Created by MaDeng on 2018/10/22.
 */
public class MessageListBean {

    public long sessionId;

    public long contactsId;
    public String userName;
    public String contactsName;
    public String contactsAvatar;

    public String content;
    public int unReadNum;
    public Date createTime;
}
