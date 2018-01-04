package com.deep.imagelib.impl;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.method.Touch;
import com.deep.imagelib.beans.CompressStyle;
import com.deep.imagelib.beans.ImageConfigure;
import com.deep.imagelib.interfaces.Convertor;
import com.deep.imagelib.utils.DeepUtils;
import com.deep.imagelib.utils.ToUtils;

/**
 * Created by wangfei on 2018/1/2.
 */

public class FileConvertor extends Convertor{


    private File myFile;
    public FileConvertor(Context context, File file, ImageConfigure configure) {
        this.context = context;
        this.myFile = file;
        this.configure  =  configure;
    }
    @Override
    public File asFile() {
        if (configure.compressStyle == CompressStyle.Origin){
            return myFile;
        }else {
            if (file == null){
                file = ToUtils.binary2File(asBinary(),configure);
            }

            return file ;
        }

    }

    @Override
    public byte[] asBinary() {
        if (binary == null){
            binary = ToUtils.file2Binary(myFile,configure.compressFormat);
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
