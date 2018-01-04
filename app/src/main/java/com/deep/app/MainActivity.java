package com.deep.app;

import java.io.File;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.deep.imagelib.DeepImage;
import com.deep.imagelib.beans.CompressStyle;
import com.deep.imagelib.beans.ImageConfigure;
import com.deep.imagelib.beans.ImageConfigure.Expect;
import com.deep.imagelib.log.Logger;

public class MainActivity extends AppCompatActivity {
    private ImageView img;
    private TextView message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        message = (TextView)findViewById(R.id.message);
        findViewById(R.id.b_file).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap = BitmapFactory.decodeResource( MainActivity.this.getResources(), R.drawable.datu);
                DeepImage deepImage = new DeepImage(MainActivity.this,bitmap,null);
                message.setText("图片保存路径为："+deepImage.asFile().getAbsolutePath()+" 源文件大小："+bitmap.getByteCount()+"  文件大小："+deepImage.asFile().length());

            }
        });
        findViewById(R.id.b_compress).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap = BitmapFactory.decodeResource( MainActivity.this.getResources(), R.drawable.datu);
                ImageConfigure configure = new ImageConfigure();
                configure.compressStyle = CompressStyle.SCALE;
                Expect expect = new Expect();
                expect.width = 100;
                expect.height = 100;
                configure.expect = expect;
                DeepImage deepImage = new DeepImage(MainActivity.this,bitmap,configure);
                message.setText("图片宽度为："+deepImage.asBitmap().getWidth());


            }
        });
        findViewById(R.id.b_binary).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap = BitmapFactory.decodeResource( MainActivity.this.getResources(), R.drawable.datu);
                ImageConfigure configure = new ImageConfigure();
                configure.compressStyle = CompressStyle.QUALITY;
                Expect expect = new Expect();
                expect.maxCount = 350*1024;
                configure.expect = expect;
                DeepImage deepImage = new DeepImage(MainActivity.this,bitmap,configure);

                message.setText("图片大小为："+deepImage.asBinary().length);

            }
        });
        findViewById(R.id.r_file).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                DeepImage deepImage = new DeepImage(MainActivity.this,R.drawable.dadadatu,null);

                message.setText("图片保存路径为："+deepImage.asFile().getAbsolutePath()+" 源文件大小："+deepImage.asBitmap().getByteCount()+"  文件大小："+deepImage.asFile().length());

            }
        });
        findViewById(R.id.r_bitmap).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                ImageConfigure configure = new ImageConfigure();
                configure.compressStyle = CompressStyle.SCALE;
                Expect expect = new Expect();
                expect.width = 100;
                expect.height = 100;
                configure.expect = expect;
                DeepImage deepImage = new DeepImage(MainActivity.this,R.drawable.dadadatu,configure);

                message.setText("图片宽度为："+deepImage.asBitmap().getWidth());

            }
        });
        findViewById(R.id.r_binary).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageConfigure configure = new ImageConfigure();
                configure.compressStyle = CompressStyle.QUALITY;
                Expect expect = new Expect();
                expect.maxCount = 350*1024;
                configure.expect = expect;
                DeepImage deepImage = new DeepImage(MainActivity.this,R.drawable.dadadatu,configure);

                message.setText("图片大小为："+deepImage.asBinary().length);

            }
        });
        findViewById(R.id.f_file).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                File file = new File("/sdcard"+File.separator+"dadadatu.jpg");
                if (!file.exists()){
                    return;
                }
                DeepImage deepImage = new DeepImage(MainActivity.this,file,null);

                message.setText("图片保存路径为："+deepImage.asFile().getAbsolutePath()+" 源文件大小："+deepImage.asBitmap().getByteCount()+"  文件大小："+deepImage.asFile().length());

            }
        });
        findViewById(R.id.f_bitmap).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                File file = new File("/sdcard"+File.separator+"dadadatu.jpg");
                if (!file.exists()){
                    return;
                }
                ImageConfigure configure = new ImageConfigure();
                configure.compressStyle = CompressStyle.SCALE;
                Expect expect = new Expect();
                expect.width = 100;
                expect.height = 100;
                configure.expect = expect;
                DeepImage deepImage = new DeepImage(MainActivity.this,file,configure);

                message.setText("图片宽度为："+deepImage.asBitmap().getWidth());

            }
        });
        findViewById(R.id.f_binary).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                File file = new File("/sdcard"+File.separator+"dadadatu.jpg");
                if (!file.exists()){
                    return;
                }

                ImageConfigure configure = new ImageConfigure();
                configure.compressStyle = CompressStyle.QUALITY;
                Expect expect = new Expect();
                expect.maxCount = 350*1024;
                configure.expect = expect;
                DeepImage deepImage = new DeepImage(MainActivity.this,file,configure);

                message.setText("图片大小为："+deepImage.asBinary().length);

            }
        });

    }
}
