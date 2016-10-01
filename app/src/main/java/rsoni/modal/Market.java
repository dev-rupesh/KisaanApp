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
 * Created by DS1 on 09/08/16.
 */
public class Market {

    public int id;
    public String mandi_name;
    public int mandi_id = -1;
    public double latitude = 24.097381;
    public double longitude = 75.053357;
    public String city;
    public String address;
    public int district_id;
    public String contact_no;
    public String email_id = "";

    public Market (){}
    public Market(boolean root){
        mandi_id = -1;
        id = -1;
        mandi_name = " -- Select One -- ";
    }

    public Market(int mandi_id, String mandi_name, int district_id) {
        this.mandi_id = mandi_id;
        this.mandi_name = mandi_name;
        this.district_id = district_id;
    }

    public static Market getMarket(Cursor cursor,boolean all_cols){
        Market market;

        if(all_cols){
            market = new Market();
            market.id = cursor.getInt(cursor.getColumnIndex("id"));
            market.mandi_name = cursor.getString(cursor.getColumnIndex("mandi_name"));
            market.latitude = cursor.getDouble(cursor.getColumnIndex("latitude"));
            market.longitude = cursor.getDouble(cursor.getColumnIndex("longitude"));
            market.city = cursor.getString(cursor.getColumnIndex("city"));
            market.address = cursor.getString(cursor.getColumnIndex("address"));
            market.district_id = cursor.getInt(cursor.getColumnIndex("district_id"));
            market.contact_no = cursor.getString(cursor.getColumnIndex("contact_no"));
            market.email_id = cursor.getString(cursor.getColumnIndex("email_id"));
        }else {
            market = new Market(
                    cursor.getInt(cursor.getColumnIndex("mandi_id")),
                    cursor.getString(cursor.getColumnIndex("mandi_name")),
                    cursor.getInt(cursor.getColumnIndex("district_id")));
        }
        return market;
    }



    public static Map<Integer,List<Market>> getMarketMap(Context context) throws IOException {
        Map<Integer,List<Market>> marketMap = new HashMap<Integer, List<Market>>();

        List<Market> markets = App.mydb.getAllMarkets();
        for (Market market : markets){
            if(!marketMap.containsKey(market.district_id)){
                marketMap.put(market.district_id,new ArrayList<Market>());
            }
            marketMap.get(market.district_id).add(market);
        }

        return marketMap;
    }

    public static List<Market> getMarkets(Context context) throws IOException {
        List<Market> markets = App.mydb.getAllMarkets();
        System.out.println("markets : "+new Gson().toJson(markets));
        return markets;
    }

    @Override
    public String toString() {
        return mandi_name;
    }

    public static List<Market> getMarkets(String data) {
        Type listType = new TypeToken<List<Market>>() {}.getType();
        List<Market> markets = new Gson().fromJson(data,listType);
        System.out.println("markets : "+new Gson().toJson(markets));
        return markets;
    }
}
