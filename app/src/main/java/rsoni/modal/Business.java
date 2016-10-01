package rsoni.modal;

import android.content.Context;
import android.database.Cursor;

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

import rsoni.JustAgriAgro.App;

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




    public static List<Business> getBusinessFromJsonAray(JSONArray json_categories){
        List<Business> categories = new ArrayList<Business>();
        for (int i = 0; i < json_categories.length(); i++) {
            categories.add(Business.getBusinessFromJson(json_categories.optJSONObject(i)));
        }
        return categories;
    }

    public static Business getBusiness(Cursor cursor){
        Business business = new Business();
        business.id = cursor.getInt(cursor.getColumnIndex("id"));
        business.business = cursor.getString(cursor.getColumnIndex("business"));
        business.business_id = cursor.getInt(cursor.getColumnIndex("business_id"));
        return business;
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
        List<Business> businesses = App.mydb.getAllBusiness(false);
        for (Business business : businesses){
            businessMap.put(business.business_id,business);
        }

        return businessMap;
    }

    public static List<Business> getBusiness(String data) {
        Type listType = new TypeToken<List<Business>>() {}.getType();
        List<Business> businesses = new Gson().fromJson(data,listType);
        System.out.println("businesses : "+new Gson().toJson(businesses));
        return businesses;
    }

    @Override
    public String toString() {
        return business;
    }
}
