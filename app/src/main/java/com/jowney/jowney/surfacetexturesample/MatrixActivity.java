package com.jowney.jowney.surfacetexturesample;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Jowney on 2018/4/25.
 */

public class MatrixActivity extends Activity {
    ImageView imageView;
    Bitmap baseBitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matrix_activity_layout);
        imageView = findViewById(R.id.imageView3);
        baseBitmap = getImageFromAssetsFile(this, "face.png");
        imageView.setImageBitmap(baseBitmap);
    }


    /**
     * 图片缩放
     *
     * @param view
     */
    public void goScale(View view) {
        bitmapScale(5f, 5f);
    }

    protected void bitmapScale(float x, float y) {
        // 因为要将图片放大，所以要根据放大的尺寸重新创建Bitmap
        Bitmap afterBitmap = Bitmap.createBitmap(
                (int) (baseBitmap.getWidth() * x),
                (int) (baseBitmap.getHeight() * y), baseBitmap.getConfig());
        Canvas canvas = new Canvas(afterBitmap);
        // 初始化Matrix对象
        Matrix matrix = new Matrix();
        // 根据传入的参数设置缩放比例
        matrix.setScale(x, y);
        // 根据缩放比例，把图片draw到Canvas上
        canvas.drawColor(getResources().getColor(R.color.colorAccent));
        canvas.drawBitmap(baseBitmap, matrix, new Paint());
        imageView.setImageBitmap(afterBitmap);
        baseBitmap = afterBitmap;
    }


    /**
     * 图片移动
     *
     * @param view
     */
    public void goTranslate(View view) {
        bitmapTranslate(100, 100);
    }

    protected void bitmapTranslate(float dx, float dy) {
        // 需要根据移动的距离来创建图片的拷贝图大小
        Bitmap afterBitmap = Bitmap.createBitmap(
                (int) (baseBitmap.getWidth() + dx),
                (int) (baseBitmap.getHeight() + dy), baseBitmap.getConfig());
        Canvas canvas = new Canvas(afterBitmap);
        Matrix matrix = new Matrix();
        // 设置移动的距离
        matrix.setTranslate(dx, dy);
        canvas.drawColor(getResources().getColor(R.color.colorAccent));
        canvas.drawBitmap(baseBitmap, matrix, new Paint());
        imageView.setImageBitmap(afterBitmap);
        baseBitmap = afterBitmap;
    }

    /**
     * 图片旋转
     *
     * @param view
     */
    public void goRotate(View view) {
        bitmapRotate(10);
    }

    protected void bitmapRotate(float degrees) {
        // 创建一个和原图一样大小的图片
        Bitmap afterBitmap = Bitmap.createBitmap(baseBitmap.getWidth(),
                baseBitmap.getHeight(), baseBitmap.getConfig());
        Canvas canvas = new Canvas(afterBitmap);
        Matrix matrix = new Matrix();
        // 根据原图的中心位置旋转

        matrix.setRotate(degrees, (float) baseBitmap.getWidth() / 2, (float) baseBitmap.getHeight() / 2);
        canvas.drawBitmap(baseBitmap, matrix, new Paint());
        imageView.setImageBitmap(afterBitmap);
        baseBitmap = afterBitmap;
    }

    /**
     * 图片倾斜
     *
     * @param view
     */
    public void goSkew(View view) {
        bitmapSkew(2,2);
    }

    protected void bitmapSkew(float dx, float dy) {
        // 根据图片的倾斜比例，计算变换后图片的大小，
        Bitmap afterBitmap = Bitmap.createBitmap(baseBitmap.getWidth()
                + (int) (baseBitmap.getWidth() * dx), baseBitmap.getHeight()
                + (int) (baseBitmap.getHeight() * dy), baseBitmap.getConfig());
        Canvas canvas = new Canvas(afterBitmap);
        Matrix matrix = new Matrix();
        // 设置图片倾斜的比例
        matrix.setSkew(dx, dy);
        canvas.drawBitmap(baseBitmap, matrix, new Paint());
        imageView.setImageBitmap(afterBitmap);
    }

    public static Bitmap getImageFromAssetsFile(Context context, String fileName) {
        Bitmap image = null;
        AssetManager am = context.getResources().getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
