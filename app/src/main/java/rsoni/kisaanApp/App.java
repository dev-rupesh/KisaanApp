package rsoni.kisaanApp;

import android.app.Application;
import rsoni.modal.User;

/**
 * Created by DS1 on 03/08/16.
 */
public class App extends Application{

    public static DBHelper mydb;

    public static DataServiceGame dataServiceGame = new DataServiceGame();
    public static String REG_ID = "";
    public static boolean ShowAdd = false;
    public static String nickname = null;


    public static int for_rating = 1;
    public static int for_review = 2;

    // 192.168.1.7

    // public static final String AD_UNIT_ID =
    // "ca-app-pub-8630363907583650/7878872929";
    public static final String AD_UNIT_ID = "ca-app-pub-3779373701273566/1816695056";

    // public static String SENDER_ID = "915094156623";
    public static String SENDER_ID = "1008274990345";

    public static SharedPreferences mPrefs;
    public static Context context;
    public static MixpanelAPI mMixpanel;
    public static final String mixpanel_Token = "d541504f1d6fa3c56c647f55519d6ad3";
    public static final String MIXPANEL_DISTINCT_ID_NAME = "Mixpanel Example $distinctid";

    private static Like like;
    private static Comment comment;
    private static AppUser appUser = new AppUser();

    public static Restaurant selectedRestaurant = null;

    public static boolean showCountdown = false;

    @Override
    public void onCreate() {

        context = getApplicationContext();
        mydb = new DBHelper(context);
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        getAppUser();
        final String trackingDistinctId = getTrackingDistinctId();

        // Initialize the Mixpanel library for tracking and push notifications.
        mMixpanel = MixpanelAPI.getInstance(context, mixpanel_Token);

        // We also identify the current user with a distinct ID, and
        // register ourselves for push notifications from Mixpanel.

        mMixpanel.identify(trackingDistinctId); // this is the distinct_id value
        // that
        // will be sent with events. If you choose not to set this,
        // the SDK will generate one for you

        mMixpanel.getPeople().identify(trackingDistinctId); // this is the
        // distinct_id
        // that will be used for people analytics. You must set this explicitly
        // in order
        // to dispatch people data.
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        mMixpanel.flush();
        super.onTerminate();
    }

    private String getTrackingDistinctId() {
        final SharedPreferences prefs = mPrefs;

        String ret = prefs.getString(MIXPANEL_DISTINCT_ID_NAME, null);
        if (ret == null) {
            ret = generateDistinctId();
            final SharedPreferences.Editor prefsEditor = prefs.edit();
            prefsEditor.putString(MIXPANEL_DISTINCT_ID_NAME, ret);
            prefsEditor.commit();
        }

        return ret;
    }



    private String generateDistinctId() {
        final Random random = new Random();
        final byte[] randomBytes = new byte[32];
        random.nextBytes(randomBytes);
        return Base64.encodeToString(randomBytes, Base64.NO_WRAP
                | Base64.NO_PADDING);
    }

    //public static String ServiceUrl = "http://192.168.1.112:8081/dealjolly/";
    // //Rupesh Ip
    // public static String ServiceUrl = "http://54.213.71.101/ISL/api/";
    // public static String ServiceUrl = "http://pointpi.com/Rupeemax/";
    public static String ServiceUrl = "http://dealjolly.com/";

    // public static String ServiceUrl =
    // "http://192.168.1.142:8080/SoccerBuddy/api/"; // deepak IP
    //

    public static void saveAreas(Context context, String areas_json) {
        Editor editor = mPrefs.edit();
        editor.putString("areas_json", areas_json);
        editor.commit();
    }

    public static void saveCuisines(Context context, String cuisines_json) {
        Editor editor = mPrefs.edit();
        editor.putString("cuisines_json", cuisines_json);
        editor.commit();
    }

    public static String getAreas(Context context) {
        return mPrefs.getString("areas_json", "[]");
    }

    public static String getCuisines(Context context) {
        return mPrefs.getString("cuisines_json", "[]");
    }

    public static void getNickName() {
        nickname = mPrefs.getString("nick_name", null);
    }

    public static void setNickName(String nick_name) {
        Editor editor = mPrefs.edit();
        editor.putString("nick_name", nick_name);
        editor.commit();
    }

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
        Editor editor = mPrefs.edit();
        editor.putInt("app_user_id", appUser.id);
        editor.putString("app_user_name", appUser.username);
        editor.putString("app_user_email", appUser.email);
        editor.commit();
        getAppUser();
    }

    public static void setAppRegisterd(Context context) {
        Editor editor = mPrefs.edit();
        editor.putBoolean("isRegister", true);
        editor.commit();
    }

    public static boolean isAppRegistered(Context context) {
        return mPrefs.getBoolean("isRegister", false);
    }

    public static void clearAppRegistered(Context context) {
        Editor prefsEditor = mPrefs.edit();
        prefsEditor.remove("isRegister");
        prefsEditor.commit();
    }

    public static void Logout(Context context) {
        Editor editor = mPrefs.edit();
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
                                    Login(context);
                                }
                            }).setNegativeButton(android.R.string.no, null)
                    .show();

        return false;
    }

    public static void checkForLogout(final Context context){
        AlertDialog.Builder builder = new  AlertDialog.Builder(context);
        builder.setMessage("Would you like to Sign Out ?");
        builder.setNegativeButton("No", null);
        builder.setPositiveButton("Yes", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                App.Logout(context);
                NavigationDrawerFragment.tv_menu_item1.setText("Sign in");
            }
        });
        builder.create().show();
    }

    public static void Login(final Context context){
        context.startActivity(new Intent(context, LoginActivity.class));
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
