package rsoni.JustAgriAgro;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDex;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import rsoni.Utils.DataSyncCheck;
import rsoni.Utils.SettingSyncCheck;
import rsoni.WebServices.DBHelper;
import rsoni.WebServices.NetworkService;
import rsoni.modal.AppUser;
import rsoni.modal.Business;
import rsoni.modal.UserProfile;

/**
 * Created by DS1 on 03/08/16.
 */
public class App extends Application{

    public static DBHelper mydb;

    public static Gson gson = new Gson();

    public static SimpleDateFormat dateFormate_DDMMYYY = new SimpleDateFormat("dd MMM yyyy");
    public static SimpleDateFormat dateFormate_DDMMYYY_Time = new SimpleDateFormat("dd MMM yyyy hh:mm a");
    public static NetworkService networkService = new NetworkService();
    public static String REG_ID = "";
    public static DataSyncCheck dataSyncCheck = null;
    public static SettingSyncCheck settingSyncCheck = null;


    public static SharedPreferences mPrefs;
    public static Context context;

    public static AppUser appUser = new AppUser();

    //public static List<Business> businesses = null;
    public static long last_update_count = 0;
    public static long profile_update_count = 0;
    public static boolean isactive = false;

    @Override
    public void onCreate() {
        context = getApplicationContext();
        mydb = new DBHelper(context);
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        getAppUser();
        getUserProfile();
        getSettingSyncCheck();
        getDataSyncCheck();
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public static String ServiceUrl = "http://rupeshs.in/JustAgriAgro/api/";



    public static void getAppUser() {
        String json = mPrefs.getString("app_user",null);
        System.out.println("get json : "+json);
        if(json!=null){
            appUser = gson.fromJson(json,AppUser.class);
            //System.out.println(""+appUser.userProfile.business_id);
        }else{
            appUser = null;
        }
    }



    public static void getUserAuthUpdateCount(){
        App.profile_update_count = mPrefs.getLong("profile_update_count",0);
        App.isactive = mPrefs.getBoolean("isactive",true);
    }

    public static void updateUserAuthUpdateCount(){
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putLong("profile_update_count", profile_update_count);
        editor.putBoolean("isactive", isactive);
        editor.commit();
    }

    public static void getUserProfile() {
        String json = mPrefs.getString("user_profile",null);
        if(json!=null){
            appUser.userProfile = gson.fromJson(json,UserProfile.class);
        }
    }

    public static void saveUserProfile() {
        SharedPreferences.Editor editor = mPrefs.edit();
        String json = gson.toJson(appUser.userProfile);
        editor.putString("user_profile", json);
        editor.commit();
        getUserProfile();
    }

    public static void getDataSyncCheck() {
        String json = mPrefs.getString("data_sync",null);
        if(json!=null){
            dataSyncCheck = gson.fromJson(json,DataSyncCheck.class);
        }else{
            dataSyncCheck = new DataSyncCheck();
        }
    }

    public static Map<String,Object> getSettings(){
        Map<String, Object> settings = new Gson().fromJson(mPrefs.getString("app_settings",null), new TypeToken<HashMap<String, Object>>() {}.getType());
        return settings;
    }

    public static void updateSettings(String setting_json){
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString("app_settings", setting_json);
        editor.commit();
    }

    public static long getLastUpdate(){
        return mPrefs.getLong("last_update",0);
    }

    public static void updateLastUpdate(long time){
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putLong("last_update", time);
        editor.commit();
    }


    public static void getSettingSyncCheck() {
        String json = mPrefs.getString("setting_sync",null);

        if(json!=null){
            dataSyncCheck = gson.fromJson(json,DataSyncCheck.class);
        }else{
            dataSyncCheck = new DataSyncCheck();
        }
    }
    public static void saveDataSyncCheck() {
        SharedPreferences.Editor editor = mPrefs.edit();
        String json = gson.toJson(dataSyncCheck);
        editor.putString("data_sync", json);
        editor.commit();
        getDataSyncCheck();
    }
    public static void saveSettingSyncCheck() {
        SharedPreferences.Editor editor = mPrefs.edit();
        String json = gson.toJson(dataSyncCheck);
        editor.putString("setting_sync", json);
        editor.putLong("last_update_count", last_update_count);
        editor.commit();
        getDataSyncCheck();
    }

    public static AppUser getLogedAppUser() {
        if (appUser == null) {
            appUser = new AppUser();
        }
        getAppUser();
        return appUser;
    }

    public static void saveAppUser(AppUser appUser) {
        SharedPreferences.Editor editor = mPrefs.edit();
        String json = gson.toJson(appUser);
        System.out.println("json : "+json);
        editor.putString("app_user", json);
        editor.commit();
        getAppUser();
    }

    public static void setAppRegisterd(Context context) {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putBoolean("isRegister", true);
        editor.commit();
    }

    public static boolean isAppRegistered(Context context) {
        return mPrefs.getBoolean("isRegister", false);
    }

    public static void clearAppRegistered(Context context) {
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.remove("isRegister");
        prefsEditor.commit();
    }

    public static void Logout(Context context) {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.clear();
        editor.commit();
        mydb.truncateDB();
        getDataSyncCheck();
    }

    public static void goToAppUpdate(Activity activity){
        Intent intent = new Intent(Intent.ACTION_VIEW , Uri.parse("market://details?id="+context.getPackageName()));
        activity.startActivity(intent);
        activity.finish();
    }

    public static boolean checkForLogin(final Context context) {

        if (isAppRegistered(context))
            return true;
        else
            new AlertDialog.Builder(context)
                    .setTitle("Sign in")
                    .setMessage("Please Sign in to continue")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes,
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog,
                                                    int whichButton) {
                                }
                            }).setNegativeButton(android.R.string.no, null)
                    .show();

        return false;
    }

    public static ProgressDialog ShowLoader(Context context){
        ProgressDialog pd = new ProgressDialog(context);
        pd.setTitle("Processing...");
        pd.setMessage("Please wait.");
        pd.setCancelable(false);
        pd.setIndeterminate(true);
        pd.show();
        return pd;
    }

    public static SimpleDateFormat DateOnly = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat DateOnlyWithMonthName = new SimpleDateFormat("MMM dd, yyyy");
    public static SimpleDateFormat DateHourMinut = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
    public static SimpleDateFormat DateHourMinutSeconds = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat DateMonthHourMinutSeconds = new SimpleDateFormat("dd-MMM hh:mm a");


    public static SimpleDateFormat HourMinut = new SimpleDateFormat("h:mm a");

    public static AlertDialog.Builder errorDialog = null;

    public static void hideSoftKeyBoard(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public static int getAppVersion(){
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        int versionCode = packageInfo.versionCode;
        return versionCode;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show, final View mFormView, final View mProgressView) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}
