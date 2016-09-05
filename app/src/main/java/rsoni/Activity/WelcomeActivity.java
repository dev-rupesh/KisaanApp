package rsoni.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import rsoni.kisaanApp.App;
import rsoni.kisaanApp.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class WelcomeActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 3000;
    private Activity context;
    TextView tv_aap_name;


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
                Intent nextIntent = null;
                /* Create an Intent that will start the Menu-Activity. */

                if(App.mydb.getStates(false).isEmpty()){
                    App.mydb.AddMasterDataFromJson(context);
                }

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
        }, SPLASH_DISPLAY_LENGTH);

    }

}
