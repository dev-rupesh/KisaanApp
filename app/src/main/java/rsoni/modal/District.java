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
public class District {

    int id = 0;
    public String district_name = "";
    public int district_id = 0;
    int state_id = 0;

    public District(){}
    public District(boolean root){
        this.district_id = -1;
        this.district_name = " -- Select One -- ";
    }

    public static Map<Integer,List<District>> getDistrictMap(Context context) throws IOException {
        Type listType = new TypeToken<List<District>>() {}.getType();
        InputStream input = context.getAssets().open("district.json");
        Reader reader = new InputStreamReader(input, "UTF-8");
        Map<Integer,List<District>> districtMap = new HashMap<Integer, List<District>>();

        List<District> districts = new Gson().fromJson(reader,listType);
        for (District district : districts){
            if(!districtMap.containsKey(district.state_id)){
                districtMap.put(district.state_id,new ArrayList<District>());
            }
            districtMap.get(district.state_id).add(district);
        }
        return districtMap;
    }

    @Override
    public String toString() {
        return district_name.trim();
    }

}