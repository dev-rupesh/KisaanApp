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

import java.util.ArrayList;
import java.util.List;

import rsoni.Adapter.NewsListAdaptor;
import rsoni.Utils.DataResult;
import rsoni.Utils.Task;
import rsoni.kisaanApp.App;
import rsoni.kisaanApp.R;
import rsoni.modal.AppUser;
import rsoni.modal.NewsItem;

public class NewsActivity extends AppCompatActivity {


    ListView lv_news;
    NewsListAdaptor listAdaptor;
    List<NewsItem> newsItems = new ArrayList<>();
    List<NewsItem> newsItemsTemp = new ArrayList<>();
    BackgroundTask backgroundTask;
    Context context;
    int latest_invoice_no = 0;
    NewsItem newsItemTemp = new NewsItem();

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
        listAdaptor =  new NewsListAdaptor(context,newsItems);
        lv_news.setAdapter(listAdaptor);
        backgroundTask = new BackgroundTask(Task.news_list_db);
        backgroundTask.execute((Void) null);
    }

    private void initView() {
        lv_news = (ListView) findViewById(R.id.lv_news);
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
                case news_list_db:
                    newsItemsTemp.clear();
                    newsItemsTemp = App.mydb.getNews(20);
                    break;
                case news_list_web:
                    newsItemTemp.setId(latest_invoice_no);
                    dataResult = App.networkService.News(Task.news_list_web,newsItemTemp);
                    break;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            backgroundTask = null;
            //showProgress(false);
            switch(task) {
                case news_list_db:
                    if(!newsItemsTemp.isEmpty()){
                        latest_invoice_no = newsItemsTemp.get(0).getId();
                        newsItems.addAll(0,newsItemsTemp);
                        listAdaptor.notifyDataSetChanged();
                    }
                    backgroundTask = new BackgroundTask(Task.news_list_web);
                    backgroundTask.execute((Void) null);
                    break;
                case news_list_web:
                    if (dataResult.Status) {
                        newsItemsTemp.clear();
                        newsItemsTemp = (List<NewsItem>) dataResult.Data;
                        if(!newsItemsTemp.isEmpty()){
                            newsItems.addAll(0,newsItemsTemp);
                            listAdaptor.notifyDataSetChanged();
                        }
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