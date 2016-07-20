package m.mcoupledate;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Home extends AppCompatActivity {


    private String conAPI = "http://140.117.71.216/pinkCon/";

    private Context mContext;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;

    private String id = MainActivity.getUserId();

    private int count;

    // 宣告 LinearLayout 物件(為了動態新增)
    private RelativeLayout homeLayout;
    //private TextView tsetDialog;
    //此TextView是靜態新增的
    private TextView totalDaysDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //靜態
        totalDaysDialog = (TextView)findViewById(R.id.totalDaysDialog);
        totalDays();

        // 取得 LinearLayout 物件(為了動態新增)
        homeLayout = (RelativeLayout)findViewById(R.id.viewObj);
        //動態
        count = 0;//要抓取第幾筆資料
        //while(true) {
        for(int i = 0;i<4;i++){
            TextView tsetDialog = new TextView(this);
            tsetDialog.setText("失敗" + count);
            homeLayout.addView(tsetDialog);
            printMemorialDays(tsetDialog);
        }
    }
    //算總共交往多久
    public void totalDays() {
        mContext = this;
        mRequestQueue = Volley.newRequestQueue(mContext);
        mStringRequest = new StringRequest(Request.Method.POST, conAPI+"totalDays.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        totalDaysDialog.setText(response);//php echo出現的地方
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        totalDaysDialog.setText(error.getMessage()+"-----"+error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<String, String>();

                        map.put("User", id);

                        return map;
                    }
                };

        mRequestQueue.add(mStringRequest);
    }
    //印出紀念日們
    public void printMemorialDays(final TextView view) {
        mContext = this;
        mRequestQueue = Volley.newRequestQueue(mContext);
        mStringRequest = new StringRequest(Request.Method.POST, conAPI+"printMemorialDays.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        view.setText(response);//php echo出現的地方
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        view.setText(error.getMessage()+"-----"+error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();

                map.put("User", id);
                String strcount = "";
                strcount += count;
                map.put("Count",  strcount);
                count += 1;
                return map;
            }
        };
        mRequestQueue.add(mStringRequest);
    }
}
