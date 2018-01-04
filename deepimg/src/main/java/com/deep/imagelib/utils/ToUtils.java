package com.deep.imagelib.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import com.deep.imagelib.beans.ImageConfigure;
import com.deep.imagelib.log.Logger;

/**
 * Created by wangfei on 2017/12/29.
 */

public class ToUtils {
    public static int MAX_WIDTH = 768;
    public static float imageSize = 3072.f;
    public static int MAX_HEIGHT = 1024;
    public static File binary2File(byte[] data, ImageConfigure configure) {
        try {
            File file = DeepUtils.generateCacheFile(configure.directoryname,configure.filename);
            file = DeepUtils.getFileFromBytes(data, file);
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    public static File bitmap2File(Bitmap bitmap, ImageConfigure configure) {
        try {
            File file = DeepUtils.generateCacheFile(configure.directoryname,configure.filename);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            // 把压缩后的数据存放到baos中
            Logger.begin();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            Logger.end("bitmap2File");
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static byte[] res2Binary(Context context, int id, boolean isLoadImgByCompress, Bitmap.CompressFormat format) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (!isLoadImgByCompress) {
            Resources r = context.getResources();
            Drawable d;
            if (Build.VERSION.SDK_INT >= 21) {
                d = r.getDrawable(id, null);
            } else {
                d = r.getDrawable(id);
            }
            Bitmap bitmap = DeepUtils.drawableToBitmap(d);
            bitmap.compress(format, 100, baos);
            return baos.toByteArray();
        } else {
            byte[] data = new byte[0];
            try {
                BitmapFactory.Options opt = new BitmapFactory.Options();
                opt.inPreferredConfig = Bitmap.Config.RGB_565;
                InputStream is = context.getResources().openRawResource(id);
                Bitmap bitmap = BitmapFactory.decodeStream(is, null, opt);
                bitmap.compress(format, 100, baos);


                data = baos.toByteArray();
            }catch (Error e){
                e.printStackTrace();
            }
            return  data;
        }
    }
    public static Bitmap binary2Bitmap(byte[] data) {
        if (data != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            return bitmap;
        }
        return null;
    }
    public static byte[] file2Binary(File file, Bitmap.CompressFormat bitmapformat) {
        return imagefile2Bytes(file, bitmapformat);
    }
    private static byte[] imagefile2Bytes(File file, Bitmap.CompressFormat bitmapformat) {
        if (file == null || !file.getAbsoluteFile().exists()) {
            return null;
        }

        byte[] data = readData(file);

        if (data!=null) {
            String format = ImageFormat.checkFormat(data);
            if (ImageFormat.FORMAT_NAMES[ImageFormat.FORMAT_GIF].equals(format)) {
                return data;
            } else {
                return compressBmp(data, bitmapformat);
            }
        }
        return null;
    }
    private static byte[] readData(File file) {
        byte[] data = null;
        InputStream is = null;
        ByteArrayOutputStream bos = null;
        try {
            is = new FileInputStream(file);
            bos = new ByteArrayOutputStream();

            byte[] bytes = new byte[4 * 1024];
            int len;
            while ((len = is.read(bytes)) != -1) {
                bos.write(bytes, 0, len);
            }
            data = bos.toByteArray();
        } catch (Throwable e) {

            return new byte[1];
        }finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
               e.printStackTrace();
            }
        }
        return data;
    }
    private static byte[] compressBmp(byte[] data, Bitmap.CompressFormat format) {
        Bitmap bitmap = null;
        ByteArrayOutputStream stream = null;
        byte[] bitmapdata = null;
        try {
            BitmapFactory.Options bmpOptions = getBitmapOptions(data);
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length,
                bmpOptions);
            stream = new ByteArrayOutputStream();
            if (bitmap != null) {
                bitmap.compress(format, 100, stream);
                bitmap.recycle();
                System.gc();
            }
            bitmapdata = stream.toByteArray();
        } catch (Exception e) {
           e.printStackTrace();

        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmapdata;
    }
    private static BitmapFactory.Options getBitmapOptions(byte[] data) {
        BitmapFactory.Options bmpOptions = new BitmapFactory.Options();
        // 设置为true,表示解析Bitmap对象，该对象不占内存
        bmpOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(data, 0, data.length, bmpOptions);

        int widthRatio = (int) Math.ceil(bmpOptions.outWidth / MAX_WIDTH);
        int heightRatio = (int) Math.ceil(bmpOptions.outHeight / MAX_HEIGHT);

        /*
         * If both of the ratios are greater than 1, one of the sides of the
         * image is greater than the screen
         */
        if (heightRatio > 1 && widthRatio > 1) {
            if (heightRatio > widthRatio) {
                // Height ratio is larger, scale according to it
                bmpOptions.inSampleSize = heightRatio;
            } else {
                // Width ratio is larger, scale according to it
                bmpOptions.inSampleSize = widthRatio;
            }
        } else if (heightRatio > 2) {
            // Height ratio is larger, scale according to it
            bmpOptions.inSampleSize = heightRatio;
        } else if (widthRatio > 2) {
            // Width ratio is larger, scale according to it
            bmpOptions.inSampleSize = widthRatio;
        }
        // Decode it for real
        bmpOptions.inJustDecodeBounds = false;
        return bmpOptions;
    }
    public static byte[] bitmap2Binary(Bitmap bitmap, Bitmap.CompressFormat format) {
        return bitmap2Bytes(bitmap, format);
    }
    private static byte[] bitmap2Bytes(Bitmap bitmap, Bitmap.CompressFormat format) {
        ByteArrayOutputStream baos = null;
        if (bitmap == null || bitmap.isRecycled()) {
            return null;
        }
        try {
            baos = new ByteArrayOutputStream();
            int bitmapSize = bitmap.getRowBytes() * bitmap.getHeight() / 1024;
            int quality = 100;
            if (bitmapSize > imageSize) {
                quality = (int) ((imageSize / bitmapSize) * quality);
            }

            bitmap.compress(format, quality, baos);
            byte[] b = baos.toByteArray();
            return b;
        } catch (Exception e) {
           e.printStackTrace();
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return new byte[1];
    }

}
