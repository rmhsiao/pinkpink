package m.mcoupledate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CalendarView;
import android.widget.Toast;

public class ModifyMemorialDay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_memorial_day);
        //取得calendarView物件
        CalendarView calendarView = (CalendarView) findViewById(R.id.modifyMemoryDay);

        //印出原本是紀念日的日子


        //監聽當日期改變
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            //Month從0算起
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String date;
                if(month + 1 < 10)
                    date = year + "-0" + (month+1) + "-" + dayOfMonth;
                else
                    date = year + "0" + (month+1) + "-" + dayOfMonth;
                //比對日期
                //從sqlite撈會員資料
                Toast.makeText(getApplicationContext(), date, Toast.LENGTH_SHORT).show();
            }

        });

    }
}
/*
原本是紀念日的藥不同顏色
輕按標示原本紀念日
久按修改紀念日
*/