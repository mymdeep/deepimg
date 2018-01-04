package com.deep.imagelib.beans;

import android.graphics.Bitmap;

/**
 * Created by wangfei on 2017/12/29.
 */

public class ImageConfigure {
    public String filename = "";
    public String directoryname = "";
    public boolean isLoadImgByCompress = true;
    public Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.JPEG;
    public CompressStyle compressStyle = CompressStyle.Origin;
    public Expect expect = new Expect();
    public static class Expect{
        public  int width = 0;
        public  int height = 0;
        public  int maxCount = 0;
    }
}
