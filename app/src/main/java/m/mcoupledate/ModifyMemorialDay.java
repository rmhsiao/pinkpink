package m.mcoupledate;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

public class ModifyMemorialDay extends AppCompatActivity {
    private EditText eventName;
    private Button modifyName;
    private CalendarView calendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_memorial_day);

        eventName = (EditText)findViewById(R.id.aaa);
        modifyName = (Button)findViewById(R.id.modifyName);
        calendarView = (CalendarView) findViewById(R.id.modifyMemoryDay);
        modifyName.setOnClickListener(btn);

        //印出原本是紀念日的日子為其他顏色

        //監聽當日期改變
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            //Month從0算起
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String date;
                if(month + 1 < 10)
                    date = year + "-0" + (month+1) + "-" + dayOfMonth;
                else
                    date = year + "0" + (month+1) + "-" + dayOfMonth;
                //開啟資料庫
                SQLiteDatabase db = openOrCreateDatabase("db.db", MODE_PRIVATE, null);
                //從sqlite撈資料印出
                eventName.setText("北七");

                //Toast.makeText(getApplicationContext(), date, Toast.LENGTH_SHORT).show();
            }
        }
        );
    }
    private Button.OnClickListener btn = new Button.OnClickListener(){
        @Override
        public void onClick(View v){
            //eventName.getText();
            eventName.setText(eventName.getText());
            //存入sqlite
            Toast.makeText(ModifyMemorialDay.this, "修改成功", Toast.LENGTH_SHORT).show();
        }
    };
}
/*改進
原本是紀念日的藥不同顏色
輕按標示原本紀念日
久按修改紀念日
*/
/*待做
印出原本是紀念日的日子為其他顏色
從sqlite撈資料印出
存入sqlite
sqlite與資料庫的同步
*/