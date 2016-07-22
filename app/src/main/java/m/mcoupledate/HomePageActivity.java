package m.mcoupledate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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


public class HomePageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String conAPI = "http://140.117.71.216/pinkCon/";

    private Context mContext;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;

    private String id = "1763438647274913";

    private int count;

    // 宣告 LinearLayout 物件(為了動態新增)
    private RelativeLayout homeLayout;
    //private TextView tsetDialog;
    //此TextView是靜態新增的
    private TextView totalDaysDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_memberData) {
            // 設定從這個活動跳至 home 的活動
            Intent intent = new Intent(HomePageActivity.this, memberData.class);
            // 開始跳頁
            startActivity(intent);
        } else if (id == R.id.nav_memorialDay) {
            // 設定從這個活動跳至 設定紀念日 的活動
            Intent intent = new Intent(HomePageActivity.this, ModifyMemorialDay.class);
            // 開始跳頁
            startActivity(intent);
        } else if (id == R.id.nav_myViewpoint) {

        } else if (id == R.id.nav_myTravle) {

        } else if (id == R.id.nav_travleEdit) {

        } else if (id == R.id.nav_logout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
