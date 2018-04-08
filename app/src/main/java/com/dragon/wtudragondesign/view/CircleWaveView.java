package com.dragon.wtudragondesign.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.LinearInterpolator;

import com.dragon.wtudragondesign.R;


public class CircleWaveView extends View{

    public static final String TAG="CircleWaveView";

    private int waveColor;//波浪的颜色
    private float waveHight;//波浪的高度
    private int waveBackColor;//波浪的后部的颜色
    
    private int waveColor2;//波浪2的颜色
    private float waveHight2;//波浪2的高度
    private int waveBackColor2;//波浪2的后部的颜色
   
    
    private float innerRadius;//内部圆的半径，即波浪要显示的区域
    private float outCircleWidth;//外部圆环的宽度
    private int outCircleColor;//外部圆环的颜色
    private float textSize;//字体大小
    private int textColor;//字体颜色
    private float waveWidth;//波浪的宽度
    private float waveWidth2;//波浪2的宽度

    private float centerX,centerY;//view中心点
    private float progress=0.3f;//绘制的进度
    private float wholeMoveY,startY;//Y轴上总的移动距离与初始位置
    private int maxProgress=100;//最大的进度
    private int curProgress=50;//当前的进度
    private float percentage=0.5f;//当前进度的百分比
    private ValueAnimator waveAnim,riseAnim;


    private Paint wavePaint,textPaint,centerPaint,outPaint,wavePaint2;


    public CircleWaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.CircleWaveView);
        waveColor=typedArray.getColor(R.styleable.CircleWaveView_waveColor, Color.WHITE);
        waveHight=typedArray.getDimension(R.styleable.CircleWaveView_waveHight,20);
        waveBackColor=typedArray.getColor(R.styleable.CircleWaveView_waveBackColor,Color.GRAY);
        
        waveColor2=typedArray.getColor(R.styleable.CircleWaveView_waveColor2, Color.WHITE);
        waveHight2=typedArray.getDimension(R.styleable.CircleWaveView_waveHight2,20);
        waveBackColor2=typedArray.getColor(R.styleable.CircleWaveView_waveBackColor2,Color.GRAY);
        
        innerRadius=typedArray.getDimension(R.styleable.CircleWaveView_innerRadius,50);
        outCircleWidth=typedArray.getDimension(R.styleable.CircleWaveView_outCircleWidth,10);
        outCircleColor=typedArray.getColor(R.styleable.CircleWaveView_outCircleColor,Color.BLUE);
        textSize=typedArray.getDimension(R.styleable.CircleWaveView_textSize,20);
        textColor=typedArray.getColor(R.styleable.CircleWaveView_textColor,Color.BLUE);
        waveWidth=typedArray.getDimension(R.styleable.CircleWaveView_waveWidth,100);
        
        waveWidth2=typedArray.getDimension(R.styleable.CircleWaveView_waveWidth2,100);
        
        initPaint();
        typedArray.recycle();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX=w/2.0f;
        centerY=h/2.0f;
        wholeMoveY=2*(innerRadius+waveHight);//这是波浪上下移动的最大距离
        startY=centerY+innerRadius+waveHight;//这是波浪最初的位置即progress=0,
    }

    private void initPaint(){
        wavePaint=new Paint();
        wavePaint2=new Paint();
        
        textPaint=new Paint();
        centerPaint=new Paint();
        outPaint=new Paint();

        wavePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        wavePaint2.setStyle(Paint.Style.FILL_AND_STROKE);
        
        centerPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        outPaint.setStyle(Paint.Style.STROKE);
        outPaint.setStrokeWidth(outCircleWidth);

        wavePaint.setAntiAlias(true);
        wavePaint2.setAntiAlias(true);
        centerPaint.setAntiAlias(true);
        outPaint.setAntiAlias(true);
        textPaint.setAntiAlias(true);

        wavePaint.setColor(waveColor);
        wavePaint2.setColor(waveColor2);
        centerPaint.setColor(waveBackColor);
        outPaint.setColor(outCircleColor);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        outPaint.setStrokeCap(Paint.Cap.ROUND);
        outPaint.setStrokeJoin(Paint.Join.ROUND);
    }

    private RectF out;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCircles(canvas);
        drawCircles2(canvas);
        
        drawWave(canvas);
        drawWave2(canvas);
        canvas.drawArc(out,0,360.0f,false,outPaint);
        drawText(canvas);
    }
    
    //中心文字
    private void drawText(Canvas canvas){
        String text=(int)(percentage*100)+"天";
        Paint.FontMetrics metrics=textPaint.getFontMetrics();
        //获取文字的长度
        float textWidth=textPaint.measureText(text);
        //计算文字的高度，这里计算可能有点小问题不过我们需要的只是一个大约高度，不影响,不纠结，重点是贝塞尔曲线的绘制
        float textHight=metrics.descent-metrics.top;
        //计算文字的位置，我们让文字的中心与view的中心重合
        int textX= (int) (centerX-textWidth/2);
        int textY= (int) (centerY+textHight/2);
        canvas.drawText(text,textX,textY,textPaint);
    }

    private Path path,clipPath;
    private Path path2,clipPath2;
    private float curX,curY;
    private float curX2,curY2;

    /**
     * 在view中心的圆上开始绘制水波纹，即绘制一段贝塞尔曲线
     * @param canvas
     */
    private void drawWave(Canvas canvas){
        if(clipPath==null){
            path=new Path();
            clipPath=new Path();
            //要裁剪的圆形画布，innerRadius是在xml文件中定义的内部的圆的半径
            //即波浪最终显示的范围
            clipPath.addCircle(centerX,centerY,innerRadius, Path.Direction.CCW);
        }
        canvas.save();
        //裁剪画布在圆形的画布上绘制曲线
        canvas.clipPath(clipPath);
        path.reset();
        //默认progress=0，也就是从-waveWidth处开始绘制，多绘制了一个波纹的长度用于后边的移动
        curX=centerX-innerRadius-waveWidth*(1-progress);
        curY=startY-wholeMoveY*(curProgress*1.0f/maxProgress);
        path.moveTo(curX,curY);
        for (float i=-waveWidth;i<centerY+innerRadius+waveWidth;i+=waveWidth){
            path.rQuadTo(waveWidth/4,waveHight,waveWidth/2,0);
            path.rQuadTo(waveWidth/4,-waveHight,waveWidth/2,0);
        }
        path.lineTo(getWidth(),getHeight());
        path.lineTo(0,getHeight());
        path.close();
        canvas.drawPath(path,wavePaint);
        canvas.restore();
    }
    
    /**
     * 在view中心的圆上开始绘制水波纹，即绘制一段贝塞尔曲线
     * @param canvas
     */
    private void drawWave2(Canvas canvas){
        if(clipPath2==null){
            path2=new Path();
            clipPath2=new Path();
            //要裁剪的圆形画布，innerRadius是在xml文件中定义的内部的圆的半径
            //即波浪最终显示的范围
            clipPath2.addCircle(centerX,centerY,innerRadius, Path.Direction.CCW);
        }
        canvas.save();
        //裁剪画布在圆形的画布上绘制曲线
        canvas.clipPath(clipPath2);
        path2.reset();
        //默认progress=0，也就是从-waveWidth处开始绘制，多绘制了一个波纹的长度用于后边的移动
        curX2=centerX-innerRadius-waveWidth2*(1-progress)+4;
        curY2=startY-wholeMoveY*(curProgress*1.0f/maxProgress)+4;
        path2.moveTo(curX2,curY2);
        for (float i=-waveWidth2;i<centerY+innerRadius+waveWidth2;i+=waveWidth2){
            path2.rQuadTo(waveWidth2/4,waveHight2,waveWidth2/2,0);
            path2.rQuadTo(waveWidth2/4,-waveHight2,waveWidth2/2,0);
        }
        path2.lineTo(getWidth(),getHeight());
        path2.lineTo(0,getHeight());
        path2.close();
        canvas.drawPath(path2,wavePaint2);
        canvas.restore();
    }


    /**
     * 绘制波浪的背景
     * @param canvas
     */
    private void drawCircles(Canvas canvas){
        canvas.drawCircle(centerX,centerY,innerRadius,centerPaint);
        if (out==null){
            out=new RectF();
            out.left=centerX-innerRadius-outCircleWidth/4;
            out.top=centerY-innerRadius-outCircleWidth/4;
            out.right=centerX+innerRadius+outCircleWidth/4;
            out.bottom=centerY+innerRadius+outCircleWidth/4;
        }
    }
    
    /**
     * 绘制波浪的背景
     * @param canvas
     */
    private void drawCircles2(Canvas canvas){
        canvas.drawCircle(centerX,centerY,innerRadius,centerPaint);
        if (out==null){
            out=new RectF();
            out.left=centerX-innerRadius-outCircleWidth/4;
            out.top=centerY-innerRadius-outCircleWidth/4;
            out.right=centerX+innerRadius+outCircleWidth/4;
            out.bottom=centerY+innerRadius+outCircleWidth/4;
        }
    }


    public int getMaxProgress() {
        return maxProgress;
    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
    }

    public int getCurProgress() {
        return curProgress;
    }

    public void setCurProgress(int curProgress) {
        if (curProgress<0)return;
        if (riseAnim!=null){
            riseAnim.cancel();
        }
        riseAnim=generAnim(curProgress,600);
        riseAnim.start();
    }




    public void setWaveAnim(boolean run){
       if (waveAnim==null){
           waveAnim=ValueAnimator.ofFloat(0,1);
           waveAnim.setDuration(1000);
           waveAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
               @Override
               public void onAnimationUpdate(ValueAnimator animation) {
                   progress=animation.getAnimatedFraction();
                   invalidate();
               }
           });
           waveAnim.setInterpolator(new LinearInterpolator());
           //无限循环
           waveAnim.setRepeatCount(ValueAnimator.INFINITE);
       }
        if (run){
            if (!waveAnim.isRunning()){
                waveAnim.start();
            }
        }else {
            if (waveAnim.isRunning()){
                waveAnim.cancel();
            }
        }
    }


    /**
     *
     * @return
     */
    private ValueAnimator generAnim(int finalProgress,long du){
        double start=1.0f*curProgress/maxProgress;
        double end=1.0f*finalProgress/maxProgress;
        //Log.i(TAG,"start:"+start+"   end:"+end+"  finalProgress:"+finalProgress+"  curProgress:"+curProgress);
        final ValueAnimator animator=ValueAnimator.ofFloat((float) start,(float)end);
        animator.setDuration(du);
        animator.setInterpolator(new AnticipateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                percentage=(float)animation.getAnimatedValue();
                curProgress= (int)(maxProgress*percentage);
                //Log.i(TAG,"curProgress:"+curProgress+"    animation.getAnimatedFraction()"+animation.getAnimatedFraction());
                postInvalidate();
            }
        });
        return animator;
    }

}