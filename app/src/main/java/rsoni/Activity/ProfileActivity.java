package rsoni.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import rsoni.Utils.DataResult;
import rsoni.Utils.Task;
import rsoni.JustAgriAgro.App;
import rsoni.kisaanApp.R;
import rsoni.modal.AppUser;
import rsoni.modal.District;
import rsoni.modal.Market;
import rsoni.modal.State;
import rsoni.modal.UserProfile;
import rsoni.modal.UserSubCategory;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener,MultiSelectionSpinner.OnMultipleItemsSelectedListener{

    private UserProfileTask mUserProfileTask = null;

    // UI references.
    // Edit Mode
    private EditText et_fname;
    private EditText et_lname;
    private Spinner spStates;
    private Spinner spDistricts;
    //private Spinner spUserSubCategory;
    private Spinner spMarkets;
    private EditText et_address;
    private EditText et_pincode;
    private Button btn_update_profile,btn_cancel_update_profile;
    private TextView tv_user_sub_cat_label;
    private LinearLayout ll_user_profile_edit;
    private MultiSelectionSpinner multiSelectionSpinner;

    // View only Mode
    private TextView tv_name_of_company,tv_name_of_proprietor,tv_address,tv_district,tv_pincode,tv_market,tv_mobile,tv_email,tv_business;
    private LinearLayout ll_user_profile;
    private Button btn_edit_profile;

    // UI references.
    private boolean edit_mode = false;
    private List<State> states = null;
    private List<UserSubCategory> userSubCategories;
    private List<Market> markets;
    private List<District> districtList;



    private ArrayAdapter<State> stateArrayAdapter;
    private ArrayAdapter<District> districtArrayAdapter;
    private ArrayAdapter<Market> marketArrayAdapter;

    private View mProgressView;
    private View mProfileFormView;


    // Activity reference
    private AppUser appUser;
    private UserProfile userProfile = new UserProfile();
    private String from;
    private Context context;

    private State selectedState;
    private District selectedDistrict;
    private Market selectedMarket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_action_bar_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;
        from = getIntent().getStringExtra("from");
        initView();
    }

    private void initView(){
        mProgressView = findViewById(R.id.profile_progress);

        ll_user_profile = (LinearLayout) findViewById(R.id.ll_user_profile);
        tv_name_of_company = (TextView) findViewById(R.id.tv_name_of_company);
        tv_name_of_proprietor = (TextView) findViewById(R.id.tv_name_of_proprietor);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_district = (TextView) findViewById(R.id.tv_district);
        tv_pincode = (TextView) findViewById(R.id.tv_pincode);
        tv_market = (TextView) findViewById(R.id.tv_market);
        tv_mobile = (TextView) findViewById(R.id.tv_mobile);
        tv_email = (TextView) findViewById(R.id.tv_email);
        tv_business = (TextView) findViewById(R.id.tv_business);
        btn_edit_profile = (Button) findViewById(R.id.btn_edit_profile);
        btn_edit_profile.setOnClickListener(this);

        ll_user_profile_edit = (LinearLayout) findViewById(R.id.ll_user_profile_edit);
        mProfileFormView = findViewById(R.id.profile_form);
        et_fname = (EditText)findViewById(R.id.et_fname);
        et_lname = (EditText)findViewById(R.id.et_lname);
        spStates = (Spinner)findViewById(R.id.sp_states);

        spDistricts = (Spinner)findViewById(R.id.sp_districts);

        spMarkets = (Spinner)findViewById(R.id.sp_markets);
        //spUserSubCategory = (Spinner)findViewById(R.id.spUserSubCategory);
        multiSelectionSpinner = (MultiSelectionSpinner) findViewById(R.id.mySpinner);

        et_address = (EditText)findViewById(R.id.et_address);
        et_pincode = (EditText)findViewById(R.id.et_pincode);
        tv_user_sub_cat_label = (TextView)findViewById(R.id.tv_user_sub_cat_label);
        btn_update_profile = (Button) findViewById(R.id.btn_update_profile);
        btn_update_profile.setOnClickListener(this);
        btn_cancel_update_profile = (Button) findViewById(R.id.btn_cancel_update_profile);
        btn_cancel_update_profile.setOnClickListener(this);

        if((from!=null && from.equalsIgnoreCase("start")) || App.appUser.userProfile==null) {
            edit_mode = true;
        }else{
            edit_mode = false;
        }
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


        userProfile.company_name = et_fname.getText().toString();
        userProfile.owner_name = et_lname.getText().toString();

        userProfile.state_id = ((State)spStates.getSelectedItem()).state_id;
        userProfile.state_name = ((State)spStates.getSelectedItem()).state_name;

        userProfile.district_id = ((District)spDistricts.getSelectedItem()).district_id;
        userProfile.district_name = ((District)spDistricts.getSelectedItem()).district_name;

        userProfile.market_id = ((Market)spMarkets.getSelectedItem()).mandi_id;
        userProfile.market_name = ((Market)spMarkets.getSelectedItem()).mandi_name;

        //userProfile.usersubcat_id = ((UserSubCategory)spUserSubCategory.getSelectedItem()).usersubcat_id;
        userProfile.address = et_address.getText().toString();

        String pincode = et_pincode.getText().toString();


        boolean cancel = false;
        View focusView = null;

        // Check for a empty company_name.
        if (TextUtils.isEmpty(userProfile.company_name)) {
            et_fname.setError(getString(R.string.error_field_required));
            focusView = et_fname;
            cancel = true;
        }
        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(userProfile.owner_name)) {
            et_lname.setError(getString(R.string.error_field_required));
            focusView = et_lname;
            cancel = true;
        }
        if (TextUtils.isEmpty(userProfile.address)) {
            et_address.setError(getString(R.string.error_field_required));
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
            userProfile.business_id = App.gson.toJson(multiSelectionSpinner.getSelectedIds());
            //mUserProfileTask = new UserProfileTask(userProfile);
            //mUserProfileTask.execute((Void) null);

            userProfile.print();

            App.appUser.userProfile = new UserProfile();
            App.appUser.userProfile.copy(userProfile);
            App.saveUserProfile();
            App.appUser.userProfile.print();
            showProgress(true);
            new UserProfileTask(App.appUser).execute((Void) null);

        }
    }

    private boolean isPinCodeValid(String pin){
        return pin.length() == 6;
    }


    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        App.getUserProfile();
        setViewMode();
    }

    @Override
    public void onClick(View view) {
        if(view == btn_edit_profile){
            edit_mode = true;
            setViewMode();
        }else if(view == btn_update_profile){
            attemptProfileUpdate();
        }else if(view == btn_cancel_update_profile){
            edit_mode = false;
            setViewMode();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        ArrayAdapter arrayAdapter = (ArrayAdapter) adapterView.getAdapter();
        if (arrayAdapter == stateArrayAdapter) {
            System.out.println("State selected...");
            selectedState = (State) arrayAdapter.getItem(i);
            districtList = App.mydb.getDistricts(true, selectedState.state_id);
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
        } else if (arrayAdapter == districtArrayAdapter) {
            System.out.println("District selected...");
            selectedDistrict = (District) arrayAdapter.getItem(i);
            markets = App.mydb.getMarkets(true, selectedDistrict.district_name);
            marketArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, markets); //selected item will look like a spinner set from XML
            marketArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spMarkets.setAdapter(marketArrayAdapter);
            spMarkets.setOnItemSelectedListener(this);
            int index = 0;
            if (App.appUser.userProfile != null) {
                for (Market market : markets) {
                    if (market.mandi_name.equalsIgnoreCase(App.appUser.userProfile.market_name)) {
                        spMarkets.setSelection(index);
                        break;
                    }
                    index++;
                }
            }
        }else if(arrayAdapter == marketArrayAdapter){
            System.out.println("Market selected...");
            selectedMarket = (Market) arrayAdapter.getItem(i);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void setViewMode(){
        if(edit_mode){
            ll_user_profile_edit.setVisibility(View.VISIBLE);
            ll_user_profile.setVisibility(View.GONE);
            if(states==null){
                    states = App.mydb.getStates(true);
            }
            stateArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, states); //selected item will look like a spinner set from XML
            stateArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spStates.setAdapter(stateArrayAdapter);
            spStates.setOnItemSelectedListener(this);

            String lbl = "";
            if(App.appUser.userCategory==1)lbl="Commission Agent For";
            else if(App.appUser.userCategory==2)lbl="Broker in";
            else if(App.appUser.userCategory==3)lbl="Processing Unit Type";
            else if(App.appUser.userCategory==4)lbl="Treading in";
            else if(App.appUser.userCategory==4)lbl="Transport Type";

            tv_user_sub_cat_label.setText(lbl);
            int index = 0;

            multiSelectionSpinner.setItems2(App.businesses);

            if(App.appUser.userProfile!=null){

                et_fname.setText(App.appUser.userProfile.company_name);
                et_lname.setText(App.appUser.userProfile.owner_name);
                et_address.setText(App.appUser.userProfile.address);
                et_pincode.setText(""+App.appUser.userProfile.pincode);

                for(State state : states){
                    if(state.state_name.equalsIgnoreCase(App.appUser.userProfile.state_name)){
                        spStates.setSelection(index);
                        break;
                    }
                    index++;
                }
                index = 0;

                if(App.appUser.userProfile.business_id!=null && !App.appUser.userProfile.business_id.isEmpty()) {
                    int[] business_ids = App.gson.fromJson(App.appUser.userProfile.business_id, int[].class);
                    multiSelectionSpinner.setSelection2(business_ids);
                }else{
                    multiSelectionSpinner.setSelection2(new int[]{});
                }
            }
            multiSelectionSpinner.setListener(this);


        }else{
            ll_user_profile.setVisibility(View.VISIBLE);
            ll_user_profile_edit.setVisibility(View.GONE);
            if(App.appUser.userProfile!=null) {
                ll_user_profile.setVisibility(View.VISIBLE);
                tv_name_of_company.setText(App.appUser.userProfile.company_name);
                tv_name_of_proprietor.setText(App.appUser.userProfile.owner_name);
                tv_address.setText(App.appUser.userProfile.address);
                tv_district.setText(""+App.appUser.userProfile.state_name+" - "+App.appUser.userProfile.district_name);
                tv_pincode.setText("Pin Code - "+App.appUser.userProfile.pincode);
                tv_mobile.setText(App.appUser.mobile);
                tv_email.setText(App.appUser.email);
                tv_market.setText(App.appUser.userProfile.market_name);

                if(App.appUser.userProfile.business_id!=null && !App.appUser.userProfile.business_id.isEmpty()) {
                    int[] business_ids = App.gson.fromJson(App.appUser.userProfile.business_id, int[].class);

                    String businesses = "";
                    for (Integer integer : business_ids) {
                        businesses += "\n" + App.businessIdMap.get(integer).business;
                    }
                    tv_business.setText(businesses.replaceFirst("\n",""));
                }
            }
        }
    }

    public class UserProfileTask extends AsyncTask<Void, Void, Boolean> {
        AppUser appUser;
        DataResult dataResult;
        UserProfileTask(AppUser appUser) {
            this.appUser = appUser;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            dataResult = App.networkService.Profile(Task.update_profile,appUser);

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mUserProfileTask = null;
            //showProgress(false);

            Toast.makeText(context,"Profile has been updated successfully.",Toast.LENGTH_LONG).show();
            if(from!=null && from.equalsIgnoreCase("start")){
                startActivity(new Intent(context, MainActivity.class));
                finish();
            }
            edit_mode = false;
            setViewMode();

            System.out.println("business_id : "+App.appUser.userProfile.business_id);

            mUserProfileTask = null;
            showProgress(false);
        }

        @Override
        protected void onCancelled() {
            mUserProfileTask = null;
            showProgress(false);
        }
    }

    public void setSelectedItemsInForm(String mode,int id){
        int position = 0;
        switch (mode){
            case "state":
                if(App.appUser.userProfile!=null && App.appUser.userProfile.state_id !=-1){
                    for(State state : states){
                        if(state.state_id == id){
                            spStates.setSelection(position);
                        }
                        position++;
                    }
                }
                break;
            case "district":
                if(App.appUser.userProfile!=null && App.appUser.userProfile.district_id !=-1){
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
                if(App.appUser.userProfile!=null && App.appUser.userProfile.market_id !=-1){
                    for(Market market : markets){
                        if(market.mandi_id == id){
                            spMarkets.setSelection(position);
                            break;
                        }
                        position++;
                    }
                }
                break;

        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mProfileFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mProfileFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProfileFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProfileFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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

    @Override
    public void selectedIndices(List<Integer> indices) {

    }

    @Override
    public void selectedStrings(List<String> strings) {
        Toast.makeText(this, strings.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void selectedIds(List<Integer> ids) {
        Toast.makeText(this, new Gson().toJson(ids), Toast.LENGTH_LONG).show();
    }
}
