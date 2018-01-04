package com.deep.imagelib.impl;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import com.deep.imagelib.beans.CompressStyle;
import com.deep.imagelib.beans.ImageConfigure;
import com.deep.imagelib.interfaces.Convertor;
import com.deep.imagelib.log.Logger;
import com.deep.imagelib.utils.ToUtils;

/**
 * Created by wangfei on 2018/1/2.
 */

public class BitmapConvertor extends Convertor {
    private Bitmap myBitmap;
    public BitmapConvertor(Context context, Bitmap bitmap, ImageConfigure configure) {
        this.context = context;
        this.myBitmap = bitmap;
        this.configure  =  configure;
    }
    @Override
    public File asFile() {
        if (file == null){
            if (configure.compressStyle == CompressStyle.Origin){

                file = ToUtils.bitmap2File(myBitmap,configure);

            }else {
                file = ToUtils.binary2File(asBinary(),configure);
            }

        }

        return file ;
    }

    @Override
    public byte[] asBinary() {
        if (binary == null){

            if (configure.compressStyle == CompressStyle.QUALITY&&configure.expect.maxCount != 0 ){

                binary = compressImageByQuality(myBitmap, configure.expect.maxCount);
            }else if (configure.compressStyle == CompressStyle.SCALE){
                if (configure.expect.maxCount != 0){
                    binary = compressImageByScale(myBitmap,configure.expect.maxCount);
                }else if (configure.expect.width!=0&&configure.expect.height!=0){
                    binary = compressImageByScale(myBitmap,configure.expect.width,configure.expect.height);
                }
            }else {
                binary = ToUtils.bitmap2Binary(myBitmap,configure.compressFormat);
            }
            if (binary == null){
                binary = new byte[1];
            }
        }

        return binary;
    }

    @Override
    public Bitmap asBitmap() {
        if (bitmap == null){
            if (configure.compressStyle == CompressStyle.Origin){
                return myBitmap;
            }else {
                    bitmap =   ToUtils.binary2Bitmap(asBinary());
                return bitmap;
            }
        }
        else {
            return bitmap;
        }

    }
}
