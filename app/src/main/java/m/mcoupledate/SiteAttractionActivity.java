package m.mcoupledate;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;
import com.zxl.library.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SiteAttractionActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private String headers[] = {"行政區", "愛心指數"};

    DropDownMenu mDropDownMenu;
    private BottomBar mBottomBar;

    public static String select_area = "";
    public static String select_love = "";

    // private m.mcoupledate.ListDropDownAdapter cityAdapter;


    private int[] types = new int[]{ DropDownMenu.TYPE_GRID , DropDownMenu.TYPE_LIST_CITY, DropDownMenu.TYPE_LIST_SIMPLE ,  DropDownMenu.TYPE_CUSTOM};
    private String area[] = {"不限", "楠梓區", "左營區", "鼓山區", "三民區", "鹽埕區", "前金區", "新興區", "苓雅區", "前鎮區", "旗津區", "小港區", "鳳山區", "大寮區", "鳥松區", "林園區", "仁武區", "大樹區", "大社區", "岡山區"
            , "路竹區", "橋頭區", "梓官區", "彌陀區", "永安區", "燕巢區", "田寮區", "阿蓮區", "茄萣區", "湖內區", "旗山區", "美濃區", "內門區", "杉林區", "甲仙區", "六龜區", "茂林區", "桃源區", "那瑪夏區"};
    private String love[] = {"不限", "未滿1心", "1心以上", "2心以上", "3心以上", "4心以上","5心"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_attraction);

        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.setItems(R.menu.bottom_menu);
        mBottomBar.selectTabAtPosition(1,true);
        mBottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                //单击事件 menuItemId 是 R.menu.bottombar_menu 中 item 的 id
                switch (menuItemId) {
                    case R.id.bb_menu_memorialday:
                        Intent go2 = new Intent(SiteAttractionActivity.this, HomePageActivity.class);
                        startActivity(go2);
                        break;
                    case R.id.bb_menu_site:
                        break;
                    case R.id.bb_menu_trip:
                        Toast.makeText(SiteAttractionActivity.this, "敬請期待", Toast.LENGTH_LONG).show();
                        mBottomBar.selectTabAtPosition(1,true);
                        break;
                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                //重选事件，当前已经选择了这个，又点了这个tab。微博点击首页刷新页面
            }
        });

        // 当点击不同按钮的时候，设置不同的颜色
        // 可以用以下三种方式来设置颜色.
        mBottomBar.mapColorForTab(0, 0xFF5D4037);
        mBottomBar.mapColorForTab(1, ContextCompat.getColor(this, R.color.colorAccent));
        mBottomBar.mapColorForTab(2, "#7B1FA2");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mDropDownMenu= (DropDownMenu) findViewById( R.id.dropDownMenu);
        initView();
    }

    private void initView() {
        View contentView = getLayoutInflater().inflate(R.layout.activity_site_contentview, null);
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), initViewData(), contentView);
        init();
        //该监听回调只监听默认类型，如果是自定义view请自行设置，参照demo
        mDropDownMenu.addMenuSelectListener(new DropDownMenu.OnDefultMenuSelectListener() {
            @Override
            public void onSelectDefaultMenu(int index, int pos, String clickstr) {
                String end = null;
                //index:点击的tab索引，pos：单项菜单中点击的位置索引，clickstr：点击位置的字符串
                switch( index )  /*status 只能為整數、長整數或字元變數.*/
                {
                    case 0:
                        select_area = clickstr;
                        break;
                    case 1:
                        select_love = clickstr;
                        break;
                }
                Toast.makeText(getBaseContext(), clickstr, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //設置篩選選單
    private List<HashMap<String, Object>> initViewData() {
        List<HashMap<String, Object>> viewDatas = new ArrayList<>();
        HashMap<String, Object> map1,map2;
        map1 = new HashMap<String, Object>();
        map2 = new HashMap<String, Object>();

        map1.put(DropDownMenu.KEY, DropDownMenu.TYPE_GRID);
        map1.put(DropDownMenu.VALUE, area);
        viewDatas.add(map1);

        map2.put(DropDownMenu.KEY, DropDownMenu.TYPE_LIST_SIMPLE);
        map2.put(DropDownMenu.VALUE, love);
        viewDatas.add(map2);

        return viewDatas;
    }

    //動態抓取
    public void init()
    {
        String name = "HAHA";
        String date = "LOC";
        LinearLayout linearLayout1=(LinearLayout)findViewById(R.id.site_info);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        View view = LayoutInflater.from(this).inflate(R.layout.attraction_init, null);
        view.setLayoutParams(lp);
//圖片
//      ImageView imageView1 = (ImageView) findViewById( R.id.attraction_image);

        TextView tv1 = (TextView) view.findViewById(R.id.title);
        TextView tv2 = (TextView) view.findViewById(R.id.location);
        tv1.setText(name);
        tv2.setText(date);
//RatingBar抓分數
//        float val = Float.parseFloat("4");
//        RatingBar rb = (RatingBar)findViewById(R.id.ratingBar);
//        rb.setRating(val);

        linearLayout1.addView(view);
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
            Intent intent = new Intent(SiteAttractionActivity.this, MemberData.class);
            // 開始跳頁
            startActivity(intent);
        } else if (id == R.id.nav_memorialDay) {
            // 設定從這個活動跳至 home 的活動
            Intent intent = new Intent(SiteAttractionActivity.this, ModifyMemorialDay.class);
            // 開始跳頁
            startActivity(intent);

        } else if (id == R.id.nav_myViewpoint) {
            Intent go2 = new Intent(SiteAttractionActivity.this, ManageSiteActivity.class);
            startActivity(go2);

        } else if (id == R.id.nav_myTravle) {
            Intent go2 = new Intent(SiteAttractionActivity.this, SiteAttractionActivity.class);
            startActivity(go2);

        } else if (id == R.id.nav_travleEdit) {
            Intent go2 = new Intent(SiteAttractionActivity.this, SiteInfoActivity.class);
            startActivity(go2);

        } else if (id == R.id.nav_logout) {
            Intent go2 = new Intent(SiteAttractionActivity.this, SiteRestaurantActivity.class);
            startActivity(go2);
        } else if (id == R.id.my_attraction) {
            Intent go2 = new Intent(SiteAttractionActivity.this, MyAttractionActivity.class);
            startActivity(go2);
        } else if (id == R.id.my_restaurant) {
            Intent go2 = new Intent(SiteAttractionActivity.this, MyRestaurantActivity.class);
            startActivity(go2);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
