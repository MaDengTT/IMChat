package com.mdshi.im.ui.show;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.mdshi.common.image.ImageLoader;
import com.mdshi.im.R;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by MaDeng on 2018/3/19.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private List<String> mDatas;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    private ItemOnClickListent listent;
    private ImageLoader loader;


    public interface ItemOnClickListent {
        public void onClick(View view, int position);
    }

    public void setOnItemClickListent(ItemOnClickListent listent) {
        this.listent = listent;
    }

    public ImageAdapter(List<String> images,Context mContext,ImageLoader loader,ItemOnClickListent listent) {
        this.loader = loader;
        this.mDatas = images;
        this.mContext = mContext;
        this.mLayoutInflater = LayoutInflater.from(mContext);
        this.listent = listent;
    }

    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(mLayoutInflater.inflate(R.layout.item_iamge, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ImageAdapter.ViewHolder holder, final int position) {
        holder.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listent != null) {
                    listent.onClick(v,position);
                }
            }
        });
        String s = mDatas.get(position);
        if (TextUtils.equals("0000", s)) {
            loader.loadImgToIv(R.drawable.plus,holder.imageView);
        }else {
            loader.loadImgToIv(s,holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return mDatas == null?0:mDatas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.sdv);
        }

        private void setListener(View.OnClickListener listener) {
            imageView.setOnClickListener(listener);
        }
    }


}
