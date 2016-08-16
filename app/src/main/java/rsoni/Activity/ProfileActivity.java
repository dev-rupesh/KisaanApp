package rsoni.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rsoni.Utils.DataResult;
import rsoni.Utils.Task;
import rsoni.kisaanApp.App;
import rsoni.kisaanApp.R;
import rsoni.modal.AppUser;
import rsoni.modal.District;
import rsoni.modal.Market;
import rsoni.modal.State;
import rsoni.modal.UserProfile;
import rsoni.modal.UserSubCategory;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener {

    private UserProfileTask mUserProfileTask = null;

    // UI references.
    // Edit Mode
    private EditText et_fname;
    private EditText et_lname;
    private Spinner spStates;
    private Spinner spDistricts;
    private Spinner spUserSubCategory;
    private Spinner spMarkets;
    private EditText et_address;
    private EditText et_pincode;
    private Button btn_update_profile;
    private TextView tv_user_sub_cat_label;

    // View only Mode

    // UI references.
    private boolean edit_mode = false;
    private List<State> states = null;
    private List<UserSubCategory> userSubCategories;
    private List<Market> markets;
    private List<District> districtList;
    private Map<Integer,List<District>> districtsMap = null;
    private Map<String,List<Market>> marketMap = null;
    private Map<Integer,List<UserSubCategory>> userSubCategoryMap = null;

    private ArrayAdapter<State> stateArrayAdapter;
    private ArrayAdapter<District> districtArrayAdapter;
    private ArrayAdapter<Market> marketArrayAdapter;
    private ArrayAdapter<UserSubCategory> userSubCategoryArrayAdapter;


    // Activity reference
    private AppUser appUser;
    private UserProfile userProfile = new UserProfile();
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        context = this;
        initView();
    }

    private void initView(){
        et_fname = (EditText)findViewById(R.id.et_fname);
        et_lname = (EditText)findViewById(R.id.et_lname);
        spStates = (Spinner)findViewById(R.id.sp_states);

        spDistricts = (Spinner)findViewById(R.id.sp_districts);

        spMarkets = (Spinner)findViewById(R.id.sp_markets);
        spUserSubCategory = (Spinner)findViewById(R.id.spUserSubCategory);

        et_address = (EditText)findViewById(R.id.et_address);
        et_pincode = (EditText)findViewById(R.id.et_pincode);
        tv_user_sub_cat_label = (TextView)findViewById(R.id.tv_user_sub_cat_label);
        btn_update_profile = (Button) findViewById(R.id.btn_update_profile);
        btn_update_profile.setOnClickListener(this);
        edit_mode = true;
    }

    private void setViewMode(){
    }

    private void attemptProfileUpdate() {
        if (mUserProfileTask != null) {
            return;
        }
        // Reset errors.
        et_fname.setError(null);
        et_lname.setError(null);
        et_address.setError(null);
        et_pincode.setError(null);


        userProfile.fname = et_fname.getText().toString();
        userProfile.lname = et_lname.getText().toString();

        userProfile.stateid = ((State)spStates.getSelectedItem()).state_id;
        userProfile.statename = ((State)spStates.getSelectedItem()).state_name;

        userProfile.districtid = ((District)spDistricts.getSelectedItem()).district_id;
        userProfile.districtname = ((District)spDistricts.getSelectedItem()).district_name;

        userProfile.marketid = ((Market)spMarkets.getSelectedItem()).mandi_id;
        userProfile.marketname = ((Market)spMarkets.getSelectedItem()).mandi_name;

        userProfile.usersubcatid = ((UserSubCategory)spUserSubCategory.getSelectedItem()).usersubcat_id;
        userProfile.address = et_address.getText().toString();
        String pincode = et_pincode.getText().toString();


        boolean cancel = false;
        View focusView = null;

        // Check for a empty fname.
        if (TextUtils.isEmpty(userProfile.fname)) {
            et_fname.setError(getString(R.string.error_invalid_password));
            focusView = et_fname;
            cancel = true;
        }
        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(userProfile.lname)) {
            et_lname.setError(getString(R.string.error_invalid_password));
            focusView = et_lname;
            cancel = true;
        }
        if (TextUtils.isEmpty(userProfile.address)) {
            et_address.setError(getString(R.string.error_invalid_password));
            focusView = et_address;
            cancel = true;
        }
        if (TextUtils.isEmpty(pincode)) {
            et_pincode.setError(getString(R.string.error_field_required));
            focusView = et_pincode;
            cancel = true;
        }
        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(pincode) && !isPinCodeValid(pincode)) {
            et_pincode.setError(getString(R.string.error_invalid_pincode));
            focusView = et_pincode;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //showProgress(true);
            userProfile.pincode = Integer.parseInt(pincode);
            //mUserProfileTask = new UserProfileTask(userProfile);
            //mUserProfileTask.execute((Void) null);

            userProfile.print();

            App.appUser.userProfile = new UserProfile();
            App.appUser.userProfile.copy(userProfile);
            App.saveUserProfile();
            App.appUser.userProfile.print();
        }
    }

    private boolean isPinCodeValid(String pin){
        return pin.length() == 6;
    }


    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        App.getUserProfile();

        if(edit_mode){
            try {
                if(states==null){
                    states = State.getStateList(this);
                    states.add(0,new State(true));
                }
                if(districtsMap == null){
                    districtsMap = District.getDistrictMap(this);
                    districtsMap.put(-1,new ArrayList<District>());
                }
                if(marketMap==null){
                    marketMap = Market.getMarketMap(this);
                    marketMap.put("-1",new ArrayList<Market>());
                }
                if(userSubCategories==null){
                    userSubCategoryMap = UserSubCategory.getUserSubCategoryMap(this);
                    System.out.println("UserCAtegory : "+ App.appUser.userCategory);
                    userSubCategories = userSubCategoryMap.get(App.appUser.userCategory);
                    if(userSubCategories==null){
                        userSubCategories = new ArrayList<UserSubCategory>();
                    }
                    userSubCategories.add(0,new UserSubCategory(true));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            stateArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, states); //selected item will look like a spinner set from XML
            stateArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spStates.setAdapter(stateArrayAdapter);
            spStates.setOnItemSelectedListener(this);

            userSubCategoryArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, userSubCategories); //selected item will look like a spinner set from XML
            userSubCategoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spUserSubCategory.setAdapter(userSubCategoryArrayAdapter);
            //spUserSubCategory.setOnItemSelectedListener(this);
            String lbl = "";
            if(App.appUser.userCategory==1)lbl="Commission Agent For";
            else if(App.appUser.userCategory==2)lbl="Broker in";
            else if(App.appUser.userCategory==3)lbl="Processing Unit Type";
            else if(App.appUser.userCategory==4)lbl="Treading in";
            else if(App.appUser.userCategory==4)lbl="Transport Type";

            tv_user_sub_cat_label.setText(lbl);

        }else{

        }
    }

    @Override
    public void onClick(View view) {
        if(view == btn_update_profile){
            attemptProfileUpdate();
        }
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
        }else if(arrayAdapter == districtArrayAdapter){
            System.out.println("State selected...");
            District district = (District) arrayAdapter.getItem(i);
            System.out.println("district : "+district.district_name);
            if(marketMap.get(district.district_name)!=null) {
                markets =  marketMap.get(district.district_name);
                if(markets.size()==0 || markets.get(0).id !=-1) markets.add(0,new Market(true));
                marketArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,markets); //selected item will look like a spinner set from XML
                marketArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spMarkets.setAdapter(marketArrayAdapter);
                spMarkets.setOnItemSelectedListener(this);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public class UserProfileTask extends AsyncTask<Void, Void, Boolean> {
        UserProfile userProfile;
        DataResult dataResult;
        UserProfileTask(UserProfile userProfile) {
            this.userProfile = userProfile;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            dataResult = App.networkService.UserAuth(Task.mobile_login,appUser);

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mUserProfileTask = null;
            //showProgress(false);

            if(dataResult.Status){

            }else{
                Toast.makeText(context,"Wrong Password",Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            mUserProfileTask = null;
            //showProgress(false);
        }
    }

    public void setSelectedItemsInForm(String mode,int id){
        int position = 0;
        switch (mode){
            case "state":
                if(App.appUser.userProfile!=null && App.appUser.userProfile.stateid!=-1){
                    for(State state : states){
                        if(state.state_id == id){
                            spStates.setSelection(position);
                        }
                        position++;
                    }
                }
                break;
            case "district":
                if(App.appUser.userProfile!=null && App.appUser.userProfile.districtid!=-1){
                    for(District district : districtList){
                        if(district.district_id == id){
                            spDistricts.setSelection(position);
                            break;
                        }
                        position++;
                    }
                }
                break;
            case "market":
                if(App.appUser.userProfile!=null && App.appUser.userProfile.marketid!=-1){
                    for(Market market : markets){
                        if(market.mandi_id == id){
                            spMarkets.setSelection(position);
                            break;
                        }
                        position++;
                    }
                }
                break;
            case "usersubcat":
                if(App.appUser.userProfile!=null && App.appUser.userProfile.usersubcatid!=-1){
                    for(UserSubCategory userSubCategory : userSubCategories){
                        if(userSubCategory.usersubcat_id == id){
                            spUserSubCategory.setSelection(position);
                            break;
                        }
                        position++;
                    }
                }
                break;

        }
    }


}
