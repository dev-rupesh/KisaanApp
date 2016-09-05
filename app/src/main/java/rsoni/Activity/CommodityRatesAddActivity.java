package rsoni.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import rsoni.Adapter.BuyListAdaptor;
import rsoni.Adapter.CommodityPriceListAdaptor;
import rsoni.kisaanApp.App;
import rsoni.kisaanApp.R;
import rsoni.modal.BuyNode;
import rsoni.modal.Commodity;
import rsoni.modal.CommodityCat;
import rsoni.modal.CommodityPrice;
import rsoni.modal.District;
import rsoni.modal.Market;
import rsoni.modal.State;

public class CommodityRatesAddActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener {

    Button btn_search_price,btn_add_price,btn_add_price_submit;
    ListView lv_search_result;
    LinearLayout ll_add_commodity;
    EditText et_commodity;

    Spinner sp_commoditycat,sp_commodity;

    CommodityPriceListAdaptor listAdaptor;
    List<CommodityPrice> myCommodityPrices = new ArrayList<>();

    CommodityCat selectedCommodityCat;
    Commodity selectedCommodity;

    private ArrayAdapter<CommodityCat> commodityCatArrayAdapter;
    private ArrayAdapter<Commodity> commodityArrayAdapter;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_rates_add);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_action_bar_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;
        initView();
    }

    private void initView() {
        btn_search_price = (Button) findViewById(R.id.btn_search_price);
        btn_search_price.setOnClickListener(this);

        btn_add_price = (Button) findViewById(R.id.btn_add_price);
        btn_add_price.setOnClickListener(this);

        btn_add_price_submit = (Button) findViewById(R.id.btn_add_price_submit);
        btn_add_price_submit.setOnClickListener(this);

        sp_commoditycat = (Spinner) findViewById(R.id.sp_commoditycat);
        sp_commoditycat.setOnItemSelectedListener(this);
        sp_commodity = (Spinner) findViewById(R.id.sp_commodity);

        et_commodity = (EditText) findViewById(R.id.et_commodity);

        lv_search_result = (ListView) findViewById(R.id.lv_search_result);
        ll_add_commodity = (LinearLayout) findViewById(R.id.ll_add_commodity);

    }

    @Override
    public void onAttachedToWindow() {
        commodityCatArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, App.mydb.getCommodityCat(true)); //selected item will look like a spinner set from XML
        commodityCatArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_commoditycat.setAdapter(commodityCatArrayAdapter);

        listAdaptor = new CommodityPriceListAdaptor(context,myCommodityPrices);
        lv_search_result.setAdapter(listAdaptor);
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

        if(v == btn_add_price){
            toggleAddCommodityView(true);
        }else if(v == btn_add_price_submit){
            toggleAddCommodityView(false);
        }else if(v == btn_search_price){
            startActivity(new Intent(context,CommodityRatesSearchActivity.class));
        }

    }

    private void validateForm(){
        boolean is_validate = true;
        if(is_validate){
            CommodityPrice commodityPrice = new CommodityPrice();
            commodityPrice.commodity_cat_id = selectedCommodityCat.id;
            commodityPrice.price_note = et_commodity.getText().toString();
            commodityPrice.price_date = System.currentTimeMillis();
            myCommodityPrices.add(commodityPrice);
            listAdaptor.notifyDataSetChanged();
        }
    }

    private void toggleAddCommodityView(boolean show){
        if(show) ll_add_commodity.setVisibility(View.VISIBLE); else ll_add_commodity.setVisibility(View.GONE);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ArrayAdapter arrayAdapter = (ArrayAdapter) parent.getAdapter();
        if(arrayAdapter == commodityCatArrayAdapter){
            System.out.println("CommodityCat selected...");
            selectedCommodityCat = (CommodityCat) arrayAdapter.getItem(position);
            System.out.println("commodity_cat : "+selectedCommodityCat.commodity_cat);
            commodityArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,App.mydb.getCommodity(true,selectedCommodityCat.id)); //selected item will look like a spinner set from XML
            commodityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_commodity.setAdapter(commodityArrayAdapter);
        }else if(arrayAdapter == commodityArrayAdapter){
            System.out.println("Commodity selected...");
            selectedCommodity = (Commodity) arrayAdapter.getItem(position);
            System.out.println("commodity : "+selectedCommodity.commodity);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
