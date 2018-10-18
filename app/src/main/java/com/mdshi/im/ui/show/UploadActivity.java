package com.mdshi.im.ui.show;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.mdshi.common.base.BaseActivity;
import com.mdshi.common.image.ImageLoader;
import com.mdshi.im.R;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UploadActivity extends BaseActivity {

    ImageAdapter adapter;

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.ll_ed)
    LinearLayout llEd;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.sw_to_square)
    Switch swToSquare;
    @BindView(R.id.ll_to_square)
    RelativeLayout llToSquare;
    @BindView(R.id.sw_to_team)
    Switch swToTeam;
    @BindView(R.id.ll_to_team)
    RelativeLayout llToTeam;
    private List<String> images;

    @Inject
    ImageLoader loader;

    public static void start(Context context) {
        Intent starter = new Intent(context, UploadActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploade);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        images = new ArrayList<>();
        images.add("0000");
        recycler.setLayoutManager(new GridLayoutManager(this, 4));
        adapter = new ImageAdapter(images, this, loader, (view, position) -> {
            if (view.getId() == R.id.sdv) {
                putImage(getImageSize());
            }
        });
        recycler.setAdapter(adapter);
    }

    private int getImageSize() {

        if (images.get(images.size() - 1).equals("0000")) {
            return 9 - images.size() + 1;
        } else {
            return 0;
        }
    }


    private void setDataToAdapter(List<String> datas) {
        if (images.get(images.size() - 1).equals("0000")) {
            images.remove(images.size() - 1);
        }
        images.addAll(datas);
        if (images.size() < 9) {
            images.add("0000");
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onImageData(List<String> data) {
        setDataToAdapter(data);
    }
}
