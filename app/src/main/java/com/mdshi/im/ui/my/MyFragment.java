package com.mdshi.im.ui.my;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.mdshi.common.base.BaseFragment;
import com.mdshi.common.constan.UserData;
import com.mdshi.common.image.AvatarConfig;
import com.mdshi.common.image.ImageLoader;
import com.mdshi.im.R;
import com.mdshi.im.ui.userui.EditUserActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends BaseFragment {


    @Inject
    UserData userData;
    @Inject
    ImageLoader imageLoader;

    private View root;
    private ImageView ivAvatar;
    private TextView tvUserName;
    private TextView tvUserInfo;

    RecyclerView menuRV;
    private MenuAdapter menuAdapter;

    public MyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_my, container, false);
        initView();
        initMenu();
        initData();
        return root;
    }

    private void initMenu() {
        List<Menu> data = new ArrayList<>();

        data.add(new Menu(R.drawable.ic_setting, "设置", null, false, Menu.MENU_SETTING));

        menuAdapter.setNewData(data);
    }

    private void initData() {
        userData.observe(this, userEntity -> {
            if (userEntity != null) {
                imageLoader.loadImaToIv(new AvatarConfig(ivAvatar,userEntity.avatar));
                tvUserName.setText(userEntity.userName);
                tvUserInfo.setText(userEntity.userID+"");
            }
        });
    }

    private void initView() {
        ivAvatar = root.findViewById(R.id.iv_avatar);
        tvUserName = root.findViewById(R.id.tv_user_name);
        tvUserInfo = root.findViewById(R.id.tv_user_info);

        ivAvatar.setOnClickListener(v -> EditUserActivity.start(getActivity()));

        menuRV = root.findViewById(R.id.recycler_view);
        menuRV.setLayoutManager(new GridLayoutManager(getContext(), 4));
        menuRV.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Menu menu = (Menu) adapter.getItem(position);
                menuClick(menu.getType());
            }
        });
        menuAdapter = new MenuAdapter(null);
        menuRV.setAdapter(menuAdapter);
    }

    private void menuClick(int type) {
        switch (type) {
            case Menu.MENU_SETTING:

                break;
        }
    }

}
