package rsoni.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import rsoni.JustAgriAgro.App;
import rsoni.Utils.DataResult;
import rsoni.Utils.Task;
import rsoni.kisaanApp.R;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener{

    private Activity context;

    Button btn_change_pass,btn_update_app_data;
    TextView et_old_password,et_new_password,et_new_password_r;

    String old_pass,new_pass,new_pass_r;

    BackgroundTask backgroundTask;
    private View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_action_bar_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Settings JustAgriAgro");
        context = this;
        initView();
    }

    private void initView() {

        mProgressView = findViewById(R.id.setting_progress);
        btn_change_pass = (Button) findViewById(R.id.btn_change_pass);
        btn_change_pass.setOnClickListener(this);
        btn_update_app_data = (Button) findViewById(R.id.btn_update_app_data);
        btn_update_app_data.setOnClickListener(this);

        et_old_password = (TextView) findViewById(R.id.et_old_password);
        et_new_password = (TextView) findViewById(R.id.et_new_password);
        et_new_password_r = (TextView) findViewById(R.id.et_new_password_r);
    }

    private void validateChangePass(){
        et_old_password.setError(null);
        et_new_password.setError(null);
        et_new_password_r.setError(null);

        old_pass = et_old_password.getText().toString();
        new_pass = et_new_password.getText().toString();
        new_pass_r = et_new_password_r.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for Empty Field.
        if (TextUtils.isEmpty(old_pass)) {
            et_old_password.setError(getString(R.string.error_field_required));
            focusView = et_old_password;
            cancel = true;
        }

        if (TextUtils.isEmpty(new_pass)) {
            et_new_password.setError(getString(R.string.error_field_required));
            focusView = et_new_password;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(new_pass) && !isPasswordValid(new_pass)) {
            et_new_password.setError(getString(R.string.error_invalid_password));
            focusView = et_new_password;
            cancel = true;
        }

        if (TextUtils.isEmpty(new_pass_r)) {
            et_new_password_r.setError(getString(R.string.error_field_required));
            focusView = et_new_password_r;
            cancel = true;
        }

        // Check for a confirm pass.
        if (!new_pass.equals(new_pass_r)) {
            et_new_password_r.setError(getString(R.string.error_confirm_password));
            focusView = et_new_password_r;
            cancel = true;
        }
        if(cancel) return;

        backgroundTask = new BackgroundTask(Task.change_password);
        backgroundTask.execute((Void) null);
    }
    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    private void validateUpdateAppData(){

        App.getLastSync();
        if(System.currentTimeMillis()-App.last_update_count>0){
            backgroundTask = new BackgroundTask(Task.update_master);
            backgroundTask.execute((Void) null);
        }else{
            Toast.makeText(context, "Your data has been updated recently.", Toast.LENGTH_LONG).show();
        }

    }



    @Override
    public void onClick(View v) {
        if(v == btn_change_pass){
            validateChangePass();
            App.hideSoftKeyBoard(v);
        }else if(v == btn_update_app_data){
            validateUpdateAppData();
        }

    }

    public class BackgroundTask extends AsyncTask<Void, Void, Boolean> {

        DataResult dataResult;
        Task task;

        public  BackgroundTask(Task task){
            this.task = task;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress(true);
            System.out.println("onPreExecute called...");
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            switch(task){
                case update_master:
                    dataResult = App.networkService.Master(Task.update_master,null);
                    break;
                case change_password:
                    dataResult = App.networkService.UserAuth(Task.change_password,App.appUser,old_pass,new_pass);
                    break;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            backgroundTask = null;

            switch(task) {
                case update_master:
                    App.last_update_count = System.currentTimeMillis();
                    App.setLastSync();
                    Toast.makeText(context, "App data has been updated.", Toast.LENGTH_LONG).show();
                    break;
                case change_password:
                    if (dataResult.Status) {
                        Toast.makeText(context, dataResult.msg, Toast.LENGTH_LONG).show();
                        clearForm();
                    } else {
                        Toast.makeText(context, dataResult.msg, Toast.LENGTH_LONG).show();
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

    private void clearForm() {
        et_old_password.setText("");
        et_new_password.setText("");
        et_new_password_r.setText("");
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


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

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
        }
    }
}
