package com.mdshi.common.utils;

import android.app.ActivityManager;
import android.content.Context;

/**
 * @author Rabtman
 */

public class SystemUtils {

  /**
   * 获取进程名称
   */
  public static String getCurProcessName(Context context) {
    int pid = android.os.Process.myPid();
    ActivityManager activityManager = (ActivityManager)context
        .getSystemService(Context.ACTIVITY_SERVICE);
    if (activityManager != null) {
      for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
          .getRunningAppProcesses()) {
        if (appProcess.pid == pid) {
          return appProcess.processName;
        }
      }
    }
    return "";
  }
}
