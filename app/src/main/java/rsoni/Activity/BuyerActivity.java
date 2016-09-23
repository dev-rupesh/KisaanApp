package rsoni.Activity;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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
import rsoni.Utils.DataResult;
import rsoni.Utils.Task;
import rsoni.kisaanApp.App;
import rsoni.kisaanApp.R;
import rsoni.modal.Business;
import rsoni.modal.BuyNode;

public class BuyerActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tv_name_of_company,tv_name_of_proprietor,tv_address,tv_district,tv_mobile;
    LinearLayout ll_user_profile,ll_add_node_form;
    ListView lv_buys;
    BuyListAdaptor listAdaptor;
    List<BuyNode> buyNodes = new ArrayList<>();
    BackgroundTask backgroundTask;
    Context context;

    Spinner sp_business;
    EditText et_buy_note;
    BuyNode buyNode = new BuyNode();
    private ArrayAdapter<Business> businessArrayAdapter;
    List<Business> my_businesses;

    Button btn_add_node,btn_cancel_node,btn_save_node;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_action_bar_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.context = this;
        initView();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        setProfileData();
        new BackgroundTask(Task.list_buy_node).execute();
    }

    private void initView() {
        ll_user_profile = (LinearLayout) findViewById(R.id.ll_user_profile);
        ll_add_node_form = (LinearLayout) findViewById(R.id.ll_add_node_form);
        tv_name_of_company = (TextView) findViewById(R.id.tv_name_of_company);
        tv_name_of_proprietor = (TextView) findViewById(R.id.tv_name_of_proprietor);

        sp_business = (Spinner) findViewById(R.id.sp_business);
        et_buy_note = (EditText) findViewById(R.id.et_buy_note);

        btn_add_node = (Button) findViewById(R.id.btn_add_node);
        btn_add_node.setOnClickListener(this);
        btn_cancel_node = (Button) findViewById(R.id.btn_cancel_node);
        btn_cancel_node.setOnClickListener(this);
        btn_save_node = (Button) findViewById(R.id.btn_save_node);
        btn_save_node.setOnClickListener(this);

        //tv_address = (TextView) findViewById(R.id.tv_address);
        //tv_district = (TextView) findViewById(R.id.tv_district);
        //tv_mobile = (TextView) findViewById(R.id.tv_mobile);
        lv_buys = (ListView) findViewById(R.id.lv_buys);
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

    private void getBuyNodeAndSave(){

        buyNode = new BuyNode();
        buyNode.user_id = App.appUser.id;
        buyNode.usercat = App.appUser.userCategory;
        buyNode.state_id = App.appUser.userProfile.state_id;
        buyNode.district_id = App.appUser.userProfile.district_id;
        buyNode.market_id = App.appUser.userProfile.market_id;

        buyNode.business_id = ((Business)sp_business.getSelectedItem()).business_id;
        buyNode.buy_note = et_buy_note.getText().toString();

        if(validatePostBuyNote()){
            toggelAddNodeForm();
            new BackgroundTask(Task.add_buy_node).execute((Void) null);
        }
}

    private boolean validatePostBuyNote(){
        if(buyNode.business_id<=0){
            Toast.makeText(context,"Select Entry For",Toast.LENGTH_SHORT).show();
            sp_business.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(buyNode.buy_note)) {
            et_buy_note.setError(getString(R.string.error_field_required));
            et_buy_note.requestFocus();
            return false;
        }
        return true;
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
            App.hideSoftKeyBoard(v);
            getBuyNodeAndSave();
        }
    }

    public void toggelAddNodeForm(){
        if(ll_add_node_form.getVisibility()==View.VISIBLE){
            ll_add_node_form.setVisibility(View.GONE);
        }else{
            et_buy_note.setText("");
            sp_business.setSelection(0);
            ll_add_node_form.setVisibility(View.VISIBLE);
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
                case add_buy_node:
                    System.out.println("in add_buy_node...");
                    dataResult = App.networkService.BuyNode(task, buyNode);
                    break;
                case list_buy_node:
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
                case add_buy_node:
                    if (dataResult.Status) {
                        buyNode = (BuyNode) dataResult.Data;
                        App.mydb.updateBuyNode(buyNode);
                        buyNodes = App.mydb.getBuyNodes();
                        //buyNodes.add(buyNode);
                        listAdaptor =  new BuyListAdaptor(context,buyNodes);
                        lv_buys.setAdapter(listAdaptor);
                    } else {
                        Toast.makeText(context, "No buy node found", Toast.LENGTH_LONG).show();
                    }
                    break;
                case list_buy_node:
                    if (dataResult.Status) {
                        buyNodes = (List<BuyNode>) dataResult.Data;
                        if(!App.dataSyncCheck.buynode) {
                            App.dataSyncCheck.buynode = true;
                            App.saveDataSyncCheck();
                            App.mydb.saveBuyNodes(buyNodes);
                        }
                        listAdaptor =  new BuyListAdaptor(context,buyNodes);
                        lv_buys.setAdapter(listAdaptor);
                    } else {
                        Toast.makeText(context, "No buy node found", Toast.LENGTH_LONG).show();
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
