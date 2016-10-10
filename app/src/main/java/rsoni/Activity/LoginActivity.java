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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import rsoni.JustAgriAgro.R;
import rsoni.Utils.DataResult;
import rsoni.Utils.Task;
import rsoni.JustAgriAgro.App;
import rsoni.modal.AppUser;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText mMobileView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private TextView mForgotPass;
    private TextView mRegister;
    private Button mEmailSignInButton;

    // Activity reference
    private AppUser appUser = new AppUser();
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_action_bar_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        context = this;
        // Set up the login form.
        mMobileView = (EditText) findViewById(R.id.mobile);
        mPasswordView = (EditText) findViewById(R.id.password);
        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(this);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        mForgotPass = (TextView) findViewById(R.id.m_forgot_pass);
        mForgotPass.setOnClickListener(this);
        mRegister = (TextView) findViewById(R.id.m_register);
        mRegister.setOnClickListener(this);

    }
    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {



        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mMobileView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        //String email = mMobileView.getText().toString();
        //String password = mPasswordView.getText().toString();

        appUser.mobile = mMobileView.getText().toString();
        appUser.password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(appUser.password) && !isPasswordValid(appUser.password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        /*// Check for a valid email address.
        if (TextUtils.isEmpty(appUser.username)) {
            mMobileView.setError(getString(R.string.error_field_required));
            focusView = mMobileView;
            cancel = true;
        } else if (!isEmailValid(appUser.username)) {
            mMobileView.setError(getString(R.string.error_invalid_email));
            focusView = mMobileView;
            cancel = true;
        }*/

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(appUser);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 2;
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

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {

        if(v == mForgotPass){
            Intent i = new Intent(getApplicationContext(),ForgotPassActivity.class);
            startActivity(i);
            //finish();
        }else if(v == mRegister){
            Intent i = new Intent(getApplicationContext(),RegisterActivity.class);
            startActivity(i);
            //finish();
        }else if(v == mEmailSignInButton){
            App.hideSoftKeyBoard(v);
            attemptLogin();
        }else if(v == mForgotPass){

        }
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
        AppUser appUser ;
        DataResult dataResult;
        UserLoginTask(AppUser appUser) {
            this.appUser = appUser;
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
            mAuthTask = null;
            showProgress(false);

            if(dataResult.Status){
                System.out.println("data : "+ App.gson.toJson(dataResult.Data));
                App.saveAppUser((AppUser) dataResult.Data);
                App.setAppRegisterd(context);
                Intent nextIntent = null;
                if(App.appUser.userProfile==null){
                    nextIntent = new Intent(context, ProfileActivity.class);
                    nextIntent.putExtra("from","start");
                }else{
                    nextIntent = new Intent(context, MainActivity.class);
                }
                Intent mainIntent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(nextIntent);
                finish();
            }else{
                Toast.makeText(context,"Wrong Password",Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

