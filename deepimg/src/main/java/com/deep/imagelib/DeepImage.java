package com.deep.imagelib;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import com.deep.imagelib.beans.ImageConfigure;
import com.deep.imagelib.impl.BinaryConvertor;
import com.deep.imagelib.impl.BitmapConvertor;
import com.deep.imagelib.impl.FileConvertor;
import com.deep.imagelib.impl.ResConvertor;
import com.deep.imagelib.interfaces.Convertor;
import com.deep.imagelib.log.Logger;

/**
 * Created by wangfei on 2017/12/29.
 */

public class DeepImage {
    private Context context;
    private ImageConfigure configure;
    private Convertor convertor;
    public DeepImage(Context context, Object o,ImageConfigure configure){
        if (configure !=null ){
            this.configure = configure;
        }else {
            this.configure = new ImageConfigure();
        }
        this.context = context;
        init(context,o,this.configure);

    }

    private void init(Context context, Object obj,ImageConfigure configure) {


        if (obj instanceof File) {
            convertor = new FileConvertor(context, (File)obj,configure);

        }  else if (obj instanceof Integer) {
            convertor = new ResConvertor(context, (Integer)obj,configure);
        } else if (obj instanceof byte[]) {
            convertor = new BinaryConvertor(context, (byte[])obj,configure);
        } else if (obj instanceof Bitmap) {
            convertor = new BitmapConvertor(context, (Bitmap)obj,configure);
        } else {
            Logger.error("deep image暂时只支持resource，file，bitmap，二进制字节流四种类型");
        }

    }
    public byte[] asBinary(){
        return convertor.asBinary();
    }
    public Bitmap asBitmap(){
        return convertor.asBitmap();
    }
    public File asFile(){
        return convertor.asFile();
    }

}
