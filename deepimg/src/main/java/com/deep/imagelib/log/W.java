package com.deep.imagelib.log;

import android.util.Log;

/**
 * Created by wangfei on 2017/11/29.
 */

public class W implements UInterface{
    @Override
    public void log(String tag, String message) {
        Log.w(tag,message);
    }
}
