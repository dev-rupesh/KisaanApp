package rsoni.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import rsoni.JustAgriAgro.R;
import rsoni.Utils.DataResult;
import rsoni.Utils.Task;
import rsoni.JustAgriAgro.App;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class WelcomeActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 3000;
    private Activity context;
    TextView tv_aap_name;
    BackgroundTask backgroundTask;
    Map<String, Object> settings,current_settings = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = this;

        tv_aap_name = (TextView) findViewById(R.id.tv_app_name);
        Typeface face= Typeface.createFromAsset(getAssets(),"font/georgiaz.ttf");
        tv_aap_name.setTypeface(face);

        //startActivity(new Intent(WelcomeActivity.this, TestActivity.class));
        //this.finish();
        //if(true) return;

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                /* Create an Intent that will start the Menu-Activity. */
                App.last_update_count = App.getLastUpdate();
                current_settings = App.getSettings();

                if(App.mydb.getStates(false).isEmpty()|| current_settings == null){
                    getMasterData();
                }else if(System.currentTimeMillis() - App.last_update_count > 10000){
                    SyncSettings();
                }else{
                    openApp();
                }

                //openApp();

            }
        }, SPLASH_DISPLAY_LENGTH);

    }

    public void openApp(){
        Intent nextIntent = null;
        if(App.isAppRegistered(getApplicationContext())) {
            if(App.appUser.userProfile==null){
                nextIntent = new Intent(WelcomeActivity.this, ProfileActivity.class);
                nextIntent.putExtra("from","start");
            }else{
                nextIntent = new Intent(WelcomeActivity.this, MainActivity.class);
            }
        }else{
            nextIntent = new Intent(WelcomeActivity.this, LoginActivity.class);
        }
        context.startActivity(nextIntent);
        context.finish();
    }

    public void SyncSettings(){
        backgroundTask = new BackgroundTask(Task.get_settings);
        backgroundTask.execute((Void) null);
    }

    public void getMasterData(){

        //App.mydb.AddMasterDataFromJson(context);

        backgroundTask = new BackgroundTask(Task.get_master);
        backgroundTask.execute((Void) null);
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
                case get_settings:
                    dataResult = App.networkService.Master(Task.get_settings,null);
                    break;
                case get_master:
                    dataResult = App.networkService.Master(Task.get_master,null);
                    break;
                case update_master:
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
                case get_settings:
                    if(dataResult.Status){
                        settings = (Map<String, Object>) dataResult.Data;
                        for(String key : settings.keySet()){
                            System.out.println("settings("+key+") = "+settings.get(key));
                        }
                        int current_app_version = App.getAppVersion();
                        int app_version = ((Double)settings.get("app_version")).intValue();
                        if(current_app_version < app_version){
                            System.out.println("New app version detected");
                            App.goToAppUpdate(WelcomeActivity.this);
                        }else{
                            System.out.println("App version is latest.");
                            current_settings = App.getSettings();
                            int current_master_date = ((Double)current_settings.get("master_data")).intValue();
                            int master_data = ((Double)settings.get("master_data")).intValue();
                            if(current_master_date < master_data){
                                backgroundTask = new BackgroundTask(Task.update_master);
                                backgroundTask.execute((Void) null);
                            }else{
                                openApp();
                            }
                        }
                    }
                    break;
                case get_master:
                    App.last_update_count = System.currentTimeMillis();
                    settings = new HashMap<String, Object>();
                    settings.put("app_version",App.getAppVersion());
                    settings.put("master_data",System.currentTimeMillis());
                    App.updateSettings(App.gson.toJson(settings));
                    App.updateLastUpdate(App.last_update_count);
                    openApp();
                    break;
                case update_master:
                    App.last_update_count = System.currentTimeMillis();
                    App.updateSettings(App.gson.toJson(settings));
                    App.updateLastUpdate(App.last_update_count);
                    openApp();
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
