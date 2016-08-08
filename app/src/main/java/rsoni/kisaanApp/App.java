package rsoni.kisaanApp;

import android.app.AlertDialog;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.text.SimpleDateFormat;

import rsoni.Utils.DBHelper;
import rsoni.WebServices.NetworkService;
import rsoni.modal.AppUser;

/**
 * Created by DS1 on 03/08/16.
 */
public class App extends Application{

    public static DBHelper mydb;

    public static NetworkService networkService = new NetworkService();
    public static String REG_ID = "";


    public static SharedPreferences mPrefs;
    public static Context context;

    private static AppUser appUser = new AppUser();

    @Override
    public void onCreate() {

        context = getApplicationContext();
        mydb = new DBHelper(context);
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        getAppUser();

        super.onCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }



    //public static String ServiceUrl = "http://192.168.1.112:8081/dealjolly/";
    // //Rupesh Ip
    // public static String ServiceUrl = "http://54.213.71.101/ISL/api/";
    // public static String ServiceUrl = "http://pointpi.com/Rupeemax/";
    public static String ServiceUrl = "http://rupeshs.in/justagriagro/api/";

    // public static String ServiceUrl =
    // "http://192.168.1.142:8080/SoccerBuddy/api/"; // deepak IP
    //


    public static void getAppUser() {
        appUser.id = mPrefs.getInt("app_user_id", 0);
        appUser.username = mPrefs.getString("app_user_name", "");
        appUser.email = mPrefs.getString("app_user_email", "");
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
        editor.putInt("app_user_id", appUser.id);
        editor.putString("app_user_name", appUser.username);
        editor.putString("app_user_email", appUser.email);
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
                                    ;
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
    public static SimpleDateFormat DateOnlyWithMonthName = new SimpleDateFormat(
            "MMM dd, yyyy");
    public static SimpleDateFormat DateHourMinut = new SimpleDateFormat(
            "yyyy-MM-dd hh:mm a");
    public static SimpleDateFormat DateHourMinutSeconds = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    public static SimpleDateFormat DateMonthHourMinutSeconds = new SimpleDateFormat(
            "dd-MMM hh:mm a");

    public static SimpleDateFormat HourMinut = new SimpleDateFormat("h:mm a");

    public static AlertDialog.Builder errorDialog = null;

    public static void hideSoftKeyBoard(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}
