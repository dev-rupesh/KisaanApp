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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rsoni.JustAgriAgro.App;

/**
 * Created by soni on 8/13/2016.
 */
public class CommodityCat {

    public int id;
    public String commodity_cat;

    public CommodityCat(){}

    public CommodityCat(boolean root){
        this.id = -1;
        this.commodity_cat = " -- Select One -- ";
    }

    public CommodityCat(int id, String commodity_cat) {
        this.id = id;
        this.commodity_cat = commodity_cat;
    }

    public static CommodityCat getCommodityCat(Cursor cursor){
        CommodityCat commodityCat = new CommodityCat(
                cursor.getInt(cursor.getColumnIndex("id")),
                cursor.getString(cursor.getColumnIndex("commodity_cat")));
        return commodityCat;
    }

    public static List<CommodityCat> getCommodityCat(Context context) throws IOException {
        List<CommodityCat> commodityCats = App.mydb.getCommodityCat(false);
        return commodityCats;
    }

    @Override
    public String toString() {
        return commodity_cat.trim();
    }


    public static List<CommodityCat> getCommodityCat(String data) {
        Type listType = new TypeToken<List<CommodityCat>>() {}.getType();
        return new Gson().fromJson(data,listType);
    }

}
