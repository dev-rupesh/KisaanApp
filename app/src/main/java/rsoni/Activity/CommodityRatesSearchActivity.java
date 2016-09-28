package rsoni.Activity;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import rsoni.Adapter.CommodityPriceListAdaptor;
import rsoni.Utils.DataResult;
import rsoni.Utils.Task;
import rsoni.JustAgriAgro.App;
import rsoni.kisaanApp.R;
import rsoni.modal.Commodity;
import rsoni.modal.CommodityCat;
import rsoni.modal.CommodityPrice;
import rsoni.modal.District;
import rsoni.modal.Market;
import rsoni.modal.State;

public class CommodityRatesSearchActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener {

    Button btn_search_price,btn_search_submit;
    TextView tv_selected_mandi,tv_selected_commodity,tv_error_msg;
    ListView lv_search_result;
    LinearLayout ll_search_filter;
    BackgroundTask backgroundTask;
    Context context;

    Spinner sp_states_search,sp_districts_search,sp_markets_search,sp_commoditycat,sp_commodity;

     State selectedState;
     District selectedDistrict;
     Market selectedMarket;

    private ArrayAdapter<State> stateArrayAdapter;
    private ArrayAdapter<District> districtArrayAdapter;
    private ArrayAdapter<Market> marketArrayAdapter;

    CommodityCat selectedCommodityCat;
    Commodity selectedCommodity;
    CommodityPrice commodityPrice = new CommodityPrice();

    private ArrayAdapter<CommodityCat> commodityCatArrayAdapter;
    private ArrayAdapter<Commodity> commodityArrayAdapter;

    CommodityPriceListAdaptor listAdaptor;
    private List<CommodityPrice> searchCommodityPrices;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_rates_search);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_action_bar_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.context = this;
        initView();
    }

    private void initView() {

        tv_selected_mandi = (TextView) findViewById(R.id.tv_selected_mandi);
        tv_selected_commodity = (TextView) findViewById(R.id.tv_selected_commodity);
        tv_error_msg = (TextView) findViewById(R.id.tv_error_msg);

        btn_search_price = (Button) findViewById(R.id.btn_search_price);
        btn_search_price.setOnClickListener(this);
        btn_search_submit = (Button) findViewById(R.id.btn_search_submit);
        btn_search_submit.setOnClickListener(this);

        sp_states_search = (Spinner) findViewById(R.id.sp_states_search);
        sp_states_search.setOnItemSelectedListener(this);
        sp_districts_search = (Spinner) findViewById(R.id.sp_districts_search);
        sp_districts_search.setOnItemSelectedListener(this);
        sp_markets_search = (Spinner) findViewById(R.id.sp_markets_search);
        sp_markets_search.setOnItemSelectedListener(this);

        sp_commoditycat = (Spinner) findViewById(R.id.sp_commoditycat);
        sp_commoditycat.setOnItemSelectedListener(this);
        sp_commodity = (Spinner) findViewById(R.id.sp_commodity);
        sp_commodity.setOnItemSelectedListener(this);

        lv_search_result = (ListView) findViewById(R.id.lv_search_result);

        ll_search_filter = (LinearLayout) findViewById(R.id.ll_search_filter);

    }

    @Override
    public void onAttachedToWindow() {
        stateArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, App.mydb.getStates(true)); //selected item will look like a spinner set from XML
        stateArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_states_search.setAdapter(stateArrayAdapter);

        commodityCatArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, App.mydb.getCommodityCat(true)); //selected item will look like a spinner set from XML
        commodityCatArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_commoditycat.setAdapter(commodityCatArrayAdapter);

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
            validateSearchForm();
        }

    }

    private void validateSearchForm(){
        boolean is_valid = true;


        if(selectedState.state_id == -1){
            Toast.makeText(context,"Select State",Toast.LENGTH_SHORT).show();
            return;
        }
        if(selectedDistrict.district_id == -1){
            Toast.makeText(context,"Select District",Toast.LENGTH_SHORT).show();
            return;
        }
        if(selectedMarket.mandi_id == -1){
            Toast.makeText(context,"Select Mandi",Toast.LENGTH_SHORT).show();
            return;
        }
        if(selectedCommodityCat.id == -1){
            Toast.makeText(context,"Select Category",Toast.LENGTH_SHORT).show();
            return;
        }
        if(selectedCommodity.id == -1){
            Toast.makeText(context,"Select Commodity",Toast.LENGTH_SHORT).show();
            return;
        }

        if(is_valid){
            tv_selected_mandi.setText(selectedState.state_name+">>"+selectedDistrict.district_name+">>"+selectedMarket.mandi_name);
            tv_selected_commodity.setText(selectedCommodityCat.commodity_cat+">>"+selectedCommodity.commodity_name);
            toggleSearchView(false);

            commodityPrice.market_id = selectedMarket.mandi_id;
            commodityPrice.commodity_id = selectedCommodity.id;

            backgroundTask = new BackgroundTask(Task.search_commodity_price);
            backgroundTask.execute();

        }

    }

    private void toggleSearchView(boolean show){
        if(show) ll_search_filter.setVisibility(View.VISIBLE); else ll_search_filter.setVisibility(View.GONE);
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
                case search_commodity_price:
                    System.out.println("in search commodity price...");
                    dataResult = App.networkService.CommodityPrice(task, commodityPrice);
                    break;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            backgroundTask = null;
            //showProgress(false);
            switch(task) {
                case search_commodity_price:
                    if (dataResult.Status) {
                        tv_error_msg.setVisibility(View.GONE);
                        searchCommodityPrices = (List<CommodityPrice>) dataResult.Data;
                        listAdaptor =  new CommodityPriceListAdaptor(context,searchCommodityPrices);
                        lv_search_result.setAdapter(listAdaptor);
                    } else {
                        tv_error_msg.setVisibility(View.VISIBLE);
                        tv_error_msg.setText("No data found for your search selection.");
                        Toast.makeText(context, "No result found .", Toast.LENGTH_LONG).show();
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
        if(arrayAdapter == stateArrayAdapter){
            System.out.println("State selected...");
            selectedState = (State) arrayAdapter.getItem(position);
            districtArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, App.mydb.getDistricts(true,selectedState.state_id)); //selected item will look like a spinner set from XML
            districtArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_districts_search.setAdapter(districtArrayAdapter);
        }else if(arrayAdapter == districtArrayAdapter){
            System.out.println("District selected...");
            selectedDistrict = (District) arrayAdapter.getItem(position);
            System.out.println("district : "+selectedDistrict.district_name);
            marketArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,App.mydb.getMarkets(true,selectedDistrict.district_name)); //selected item will look like a spinner set from XML
            marketArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_markets_search.setAdapter(marketArrayAdapter);
        }else if(arrayAdapter == marketArrayAdapter){
            System.out.println("Market selected...");
            selectedMarket = (Market) arrayAdapter.getItem(position);
            System.out.println("mandi_name : "+selectedMarket.mandi_name+" >> "+selectedMarket.mandi_id);
        }else if(arrayAdapter == commodityCatArrayAdapter){
            System.out.println("CommodityCat selected...");
            selectedCommodityCat = (CommodityCat) arrayAdapter.getItem(position);
            System.out.println("commodity_cat : "+selectedCommodityCat.commodity_cat);
            commodityArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,App.mydb.getCommodity(true,selectedCommodityCat.id)); //selected item will look like a spinner set from XML
            commodityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_commodity.setAdapter(commodityArrayAdapter);
        }else if(arrayAdapter == commodityArrayAdapter){
            System.out.println("Commodity selected...");
            selectedCommodity = (Commodity) arrayAdapter.getItem(position);
            System.out.println("commodity_name : "+selectedCommodity.commodity_name+">>"+selectedCommodity.id);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
