package m.mcoupledate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class SiteAttractionActivity extends AppCompatActivity {

    private Spinner mSpn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_attraction);

        mSpn = (Spinner) findViewById(R.id.spn);

        ArrayAdapter<CharSequence> arrAdapSpn
                = ArrayAdapter.createFromResource(SiteAttractionActivity.this, //對應的Context
                R.array.spn_list_area, //選項資料內容
                R.layout.spinner_item); //自訂getView()介面格式(Spinner介面未展開時的View)

        arrAdapSpn.setDropDownViewResource(R.layout.spinner_dropdown_item); //自訂getDropDownView()介面格式(Spinner介面展開時，View所使用的每個item格式)
        mSpn.setAdapter(arrAdapSpn); //將宣告好的 Adapter 設定給 Spinner
        //mSpn.setOnItemSelectedListener(spnOnItemSelected);

        //動態出現所有景點
        init();
    }

    private AdapterView.OnItemSelectedListener spnRegionOnItemSelected
            = new AdapterView.OnItemSelectedListener()
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id)
        {
            // TODO Auto-generated method stub
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0)
        {
            // TODO Auto-generated method stub
        }
    };
    //String name, String date, int diff  參數暫放
    public void init()
    {
        String name = "HAHA";
        String date = "LOC";
        LinearLayout linearLayout1=(LinearLayout)findViewById(R.id.attraction_in_sv);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        View view =LayoutInflater.from(this).inflate(R.layout.attraction_init, null);
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
}
