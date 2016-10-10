package rsoni.JustAgriAgro;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import rsoni.Activity.BuyerSalerDetailsActivity;
import rsoni.Utils.DataResult;
import rsoni.Utils.Task;
import rsoni.modal.Market;
import rsoni.modal.UserProfile;

public class MandiOnMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    List<Market> markets = null;

    private Context context;
    BackgroundTask backgroundTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mandi_on_map);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_action_bar_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Mandi-Map JustAgriAgro");
        context = this;
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        backgroundTask = new BackgroundTask(Task.list_market);
        backgroundTask.execute((Void) null);

    }

    public void addMarkers(){
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                LinearLayout info = new LinearLayout(context);
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(context);
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(context);
                snippet.setTextColor(Color.GRAY);
                snippet.setText(marker.getSnippet());

                info.addView(title);
                info.addView(snippet);

                return info;
            }
        });
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        Marker marker;
        for(Market market : markets){
            System.out.println("Add Marker for : "+market.mandi_name+" >> "+market.latitude+" >> "+market.longitude);
            marker = mMap.addMarker(new MarkerOptions().position(new LatLng(market.latitude, market.longitude)).title(market.mandi_name).snippet(market.address+"\n"+market.city+"\nContact : "+market.contact_no));

            //builder.include(marker.getPosition());
        }
        if(!markets.isEmpty()) {
            //LatLngBounds bounds = builder.build();
            //int padding = 0; // offset from edges of the map in pixels
            //CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
            //mMap.animateCamera(cu);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(22.9734, 78.6569),5.0f));
        }
    }


    public class BackgroundTask extends AsyncTask<Void, Void, Boolean> {

        DataResult dataResult;
        Task task;
        boolean from_db = false;

        public  BackgroundTask(Task task){
            this.task = task;
            System.out.println("BackgroundTask initiated");
        }

        @Override
        protected void onPreExecute() {
            //showProgress(true);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            switch(task){
                case list_market:
                    System.out.println("in get profile and data ...");
                    markets = App.mydb.getAllMarkets();
                    System.out.println("size of mandi : "+markets.size());
                    break;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            backgroundTask = null;

            switch(task) {
                case list_market:
                    addMarkers();
                    break;
            }
            //showProgress(false);
        }

        @Override
        protected void onCancelled() {
            backgroundTask = null;
            //showProgress(false);
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
