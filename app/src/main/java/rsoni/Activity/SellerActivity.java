package rsoni.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import rsoni.Adapter.SellListAdaptor;
import rsoni.kisaanApp.App;
import rsoni.kisaanApp.R;
import rsoni.modal.SaleNode;

public class SellerActivity extends AppCompatActivity {

    TextView tv_name_of_company,tv_name_of_proprietor,tv_address,tv_district,tv_mobile;
    LinearLayout ll_user_profile;
    ListView lv_sells;
    SellListAdaptor listAdaptor;
    List<SaleNode> sellNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        initView();
        setProfileData();
    }

    private void initView() {
        ll_user_profile = (LinearLayout) findViewById(R.id.ll_user_profile);
        tv_name_of_company = (TextView) findViewById(R.id.tv_name_of_company);
        tv_name_of_proprietor = (TextView) findViewById(R.id.tv_name_of_proprietor);
        //tv_address = (TextView) findViewById(R.id.tv_address);
        //tv_district = (TextView) findViewById(R.id.tv_district);
        //tv_mobile = (TextView) findViewById(R.id.tv_mobile);
        lv_sells = (ListView) findViewById(R.id.lv_sells);
    }

    private void setProfileData(){
        if(App.appUser.userProfile!=null) {
            ll_user_profile.setVisibility(View.VISIBLE);
            tv_name_of_company.setText(App.appUser.userProfile.company_name);
            tv_name_of_proprietor.setText(App.appUser.userProfile.owner_name);
            //tv_address.setText(App.appUser.userProfile.address);
            //tv_district.setText("District : "+App.appUser.userProfile.district_name);
            //tv_mobile.setText("Contact : "+App.appUser.username);
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
