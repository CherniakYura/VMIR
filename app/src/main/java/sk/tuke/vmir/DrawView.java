package sk.tuke.vmir;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DrawView extends View {

    private int pointX;
    private int pointY;
    private int radius = 70;

    Canvas canvas;

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        Random random = new Random();
        pointX = random.nextInt(2000);
        pointY = random.nextInt(2000);
    }

    protected void onDraw(Canvas canvas) {
        this.canvas = canvas;
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);

        if (pointX > canvas.getWidth()) {
            pointX = canvas.getWidth() - radius;
        }
        if (pointY > canvas.getHeight()) {
            pointY = canvas.getHeight() - radius;
        }
        if (pointX < 0) {
            pointX = radius;
        }
        if (pointY < 0) {
            pointY = radius;
        }


        canvas.drawCircle(pointX, pointY, radius, paint);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int event = motionEvent.getAction();
        if (event == MotionEvent.ACTION_DOWN || event == MotionEvent.ACTION_MOVE) {
            float touchX = motionEvent.getX();
            float touchY = motionEvent.getY();

            if (isIntersecting(touchX, touchY)) {
                Random random = new Random();
                pointX = random.nextInt(2000);
                pointY = random.nextInt(2000);
            }


            // Redraw
            postInvalidate();
            return true;
        }
        return false;
    }

    public boolean isIntersecting(Float x, Float y) {
        return (Math.abs(x - pointX) < radius) && (Math.abs(y - pointY) < radius);
    }


}
