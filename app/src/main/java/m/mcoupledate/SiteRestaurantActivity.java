package m.mcoupledate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SiteRestaurantActivity extends AppCompatActivity {

    private Spinner lSpn,sSpn,cSpn,tSpn,kSpn,cSpn2,cSpn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_restaurant);

        lSpn = (Spinner) findViewById(R.id.large);
        sSpn = (Spinner) findViewById(R.id.small);
        cSpn = (Spinner) findViewById(R.id.country);
        tSpn = (Spinner) findViewById(R.id.time);
        kSpn = (Spinner) findViewById(R.id.kind);
        cSpn2 = (Spinner) findViewById(R.id.country2);
        cSpn3 = (Spinner) findViewById(R.id.country3);

        ArrayAdapter<CharSequence> arrAdapSpnL
                = ArrayAdapter.createFromResource(SiteRestaurantActivity.this, //對應的Context
                R.array.spn_list_city, //選項資料內容
                R.layout.spinner_item); //自訂getView()介面格式(Spinner介面未展開時的View)

        arrAdapSpnL.setDropDownViewResource(R.layout.spinner_dropdown_item); //自訂getDropDownView()介面格式(Spinner介面展開時，View所使用的每個item格式)
        lSpn.setAdapter(arrAdapSpnL); //將宣告好的 Adapter 設定給 Spinner
        //lSpn.setOnItemSelectedListener(spnOnItemSelected);

        ArrayAdapter<CharSequence> arrAdapSpnS
                = ArrayAdapter.createFromResource(SiteRestaurantActivity.this,
                R.array.spn_list_area,
                R.layout.spinner_item);

        arrAdapSpnS.setDropDownViewResource(R.layout.spinner_dropdown_item);
        sSpn.setAdapter(arrAdapSpnS);
        //sSpn.setOnItemSelectedListener(spnOnItemSelected);

        ArrayAdapter<CharSequence> arrAdapSpnC
                = ArrayAdapter.createFromResource(SiteRestaurantActivity.this,
                R.array.spn_list_country1,
                R.layout.spinner_item);

        arrAdapSpnC.setDropDownViewResource(R.layout.spinner_dropdown_item);
        cSpn.setAdapter(arrAdapSpnC);
        //cSpn.setOnItemSelectedListener(spnOnItemSelected);

        ArrayAdapter<CharSequence> arrAdapSpnT
                = ArrayAdapter.createFromResource(SiteRestaurantActivity.this,
                R.array.spn_list_time,
                R.layout.spinner_item);

        arrAdapSpnT.setDropDownViewResource(R.layout.spinner_dropdown_item);
        tSpn.setAdapter(arrAdapSpnT);
        //tSpn.setOnItemSelectedListener(spnOnItemSelected);

        ArrayAdapter<CharSequence> arrAdapSpnK
                = ArrayAdapter.createFromResource(SiteRestaurantActivity.this,
                R.array.spn_list_kind,
                R.layout.spinner_item);

        arrAdapSpnK.setDropDownViewResource(R.layout.spinner_dropdown_item);
        kSpn.setAdapter(arrAdapSpnK);
        //kSpn.setOnItemSelectedListener(spnOnItemSelected);

        ArrayAdapter<CharSequence> arrAdapSpnC2
                = ArrayAdapter.createFromResource(SiteRestaurantActivity.this,
                R.array.spn_list_country2,
                R.layout.spinner_item);

        arrAdapSpnC2.setDropDownViewResource(R.layout.spinner_dropdown_item);
        cSpn2.setAdapter(arrAdapSpnC2);
        //cSpn2.setOnItemSelectedListener(spnOnItemSelected);

        ArrayAdapter<CharSequence> arrAdapSpnC3
                = ArrayAdapter.createFromResource(SiteRestaurantActivity.this,
                R.array.spn_list_country3,
                R.layout.spinner_item);

        arrAdapSpnC3.setDropDownViewResource(R.layout.spinner_dropdown_item);
        cSpn3.setAdapter(arrAdapSpnC3);
        //cSpn3.setOnItemSelectedListener(spnOnItemSelected);
    }
}
