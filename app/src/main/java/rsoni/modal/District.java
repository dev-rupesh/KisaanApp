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
public class District {

    int id = 0;
    String district_name = "";
    int district_id = 0;
    int state_id = 0;

    public static List<District> getDistrictList(Context context) throws IOException {
        Type listType = new TypeToken<List<District>>() {}.getType();
        InputStream input = context.getAssets().open("district.json");
        Reader reader = new InputStreamReader(input, "UTF-8");
        List<District> districts = new Gson().fromJson(reader,listType);
        return districts;
    }


}
