package com.pursuege.arithmeticdraw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/6/14.
 */
public class DrawBitmap extends View {

    //
    private Bitmap bmp;
    private float cx;
    private float cy;
    private float cr;
    private Paint paint;

    public DrawBitmap(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bmp != null) {
            canvas.drawBitmap(bmp, 0, 0, paint);
        }
        canvas.drawCircle(cx, cy, cr, paint);
    }

    public void setCircleBmp(Bitmap bmp, int start, int end, int width, int height, int offset) {
        if (width <= 0 || height <= 0) {
            this.cr = 0;
            this.bmp=null;
            invalidate();
            return;
        }
        this.bmp = bmp;
        this.cr = (end - start) / (2.0f * width);
        this.cx = start % width;
        this.cy = start / width + this.cr;
        if (offset > 10) {
            this.cx += offset / 2;
        }
        invalidate();
    }
}
