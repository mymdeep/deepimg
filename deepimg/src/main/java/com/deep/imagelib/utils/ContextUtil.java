package com.deep.imagelib.utils;

import android.content.Context;

/**
 * Created by wangfei on 2017/12/29.
 */

public class ContextUtil {
    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    public static void setContext(Context mContext) {
        ContextUtil.mContext = mContext.getApplicationContext();
    }
}
