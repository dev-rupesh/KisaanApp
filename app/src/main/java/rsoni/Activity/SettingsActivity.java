package rsoni.Activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import rsoni.JustAgriAgro.App;
import rsoni.Utils.DataResult;
import rsoni.Utils.Task;
import rsoni.kisaanApp.R;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener{

    private Activity context;
    WelcomeActivity.BackgroundTask backgroundTask;

    Button btn_change_pass,btn_update_mandi,btn_update_commodity;
    TextView et_old_password,et_new_password,et_new_password_r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_action_bar_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Settings JustAgriAgro");
        initView();
    }

    private void initView() {
        btn_change_pass = (Button) findViewById(R.id.btn_find);
        btn_change_pass.setOnClickListener(this);
        btn_update_mandi = (Button) findViewById(R.id.btn_find);
        btn_update_mandi.setOnClickListener(this);
        btn_update_commodity = (Button) findViewById(R.id.btn_find);
        btn_update_commodity.setOnClickListener(this);

        et_old_password = (TextView) findViewById(R.id.et_old_password);
        et_new_password = (TextView) findViewById(R.id.et_new_password);
        et_new_password_r = (TextView) findViewById(R.id.et_new_password_r);

    }

    private void validateChangePass(){

    }

    private void validateUpdateMandiData(){

    }

    private void validateUpdateCommodityData(){

    }

    @Override
    public void onClick(View v) {
        if(v == btn_change_pass){
            validateChangePass();
        }else if(v == btn_update_mandi){
            validateUpdateMandiData();
        }else if(v == btn_update_commodity){
            validateUpdateCommodityData();
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
                case update_mandi_master:
                    dataResult = App.networkService.Master(Task.get_master,null);
                    break;
                case update_commodity_master:
                    dataResult = App.networkService.Master(Task.update_master,null);
                    break;
                case change_password:
                    dataResult = App.networkService.Master(Task.update_master,null);
                    break;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            backgroundTask = null;
            //showProgress(false);
            switch(task) {
                case update_mandi_master:
                    if (dataResult.Status) {


                    } else {
                        //Toast.makeText(context, "Wrong Password", Toast.LENGTH_LONG).show();
                    }
                    break;
                case update_commodity_master:
                    if (dataResult.Status) {

                    } else {
                        //Toast.makeText(context, "Wrong Password", Toast.LENGTH_LONG).show();
                    }
                    break;
                case change_password:
                    if (dataResult.Status) {

                    } else {
                        //Toast.makeText(context, "Wrong Password", Toast.LENGTH_LONG).show();
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
