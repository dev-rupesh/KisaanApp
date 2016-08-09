package rsoni.modal;

import android.content.Context;

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
 * Created by DS1 on 09/08/16.
 */
public class Market {

    int id;
    public String mandi_name;
    double latitude = 24.097381;
    double longitude = 75.053357;
    String city;
    String address;
    String district;
    String contact_no;
    int email_id = 0;

    public static Map<String,List<Market>> getMarketMap(Context context) throws IOException {
        Type listType = new TypeToken<List<Market>>() {}.getType();
        InputStream input = context.getAssets().open("market.json");
        Reader reader = new InputStreamReader(input, "UTF-8");
        Map<String,List<Market>> marketMap = new HashMap<String, List<Market>>();

        List<Market> markets = new Gson().fromJson(reader,listType);
        for (Market market : markets){
            if(!marketMap.containsKey(market.district)){
                marketMap.put(market.district,new ArrayList<Market>());
            }
            marketMap.get(market.district).add(market);
        }

        for (String name : marketMap.keySet()){
            System.out.println("name : "+name);
        }

        return marketMap;
    }

    @Override
    public String toString() {
        return mandi_name;
    }
}
