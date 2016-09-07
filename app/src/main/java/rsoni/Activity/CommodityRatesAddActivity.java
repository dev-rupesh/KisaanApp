package rsoni.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import rsoni.Adapter.BuyListAdaptor;
import rsoni.Adapter.CommodityPriceListAdaptor;
import rsoni.Utils.DataResult;
import rsoni.Utils.Task;
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

    private BackgroundTask backgroundTask = null;

    Button btn_search_price,btn_add_price,btn_add_price_submit;
    ListView lv_search_result;
    LinearLayout ll_add_commodity;
    EditText et_commodity;

    Spinner sp_commoditycat,sp_commodity;

    CommodityPriceListAdaptor listAdaptor;
    List<CommodityPrice> myCommodityPrices = new ArrayList<>();

    CommodityCat selectedCommodityCat;
    Commodity selectedCommodity;
    CommodityPrice commodityPrice = new CommodityPrice();

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
            validateForm();
        }else if(v == btn_add_price_submit){
            toggleAddCommodityView(false);
        }else if(v == btn_search_price){
            startActivity(new Intent(context,CommodityRatesSearchActivity.class));
        }

    }

    private void validateForm(){
        boolean is_validate = true;
        View focusView = null;

        commodityPrice.commodity_cat_id = selectedCommodityCat.id;
        commodityPrice.commodity_id = selectedCommodity.id;
        commodityPrice.price_note = et_commodity.getText().toString();
        commodityPrice.price_date = System.currentTimeMillis();

        if(commodityPrice.commodity_id==-1){
            Toast.makeText(context,"Select Category",Toast.LENGTH_SHORT).show();
            return;
        }
        if(selectedCommodity.id==-1){
            Toast.makeText(context,"Select Commodity",Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(commodityPrice.price_note)) {
            et_commodity.setError(getString(R.string.error_field_required));
            focusView = et_commodity;
            focusView.requestFocus();
            return;
        }

        if(is_validate){
            toggleAddCommodityView(true);
            myCommodityPrices.add(commodityPrice);
            listAdaptor.notifyDataSetChanged();
        }
    }

    private void toggleAddCommodityView(boolean show){
        if(show) ll_add_commodity.setVisibility(View.VISIBLE); else ll_add_commodity.setVisibility(View.GONE);
    }
    public class BackgroundTask extends AsyncTask<Void, Void, Boolean> {

        DataResult dataResult;
        Task task;

        public  BackgroundTask(Task task){
            this.task = task;
            System.out.println(" BackgroundTask initiated ");
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            switch(task){
                case add_commodity_price:
                    System.out.println("in add_buy_node...");
                    dataResult = App.networkService.CommodityPrice(task, commodityPrice);
                    break;
                case list_commodity_price:
                    System.out.println("in list buy node...");
                    if(App.dataSyncCheck.buynode) {
                        System.out.println("get list buy node by db");
                        dataResult = new DataResult(true, "", App.mydb.getBuyNodes());
                    }else {
                        System.out.println("get list buy node by server");
                        dataResult = App.networkService.BuyNode(task, null);
                    }
                    break;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            backgroundTask = null;
            //showProgress(false);
            switch(task) {
                case add_commodity_price:
                    if (dataResult.Status) {
                        commodityPrice = (CommodityPrice) dataResult.Data;
                        App.mydb.saveCommodityPrice(commodityPrice);
                        myCommodityPrices.add(commodityPrice);
                        listAdaptor =  new CommodityPriceListAdaptor(context,myCommodityPrices);
                        lv_search_result.setAdapter(listAdaptor);
                    } else {
                        Toast.makeText(context, "Price not added, please try again...", Toast.LENGTH_LONG).show();
                    }
                    break;
                case list_commodity_price:
                    if (dataResult.Status) {
                        myCommodityPrices = (List<CommodityPrice>) dataResult.Data;
                        if(!App.dataSyncCheck.buynode) {
                            App.dataSyncCheck.buynode = true;
                            App.saveDataSyncCheck();
                            App.mydb.saveCommodityPrices(myCommodityPrices);
                        }
                        listAdaptor =  new CommodityPriceListAdaptor(context,myCommodityPrices);
                        lv_search_result.setAdapter(listAdaptor);
                    } else {
                        Toast.makeText(context, "No price added by you.", Toast.LENGTH_LONG).show();
                    }
                    break;
            }
        }

        @Override
        protected void onCancelled() {
            backgroundTask = null;
            //showProgress(false);
        }
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
