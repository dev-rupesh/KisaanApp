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
public class District {

    int id = 0;
    public String district_name = "";
    public int district_id = 0;
    public int state_id = 0;

    public District(){}
    public District(boolean root){
        this.district_id = -1;
        this.district_name = " -- Select One -- ";
    }

    public District(String district_name, int district_id, int state_id) {
        this.district_name = district_name;
        this.district_id = district_id;
        this.state_id = state_id;
    }

    public static Map<Integer,List<District>> getDistrictMap(Context context) throws IOException {
        Map<Integer,List<District>> districtMap = new HashMap<Integer, List<District>>();
        List<District> districts = App.mydb.getAllDistricts(false);
        for (District district : districts){
            if(!districtMap.containsKey(district.state_id)){
                districtMap.put(district.state_id,new ArrayList<District>());
            }
            districtMap.get(district.state_id).add(district);
        }
        return districtMap;
    }

    public static List<District> getDistricts(Context context) throws IOException {
        List<District> districts = App.mydb.getAllDistricts(false);
        return districts;
    }

    public static District getDistrict(Cursor cursor){
        District state = new District(
                cursor.getString(cursor.getColumnIndex("district_name")),
                cursor.getInt(cursor.getColumnIndex("district_id")),
                cursor.getInt(cursor.getColumnIndex("state_id")));
        return state;
    }

    @Override
    public String toString() {
        return district_name.trim();
    }

    public static List<District> getDistricts(String data) {
        Type listType = new TypeToken<List<District>>() {}.getType();
        List<District> districts = new Gson().fromJson(data,listType);
        return districts;
    }
}
