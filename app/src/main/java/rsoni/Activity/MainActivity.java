package rsoni.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import rsoni.kisaanApp.App;
import rsoni.kisaanApp.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Context context;
    TextView user_name,user_mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_action_bar_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        if(App.appUser.userProfile==null){
            Toast.makeText(context,"Please add your profile to continue...",Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,ProfileActivity.class));
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        MenuItem item = navigationView.getMenu().findItem(R.id.nav_sign_out);
        item.setVisible(true);

        user_name = (TextView) findViewById(R.id.user_name);
        user_mobile = (TextView) findViewById(R.id.user_mobile);

        user_mobile.setText(App.appUser.mobile);
        if(App.appUser.userProfile!=null){
            user_name.setText(App.appUser.userProfile.company_name);
        }else{
            user_name.setText("Welcome");
        }

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //MenuItem item = navigationView.getMenu().findItem(R.id.nav_sign_out);
        //item.setVisible(false);
        //return super.onPrepareOptionsMenu(menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_commodity_rates) {
            //startActivity(new Intent(this,CommodityRatesSearchActivity.class));
            startActivity(new Intent(this,CommodityPriceAddActivity.class));
        } /*else if (id == R.id.nav_weather_report) {
            startActivity(new Intent(this,WeatherReportActivity.class));
        } */else if (id == R.id.nav_news) {
            startActivity(new Intent(this,NewsActivity.class));
        } else if (id == R.id.nav_market_map) {
            //startActivity(new Intent(this,MandiOnMapActivity.class));
        } else if (id == R.id.nav_find) {
            startActivity(new Intent(this,FindActivity.class));
        } else if (id == R.id.nav_buyer) {
            startActivity(new Intent(this,BuyerActivity.class));
        } else if (id == R.id.nav_seller) {
            startActivity(new Intent(this,SalerActivity.class));
        } else if (id == R.id.nav_profile) {
            startActivity(new Intent(this,ProfileActivity.class));
        } else if (id == R.id.nav_sign_out) {
            App.Logout(context);
            startActivity(new Intent(this,LoginActivity.class));
            finish();
        } else if (id == R.id.nav_share) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.android.example"));
            startActivity(intent);
        } else if (id == R.id.nav_contact_us) {
            startActivity(new Intent(this,ContactUsActivity.class));
        } else if (id == R.id.nav_about_us) {
            startActivity(new Intent(this,AboutUsActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
