package rsoni.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import rsoni.kisaanApp.R;

public class CommodityRatesActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_search_price,btn_add_price,btn_search_submit,btn_add_price_submit;
    TextView dd;
    ListView lv_search_result;
    LinearLayout ll_add_commodity,ll_search_filter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_rates);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_action_bar_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
    }

    private void initView() {
        btn_search_price = (Button) findViewById(R.id.btn_search_price);
        btn_search_price.setOnClickListener(this);
        btn_add_price = (Button) findViewById(R.id.btn_search_price);
        btn_add_price.setOnClickListener(this);

        btn_search_submit = (Button) findViewById(R.id.btn_search_submit);
        btn_search_submit.setOnClickListener(this);
        btn_add_price_submit = (Button) findViewById(R.id.btn_add_price_submit);
        btn_add_price_submit.setOnClickListener(this);

        lv_search_result = (ListView) findViewById(R.id.lv_search_result);

        ll_add_commodity = (LinearLayout) findViewById(R.id.ll_add_commodity);
        ll_search_filter = (LinearLayout) findViewById(R.id.ll_search_filter);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {

        if(v == btn_search_price){
            toggleSearchView(true);
        }else if(v == btn_search_submit){
            toggleSearchView(false);
        }else if(v == btn_add_price){
            toggleAddCommodityView(true);
        }else if(v == btn_add_price_submit){
            toggleAddCommodityView(false);
        }

    }

    private void toggleSearchView(boolean show){
        if(show) ll_search_filter.setVisibility(View.VISIBLE); else ll_search_filter.setVisibility(View.GONE);
    }

    private void toggleAddCommodityView(boolean show){
        if(show) ll_add_commodity.setVisibility(View.VISIBLE); else ll_add_commodity.setVisibility(View.GONE);
    }
}
