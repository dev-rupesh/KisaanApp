package rsoni.modal;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

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
 * Created by DS1 on 21/08/16.
 */
public class Business {
    public int id;
    public String business;
    public int business_id;

    public Business(){}
    public Business(boolean root){
        this.business_id = -1;
        this.business = " -- Select One -- ";
    }

    public static List<Business> getBusinessList(Context context){
        Type listType = new TypeToken<List<Business>>() {}.getType();
        InputStream input = null;
        List<Business> states = null;
        try {
            input = context.getAssets().open("business.json");
            Reader reader = new InputStreamReader(input, "UTF-8");
            states = new Gson().fromJson(reader,listType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return states;
    }


    public static List<Business> getBusinessFromJsonAray(JSONArray json_categories){
        List<Business> categories = new ArrayList<Business>();
        for (int i = 0; i < json_categories.length(); i++) {
            categories.add(Business.getBusinessFromJson(json_categories.optJSONObject(i)));
        }
        return categories;
    }

    public static Business getBusinessFromJson(JSONObject json_deal){
        Business business = new Business();
        business.id = json_deal.optInt("id");
        business.business = json_deal.optString("business");
        business.business_id = json_deal.optInt("business_id");
        return business;
    }

    public static Map<Integer,Business> getBusinessMap(Context context) {
        Type listType = new TypeToken<List<District>>() {}.getType();
        InputStream input = null;
        Map<Integer,Business> businessMap = new HashMap<Integer, Business>();
        try {
            input = context.getAssets().open("district.json");
            Reader reader = new InputStreamReader(input, "UTF-8");
            List<Business> businesses = getBusinessList(context);
            for (Business business : businesses){
                businessMap.put(business.business_id,business);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return businessMap;
    }

    @Override
    public String toString() {
        return business;
    }
}
