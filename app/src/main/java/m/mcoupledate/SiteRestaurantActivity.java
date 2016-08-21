package m.mcoupledate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SiteRestaurantActivity extends AppCompatActivity {

    @InjectView(R.id.dropDownMenu)
    DropDownMenu mDropDownMenu;
    private String headers[] = {"行政區", "時段", "種類", "口味"};
    private List<View> popupViews = new ArrayList<>();


   // private m.mcoupledate.ListDropDownAdapter cityAdapter;
    pri


    private String area[] = {"不限", "楠梓區", "左營區", "鼓山區", "三民區", "鹽埕區", "前金區", "新興區", "苓雅區", "前鎮區", "旗津區", "小港區", "鳳山區", "大寮區", "鳥松區", "林園區", "仁武區", "大樹區", "大社區", "岡山區"
            , "路竹區", "橋頭區", "梓官區", "彌陀區", "永安區", "燕巢區", "田寮區", "阿蓮區", "茄萣區", "湖內區", "旗山區", "美濃區", "內門區", "杉林區", "甲仙區", "六龜區", "茂林區", "桃源區", "那瑪夏區"};
    private String ages[] = {"不限", "早餐", "午餐", "早午餐", "下午茶", "晚餐", "消夜"};
    private String kinds[] = {"不限", "火烤", "吃到飽", "壽喜燒", "輕食", "晚餐", "素食", "速食", "海鮮", "手搖飲品", "咖啡廳", "小吃", "酒吧", "壽司", "火鍋", "排餐", "鐵板燒", "烘培", "熱炒", "甜點", "冰品", "拉麵", "炸物"};
    private String constellations[] = {"不限", "美式", "歐陸", "南洋", "日式", "韓式", "中港台"};

    private int constellationPosition[] = {0,0,0};
    private int cityPosition = 0;
    private int kindPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_restaurant);

        ButterKnife.inject(this);
        initView();
        init();
    }

    public void init()
    {
        String name = "HAHA";
        String date = "LOC";
        LinearLayout linearLayout1=(LinearLayout)findViewById(R.id.attractionL);
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

    private void initView() {
        //init city menu
        /*final ListView cityView = new ListView(this);
        cityView.setDividerHeight(0);
        cityAdapter = new ListDropDownAdapter(this, Arrays.asList(area));
        cityView.setAdapter(cityAdapter);*/

        final View cityView = getLayoutInflater().inflate(R.layout.custom_layout, null);
        GridView city = ButterKnife.findById(cityView, R.id.constellation);
        cityAdapter = new ConstellationAdapter(this, Arrays.asList(area));
        city.setAdapter(cityAdapter);
        TextView ok1 = ButterKnife.findById(cityView, R.id.ok);
        ok1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDropDownMenu.setTabText(cityPosition == 0 ? headers[3] : area[cityPosition]);
                mDropDownMenu.closeMenu();
            }
        });

        //init age menu
        final ListView ageView = new ListView(this);
        ageView.setDividerHeight(0);
        ageAdapter = new ListDropDownAdapter(this, Arrays.asList(ages));
        ageView.setAdapter(ageAdapter);

        //init sex menu

        final View kindView = getLayoutInflater().inflate(R.layout.custom_layout, null);
        GridView kind = ButterKnife.findById(kindView, R.id.constellation);
        kindAdapter = new ConstellationAdapter(this, Arrays.asList(kinds));
        kind.setAdapter(kindAdapter);
        TextView okkind = ButterKnife.findById(kindView, R.id.ok);
        okkind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDropDownMenu.setTabText(kindPosition == 0 ? headers[3] : kinds[kindPosition]);
                mDropDownMenu.closeMenu();
            }
        });

        //init constellation
        final View constellationView = getLayoutInflater().inflate(R.layout.custom_layout, null);
        GridView constellation = ButterKnife.findById(constellationView, R.id.constellation);
        constellationAdapter = new ConstellationAdapter(this, Arrays.asList(constellations));
        constellation.setAdapter(constellationAdapter);
        TextView ok = ButterKnife.findById(constellationView, R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDropDownMenu.setTabText(constellationPosition[0] == 0 ? headers[3] : constellations[constellationPosition[0]]);
                mDropDownMenu.closeMenu();
            }
        });

        //init popupViews
        popupViews.add(cityView);
        popupViews.add(ageView);
        popupViews.add(kindView);
        popupViews.add(constellationView);

        //add item click event
        city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cityAdapter.setCheckItem(position);
                cityPosition = position;
            }
        });

        ageView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ageAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[1] : ages[position]);
                mDropDownMenu.closeMenu();
            }
        });

        kind.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                kindAdapter.setCheckItem(position);
                kindPosition = position;
            }
        });

        constellation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                constellationAdapter.setCheckItem(position);
                constellationPosition[0] = position;
            }
        });

        //內容放這裡-----------------------------------------------------

        String name = "HAHA";
        String date = "LOC";
        LinearLayout linearLayout1=(LinearLayout)findViewById(R.id.attractionL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        View view = LayoutInflater.from(this).inflate(R.layout.attraction_init, null);
        view.setLayoutParams(lp);

        //圖片
        //ImageView imageView1 = (ImageView) findViewById( R.id.attraction_image);

        TextView tv1 = (TextView) view.findViewById(R.id.title);
        TextView tv2 = (TextView) view.findViewById(R.id.location);
        tv1.setText(name);
        tv2.setText(date);

        //RatingBar抓分數
        //float val = Float.parseFloat("4");
        //RatingBar rb = (RatingBar)findViewById(R.id.ratingBar);
        //rb.setRating(val);

        //init dropdownview

        linearLayout1.addView(view);


        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews,view);
    }

    @Override
    public void onBackPressed() {
        //退出activity前关闭菜单
        if (mDropDownMenu.isShowing()) {
            mDropDownMenu.closeMenu();
        } else {
            super.onBackPressed();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.site_search, menu);
        return true;
    }

    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            String msg = "";
            switch (menuItem.getItemId()) {
            }

            if(!msg.equals("")) {
                Toast.makeText(SiteRestaurantActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    };
}
