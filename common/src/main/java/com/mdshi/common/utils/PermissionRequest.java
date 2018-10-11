package com.mdshi.common.utils;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;

public class PermissionRequest {
    private static final String TAG = PermissionRequest.class.getSimpleName();
    private static PermissionFragment fragment;

    private static PermissionRequest permissionRequest;

    public static PermissionRequest getInstance(FragmentActivity activity){
        if(permissionRequest == null) {
            permissionRequest = new PermissionRequest(activity);
        }
        return permissionRequest;
    }

    public static PermissionRequest getInstance(Fragment fragment){
        if(permissionRequest == null) {
            permissionRequest = new PermissionRequest(fragment);
        }
        return permissionRequest;
    }

    private PermissionRequest(FragmentActivity activity) {
        fragment = getFragmentInstance(activity.getSupportFragmentManager());
    }

    private PermissionRequest(Fragment fragment) {
        PermissionRequest.fragment = getFragmentInstance(fragment.getChildFragmentManager());
    }


    /**
     * 获取fragment单例
     * @param fragmentManager
     * @return
     */
    private PermissionFragment getFragmentInstance(FragmentManager fragmentManager) {
        if(findFragment(fragmentManager)==null) {
            fragment = new PermissionFragment();
            fragmentManager.beginTransaction()
                    .add(fragment,TAG).commitNow();
            return fragment;
        }
        return findFragment(fragmentManager);
    }

    private PermissionFragment findFragment(FragmentManager fragmentManager) {
        return (PermissionFragment)fragmentManager.findFragmentByTag(TAG);
    }

    /**
     * 请求权限，根据是否拥有权限，是否拒绝再次请求等调用不同回调
     * @param listener          请求回调
     * @param permissions       请求的权限数组
     */
    public void requestPermission(@NonNull PermissionListener listener, String[] permissions) {
        fragment.requestPermissions(permissions,listener);
    }



    /**
     * 权限请求结果回调
     */
    public interface PermissionListener {
        /**
         * 通过授权
         */
        void permissionGranted();

        /**
         * 拒绝授权
         * @param permissions       被拒绝的权限
         */
        void permissionDenied(ArrayList<String> permissions);

        /**
         * 勾选不再询问按钮，做一些提示
         * @param permissions       勾选不再询问的权限
         */
        void permissionNeverAsk(ArrayList<String> permissions);
    }
}