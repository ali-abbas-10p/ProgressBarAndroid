package com.practice.progressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

public class ProgressBarGradientView extends View {
    private Paint paint, strokeFillPaint;
    private int[] colors = new int[4];
    private int strokeFillColor = Color.TRANSPARENT;
    private float stroke = 30;
    private int maxValue=100;
    private int currentValue = 50;

    public ProgressBarGradientView(Context context) {
        super(context);
        init(null);
    }

    public ProgressBarGradientView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ProgressBarGradientView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ProgressBarGradientView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {
        if (attrs!=null) {
            TypedArray ta = this.getContext().obtainStyledAttributes(attrs,R.styleable.ProgressBarGradientView);
            try {
                colors[0] = ta.getColor(R.styleable.ProgressBarGradientView_color_first,Color.BLACK);
                colors[1] = ta.getColor(R.styleable.ProgressBarGradientView_color_second,Color.BLACK);
                colors[2] = ta.getColor(R.styleable.ProgressBarGradientView_color_third,Color.BLACK);
                colors[3] = ta.getColor(R.styleable.ProgressBarGradientView_color_fourth,Color.BLACK);
                strokeFillColor = ta.getColor(R.styleable.ProgressBarGradientView_stroke_fill_color,strokeFillColor);
                stroke = ta.getDimension(R.styleable.ProgressBarGradientView_stroke_width,stroke);
                maxValue = ta.getInt(R.styleable.ProgressBarGradientView_max_value,maxValue);
                currentValue = ta.getInt(R.styleable.ProgressBarGradientView_current_value,currentValue);
            } finally {
                ta.recycle();
            }
        }
        else {
            colors[0] = Color.BLACK;
            colors[1] = Color.BLACK;
            colors[2] = Color.BLACK;
            colors[3] = Color.BLACK;
        }
        initPaint();
    }

    public void setColors(int[] colors) {
        this.colors = colors;
    }

    public void setStrokeFillColor(int strokeFillColor) {
        this.strokeFillColor = strokeFillColor;
    }

    public void setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public void setStrokeWidth(float stroke) {
        this.stroke = stroke;
    }

    private void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(stroke);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);

        strokeFillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        strokeFillPaint.setColor(strokeFillColor);
        strokeFillPaint.setStrokeWidth(stroke);
        strokeFillPaint.setStyle(Paint.Style.STROKE);
        strokeFillPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    private int getAngle() {
        if (maxValue>currentValue)
        {
            return (int) ((float) currentValue/(float) maxValue * 360f);
        }
        else
            return 360;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        float cx = this.getWidth()/2;
        float cy = this.getHeight()/2;

        super.onDraw(canvas);
        canvas.save();
        canvas.rotate(-90,cx,cy);

        paint.setShader(new SweepGradient(cx,cy,colors,null));
        RectF r = new RectF(stroke,stroke,this.getWidth()-stroke,this.getHeight()-stroke);
        canvas.drawArc(r,0,360,false,strokeFillPaint);
        canvas.drawArc(r,0,getAngle(),false,paint);

        canvas.restore();
    }
}
