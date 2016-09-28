package rsoni.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import rsoni.Utils.DataResult;
import rsoni.Utils.Task;
import rsoni.JustAgriAgro.App;
import rsoni.kisaanApp.R;
import rsoni.modal.AppUser;

/**
 * A login screen that offers login via email/password.
 */
public class RegisterActivity extends AppCompatActivity implements OnClickListener{

    private UserRegisterTask mAuthTask = null;

    // UI references.
    private EditText etEmailView;
    private EditText etPasswordView;
    private EditText etMobileView;
    private Spinner spCategoryView;
    private View mProgressView;
    private View mRegisterFormView;
    private Button mEmailSignInButton;

    // Activity reference
    private AppUser appUser = new AppUser();
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        context = this;
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_action_bar_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initView();
    }

    private void initView(){
        etEmailView = (EditText) findViewById(R.id.et_email);
        etPasswordView = (EditText) findViewById(R.id.et_password);
        etMobileView = (EditText) findViewById(R.id.et_mobile);
        spCategoryView = (Spinner) findViewById(R.id.sp_user_cat);
        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(this);
        mRegisterFormView = findViewById(R.id.register_form);
        mProgressView = findViewById(R.id.register_progress);
    }

    private void attemptRegister() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        etMobileView.setError(null);
        etEmailView.setError(null);
        etPasswordView.setError(null);

        appUser.mobile = etMobileView.getText().toString();
        appUser.email = etEmailView.getText().toString();
        appUser.password = etPasswordView.getText().toString();
        appUser.userCategory = spCategoryView.getSelectedItemPosition();

        boolean cancel = false;
        View focusView = null;

        // Check for Empty Field.
        if (TextUtils.isEmpty(appUser.mobile)) {
            etMobileView.setError(getString(R.string.error_field_required));
            focusView = etMobileView;
            cancel = true;
        }
        if (TextUtils.isEmpty(appUser.password)) {
            etPasswordView.setError(getString(R.string.error_field_required));
            focusView = etPasswordView;
            cancel = true;
        }

        // Check for a valid Mobile, if the user entered one.
        if (!TextUtils.isEmpty(appUser.username) && !isMobileValid(appUser.username)) {
            etMobileView.setError(getString(R.string.error_invalid_mobile));
            focusView = etMobileView;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(appUser.password) && !isPasswordValid(appUser.password)) {
            etPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = etPasswordView;
            cancel = true;
        }

        if (!TextUtils.isEmpty(appUser.email) && !isEmailValid(appUser.email)) {
            etEmailView.setError(getString(R.string.error_invalid_email));
            focusView = etEmailView;
            cancel = true;
        }

        if(appUser.userCategory <= 0){
            Toast.makeText(context,"Select User Category",Toast.LENGTH_SHORT).show();
            focusView = spCategoryView;
            cancel = true;
        }

        //cancel = false;

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserRegisterTask(appUser);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }
    private boolean isMobileValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() == 10;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mRegisterFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        if (view == mEmailSignInButton){
            attemptRegister();
        }else if (view == mEmailSignInButton){

        }
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {

        AppUser appUser ;
        DataResult dataResult;
        Task task;
        UserRegisterTask(AppUser appUser) {
            this.appUser = appUser;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            System.out.println("111111");
            dataResult = App.networkService.UserAuth(Task.mobile_register,appUser);
            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);
            if(dataResult.Status){
                App.saveAppUser((AppUser) dataResult.Data);
                App.setAppRegisterd(context);
                Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                //Intent startMain = new Intent(getApplicationContext(), MainActivity.class);
                //startMain.addCategory(Intent.CATEGORY_HOME);
                //startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //startActivity(startMain);
                //finish();

                //Intent intent = new Intent(context, MainActivity.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //intent.putExtra("enter_by", "register");
                //startActivity(intent);
                //finish();
            }else{
                Toast.makeText(context,dataResult.msg,Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
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

