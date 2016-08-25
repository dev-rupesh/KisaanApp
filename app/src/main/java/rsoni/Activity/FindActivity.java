package rsoni.Activity;

import android.content.Context;
import android.content.pm.ProviderInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import rsoni.Adapter.BuyListAdaptor;
import rsoni.Adapter.SaleListAdaptor;
import rsoni.Utils.Task;
import rsoni.kisaanApp.App;
import rsoni.kisaanApp.R;
import rsoni.modal.Business;
import rsoni.modal.BuyNode;
import rsoni.modal.District;
import rsoni.modal.Market;
import rsoni.modal.SaleNode;
import rsoni.modal.SearchFilter;
import rsoni.modal.State;
import rsoni.modal.UserSubCategory;

public class FindActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener {

    SearchFilter searchFilter = new SearchFilter();
    Context context;

    LinearLayout ll_search_filter,ll_search_filter_form;
    ListView lv_search_result;
    List<SaleNode> saleNodes = new ArrayList<>();
    List<BuyNode> buyNodes = new ArrayList<>();

    Spinner sp_business;
    RadioGroup rg_radioGroup;
    TextView tv_applied_filter;
    Button btn_find,btn_search_form_ok;
    private Spinner spStates;
    private Spinner spDistricts;

    private ArrayAdapter<State> stateArrayAdapter;
    private ArrayAdapter<District> districtArrayAdapter;
    private ArrayAdapter<Business> businessArrayAdapter;

    private  List<Business> businesses = new ArrayList<>();
    private List<State> states = null;
    private List<District> districtList;
    private Map<Integer,List<District>> districtsMap = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.context = this;
        initView();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        try {
            if(states==null){
                states = State.getStateList(this);
                states.add(0,new State(true));
            }
            if(districtsMap == null){
                districtsMap = District.getDistrictMap(this);
                districtsMap.put(-1,new ArrayList<District>());
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
        spStates = (Spinner)findViewById(R.id.sp_states);

        spDistricts = (Spinner)findViewById(R.id.sp_districts);
    }

    private void resetSearchFilter(){

        businesses.addAll( App.businesses);
        businesses.add(0,new Business(true));

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
        searchFilter.searchFor = "";
        tv_applied_filter.setText(searchFilter.state+" > "+searchFilter.district);
    }

    private void setSearchFilter(){
        State state = (State)spStates.getSelectedItem();
        District district  = (District) spDistricts.getSelectedItem();
        searchFilter.state_id = state.state_id;
        searchFilter.state = state.state_name;
        searchFilter.district_id = district.district_id;
        searchFilter.district = district.district_name;
        tv_applied_filter.setText(searchFilter.state+" > "+searchFilter.district);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        ArrayAdapter arrayAdapter = (ArrayAdapter) adapterView.getAdapter();
        if(arrayAdapter == stateArrayAdapter){
            System.out.println("State selected...");
            State state = (State) arrayAdapter.getItem(i);
            districtList = districtsMap.get(state.state_id);
            if(districtList.isEmpty() || districtList.get(0).district_id!=-1) districtList.add(0,new District(true));
            districtArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, districtList); //selected item will look like a spinner set from XML
            districtArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spDistricts.setAdapter(districtArrayAdapter);
            spDistricts.setOnItemSelectedListener(this);
            int index = 0;
            if(App.appUser.userProfile!=null){
                for(District district : districtList){
                    if(district.district_id==App.appUser.userProfile.district_id){
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

    private void toggalSearchForm(){
        if(ll_search_filter_form.getVisibility()==View.VISIBLE){
            ll_search_filter_form.setVisibility(View.GONE);
        }else{
            ll_search_filter_form.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onClick(View view) {
        if(view == btn_find){
            // search api call with params
        }else if(view == tv_applied_filter){
            toggalSearchForm();
        }else if(view == btn_search_form_ok){
            toggalSearchForm();
            setSearchFilter();
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

}
