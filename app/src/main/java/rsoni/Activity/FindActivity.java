package rsoni.Activity;

import android.content.Context;
import android.content.Intent;
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
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rsoni.Adapter.BuyListAdaptor;
import rsoni.Adapter.SaleListAdaptor;
import rsoni.JustAgriAgro.R;
import rsoni.Utils.DataResult;
import rsoni.Utils.Task;
import rsoni.JustAgriAgro.App;
import rsoni.modal.Business;
import rsoni.modal.BuyNode;
import rsoni.modal.District;
import rsoni.modal.SaleNode;
import rsoni.modal.SearchFilter;
import rsoni.modal.State;

public class FindActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    SearchFilter searchFilter = new SearchFilter();
    BackgroundTask backgroundTask;
    Context context;

    LinearLayout ll_search_filter, ll_search_filter_form;
    ListView lv_search_result;
    List<SaleNode> saleNodes = new ArrayList<>();
    List<BuyNode> buyNodes = new ArrayList<>();

    Spinner sp_business;
    RadioGroup rg_radioGroup;
    TextView tv_applied_filter;
    Button btn_find, btn_search_form_ok;
    private Spinner spStates;
    private Spinner spDistricts;

    private ArrayAdapter<State> stateArrayAdapter;
    private ArrayAdapter<District> districtArrayAdapter;
    private ArrayAdapter<Business> businessArrayAdapter;

    private BuyListAdaptor buyListAdaptor;
    private SaleListAdaptor saleListAdaptor;

    private List<Business> businesses = new ArrayList<>();
    private List<State> states = null;
    private List<District> districtList;
    private Map<Integer, List<District>> districtsMap = null;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    //private GoogleApiClient client;

    AdapterView.OnItemClickListener buysearchOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            BuyNode buyNode = (BuyNode) parent.getAdapter().getItem(position);
            Intent i = new Intent(context,BuyerSalerDetailsActivity.class);
            i.putExtra("user_id",buyNode.user_id);
            startActivity(i);
        }
    };

    AdapterView.OnItemClickListener salesearchOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            SaleNode saleNode = (SaleNode) parent.getAdapter().getItem(position);
            Intent i = new Intent(context,BuyerSalerDetailsActivity.class);
            i.putExtra("user_id",saleNode.user_id);
            startActivity(i);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_action_bar_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.context = this;
        initView();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
       // client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        try {
            if (states == null) {
                states = State.getStateList(this);
                states.add(0, new State(true));
            }
            if (districtsMap == null) {
                districtsMap = District.getDistrictMap(this);
                districtsMap.put(-1, new ArrayList<District>());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        resetSearchFilter();
    }

    private void initView() {
        ll_search_filter_form = (LinearLayout) findViewById(R.id.ll_search_filter_form);
        lv_search_result = (ListView) findViewById(R.id.lv_search_result);
        rg_radioGroup = (RadioGroup) findViewById(R.id.rg_radioGroup);
        sp_business = (Spinner) findViewById(R.id.sp_business);
        tv_applied_filter = (TextView) findViewById(R.id.tv_applied_filter);
        tv_applied_filter.setOnClickListener(this);
        btn_find = (Button) findViewById(R.id.btn_find);
        btn_find.setOnClickListener(this);
        btn_search_form_ok = (Button) findViewById(R.id.btn_search_form_ok);
        btn_search_form_ok.setOnClickListener(this);
        spStates = (Spinner) findViewById(R.id.sp_states);

        spDistricts = (Spinner) findViewById(R.id.sp_districts);
    }

    private void resetSearchFilter() {

        businesses.addAll(App.mydb.getAllBusiness(false));
        businesses.add(0, new Business(true));

        stateArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, states);
        stateArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spStates.setAdapter(stateArrayAdapter);
        spStates.setOnItemSelectedListener(this);

        businessArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, businesses);
        businessArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_business.setAdapter(businessArrayAdapter);

        sp_business.setOnItemSelectedListener(this);


        searchFilter.state_id = App.appUser.userProfile.state_id;
        searchFilter.state = App.appUser.userProfile.state_name;
        searchFilter.district_id = App.appUser.userProfile.district_id;
        searchFilter.district = App.appUser.userProfile.district_name;
        searchFilter.market_id = App.appUser.userProfile.market_id;
        searchFilter.market = App.appUser.userProfile.market_name;
        searchFilter.searchFor = -1;
        tv_applied_filter.setText(searchFilter.state + " > " + searchFilter.district);
    }

    private void validateDistirctSelectForm(){
        boolean is_validate = true;
        State state = (State) spStates.getSelectedItem();
        if(state.state_id == -1){
            Toast.makeText(context,"Select State",Toast.LENGTH_SHORT).show();
            return;
        }else{
            searchFilter.state_id = state.state_id;
            searchFilter.state = state.state_name;
        }
        District district = (District) spDistricts.getSelectedItem();
        if(district.district_id == -1){
            Toast.makeText(context,"Select District",Toast.LENGTH_SHORT).show();
            return;
        }  else{
            searchFilter.district_id = district.district_id;
            searchFilter.district = district.district_name;
        }

        if(is_validate){
            tv_applied_filter.setText(searchFilter.state + " > " + searchFilter.district);
            toggalDistrictSelectForm();
        }
    }

    private void validateSearchForm(){
        boolean is_validate = true;

        Business business = (Business) sp_business.getSelectedItem();
        if(business.business_id == -1){
            Toast.makeText(context,"Select Category",Toast.LENGTH_SHORT).show();
            return;
        }else{
            searchFilter.business_id = business.business_id;
            searchFilter.business = business.business;
        }

        int checkedRadioButtonId = rg_radioGroup.getCheckedRadioButtonId();
        if (checkedRadioButtonId == -1) {
            Toast.makeText(context,"Select Seller or Buyer ",Toast.LENGTH_SHORT).show();
            return;
        }else{
            View radioButton = rg_radioGroup.findViewById(checkedRadioButtonId);
            int idx = rg_radioGroup.indexOfChild(radioButton);
            searchFilter.searchFor = idx ;
        }

        if(is_validate){

            if(searchFilter.searchFor == 0)
                backgroundTask = new BackgroundTask(Task.buyer_search);
            else
                backgroundTask = new BackgroundTask(Task.seller_search);

            backgroundTask.execute((Void) null);
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        ArrayAdapter arrayAdapter = (ArrayAdapter) adapterView.getAdapter();
        if (arrayAdapter == stateArrayAdapter) {
            System.out.println("State selected...");
            State state = (State) arrayAdapter.getItem(i);
            districtList = districtsMap.get(state.state_id);
            if (districtList.isEmpty() || districtList.get(0).district_id != -1)
                districtList.add(0, new District(true));
            districtArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, districtList); //selected item will look like a spinner set from XML
            districtArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spDistricts.setAdapter(districtArrayAdapter);
            spDistricts.setOnItemSelectedListener(this);
            int index = 0;
            if (App.appUser.userProfile != null) {
                for (District district : districtList) {
                    if (district.district_id == App.appUser.userProfile.district_id) {
                        spDistricts.setSelection(index);
                        break;
                    }
                    index++;
                }
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void toggalDistrictSelectForm() {
        if (ll_search_filter_form.getVisibility() == View.VISIBLE) {
            ll_search_filter_form.setVisibility(View.GONE);
        } else {
            ll_search_filter_form.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onClick(View view) {
        if (view == btn_find) {
            validateSearchForm();
        } else if (view == tv_applied_filter) {
            toggalDistrictSelectForm();
        } else if (view == btn_search_form_ok) {
            validateDistirctSelectForm();
        }
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
                case buyer_search:
                    System.out.println("in search buyer ...");
                    dataResult = App.networkService.Search(task, searchFilter);
                    break;
                case seller_search:
                    System.out.println("in search seller ...");
                    dataResult = App.networkService.Search(task, searchFilter);
                    break;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            backgroundTask = null;
            //showProgress(false);
            switch(task) {
                case buyer_search:
                    if (dataResult.Status) {
                        //tv_error_msg.setVisibility(View.GONE);
                        buyNodes = (List<BuyNode>) dataResult.Data;
                    } else {
                        //tv_error_msg.setVisibility(View.VISIBLE);
                        //tv_error_msg.setText("No data found for your search selection.");
                        buyNodes.clear();
                        Toast.makeText(context, "No result found .", Toast.LENGTH_LONG).show();
                    }
                    buyListAdaptor =  new BuyListAdaptor(context,buyNodes);
                    lv_search_result.setAdapter(buyListAdaptor);
                    lv_search_result.setOnItemClickListener(buysearchOnItemClickListener);
                    break;
                case seller_search:
                    if (dataResult.Status) {
                        //tv_error_msg.setVisibility(View.GONE);
                        saleNodes = (List<SaleNode>) dataResult.Data;
                    } else {
                        //tv_error_msg.setVisibility(View.VISIBLE);
                        //tv_error_msg.setText("No data found for your search selection.");
                        saleNodes.clear();
                        Toast.makeText(context, "No result found .", Toast.LENGTH_LONG).show();
                    }
                    saleListAdaptor =  new SaleListAdaptor(context,saleNodes);
                    lv_search_result.setAdapter(saleListAdaptor);
                    lv_search_result.setOnItemClickListener(salesearchOnItemClickListener);
                    break;
            }
        }

        @Override
        protected void onCancelled() {
            backgroundTask = null;
            //showProgress(false);
        }
    }


}
