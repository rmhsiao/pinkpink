package m.mcoupledate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SiteRestaurantActivity extends AppCompatActivity {

    private String headers[] = {"行政區", "時段", "種類", "口味"};
    private List<View> popupViews = new ArrayList<>();


   // private m.mcoupledate.ListDropDownAdapter cityAdapter;



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
