package rsoni.Activity;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
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
import rsoni.Adapter.SellListAdaptor;
import rsoni.Utils.DataResult;
import rsoni.Utils.Task;
import rsoni.kisaanApp.App;
import rsoni.kisaanApp.R;
import rsoni.modal.Business;
import rsoni.modal.BuyNode;
import rsoni.modal.District;
import rsoni.modal.SaleNode;
import rsoni.modal.State;

public class SalerActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tv_name_of_company,tv_name_of_proprietor,tv_address,tv_district,tv_mobile;
    LinearLayout ll_user_profile,ll_add_node_form;
    ListView lv_sells;
    SellListAdaptor listAdaptor;
    List<SaleNode> saleNodes = new ArrayList<>();
    BackgroundTask backgroundTask;
    Context context;

    Spinner sp_business;
    EditText et_sale_note;
    SaleNode saleNode = new SaleNode();
    private ArrayAdapter<Business> businessArrayAdapter;
    List<Business> my_businesses;

    Button btn_add_node,btn_cancel_node,btn_save_node;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.context = this;
    }


    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        initView();
        setProfileData();
    }

    private void initView() {
        ll_user_profile = (LinearLayout) findViewById(R.id.ll_user_profile);
        ll_add_node_form = (LinearLayout) findViewById(R.id.ll_add_node_form);
        tv_name_of_company = (TextView) findViewById(R.id.tv_name_of_company);
        tv_name_of_proprietor = (TextView) findViewById(R.id.tv_name_of_proprietor);

        sp_business = (Spinner) findViewById(R.id.sp_business);
        et_sale_note = (EditText) findViewById(R.id.et_sale_note);

        btn_add_node = (Button) findViewById(R.id.btn_add_node);
        btn_add_node.setOnClickListener(this);
        btn_cancel_node = (Button) findViewById(R.id.btn_cancel_node);
        btn_cancel_node.setOnClickListener(this);
        btn_save_node = (Button) findViewById(R.id.btn_save_node);
        btn_save_node.setOnClickListener(this);

        //tv_address = (TextView) findViewById(R.id.tv_address);
        //tv_district = (TextView) findViewById(R.id.tv_district);
        //tv_mobile = (TextView) findViewById(R.id.tv_mobile);
        lv_sells = (ListView) findViewById(R.id.lv_sells);
    }

    private void setProfileData(){
        if(App.appUser.userProfile!=null) {

            System.out.println("business_id : "+App.appUser.userProfile.business_id);

            if(my_businesses==null){
                my_businesses = new ArrayList<>();
                if(App.appUser.userProfile.business_id!=null && !App.appUser.userProfile.business_id.isEmpty()) {
                    int[] ids = App.gson.fromJson(App.appUser.userProfile.business_id, int[].class);
                    for (Integer i : ids) {
                        my_businesses.add(App.businessIdMap.get(i));
                    }
                }
                my_businesses.add(0,new Business(true));
                System.out.println("my_businesses.size() : " +my_businesses.size());
            }

            ll_user_profile.setVisibility(View.VISIBLE);
            tv_name_of_company.setText(App.appUser.userProfile.company_name);
            tv_name_of_proprietor.setText(App.appUser.userProfile.owner_name);
            //tv_address.setText(App.appUser.userProfile.address);
            //tv_district.setText("District : "+App.appUser.userProfile.district_name);
            //tv_mobile.setText("Contact : "+App.appUser.username);

            businessArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, my_businesses); //selected item will look like a spinner set from XML
            businessArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_business.setAdapter(businessArrayAdapter);
        }
    }

    private void getSaleNodeAndSave(){

        saleNode = new SaleNode();
        saleNode.user_id = App.appUser.id;
        saleNode.usercat = App.appUser.userCategory;
        saleNode.state_id = App.appUser.userProfile.state_id;
        saleNode.district_id = App.appUser.userProfile.district_id;
        saleNode.market_id = App.appUser.userProfile.market_id;
        saleNode.business_id = ((Business)sp_business.getSelectedItem()).business_id;
        saleNode.sale_note = et_sale_note.getText().toString();

        new BackgroundTask(Task.add_sale_node).execute((Void) null);

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
        if(v == btn_add_node){
            toggelAddNodeForm();
        }else if(v == btn_cancel_node){
            toggelAddNodeForm();
        }else if(v == btn_save_node){
            getSaleNodeAndSave();
        }
    }

    public void toggelAddNodeForm(){
        if(ll_add_node_form.getVisibility()==View.VISIBLE){
            ll_add_node_form.setVisibility(View.GONE);
        }else{
            ll_add_node_form.setVisibility(View.VISIBLE);
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
                case add_sale_node:
                    dataResult = new DataResult(true,"",saleNode);
                    break;
                case list_sale_node:
                    dataResult = new DataResult(true,"",new ArrayList<SaleNode>());
                    break;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            backgroundTask = null;
            //showProgress(false);
            switch(task) {
                case add_sale_node:
                    if (dataResult.Status) {
                        saleNode = (SaleNode) dataResult.Data;
                        saleNodes.add(saleNode);
                        listAdaptor =  new SellListAdaptor(context,saleNodes);
                        lv_sells.setAdapter(listAdaptor);
                    } else {
                        Toast.makeText(context, "No sale node found", Toast.LENGTH_LONG).show();
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