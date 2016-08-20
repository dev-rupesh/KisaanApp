package rsoni.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import rsoni.Adapter.NewsListAdaptor;
import rsoni.Utils.DataResult;
import rsoni.Utils.Task;
import rsoni.kisaanApp.App;
import rsoni.kisaanApp.R;
import rsoni.modal.AppUser;
import rsoni.modal.NewsItem;

public class NewsActivity extends AppCompatActivity {

    TextView tv_name_of_company,tv_name_of_proprietor;
    LinearLayout ll_user_profile;
    ListView lv_news;
    NewsListAdaptor listAdaptor;
    List<NewsItem> newsItems;
    BackgroundTask backgroundTask;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        initView();
        setProfileData();
        backgroundTask = new BackgroundTask(Task.news_list_sort);
        backgroundTask.execute((Void) null);
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

    public class BackgroundTask extends AsyncTask<Void, Void, Boolean> {

        DataResult dataResult;
        Task task;

        public  BackgroundTask(Task task){
            this.task = task;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            switch(task){
                case news_list_sort:
                    dataResult = new DataResult(true,"",App.mydb.getAllNews());
                    break;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            backgroundTask = null;
            //showProgress(false);
            switch(task) {
                case news_list_sort:
                    if (dataResult.Status) {
                        newsItems = (List<NewsItem>) dataResult.Data;
                        listAdaptor =  new NewsListAdaptor(context,newsItems);
                        lv_news.setAdapter(listAdaptor);
                    } else {
                        Toast.makeText(context, "Wrong Password", Toast.LENGTH_LONG).show();
                    }
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