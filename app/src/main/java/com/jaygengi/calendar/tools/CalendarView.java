package com.jaygengi.calendar.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;


import com.jaygengi.calendar.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gengy on 2017/7/25.
 * Class desc:
 *
 * 自定义日历View，可多选
 */
public class CalendarView extends View {

    // 列的数量
    private static final int NUM_COLUMNS    =   7;
    // 行的数量
    private static final int NUM_ROWS       =   6;

    /**
     * 可选日期数据
     */
    private List<SerPrice> mOptionalDates = new ArrayList<SerPrice>();

    private Map<String,SerPrice> mapOptionalDates = new HashMap<String,SerPrice>();

    /**
     * 以选日期数据
     */
    private List<String> mSelectedDates = new ArrayList<>();

    // 背景颜色
    private int mBgColor = Color.parseColor("#ffffff");
    //黑色
    private int blackColor = Color.parseColor("#000000");
    // 天数默认颜色
    private int mDayNormalColor = Color.parseColor("#0070F8");
    // 天数不可选颜色
    private int mDayNotOptColor = Color.parseColor("#CBCBCB");

    // 价格颜色
    private int PriceColor = Color.parseColor("#e10601");

    // 天数选择后颜色
    private int mDayPressedColor = Color.WHITE;
    // 天数字体大小
    private int mDayTextSize = 14;
    // 是否可以被点击状态
    private boolean mClickable = true;

    private DisplayMetrics mMetrics;
    private Paint mPaint;
    private int mCurYear;
    private int mCurMonth;
    private int mCurDate;

    private int mSelYear;
    private int mSelMonth;
    private int mSelDate;
    private int mColumnSize;
    private int mRowSize;
    private int[][] mDays;

    // 当月一共有多少天
    private int mMonthDays;
    // 当月第一天位于周几
    private int mWeekNumber;
    // 已选中背景Bitmap
    private Bitmap mBgOptBitmap;
    // 未选中背景Bitmap
    private Bitmap mBgNotOptBitmap;

    private boolean isInit = false;//是否已经初始化
    Date nowData = new Date();
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-M-d");
    public CalendarView(Context context) {
        super(context);
        init();
    }

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // 获取手机屏幕参数
        mMetrics = getResources().getDisplayMetrics();
        // 创建画笔
        mPaint = new Paint();
        // 获取当前日期
        Calendar calendar = Calendar.getInstance();
        mCurYear    =   calendar.get(Calendar.YEAR);
        mCurMonth   =   calendar.get(Calendar.MONTH);
        mCurDate    =   calendar.get(Calendar.DATE);
        setSelYTD(mCurYear, mCurMonth, mCurDate);

        // 获取背景Bitmap
        mBgOptBitmap    = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_calendar_day_ischoose);
        mBgNotOptBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_calendar_day_isnotchoose);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Long draw = System.currentTimeMillis();

//        if(!isInit){
            initSize();

            // 绘制背景
            mPaint.setColor(mBgColor);
            //设置文本剧中
            mPaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), mPaint);

            mDays = new int[6][7];
            // 设置绘制字体大小
            mPaint.setTextSize(mDayTextSize * mMetrics.scaledDensity);
            // 设置绘制字体颜色


            // 获取当月一共有多少天
            mMonthDays = DateUtils.getMonthDays(mSelYear, mSelMonth);
            // 获取当月第一天位于周几
            mWeekNumber = DateUtils.getFirstDayWeek(mSelYear, mSelMonth);
//        }

//        Long drawEnd = System.currentTimeMillis();
//
//        Logger.t("draw").d(drawEnd-draw+"");


        Long forTime = System.currentTimeMillis();

        for(int day = 0; day < mMonthDays; day++){
            String dayStr = String.valueOf(day + 1);

            int column  =  (day + mWeekNumber - 1) % 7;
            int row     =  (day + mWeekNumber - 1) / 7;
            mDays[row][column] = day + 1;
            String str = "";
            int startX = (int) (mColumnSize * column + (mColumnSize - mPaint.measureText(dayStr)) / 2);
            int startY = (int) (mRowSize * row + mRowSize / 2 - (mPaint.ascent() + mPaint.descent()) / 2);
            // 判断当前天数是否不可选
//            Log.d("JayGengi",getSelData(mSelYear, mSelMonth, mDays[row][column]));
            //读取的数据
            if(mOptionalDates!=null && mOptionalDates.size()>0){

                SerPrice guideSerPrice  = mapOptionalDates.get(getSelData(mSelYear, mSelMonth, mDays[row][column]));
                if(guideSerPrice!=null){
                    str = guideSerPrice.getSerPrice()+"";

                if(guideSerPrice.getIsSaled()==1){
                            canvas.drawBitmap(mBgNotOptBitmap, startX - (mBgNotOptBitmap.getWidth() / 2), startY - (mBgNotOptBitmap.getHeight() / 2), mPaint);
                            mPaint.setColor(mDayNotOptColor);//mDayNormalColor
                        }else{
//                            // 可选，继续判断是否是点击过的
                            if(!mSelectedDates.contains(getSelData(mSelYear, mSelMonth, mDays[row][column]))){
//                                // 没有点击过，绘制默认背景
                            }else{
                                // 点击过，绘制点击过的背景
                                canvas.drawBitmap(mBgOptBitmap, startX - (mBgOptBitmap.getWidth() / 2), startY - (mBgOptBitmap.getHeight() / 2), mPaint);
                                mPaint.setColor(mDayPressedColor);
                            }
                        }
                }else{
                    if(!mSelectedDates.contains(getSelData(mSelYear, mSelMonth, mDays[row][column]))){
//                                // 没有点击过，绘制默认背景
                    }else{
                        // 点击过，绘制点击过的背景
                        canvas.drawBitmap(mBgOptBitmap, startX - (mBgOptBitmap.getWidth() / 2), startY - (mBgOptBitmap.getHeight() / 2), mPaint);
                        mPaint.setColor(mDayPressedColor);
                    }
                }
//                for(int i =0;i<mOptionalDates.size();i++){
//                    //比对是否有
//                    if(getSelData(mSelYear, mSelMonth, mDays[row][column])
//                            .equals(mOptionalDates.get(i).getBeginDtSt())){
//                        str = mOptionalDates.get(i).getSerPrice()+"元";
//                        if(mOptionalDates.get(i).getIsSaled()==1){
//                            canvas.drawBitmap(mBgNotOptBitmap, startX - (mBgNotOptBitmap.getWidth() / 2), startY - (mBgNotOptBitmap.getHeight() / 2), mPaint);
//                            mPaint.setColor(mDayNotOptColor);//mDayNormalColor
//                        }else{
//                            // 可选，继续判断是否是点击过的
//                            if(!mSelectedDates.contains(getSelData(mSelYear, mSelMonth, mDays[row][column]))){
//                                // 没有点击过，绘制默认背景
//                            }else{
//                                // 点击过，绘制点击过的背景
//                                canvas.drawBitmap(mBgOptBitmap, startX - (mBgOptBitmap.getWidth() / 2), startY - (mBgOptBitmap.getHeight() / 2), mPaint);
//                                mPaint.setColor(mDayPressedColor);
//                            }
//                        }
//                    }
//                }
            }else{
                // 可选，继续判断是否是点击过的
                if(!mSelectedDates.contains(getSelData(mSelYear, mSelMonth, mDays[row][column]))){
                    // 没有点击过，绘制默认背景
                }else{
                    // 点击过，绘制点击过的背景
                    canvas.drawBitmap(mBgOptBitmap, startX - (mBgOptBitmap.getWidth() / 2), startY - (mBgOptBitmap.getHeight() / 2), mPaint);
                    mPaint.setColor(mDayPressedColor);
                }
            }
            // 绘制天数
            String price = dayStr+","+str;

            mPaint.setColor(mDayNotOptColor);
            for (String line: price.split(",")) {
                //判断字符串是否包含.「小数点的形式存在于float价格中」
                if(!line.contains(".")){
                    mPaint.setColor(blackColor);
                    mPaint.setTextSize(mDayTextSize * mMetrics.scaledDensity);
                }else{
                    mPaint.setColor(PriceColor);
                    mPaint.setTextSize(30);
                }
                canvas.drawText(line, startX, startY, mPaint);
                startY += mPaint.descent() - mPaint.ascent();
            }
//            if(mOptionalDates.contains(getSelData(mSelYear, mSelMonth, mDays[row][column]))){
//                canvas.drawBitmap(mBgNotOptBitmap, startX - (mBgNotOptBitmap.getWidth() / 2), startY - (mBgNotOptBitmap.getHeight() / 2), mPaint);
//                mPaint.setColor(mDayNotOptColor);//mDayNormalColor
//            }else{
//                // 可选，继续判断是否是点击过的
//                if(!mSelectedDates.contains(getSelData(mSelYear, mSelMonth, mDays[row][column]))){
//                    // 没有点击过，绘制默认背景
//                }else{
//                    // 点击过，绘制点击过的背景
//                    canvas.drawBitmap(mBgOptBitmap, startX - (mBgOptBitmap.getWidth() / 2), startY - (mBgOptBitmap.getHeight() / 2), mPaint);
//                    mPaint.setColor(mDayPressedColor);
//                }
//
//            }
//            // 绘制天数
//            String price = dayStr+","+str;
//            mPaint.setColor(mDayNotOptColor);
//            for (String line: price.split(",")) {
//                if(line.length()<3){
//                    mPaint.setColor(mDayNotOptColor);
//                    mPaint.setTextSize(mDayTextSize * mMetrics.scaledDensity);
//                }else{
//                    mPaint.setColor(PriceColor);
//                    mPaint.setTextSize(30);
//                }
//                canvas.drawText(line, startX, startY, mPaint);
//                startY += mPaint.descent() - mPaint.ascent();
//            }
        }

//        Long forTimeEnd = System.currentTimeMillis();
//        Logger.t("fortime").d(forTimeEnd-forTime);
    }

    private int downX = 0,downY = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int eventCode = event.getAction();
        switch(eventCode){
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();
                downY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                if(!mClickable) return true;

                int upX = (int) event.getX();
                int upY = (int) event.getY();
                if(Math.abs(upX - downX) < 10 && Math.abs(upY - downY) < 10){
                    performClick();
                    onClick((upX + downX) / 2, (upY + downY) / 2);
                }
                break;
        }
        return true;
    }

    /**
     * 点击事件
     */
    private void onClick(int x, int y){
        try {
            int row = y / mRowSize;
            int column = x / mColumnSize;
            setSelYTD(mSelYear, mSelMonth, mDays[row][column]);
            String time =mSelYear+"-"+(mSelMonth+1)+"-"+mDays[row][column];
            Date date = null;
            date = sdf.parse(time);
            //判断当前月份点击的日是否是今日之前
            if(date.after(nowData)){
                // 判断是否点击过
                boolean isSelected = mSelectedDates.contains(getSelData(mSelYear, mSelMonth, mSelDate));
                // 判断是否可以添加
                //        boolean isCanAdd = mOptionalDates.contains(getSelData(mSelYear, mSelMonth, mSelDate));
                if(isSelected) {
                    mSelectedDates.remove(getSelData(mSelYear, mSelMonth, mSelDate));
                }else{
                    mSelectedDates.add(getSelData(mSelYear, mSelMonth, mSelDate));
                }
                invalidate();
                if(mListener != null){
                    // 执行回调
                    mListener.onClickDateListener(mSelYear, (mSelMonth + 1), mSelDate);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化列宽和高
     */
    private void initSize() {
        // 初始化每列的大小
        mColumnSize = getWidth() / NUM_COLUMNS;
        // 初始化每行的大小
        mRowSize = getHeight() / NUM_ROWS;
        isInit=true;
    }

    /**
     * 设置可选择日期
     * @param dates 日期数据
     */
    public void setOptionalDate(List<SerPrice> dates){
        this.mOptionalDates = dates;

        for(SerPrice serPrice : mOptionalDates){
            mapOptionalDates.put(serPrice.getBeginDtStr(),serPrice);
        }

        invalidate();
    }

    /**
     * 设置已选日期数据
     */
    public void setSelectedDates(List<String> dates){
        this.mSelectedDates = dates;
    }

    /**
     * 获取已选日期数据
     */
    public List<String> getSelectedDates(){
        return mSelectedDates;
    }

    /**
     * 设置日历是否可以点击
     */
    @Override
    public void setClickable(boolean clickable) {
        this.mClickable = clickable;
    }

    /**
     * 设置年月日
     * @param year  年
     * @param month 月
     * @param date  日
     */
    private void setSelYTD(int year, int month, int date){
        this.mSelYear   =   year;
        this.mSelMonth  =   month;
        this.mSelDate   =   date;
    }

    /**
     * 设置上一个月日历
     */
    public void setLastMonth(){
        int year    =   mSelYear;
        int month   =   mSelMonth;
        int day     =   mSelDate;
        // 如果是1月份，则变成12月份
        if(month == 0){
            year = mSelYear-1;
            month = 11;
        }else if(DateUtils.getMonthDays(year, month) == day){
            //　如果当前日期为该月最后一点，当向前推的时候，就需要改变选中的日期
            month = month-1;
            day = DateUtils.getMonthDays(year, month);
        }else{
            month = month-1;
        }
        setSelYTD(year,month,day);
        invalidate();
    }

    /**
     * 设置下一个日历
     */
    public void setNextMonth(){
        int year    =   mSelYear;
        int month   =   mSelMonth;
        int day     =   mSelDate;
        // 如果是12月份，则变成1月份
        if(month == 11){
            year = mSelYear+1;
            month = 0;
        }else if(DateUtils.getMonthDays(year, month) == day){
            //　如果当前日期为该月最后一点，当向前推的时候，就需要改变选中的日期
            month = month + 1;
            day = DateUtils.getMonthDays(year, month);
        }else{
            month = month + 1;
        }
        setSelYTD(year,month,day);
        invalidate();
    }

    /**
     * 获取当前展示的年和月份
     * @return 格式：2016-06
     */
    public String getDate(){
        String data;
        if((mSelMonth + 1) < 10){
            data = mSelYear + "-0" + (mSelMonth + 1);
        }else{
            data = mSelYear + "-" + (mSelMonth + 1);
        }
        return data;
    }

    /**
     * 获取当前展示的日期
     * @return 格式：20160606
     */
    private String getSelData(int year, int month, int date){
        String monty, day;
        month = (month + 1);

        // 判断月份是否有非0情况
        if((month) < 10) {
            monty = "0" + month;
        }else{
            monty = String.valueOf(month);
        }

        // 判断天数是否有非0情况
        if((date) < 10){
            day = "0" + (date);
        }else{
            day = String.valueOf(date);
        }
        return year +"-"+ monty +"-"+ day;
    }

    private OnClickListener mListener;

    public interface OnClickListener{
        void onClickDateListener(int year, int month, int day);
    }

    /**
     * 设置点击回调
     */
    public void setOnClickDate(OnClickListener listener){
        this.mListener = listener;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        recyclerBitmap(mBgOptBitmap);
        recyclerBitmap(mBgNotOptBitmap);
    }

    /**
     * 释放Bitmap资源
     */
    private void recyclerBitmap(Bitmap bitmap) {
        if(bitmap != null && !bitmap.isRecycled()){
            bitmap.recycle();
        }
    }
}
