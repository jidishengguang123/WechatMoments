/**
 *
 * Copyright 2013 LeTV Technology Co. Ltd., Inc. All rights reserved.
 *
 * @Author : qingxia
 *
 * @Description :
 *
 */

package com.thoughtworks.wechat.utils;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

/**
 * @Description: NOTE(qingxia): We use both Activity and FragmentActivity as
 *               base activity. So we have to move manager logic out of Activity
 *               and FragmentActivity
 * @author qingxia
 */
public final class ActivityManager {
    private static ActivityManager sInstance = new ActivityManager();
    private static List<Activity> sActivities = new LinkedList<Activity>();

    public static ActivityManager getInstance() {
        return sInstance;
    }

    private ActivityManager() {
        // Do nothing now.
    }

    public void finishAllActivities() {
        for (int i = sActivities.size() - 1; i >= 0; i--) {
            Activity activity = sActivities.get(i);
            if (activity.getParent() != null) {
                activity.getParent().finish();
            }
            activity.finish();
        }
    }

    /**
     * Activity入栈
     */
    public void onCreate(Activity activity) {
        assert (activity != null);
        sActivities.add(activity);
    }

    /**
     * Activity出栈
     */
    public void onDestroy(Activity activity) {
        assert (activity != null);
        // NOTE(qingxia): LinkedList will handle activity is not in list or null
        sActivities.remove(activity);
    }

}
