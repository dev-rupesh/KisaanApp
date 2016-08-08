package rsoni.modal;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by DS1 on 09/08/16.
 */
public class Market {

    int id;
    String mandi_name;
    double latitude = 24.097381;
    double longitude = 75.053357;
    String city;
    String address;
    String district;
    String contact_no;
    int email_id = 0;

    public static List<Market> getDistrictList(Context context) throws IOException {
        Type listType = new TypeToken<List<Market>>() {}.getType();
        InputStream input = context.getAssets().open("market.json");
        Reader reader = new InputStreamReader(input, "UTF-8");
        List<Market> markets = new Gson().fromJson(reader,listType);
        return markets;

    }


}
