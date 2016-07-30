package m.mcoupledate;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MemberData extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //下拉式選單
    private Spinner b_year;
    ArrayList<Integer>byear = new ArrayList<>();
    private Spinner b_month;
    ArrayList<Integer>bmonth = new ArrayList<>();
    private Spinner b_day;
    ArrayList<Integer>bday = new ArrayList<>();
    private Spinner r_year;
    ArrayList<Integer>ryear = new ArrayList<>();
    private Spinner r_month;
    ArrayList<Integer>rmonth = new ArrayList<>();
    private Spinner r_day;
    ArrayList<Integer>rday = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_data);
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

        //下拉式選單
        b_year = (Spinner)findViewById(R.id.b_year);
        b_month = (Spinner)findViewById(R.id.b_month);
        b_day = (Spinner)findViewById(R.id.b_day);
        r_year = (Spinner)findViewById(R.id.r_year);
        r_month = (Spinner)findViewById(R.id.r_month);
        r_day = (Spinner)findViewById(R.id.r_day);
        DateFormat stringFormatter = new SimpleDateFormat("yyyy-MM-dd");//要轉成String的
        Date now = Calendar.getInstance().getTime();//取得現在時間
        String today = stringFormatter.format(now);//將取得的時間轉成String
        int a = Integer.valueOf(today.substring(0, 4));
        for(int i = a ; i >= 1900 ; i--){
            byear.add(i);
            ryear.add(i);
        }
        for(int i = 1 ; i <= 12 ; i++){
            bmonth.add(i);
            rmonth.add(i);
        }
        for(int i = 1 ; i <= 31 ; i++){
            bday.add(i);
            rday.add(i);
        }
        //建立ArrayAdapter
        ArrayAdapter<Integer> adapter_b_year = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, byear);
        ArrayAdapter<Integer> adapter_b_month = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, bmonth);
        ArrayAdapter<Integer> adapter_b_day = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, bday);
        ArrayAdapter<Integer> adapter_r_year = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, ryear);
        ArrayAdapter<Integer> adapter_r_month = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, rmonth);
        ArrayAdapter<Integer> adapter_r_day = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, rday);
        //ArrayAdapter顯示格式
        adapter_b_year.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_b_month.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_b_day.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_r_year.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_r_month.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_r_day.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //設定Spinner的資料來源
        b_year.setAdapter(adapter_b_year);
        b_month.setAdapter(adapter_b_month);
        b_day.setAdapter(adapter_b_day);
        r_year.setAdapter(adapter_r_year);
        r_month.setAdapter(adapter_r_month);
        r_day.setAdapter(adapter_r_day);
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
        //123
        if (id == R.id.nav_memberData) {
            // 設定從這個活動跳至 home 的活動
            Intent intent = new Intent(MemberData.this, MemberData.class);
            // 開始跳頁
            startActivity(intent);
        } else if (id == R.id.nav_memorialDay) {

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
