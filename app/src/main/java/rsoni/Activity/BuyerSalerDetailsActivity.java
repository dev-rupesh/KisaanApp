package rsoni.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import rsoni.Utils.DataResult;
import rsoni.Utils.Task;
import rsoni.JustAgriAgro.App;
import rsoni.kisaanApp.R;
import rsoni.modal.UserProfile;

public class BuyerSalerDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    // Activity reference
    private UserProfile userProfile = new UserProfile();
    private String from;
    private Context context;
    BackgroundTask backgroundTask;

    // View only Mode
    private View mProgressView;
    private TextView tv_name_of_company,tv_name_of_proprietor,tv_address,tv_district,tv_pincode,tv_market,tv_mobile,tv_email,tv_business;
    private LinearLayout ll_user_profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_saler_details);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_action_bar_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;
        userProfile.id = getIntent().getIntExtra("user_id",-1);

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
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        getProfile();

    }

    private void getProfile(){
        backgroundTask = new BackgroundTask(Task.get_user_profile);
        backgroundTask.execute((Void) null);
    }


    private void showProfile(){
        if(userProfile!=null) {
            ll_user_profile.setVisibility(View.VISIBLE);
            tv_name_of_company.setText(userProfile.company_name);
            tv_name_of_proprietor.setText(userProfile.owner_name);
            tv_address.setText(userProfile.address);
            tv_district.setText(""+userProfile.state_name+" - "+userProfile.district_name);
            tv_pincode.setText("Pin Code - "+userProfile.pincode);
            tv_mobile.setText(userProfile.mobile);
            tv_email.setText(userProfile.email);
            tv_market.setText(userProfile.market_name);

            if(userProfile.business_id != null && !userProfile.business_id.isEmpty()) {
                int[] business_ids = App.gson.fromJson(userProfile.business_id, int[].class);

                String businesses = "";
                for (Integer integer : business_ids) {
                    businesses += "\n" + App.businessIdMap.get(integer).business;
                }
                tv_business.setText(businesses.replaceFirst("\n",""));
            }
        }
    }

    @Override
    public void onClick(View view) {
        if(view == tv_name_of_company){

        }else if(view == tv_name_of_company){

        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            ll_user_profile.setVisibility(show ? View.GONE : View.VISIBLE);
            ll_user_profile.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    ll_user_profile.setVisibility(show ? View.GONE : View.VISIBLE);
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
            ll_user_profile.setVisibility(show ? View.GONE : View.VISIBLE);
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
        boolean from_db = false;

        public  BackgroundTask(Task task){
            this.task = task;
            System.out.println("BackgroundTask initiated");
        }

        @Override
        protected void onPreExecute() {
            showProgress(true);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            switch(task){
                case get_user_profile:
                    System.out.println("in get profile and data ...");
                    UserProfile temp = App.mydb.getUserProfile(userProfile.id);
                    if(temp==null){
                        dataResult = App.networkService.UserProfile(task,userProfile);
                    }else{
                        from_db = true;
                        dataResult = new DataResult(true,"",temp);
                    }

                    break;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            backgroundTask = null;

            switch(task) {
                case get_user_profile:
                    if (dataResult.Status) {
                        //tv_error_msg.setVisibility(View.GONE);
                        userProfile = (UserProfile) dataResult.Data;
                        if(!from_db){
                            App.mydb.saveUserProfile(userProfile);
                        }
                        showProfile();
                    } else {

                        Toast.makeText(context, "No result found .", Toast.LENGTH_LONG).show();
                    }

                    break;
            }

            showProgress(false);
        }

        @Override
        protected void onCancelled() {
            backgroundTask = null;
            showProgress(false);
        }
    }
}
