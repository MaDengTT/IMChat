package com.mdshi.im.ui.my;

/**
 * Created by MaDeng on 2018/1/23.
 */

public class Menu {

    public static final int MENU_ADDRESS = 0x12;
    public static final int MENU_SETTING = 0x13;
    public static final int MENU_HELP = 0x14;
    public static final int MENU_SERVICE = 0x15;
    public static final int MENU_INTEGRATOR = 0x11;
    public static final int MENU_INTEGRATOR_SHOP = 0x16;
    public static final int MENU_INVITE = 0x17;
    public static final int MENU_COUPON = 0x18;
    public static final int MENU_GROUP = 0x19;
    public static final int MENU_COLLECT = 0x20;
    public static final int MENU_COOKIES = 0x21;
    public static final int MENU_ACTIVITY = 0x22;

    int icDrawable;     //菜单图片
    String menuName;    //菜单名称
    String menuInfo;    //菜单信息
    boolean isTips;      //是否显示Tips
    int type;           //Type

    public Menu() {
    }

    public Menu(int icDrawable, String menuName, String menuInfo, boolean isTips, int type) {
        this.icDrawable = icDrawable;
        this.menuName = menuName;
        this.menuInfo = menuInfo;
        this.isTips = isTips;
        this.type = type;
    }


    public boolean isTips() {
        return isTips;
    }

    public void setTips(boolean tips) {
        isTips = tips;
    }

    public int getIcDrawable() {
        return icDrawable;
    }

    public void setIcDrawable(int icDrawable) {
        this.icDrawable = icDrawable;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuInfo() {
        return menuInfo;
    }

    public void setMenuInfo(String menuInfo) {
        this.menuInfo = menuInfo;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
