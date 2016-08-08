package rsoni.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;

import rsoni.kisaanApp.App;
import rsoni.kisaanApp.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class WelcomeActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 3000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_splash);

        startActivity(new Intent(WelcomeActivity.this, ProfileActivity.class));
        finish();

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent nextIntent = null;
                /* Create an Intent that will start the Menu-Activity. */
                if(App.isAppRegistered(getApplicationContext())) {
                    nextIntent = new Intent(WelcomeActivity.this, MainActivity.class);
                }else{
                    nextIntent = new Intent(WelcomeActivity.this, LoginActivity.class);
                }
                WelcomeActivity.this.startActivity(nextIntent);
                WelcomeActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

    }

}
