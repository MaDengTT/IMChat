package com.mdshi.im.ui.show;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mdshi.common.base.BaseFragment;
import com.mdshi.common.db.entity.CircleEntity;
import com.mdshi.common.vo.Resource;
import com.mdshi.common.vo.Status;
import com.mdshi.im.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ShowFragment extends BaseFragment {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    Unbinder unbinder;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    private ShowViewModel mViewModel;

    @Inject
    ViewModelProvider.Factory factory;

    @Inject
    ShowAdapter adapter;

    public static ShowFragment newInstance() {
        return new ShowFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this, factory).get(ShowViewModel.class);
        ivAdd.setVisibility(View.VISIBLE);
        initData();
    }

    private void initData() {
        mViewModel.getCircleData().observe(this, listResource -> {
            if (listResource.data!=null) {
                adapter.setNewData(listResource.data);
            }
            if (listResource.status == Status.SUCCESS) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.iv_add)
    public void onViewClicked() {
        UploadActivity.start(getActivity());
    }
}
