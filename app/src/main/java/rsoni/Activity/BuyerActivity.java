package rsoni.Activity;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import rsoni.Adapter.BuyListAdaptor;
import rsoni.Adapter.NewsListAdaptor;
import rsoni.Utils.DataResult;
import rsoni.Utils.Task;
import rsoni.kisaanApp.App;
import rsoni.kisaanApp.R;
import rsoni.modal.BuyNode;
import rsoni.modal.NewsItem;

public class BuyerActivity extends AppCompatActivity {

    TextView tv_name_of_company,tv_name_of_proprietor,tv_address,tv_district,tv_mobile;
    LinearLayout ll_user_profile;
    ListView lv_buys;
    BuyListAdaptor listAdaptor;
    List<BuyNode> buyNotes;
    BackgroundTask backgroundTask;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        initView();
        setProfileData();
    }

    private void initView() {
        ll_user_profile = (LinearLayout) findViewById(R.id.ll_user_profile);
        tv_name_of_company = (TextView) findViewById(R.id.tv_name_of_company);
        tv_name_of_proprietor = (TextView) findViewById(R.id.tv_name_of_proprietor);
        //tv_address = (TextView) findViewById(R.id.tv_address);
        //tv_district = (TextView) findViewById(R.id.tv_district);
        //tv_mobile = (TextView) findViewById(R.id.tv_mobile);
        lv_buys = (ListView) findViewById(R.id.lv_buys);
    }

    private void setProfileData(){

        if(App.appUser.userProfile!=null) {
            ll_user_profile.setVisibility(View.VISIBLE);
            tv_name_of_company.setText(App.appUser.userProfile.company_name);
            tv_name_of_proprietor.setText(App.appUser.userProfile.owner_name);
            //tv_address.setText(App.appUser.userProfile.address);
            //tv_district.setText("District : "+App.appUser.userProfile.district_name);
            //tv_mobile.setText("Contact : "+App.appUser.username);
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
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            switch(task){
                case news_list_sort:
                    dataResult = new DataResult(true,"",App.mydb.getAllNews());
                    break;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            backgroundTask = null;
            //showProgress(false);
            switch(task) {
                case news_list_sort:
                    if (dataResult.Status) {
                        buyNotes = (List<BuyNode>) dataResult.Data;
                        listAdaptor =  new BuyListAdaptor(context,buyNotes);
                        lv_buys.setAdapter(listAdaptor);
                    } else {
                        Toast.makeText(context, "Wrong Password", Toast.LENGTH_LONG).show();
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
}
