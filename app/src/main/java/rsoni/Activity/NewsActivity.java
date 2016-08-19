package rsoni.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import rsoni.Adapter.NewsListAdaptor;
import rsoni.kisaanApp.App;
import rsoni.kisaanApp.R;
import rsoni.modal.NewsItem;

public class NewsActivity extends AppCompatActivity {

    TextView tv_name_of_company,tv_name_of_proprietor;
    LinearLayout ll_user_profile;
    ListView lv_news;
    NewsListAdaptor listAdaptor;
    List<NewsItem> newsItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
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
        lv_news = (ListView) findViewById(R.id.lv_news);
    }

    private void setProfileData(){
        if(App.appUser.userProfile!=null) {
            ll_user_profile.setVisibility(View.VISIBLE);
            tv_name_of_company.setText(App.appUser.userProfile.company_name);
            tv_name_of_proprietor.setText(App.appUser.userProfile.owner_name);
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
