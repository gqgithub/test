package com.gq.app.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.gq.app.R;

/**
 * ����������Ŀռ�
 * https://github.com/linglongxin24/DylanStepCount
 */
public class ArcProgressView extends View {

    /**
     * Բ���Ŀ��
     */
    private float borderWidth = 26f;
    /**
     * ����������ֵ�������С
     */
    private float numberTextSize = 12;
    /**
     * ����
     */
    private String stepNumber = "0";
    /**
     * ��ʼ����Բ���ĽǶ�
     */
    private float startAngle = 130;
    /**
     * �յ��Ӧ�ĽǶȺ���ʼ���Ӧ�ĽǶȵļн�
     */
    private float angleLength = 280;
    /**
     * ��Ҫ���Ƶĵ�ǰ�����ĺ�ɫԲ���յ㵽���ļн�
     */
    private float currentAngleLength = 0;
    /**
     * ����ʱ��
     */
    private int animationLength = 3000;

    public ArcProgressView(Context context) {
        super(context);


    }

    public ArcProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ArcProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**���ĵ��x����*/
        float centerX = (getWidth()) / 2;
        /**ָ��Բ������������������*/
        RectF rectF = new RectF(0 + borderWidth, borderWidth, 2 * centerX - borderWidth, 2 * centerX - borderWidth);

        /**����һ������������Ļ�ɫԲ��*/
        drawArcYellow(canvas, rectF);
        /**���ڶ��������Ƶ�ǰ���ȵĺ�ɫԲ��*/
        drawArcRed(canvas, rectF);
        /**�������������Ƶ�ǰ���ȵĺ�ɫ����*/
        drawTextNumber(canvas, centerX);
        /**�����Ĳ�������"����"�ĺ�ɫ����*/
        drawTextStepString(canvas, centerX);
    }

    /**
     * 1.�����ܲ����Ļ�ɫԲ��
     *
     * @param canvas ����
     * @param rectF  �ο��ľ���
     */
    private void drawArcYellow(Canvas canvas, RectF rectF) {
        Paint paint = new Paint();
        /** Ĭ�ϻ�����ɫ����ɫ */
        paint.setColor(getResources().getColor(R.color.gray_trance));
        /** ��ϴ�ΪԲ��*/
        paint.setStrokeJoin(Paint.Join.ROUND);
        /** ���û��ʵ���ʽ Paint.Cap.Round ,Cap.SQUARE�ȷֱ�ΪԲ�Ρ�����*/
        paint.setStrokeCap(Paint.Cap.ROUND);
        /** ���û��ʵ������ʽ Paint.Style.FILL  :����ڲ�;Paint.Style.FILL_AND_STROKE  ������ڲ������;  Paint.Style.STROKE  �������*/
        paint.setStyle(Paint.Style.STROKE);
        /**����ݹ���*/
        paint.setAntiAlias(true);
        /**���û��ʿ��*/
        paint.setStrokeWidth(borderWidth);

        /**����Բ���ķ���
         * drawArc(RectF oval, float startAngle, float sweepAngle, boolean useCenter, Paint paint)//������
         ����һ��RectF����һ������������Բ�εĽ������ڶ�������״����С���绡��
         ����������ʼ��(��)�ڵ绡�Ŀ�ʼ��Բ����ʼ�Ƕȣ���λΪ�ȡ�
         ������Բ��ɨ���ĽǶȣ�˳ʱ�뷽�򣬵�λΪ��,�����м俪ʼΪ��ȡ�
         ���������������true(��)�Ļ�,�ڻ���Բ��ʱ��Բ�İ������ڣ�ͨ�������������Σ��������false(��)�⽫��һ������,
         ��������Paint����
         */
        canvas.drawArc(rectF, startAngle, angleLength, false, paint);

    }

    /**
     * 2.���Ƶ�ǰ�����ĺ�ɫԲ��
     */
    private void drawArcRed(Canvas canvas, RectF rectF) {
        Paint paintCurrent = new Paint();
        paintCurrent.setStrokeJoin(Paint.Join.ROUND);
        paintCurrent.setStrokeCap(Paint.Cap.ROUND);//Բ�ǻ���
        paintCurrent.setStyle(Paint.Style.STROKE);//���������ʽ
        paintCurrent.setAntiAlias(true);//����ݹ���
        paintCurrent.setStrokeWidth(borderWidth);//���û��ʿ��
        paintCurrent.setColor(getResources().getColor(R.color.white));//���û�����ɫ
        canvas.drawArc(rectF, startAngle, currentAngleLength, false, paintCurrent);
    }

    /**
     * 3.Բ�����ĵĲ���
     */
    private void drawTextNumber(Canvas canvas, float centerX) {
        Paint vTextPaint = new Paint();
        vTextPaint.setTextAlign(Paint.Align.CENTER);
        vTextPaint.setAntiAlias(true);//����ݹ���
        vTextPaint.setTextSize(numberTextSize);
        Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL);
        vTextPaint.setTypeface(font);//������
        vTextPaint.setColor(getResources().getColor(R.color.white));
        Rect bounds_Number = new Rect();
        vTextPaint.getTextBounds(stepNumber, 0, stepNumber.length(), bounds_Number);
        canvas.drawText(stepNumber, centerX, getHeight() / 2 + bounds_Number.height() / 2, vTextPaint);

    }

    /**
     * 4.Բ�����ĵ�����
     */
    private void drawTextStepString(Canvas canvas, float centerX) {
        Paint vTextPaint = new Paint();
        vTextPaint.setTextSize(dipToPx(12));
        vTextPaint.setTextAlign(Paint.Align.CENTER);
        vTextPaint.setAntiAlias(true);//����ݹ���
        vTextPaint.setColor(getResources().getColor(R.color.white));
        String stepString = "";
        Rect bounds = new Rect();
        vTextPaint.getTextBounds(stepString, 0, stepString.length(), bounds);
        canvas.drawText(stepString, centerX, getHeight() / 2 + bounds.height() + getFontHeight(numberTextSize), vTextPaint);
    }

    /**
     * ��ȡ��ǰ���������ֵĸ߶�
     *
     * @param fontSize �����С
     * @return ����߶�
     */
    public int getFontHeight(float fontSize) {
        Paint paint = new Paint();
        paint.setTextSize(fontSize);
        Rect bounds_Number = new Rect();
        paint.getTextBounds(stepNumber, 0, stepNumber.length(), bounds_Number);
        return bounds_Number.height();
    }

    /**
     * dip ת����px
     *
     * @param dip
     * @return
     */

    private int dipToPx(float dip) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5f * (dip >= 0 ? 1 : -1));
    }

    /**
     * ���ߵĲ�������
     *
     * @param totalStepNum  ���õĲ���
     * @param currentCounts ���߲���
     */
    public void setCurrentCount(int totalStepNum, int currentCounts) {
        stepNumber = currentCounts + "";
        setTextSize(currentCounts);
        /**�����ǰ�ߵĲ��������ܲ�����Բ������270�ȣ����ܳ�Ϊ԰*/
        if (currentCounts > totalStepNum) {
            currentCounts = totalStepNum;
        }
        /**���߲���ռ���ܹ������İٷֱ�*/
        float scale = (float) currentCounts / totalStepNum;
        /**����ɻ������Ҫ����ĽǶȵĳ���-->����*/
        float currentAngleLength = scale * angleLength;
        /**��ʼִ�ж���*/
        setAnimation(0, currentAngleLength, animationLength);
    }

    /**
     * Ϊ�������ö���
     * ValueAnimator���������Զ������Ƶ�������ĵ�һ���࣬���Զ��������л�����ͨ�����ϵض�ֵ���в�����ʵ�ֵģ�
     * ����ʼֵ�ͽ���ֵ֮��Ķ������ɾ�����ValueAnimator��������������ġ�
     * �����ڲ�ʹ��һ��ʱ��ѭ���Ļ���������ֵ��ֵ֮��Ķ������ɣ�
     * ����ֻ��Ҫ����ʼֵ�ͽ���ֵ�ṩ��ValueAnimator�����Ҹ����������������е�ʱ����
     * ��ôValueAnimator�ͻ��Զ���������ɴӳ�ʼֵƽ���ع��ɵ�����ֵ������Ч����
     *
     * @param start   ��ʼֵ
     * @param current ����ֵ
     * @param length  ����ʱ��
     */
    private void setAnimation(float start, float current, int length) {
        ValueAnimator progressAnimator = ValueAnimator.ofFloat(start, current);
        progressAnimator.setDuration(length);
        progressAnimator.setTarget(currentAngleLength);
        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                /**ÿ���ڳ�ʼֵ�ͽ���ֵ֮�������һ��ƽ�����ɵ�ֵ����ȥ���½���*/
                currentAngleLength = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        progressAnimator.start();
    }

    /**
     * �����ı���С,��ֹ�����ر��֮��Ų��£��������С��̬����
     *
     * @param num
     */
    public void setTextSize(int num) {
        String s = String.valueOf(num);
        int length = s.length();
        if (length <= 4) {
            numberTextSize = dipToPx(40);
        } else if (length > 4 && length <= 6) {
            numberTextSize = dipToPx(30);
        } else if (length > 6 && length <= 8) {
            numberTextSize = dipToPx(20);
        } else if (length > 8) {
            numberTextSize = dipToPx(15);
        }
    }

}

