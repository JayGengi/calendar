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
import com.jaygengi.calendar.tools.DateUtils;
import com.jaygengi.calendar.tools.SerPrice;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 自定义日历
 * author: JayGengi 60167
 * email:  gengy@chinaraising.com
 * time:  2017/04/08 14:44
 */
public class CalendarActivity extends AppCompatActivity {
    public static CalendarActivity instance = null;

    Date nowData = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
    private TextView mTextSelectMonth;
    private ImageView mLastMonthView;
    private ImageView mNextMonthView;
    private CalendarView mCalendarView;
    private List<SerPrice> dateList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_main);
        instance = this;

        initView();
        initData();

    }

    /**
     * 初始化UI
     * create by gengy 60167
     * at  18-4-11 上午9:26
     */
    private void initView() {
        mTextSelectMonth = findViewById(R.id.txt_select_month);
        mLastMonthView = findViewById(R.id.img_select_last_month);//calendar_bar_iv_previours
        mNextMonthView = findViewById(R.id.img_select_next_month);//
        mCalendarView = findViewById(R.id.calendarView);

        //上一月
        mLastMonthView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Date date = sdf.parse(mCalendarView.getDate());
                    if (date.after(nowData)) {
                        mCalendarView.setLastMonth();
                        mTextSelectMonth.setText(mCalendarView.getDate());
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        //下一月
        mNextMonthView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCalendarView.setNextMonth();
                mTextSelectMonth.setText(mCalendarView.getDate());
            }
        });
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

    /**
     * 模拟数据
     * create by gengy 60167
     * at  18-4-11 上午9:25
     */
    private void initData() {
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 10; i++) {
            Date date = new Date();
            SerPrice serPrice = new SerPrice();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, +i);//+1今天的时间加一天
            date = calendar.getTime();
            if (i < 3) {
                serPrice.setSelect(true);//不可选择
            } else {
                serPrice.setSelect(false);
            }
            serPrice.setCalendarTime(DateUtils.getDateStr(date, "yyyy-MM-dd"));
            serPrice.setSerPrice((float) (1000 + i));//日期对应的设置价格
            dateList.add(serPrice);
        }
        // 设置可选日期
        mCalendarView.setOptionalDate(dateList);
        if (mCalendarView.getSelectedDates() != null && mCalendarView.getSelectedDates().size() > 0) {
            mCalendarView.getSelectedDates().clear();
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
