package com.deep.imagelib.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.text.TextUtils;
import com.deep.imagelib.log.Logger;

/**
 * Created by wangfei on 2017/12/29.
 */

public class DeepUtils {

    public static File getDirectory(String suffix){
        if (TextUtils.isEmpty(suffix)){
            String path= "";
            try {
                if (Environment.getExternalStorageDirectory()!=null&& !TextUtils.isEmpty(Environment.getExternalStorageDirectory().getCanonicalPath())){
                    path = Environment.getExternalStorageDirectory().getCanonicalPath();
                }else if (!TextUtils.isEmpty( ContextUtil.getContext().getCacheDir().getCanonicalPath())){
                    path =ContextUtil.getContext().getCacheDir().getCanonicalPath();
                }else {
                    Logger.error("没有存储路径");
                }

                Logger.error("path = "+path+File.separator+"deep");
                File dirFile = new File(path +File.separator+ "deep");
                if (dirFile != null && !dirFile.exists()) {
                    dirFile.mkdirs();
                }
                return dirFile;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            File dirFile = new File(suffix);
            if (dirFile != null && !dirFile.exists()) {
                dirFile.mkdirs();
            }
            return dirFile;
        }
        return null;
    }
    public static String getFileName(String filename) {
        if (TextUtils.isEmpty(filename)){
            long currentTime = System.currentTimeMillis();
            String fn = String.valueOf(currentTime);
            return fn;
        }else {
            return filename;
        }

    }
    public static File generateCacheFile(String suffix,String filename) throws IOException {
        Logger.error("目录："+getDirectory(suffix)+"   文件名："+getFileName(filename));
        File file = new File(getDirectory(suffix), getFileName(filename));
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();

        return file;
    }
    public static File getFileFromBytes(byte[] b, File outFile) {
        BufferedOutputStream stream = null;
        File file = outFile;
        try {
            FileOutputStream fstream = new FileOutputStream(file);
            stream = new BufferedOutputStream(fstream);
            stream.write(b);
        } catch (Exception e) {
         e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return file;
    }
    static Bitmap drawableToBitmap(Drawable drawable) // drawable 转换成bitmap
    {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }
}
