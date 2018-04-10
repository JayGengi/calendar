package com.jaygengi.calendar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jaygengi.calendar.tools.CalendarView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
/**
 * 自定义日历
 * author: JayGengi 60167
 * email:  gengy@chinaraising.com
 * time:  2017/11/14 14:44
 */
public class CalendarActivity extends AppCompatActivity implements View.OnClickListener{
    public static CalendarActivity instance = null;

    Date nowData = new Date();
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
    private TextView mTextSelectMonth;
    private ImageView mLastMonthView;
    private ImageView mNextMonthView;
    private CalendarView mCalendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_main);
        instance = this;

        initView();

    }
    private void initView(){
        mTextSelectMonth =  findViewById(R.id.txt_select_month);
        mLastMonthView =  findViewById(R.id.img_select_last_month);//calendar_bar_iv_previours
        mNextMonthView =  findViewById(R.id.img_select_next_month);//
        mCalendarView =  findViewById(R.id.calendarView);
        // 设置点击事件
        mCalendarView.setOnClickDate(new CalendarView.OnClickListener() {
            @Override
            public void onClickDateListener(int year, int month, int day) {
                Toast.makeText(getApplication(), year + "年" + month + "月" + day + "日", Toast.LENGTH_SHORT).show();
                 //获取已选择日期
                List<String> dates = mCalendarView.getSelectedDates();
                for (String date : dates) {
                    Log.d("test", "date: " + date);
                }
            }
        });
        mTextSelectMonth.setText(mCalendarView.getDate());
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_select_last_month:
                try {
                    Date date = sdf.parse(mCalendarView.getDate());
                    if(date.after(nowData)){
                        mCalendarView.setLastMonth();
                        mTextSelectMonth.setText(mCalendarView.getDate());
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.img_select_next_month:
                mCalendarView.setNextMonth();
                mTextSelectMonth.setText(mCalendarView.getDate());
                break;
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
