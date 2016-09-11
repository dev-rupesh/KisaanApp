package rsoni.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import rsoni.kisaanApp.App;
import rsoni.kisaanApp.R;
import rsoni.modal.Commodity;
import rsoni.modal.CommodityCat;
import rsoni.modal.District;
import rsoni.modal.Market;
import rsoni.modal.State;

public class TestActivity extends AppCompatActivity implements MultiSelectionSpinner.OnMultipleItemsSelectedListener,View.OnClickListener {

    Button btn_get_states,btn_get_district,btn_get_market,btn_get_commodityca,btn_get_commodety;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        String[] array = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten"};
        MultiSelectionSpinner multiSelectionSpinner = (MultiSelectionSpinner) findViewById(R.id.mySpinner);
        multiSelectionSpinner.setItems(array);
        multiSelectionSpinner.setSelection(new int[]{2, 6});
        multiSelectionSpinner.setListener(this);

        //App.mydb.AddMasterDataFromJson(this);

        initView();
    }

    private void initView() {

        btn_get_states = (Button) findViewById(R.id.btn_get_states);
        btn_get_states.setOnClickListener(this);
        btn_get_district = (Button) findViewById(R.id.btn_get_district);
        btn_get_district.setOnClickListener(this);
        btn_get_market = (Button) findViewById(R.id.btn_get_market);
        btn_get_market.setOnClickListener(this);
        btn_get_commodityca = (Button) findViewById(R.id.btn_get_commodityca);
        btn_get_commodityca.setOnClickListener(this);
        btn_get_commodety = (Button) findViewById(R.id.btn_get_commodety);
        btn_get_commodety.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

    @Override
    public void selectedIndices(List<Integer> indices) {

    }

    @Override
    public void selectedStrings(List<String> strings) {
        Toast.makeText(this, strings.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void selectedIds(List<Integer> ids) {

    }

    @Override
    public void onClick(View v) {
        if(v==btn_get_states){
            List<State> states = App.mydb.getStates(true);
            for(State state : states){
                System.out.println("state_name : "+state.state_name+" >> "+state.state_id);
            }
        }else  if(v==btn_get_district){
            List<District> districts = App.mydb.getDistricts(true,20);
            for(District district : districts){
                System.out.println("district_name : "+district.district_name);
            }
        }else  if(v==btn_get_market){
            List<Market> markets = App.mydb.getMarkets(true,"Indore");
            for(Market market : markets){
                System.out.println("mandi_name : "+market.mandi_name);
            }
        }else  if(v==btn_get_commodityca){
            List<CommodityCat> commodityCats = App.mydb.getCommodityCat(true);
            for(CommodityCat commodityCat : commodityCats){
                System.out.println("commodityCat : "+commodityCat.commodity_cat);
            }
        }else  if(v==btn_get_commodety){
            List<Commodity> commodities = App.mydb.getCommodity(true,2);
            for(Commodity commodity : commodities){
                System.out.println("commodity_name : "+commodity.commodity_name);
            }
        }
    }
}
