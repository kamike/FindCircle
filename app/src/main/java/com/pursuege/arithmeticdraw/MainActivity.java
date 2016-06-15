package com.pursuege.arithmeticdraw;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;

/**
 * kamike 2016-6-15  treatwo
 */
public class MainActivity extends Activity {
    private ImageView ivImg;
    private TextView tvResoult;
    private DrawBitmap viewBmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivImg = (ImageView) findViewById(R.id.main_iv);
        tvResoult = (TextView) findViewById(R.id.main_resoult_tv);
        viewBmp = (DrawBitmap) findViewById(R.id.main_draw_bmp);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private Bitmap bmp = null;

    public void onclickJisuan(View view) {
        if (bmp == null) {
            tvResoult.setText("图片为空");
            return;
        }
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        int len = width * height;
        int[] imgs = new int[len];
        bmp.getPixels(imgs, 0, width, 0, 0, width, height);
        int start = -1, end = 0;
        int offset = 0;
        boolean isAdd = true;
        for (int i = 0; i < len; i++) {
//            if (imgs[i] != 0 && imgs[i] != -1) {
//                log("" + imgs[i]);
//            }
            //-16777216  0xff000000
            if (imgs[i] <= -16777216) {
                if (isAdd) {
                    offset++;
                }

                if (start == -1) {
                    start = i;
                }
                end = i;
            } else {
                if (offset > 0) {
                    isAdd = false;
                }
            }
        }
        int R = (end - start) / width;
        log("start:" + start + ",end:" + end + "--------" + R);
        log("估算的直径：" + R + ",偏移量：" + offset);
        tvResoult.setText("估算的直径：" + R);
        for (int i = 0; i < R; i++) {
            imgs[start + width * i] = 0xff0000;
        }
        Bitmap bmpEnd = Bitmap.createBitmap(imgs, 0, width, width, height, Bitmap.Config.RGB_565);
        viewBmp.setCircleBmp(bmpEnd, start, end, width, height, offset);
    }

    public void onclickReset(View view) {
        ivImg.setImageBitmap(bmp);
        viewBmp.setCircleBmp(null, 0, 0, 0, 0, 0);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            ContentResolver cr = this.getContentResolver();
            try {
                bmp = BitmapFactory.decodeStream(cr.openInputStream(uri));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            ivImg.setImageBitmap(bmp);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void onclickAddPhoto(View view) {
        Intent intent = new Intent();
                /* 开启Pictures画面Type设定为image */
        intent.setType("image/*");
                /* 使用Intent.ACTION_GET_CONTENT这个Action */
        intent.setAction(Intent.ACTION_GET_CONTENT);
                /* 取得相片后返回本画面 */
        startActivityForResult(intent, 1);
    }


    private void log(String str) {
        Log.i("test_img", str);
    }
}
