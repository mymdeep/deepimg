# deepimg
## 说明
deepimg是一个简便的图片转换+压缩库，可以在bitmap，file，二进制字节流，res四种类型中轻松切换并进行压缩.
这是第一版，如有问题请联系作者
## 使用

```
compile 'com.deep:deepimg:1.0'
```
## 接口

### bitmap入参
如果使用bitmap
可以这样构建：

```
DeepImage deepImage = new DeepImage(MainActivity.this,bitmap,null);
```
转成file类型：

```
deepImage.asFile()
```
转成二进制类型：

```
deepImage.asBinary()
```
### res入参
如果使用res
可以这样构建：

```
DeepImage deepImage = new DeepImage(MainActivity.this,R.drawable.dadadatu,null);
```
转成file类型：

```
deepImage.asFile()
```
转成二进制类型：

```
deepImage.asBinary()
```
转成bitmap类型：

```
deepImage.asBitmap()
```
### file入参
如果使用本地图片
可以这样构建：

```
DeepImage deepImage = new DeepImage(MainActivity.this,file,null);
```
转成file类型：

```
deepImage.asFile()
```
转成二进制类型：

```
deepImage.asBinary()
```
转成bitmap类型：



### 二进制字节流入参
如果使用本地图片
可以这样构建：

```
DeepImage deepImage = new DeepImage(MainActivity.this,data,null);

```
转成file类型：

```
deepImage.asFile()

```
转成二进制类型：

```
deepImage.asBinary()
```
转成bitmap类型：

```
deepImage.asBitmap()
```
### 图片压缩
#### 尺寸压缩
```
ImageConfigure configure = new ImageConfigure();
configure.compressStyle = CompressStyle.SCALE;
Expect expect = new Expect();
expect.width = 100;
expect.height = 100;
configure.expect = expect;
```
其中expet为压缩期望值，或者设置大小

```
expect.maxCount = 350*1024;
```
然后将ImageConfigure放入初始化即可：

```
 DeepImage deepImage = new DeepImage(MainActivity.this,bitmap,configure);
```
### 质量压缩
质量压缩不支持宽高，需要设置压缩到的指定大小：

```
                ImageConfigure configure = new ImageConfigure();
                configure.compressStyle = CompressStyle.QUALITY;
                Expect expect = new Expect();
                expect.maxCount = 350*1024;
                configure.expect = expect;
                DeepImage deepImage = new DeepImage(MainActivity.this,bitmap,configure);
```
### 设置文件保存路径
如果不设置文件保存路径会保存到SD卡默认路径下，设置路径的方法：

```
                ImageConfigure configure = new ImageConfigure();
                configure.directoryname = "";//目录名
                configure.filename = "";//文件名
```
## 需要使用的权限
```
  <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```


