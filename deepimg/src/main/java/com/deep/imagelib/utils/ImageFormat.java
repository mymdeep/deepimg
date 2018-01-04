package com.deep.imagelib.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by wangfei on 2018/1/2.
 */

public class ImageFormat {

    public static final int FORMAT_JPEG = 0;

    public static final int FORMAT_GIF = 1;

    public static final int FORMAT_PNG = 2;

    public static final int FORMAT_BMP = 3;

    public static final int FORMAT_PCX = 4;

    public static final int FORMAT_IFF = 5;

    public static final int FORMAT_RAS = 6;

    public static final int FORMAT_PBM = 7;

    public static final int FORMAT_PGM = 8;

    public static final int FORMAT_PPM = 9;

    public static final int FORMAT_PSD = 10;

    public static final int FORMAT_SWF = 11;

    public static final String[] FORMAT_NAMES = {	"jpeg",
        "gif",
        "png",
        "bmp",
        "pcx",
        "iff",
        "ras",
        "pbm",
        "pgm",
        "ppm",
        "psd",
        "swf"};

    /**
     * 图片文件类型检查，未知类型返回"";
     * @param imageBytes
     * @return
     */
    public static String checkFormat(byte[] imageBytes) {
        InputStream is = null;
        try {
            is = new ByteArrayInputStream(imageBytes);
            int b1 = is.read();
            int b2 = is.read();
            if (b1 == 0x47 && b2 == 0x49) {
                return FORMAT_NAMES[FORMAT_GIF];
            } else if (b1 == 0x89 && b2 == 0x50) {
                return FORMAT_NAMES[FORMAT_PNG];
            } else if (b1 == 0xff && b2 == 0xd8) {
                return FORMAT_NAMES[FORMAT_JPEG];
            } else if (b1 == 0x42 && b2 == 0x4d) {
                return FORMAT_NAMES[FORMAT_BMP];
            } else if (b1 == 0x0a && b2 < 0x06) {
                return FORMAT_NAMES[FORMAT_PCX];
            } else if (b1 == 0x46 && b2 == 0x4f) {
                return FORMAT_NAMES[FORMAT_IFF];
            } else if (b1 == 0x59 && b2 == 0xa6) {
                return FORMAT_NAMES[FORMAT_RAS];
            } else if (b1 == 0x50 && b2 >= 0x31 && b2 <= 0x36) {
                int id = b2 - '0';
                if (id < 1 || id > 6) {
                    return "";
                }
                final int[] pnmFormats = {FORMAT_PBM, FORMAT_PGM, FORMAT_PPM};
                int format = pnmFormats[(id - 1) % 3];
                return FORMAT_NAMES[format];
            } else if (b1 == 0x38 && b2 == 0x42) {
                return FORMAT_NAMES[FORMAT_PSD];
            } else if (b1 == 0x46 && b2 == 0x57) {
                return FORMAT_NAMES[FORMAT_SWF];
            } else {
                return "";
            }
        }
        catch (Exception e) {

            return "";
        }
        finally {
            if (is != null) {
                try {
                    is.close();
                }
                catch (IOException e) {
                 e.printStackTrace();
                }
            }
        }
    }

}
