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

/**
 * Created by soni on 8/13/2016.
 */
public class Commodity {

    public int id;
    public String commodity;
    public int commodity_cat_id;

    public Commodity(){}

    public Commodity(boolean root){
        this.id = -1;
        this.commodity = " -- Select One -- ";
    }

    public Commodity(int id, String commodity, int commodity_cat_id) {
        this.id = id;
        this.commodity = commodity;
        this.commodity_cat_id = commodity_cat_id;
    }

    public static Commodity getCommodity(Cursor cursor){
        Commodity commodity = new Commodity(
                cursor.getInt(cursor.getColumnIndex("id")),
                cursor.getString(cursor.getColumnIndex("commodity")),
                cursor.getInt(cursor.getColumnIndex("commodity_cat_id")));
        return commodity;
    }

    public static List<Commodity> getCommodities(Context context) throws IOException {
        Type listType = new TypeToken<List<Commodity>>() {}.getType();
        InputStream input = context.getAssets().open("commodity.json");
        Reader reader = new InputStreamReader(input, "UTF-8");
        List<Commodity> commodities = new Gson().fromJson(reader,listType);
        return commodities;
    }

    @Override
    public String toString() {
        return commodity.trim();
    }


}
