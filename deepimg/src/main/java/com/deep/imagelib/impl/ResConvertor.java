package com.deep.imagelib.impl;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import com.deep.imagelib.beans.CompressStyle;
import com.deep.imagelib.beans.ImageConfigure;
import com.deep.imagelib.beans.ImageConfigure.Expect;
import com.deep.imagelib.interfaces.Convertor;
import com.deep.imagelib.log.Logger;
import com.deep.imagelib.utils.ToUtils;

/**
 * Created by wangfei on 2017/12/29.
 */

public class ResConvertor extends Convertor{

    private int id = 0;


    public ResConvertor(Context context, int resId, ImageConfigure configure) {
        this.context = context;
        this.id = resId;
        this.configure  =  configure;
    }
    @Override
    public File asFile() {
        if (file == null){

            file = ToUtils.binary2File(asBinary(),configure);

        }

        return file ;
    }



    @Override
    public byte[] asBinary() {
        if (binary == null){
         binary = ToUtils.res2Binary(context,id,configure.isLoadImgByCompress,configure.compressFormat);
            if (configure.compressStyle == CompressStyle.QUALITY&&configure.expect.maxCount != 0 ){

                binary = compressImageByQuality(binary, configure.expect.maxCount);
            }else if (configure.compressStyle == CompressStyle.SCALE){
                if (configure.expect.maxCount != 0){
                    binary = compressImageByScale(binary,configure.expect.maxCount);
                }else if (configure.expect.width!=0&&configure.expect.height!=0){
                    binary = compressImageByScale(binary,configure.expect.width,configure.expect.height);
                }
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
          bitmap =   ToUtils.binary2Bitmap(asBinary());
        }
        return bitmap;
    }
}
