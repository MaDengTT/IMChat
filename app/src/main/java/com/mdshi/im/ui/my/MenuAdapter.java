package com.mdshi.im.ui.my;

import android.support.annotation.Nullable;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mdshi.im.R;

import java.util.List;

/**
 * Created by MaDeng on 2018/1/23.
 */

public class MenuAdapter extends BaseQuickAdapter<Menu,BaseViewHolder> {
    public MenuAdapter(@Nullable List<Menu> data) {
        super(R.layout.my_menu_layout,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Menu item) {
        helper.setImageResource(R.id.iv_menu_ic, item.getIcDrawable())
                .setText(R.id.tv_menu_name, item.getMenuName())
                .setText(R.id.tv_menu_info, item.getMenuInfo())
                .setVisible(R.id.tv_menu_tips, item.isTips);
    }
}
