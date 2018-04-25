package com.jowney.jowney.surfacetexturesample;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

/**
 * Created by Jowney on 2018/4/25.
 */

public class SurfaceViewActivity extends Activity implements SurfaceHolder.Callback {
    ImageView imageView;
    SurfaceView surfaceView;
    SurfaceHolder holder;
    SurfaceTexture surfaceTexture;
    Camera mCamera;
    private static String TAG = "SurfaceViewActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.surfaceview_activity_layout);
        surfaceView = findViewById(R.id.surfaceView);
        holder = surfaceView.getHolder();
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        holder.addCallback(this);
        surfaceTexture = new SurfaceTexture(10);

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mCamera = Camera.open();

        try {
            Camera.Parameters parameters = mCamera.getParameters();
            parameters.setPreviewSize(640, 480);
            mCamera.setParameters(parameters);
            mCamera.setDisplayOrientation(90);
            mCamera.setPreviewTexture(surfaceTexture);

            mCamera.setPreviewCallback(new Camera.PreviewCallback() {
                @Override
                public void onPreviewFrame(byte[] bytes, Camera camera) {
                    Log.i(TAG, "onPreviewFrame: " + bytes.toString());
                    YuvImage yuvimage = new YuvImage(bytes, ImageFormat.NV21, 640, 480, null);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    yuvimage.compressToJpeg(new Rect(0, 0, 640, 480), 90,
                            baos);
                    byte[] jdata = baos.toByteArray();

                    Bitmap bitmapVideo = BitmapFactory.decodeByteArray(jdata, 0, jdata.length);// 视频
                    Matrix matrix = new Matrix();
                 /*   matrix.postRotate(90);
                    matrix.postTranslate(640, 0);*/
                   /* matrix.postScale(-1,1);
                    matrix.postTranslate(840, 0);*/
                    matrix.postScale(2, 2);
                  /*  matrix.postScale(-1,1);
                    matrix.postTranslate(640, 0);
                    matrix.postRotate(-90);
                    matrix.postTranslate(0, 640);
                    matrix.postScale(2, 2);*/
                 /*   matrix.postRotate(90);
                    matrix.postTranslate(640, 0);*/
                  //  matrix.postScale(2, 2);

                    Canvas canvas = holder.lockCanvas();


                    canvas.drawBitmap(bitmapVideo, matrix, null);
                    //   canvas.drawColor(getResources().getColor(R.color.colorAccent));
                    holder.unlockCanvasAndPost(canvas);
                }
            });
            mCamera.startPreview();
        } catch (Exception ioe) {
            // Something bad happened
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mCamera.stopPreview();
        mCamera.release();
    }
}
