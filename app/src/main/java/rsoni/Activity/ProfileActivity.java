package rsoni.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import rsoni.kisaanApp.R;
import rsoni.modal.AppUser;
import rsoni.modal.District;
import rsoni.modal.Market;
import rsoni.modal.State;
import rsoni.modal.UserProfile;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener {

    // UI references.
    // Edit Mode
    private EditText et_fname;
    private EditText et_lname;
    private Spinner spStates;
    private Spinner spDistricts;
    private Spinner spMarkets;
    private EditText et_address;
    private EditText et_pincode;

    // View only Mode



    // UI references.
    private AppUser appUser;
    private UserProfile userProfile;
    private boolean edit_mode = false;
    private List<State> states = null;
    private Map<Integer,List<District>> districtsMap = null;
    private Map<String,List<Market>> marketMap = null;

    private ArrayAdapter<State> stateArrayAdapter;
    private ArrayAdapter<District> districtArrayAdapter;
    private ArrayAdapter<Market> marketArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initView();
    }

    private void initView(){
        et_fname = (EditText)findViewById(R.id.et_fname);
        et_lname = (EditText)findViewById(R.id.et_lname);
        spStates = (Spinner)findViewById(R.id.sp_states);
        spStates.setOnItemSelectedListener(this);
        spDistricts = (Spinner)findViewById(R.id.sp_districts);
        spDistricts.setOnItemSelectedListener(this);
        spMarkets = (Spinner)findViewById(R.id.sp_markets);
        spMarkets.setOnItemSelectedListener(this);
        et_address = (EditText)findViewById(R.id.et_address);
        et_pincode = (EditText)findViewById(R.id.et_pincode);
        edit_mode = true;
    }

    private void setViewMode(){
    }


    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if(edit_mode){
            try {
                if(states==null)states = State.getStateList(this);
                if(districtsMap == null)districtsMap = District.getDistrictMap(this);
                if(marketMap==null)marketMap = Market.getMarketMap(this);
            } catch (IOException e) {
                e.printStackTrace();
            }

            stateArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, states); //selected item will look like a spinner set from XML
            stateArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spStates.setAdapter(stateArrayAdapter);

        }else{

        }
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        ArrayAdapter arrayAdapter = (ArrayAdapter) adapterView.getAdapter();
        if(arrayAdapter == stateArrayAdapter){
            System.out.println("State selected...");
            State state = (State) arrayAdapter.getItem(i);
            districtArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, districtsMap.get(state.state_id)); //selected item will look like a spinner set from XML
            districtArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spDistricts.setAdapter(districtArrayAdapter);
        }else if(arrayAdapter == districtArrayAdapter){
            System.out.println("State selected...");
            District district = (District) arrayAdapter.getItem(i);
            System.out.println("district : "+district.district_name);
            if(marketMap.get(district.district_name)!=null) {
                marketArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, marketMap.get(district.district_name)); //selected item will look like a spinner set from XML
                marketArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spMarkets.setAdapter(marketArrayAdapter);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
