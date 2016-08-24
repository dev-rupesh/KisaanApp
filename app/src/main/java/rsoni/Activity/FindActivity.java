package rsoni.Activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import rsoni.Adapter.BuyListAdaptor;
import rsoni.Adapter.SaleListAdaptor;
import rsoni.Utils.Task;
import rsoni.kisaanApp.App;
import rsoni.kisaanApp.R;
import rsoni.modal.BuyNode;
import rsoni.modal.SaleNode;
import rsoni.modal.SearchFilter;

public class FindActivity extends AppCompatActivity {

    SearchFilter searchFilter = new SearchFilter();
    Context context;

    LinearLayout ll_search_filter,ll_search_filter_form;
    ListView lv_search_result;
    SaleListAdaptor listAdaptorSale;
    BuyListAdaptor listAdaptorBuy;
    List<SaleNode> saleNodes = new ArrayList<>();
    List<BuyNode> buyNodes = new ArrayList<>();

    Spinner sp_districts;
    RadioGroup rg_radioGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.context = this;
        initView();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        resetSearchFilter();
    }

    private void initView() {

    }

    private void resetSearchFilter(){
        searchFilter.state_id = App.appUser.userProfile.state_id;
        searchFilter.state = App.appUser.userProfile.state_name;
        searchFilter.district_id = App.appUser.userProfile.district_id;
        searchFilter.district = App.appUser.userProfile.district_name;
        searchFilter.market_id = App.appUser.userProfile.market_id;
        searchFilter.market = App.appUser.userProfile.market_name;
        searchFilter.searchFor = -1;
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
