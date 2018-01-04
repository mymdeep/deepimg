package com.deep.imagelib.interfaces;

import java.io.ByteArrayOutputStream;
import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import com.deep.imagelib.beans.ImageConfigure;
import com.deep.imagelib.log.Logger;

/**
 * Created by wangfei on 2017/12/29.
 */

public abstract class Convertor {
    protected Bitmap bitmap ;
    protected File file;
    protected byte[] binary;
    protected ImageConfigure configure;
    protected Context context;

    public abstract File asFile();



    public abstract  byte[] asBinary();

    public abstract Bitmap asBitmap();

    public  byte[] compressImageByScale( byte[] data, int byteCount){

            if (data == null){
                return new byte[1];
            }
            if (data.length<=0){
                return new byte[1];
            }
            Bitmap bitmap = null;
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
           return compressImageByScale(bitmap,byteCount);

    }
    public  byte[] compressImageByScale(Bitmap bitmap, int byteCount){
        try {


            byte[] data =new byte[1];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();


            while (baos.toByteArray().length > byteCount ) {
                double scale = Math.sqrt(1.0 * bitmap.getByteCount() / byteCount);
                int scaledW = (int) (bitmap.getWidth() / scale);
                int scaledH = (int) (bitmap.getHeight() / scale);
                bitmap = Bitmap.createScaledBitmap(bitmap, scaledW, scaledH, true);
                baos.reset();
                bitmap.compress(configure.compressFormat, 100, baos);
                data = baos.toByteArray();
            }

            if (baos.toByteArray().length > byteCount) {
                Logger.error("压缩出错");
                return new byte[1];
            } else {
                return data;
            }
        }catch (Throwable e){
            e.printStackTrace();
        }
        return new byte[1];
    }
    public  byte[] compressImageByScale( byte[] data, int width,int height){


            if (data == null){
                return new byte[1];
            }
            if (data.length<=0){
                return new byte[1];
            }
            Bitmap bitmap = null;
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            return compressImageByScale(bitmap,width,height);



    }
    public  byte[] compressImageByScale( Bitmap bitmap, int width,int height){
        try {
            long time1 = System.currentTimeMillis();
            byte[] data;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int scaledW = width;
            int scaledH = height;
            bitmap = Bitmap.createScaledBitmap(bitmap, scaledW, scaledH, true);
            baos.reset();
            bitmap.compress(configure.compressFormat, 100, baos);
            data = baos.toByteArray();
            long time2 = System.currentTimeMillis();

            return data;

        }catch (Throwable e){
            e.printStackTrace();
        }
        return new byte[1];
    }
    public  byte[] compressImageByQuality( byte[] datas,  int byteCount){

        if (datas != null && datas.length >= byteCount) {

            Bitmap tmpBitmap = BitmapFactory.decodeByteArray(datas, 0,
                datas.length);
           return compressImageByQuality(tmpBitmap,byteCount);

        }

        return datas;
    }
    public  byte[] compressImageByQuality( Bitmap bitmap,  int byteCount){
        boolean isFinish = false;

        if (bitmap != null) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            int times = 1;
            // 循环压缩图片
            double percentage = 1.0;
            double v= byteCount/bitmap.getByteCount();
            Logger.single(0,"压缩基准值为："+v);
            v = Math.max(v,0.2);
            while (!isFinish && times <= 10) {
                percentage = Math.pow(v, times);
                int quality = (int) (100 * percentage);
                bitmap.compress(configure.compressFormat, quality, outputStream);
                long time2 = System.currentTimeMillis();
                if ( outputStream.size() < byteCount) {
                    isFinish = true;
                } else {
                    outputStream.reset();
                    times++;
                }

            } // end of while


            byte[] compress_datas = outputStream.toByteArray();
            if (!bitmap.isRecycled()) {
                bitmap.recycle();
            }
            // 最后压缩得到的缩略图为0,原因 : 原始图片过大
            if (compress_datas != null && compress_datas.length <= 0) {
                Logger.error("压缩出错");
            }
            return compress_datas;

        }

        return new byte[1];
    }
}
