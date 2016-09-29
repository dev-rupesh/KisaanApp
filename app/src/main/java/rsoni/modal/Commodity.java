package rsoni.modal;

import android.content.Context;
import android.database.Cursor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

import rsoni.JustAgriAgro.App;

/**
 * Created by soni on 8/13/2016.
 */
public class Commodity {

    public int id;
    public String commodity_name;
    public int commodity_cat_id;

    public Commodity(){}

    public Commodity(boolean root){
        this.id = -1;
        this.commodity_name = " -- Select One -- ";
    }

    public Commodity(int id,String commodity_name, int commodity_cat_id) {
        this.id = id;
        this.commodity_name = commodity_name;
        this.commodity_cat_id = commodity_cat_id;
    }

    public static Commodity getCommodity(Cursor cursor){
        Commodity commodity = new Commodity(
                cursor.getInt(cursor.getColumnIndex("id")),
                cursor.getString(cursor.getColumnIndex("commodity_name")),
                cursor.getInt(cursor.getColumnIndex("commodity_cat_id")));

        System.out.println("commodity : "+commodity.id+">>"+commodity.commodity_name);

        return commodity;
    }

    public static List<Commodity> getCommodities(Context context) throws IOException {
        List<Commodity> commodities = App.mydb.getAllCommodity(false);
        return commodities;
    }

    @Override
    public String toString() {
        return commodity_name.trim();
    }


    public static List<Commodity> getCommodities(String data) {
        Type listType = new TypeToken<List<Commodity>>() {}.getType();
        return new Gson().fromJson(data,listType);
    }
}
