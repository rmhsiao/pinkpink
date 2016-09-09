package m.mcoupledate;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class ModifyMemorialDay extends AppCompatActivity {

    private SQLiteDatabase db = null;
    private String conAPI = "http://140.117.71.216/pinkCon/";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_memorial_day);

        printMemorialDays();
    }

    //印出紀念日們(新版)
    public void printMemorialDays() {
        db = openOrCreateDatabase("userdb.db", MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery("select * from memorialday",null);
        cursor.moveToFirst();
        do{
            if(cursor.getCount() != 0){
                int mdId = cursor.getInt(0);
                //Log.d("mdId", ""+mdId);
                String name = cursor.getString(1);
                String theDay = cursor.getString(2);
                DateFormat stringFormatter = new SimpleDateFormat("yyyy-MM-dd");//要轉成String的
                SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");//要轉成Date的

                Date now = Calendar.getInstance().getTime();//取得現在時間
                String today = stringFormatter.format(now);//將取得的時間轉成String

                //String to Date 紀念日時間
                Date d1 = null;
                try{
                    String d1_str = today.substring(0, 4) + "-" + theDay.substring(5, 7) + "-" + theDay.substring(8, 10);
                    d1 = dateFormatter.parse(d1_str);
                }
                catch (ParseException e){
                    e.printStackTrace();
                }
                //String to Date 現在時間
                Date d2 = null;
                try{
                    d2 = dateFormatter.parse(today);
                }
                catch (ParseException e){
                    e.printStackTrace();
                }
                //把過的忽略
                if(Integer.valueOf(theDay.substring(5, 7)) > Integer.valueOf(today.substring(5, 7))){//月大於 日就不用比了
                    long diff = d2.getTime() - d1.getTime();
                    String diffstr = "" + diff/(1000*60*60*24);
                    init(name, theDay, Math.abs(Integer.valueOf(diffstr)), mdId);
                }
                else if(Integer.valueOf(theDay.substring(5, 7)) == Integer.valueOf(today.substring(5, 7)) && Integer.valueOf(theDay.substring(8, 10)) >= Integer.valueOf(today.substring(8, 10))){//月等於 比日
                    long diff = d2.getTime() - d1.getTime();
                    String diffstr = "" + diff/(1000*60*60*24);
                    init(name, theDay, Math.abs(Integer.valueOf(diffstr)), mdId);
                }
                else if(Integer.valueOf(theDay.substring(5, 7)) < Integer.valueOf(today.substring(5, 7))){//月小於  年+1
                    try{
                        String d1_str = (Integer.valueOf(today.substring(0, 4) + 1)) + "-" + theDay.substring(5, 7) + "-" + theDay.substring(8, 10);
                        d1 = dateFormatter.parse(d1_str);
                    }
                    catch (ParseException e){
                        e.printStackTrace();
                    }
                    long diff = d2.getTime() - d1.getTime();
                    String diffstr = "" + diff/(1000*60*60*24);
                    init(name, theDay, Math.abs(Integer.valueOf(diffstr)), mdId);
                }
                else if(Integer.valueOf(theDay.substring(5, 7)) == Integer.valueOf(today.substring(5, 7)) && Integer.valueOf(theDay.substring(8, 10)) < Integer.valueOf(today.substring(8, 10))){//月等於 日小於  年+1
                    try{
                        String d1_str = (Integer.valueOf(today.substring(0, 4) + 1)) + "-" + theDay.substring(5, 7) + "-" + theDay.substring(8, 10);
                        d1 = dateFormatter.parse(d1_str);
                    }
                    catch (ParseException e){
                        e.printStackTrace();
                    }
                    long diff = d2.getTime() - d1.getTime();
                    String diffstr = "" + diff/(1000*60*60*24);
                    init(name, theDay, Math.abs(Integer.valueOf(diffstr)), mdId);
                }
            }
        }while(cursor.moveToNext());
    }

    //新增單筆紀念日資料
    public void init(String name, String date, int diff, int bId)
    {
        String diffDay = Integer.toString(diff);
        LinearLayout linearLayout1=(LinearLayout)findViewById(R.id.activity_service_select);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        View view =LayoutInflater.from(this).inflate(R.layout.my_mday, null);
        view.setLayoutParams(lp);
        TextView tv1 = (TextView) view.findViewById(R.id.m_context);
        TextView tv2 = (TextView) view.findViewById(R.id.m_day);
        TextView tv3 = (TextView) view.findViewById(R.id.m_diffTime);
        Button ebt = (Button)view.findViewById(R.id.edit_button);
        Button dbt = (Button)view.findViewById(R.id.delete_button);
        ebt.setTag(bId);
        ebt.setTag(bId);
        ebt.setOnClickListener(editOnClickListener);
        dbt.setOnClickListener(clearOnClickListener);
        ebt.setId(bId);
        dbt.setId(bId);
        tv1.setText(name);
        tv2.setText(date);
        tv3.setText(diffDay);
        linearLayout1.addView(view);
    }

    private Button.OnClickListener editOnClickListener
            = new Button.OnClickListener(){

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            AlertDialog.Builder alert = new AlertDialog.Builder(ModifyMemorialDay.this);
            LayoutInflater inflater = getLayoutInflater();
            Button editBtn =  (Button)arg0; //在new 出所按下的按鈕
            int id = editBtn.getId();//獲取被點擊的按鈕的id
            alert.setTitle("Title");
            // 使用你設計的layout
            final View inputView = inflater.inflate(R.layout.activity_member_data_edit, null);
            alert.setView(inputView);
            final EditText inputName = (EditText)inputView.findViewById(R.id.username);
            final EditText inputT1 = (EditText)inputView.findViewById(R.id.r_year);
            final EditText inputT2 = (EditText)inputView.findViewById(R.id.r_month);
            final EditText inputT3 = (EditText)inputView.findViewById(R.id.r_day);


            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // 修改動作
                }
            });

            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                // do something when the button is clicked
                public void onClick(DialogInterface arg0, int arg1) {
                    //不管他
                }
            });
            alert.show();
        }};

    private Button.OnClickListener clearOnClickListener
            = new Button.OnClickListener(){

        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            Button delBtn =  (Button)arg0; //在new 出所按下的按鈕
            int id = delBtn.getId();//獲取被點擊的按鈕的id
            AlertDialog.Builder delAlertDialog = new AlertDialog.Builder(ModifyMemorialDay.this);
            delAlertDialog.setTitle("- 確定刪除此紀念日? -");
            delAlertDialog.setMessage(Integer.toString(id));
            delAlertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                // do something when the button is clicked
                public void onClick(DialogInterface arg0, int arg1) {
                    //刪除動作寫這裡
                }
            });
            delAlertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                // do something when the button is clicked
                public void onClick(DialogInterface arg0, int arg1) {
                    //...
                }
            });
            delAlertDialog.show();
        }};
}
/*改進
原本是紀念日的藥不同顏色
輕按標示原本紀念日
久按修改紀念日

把自己生日也顯示

*/
/*待做
印出原本是紀念日的日子為其他顏色
從sqlite撈資料印出
存入sqlite
sqlite與資料庫的同步
*/
