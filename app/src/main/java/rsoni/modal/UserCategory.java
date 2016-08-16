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
 * Created by soni on 8/13/2016.
 */
public class UserCategory {

    int id;
    int usercat_id;
    String usercat;
    String catdesc;
    int active ;

    public UserCategory(){}
    public UserCategory(boolean root){
        this.usercat_id = -1;
        this.usercat = " -- Select One -- ";
    }

    public static Map<Integer,List<UserCategory>> getUserCategoryMap(Context context) throws IOException {
        Type listType = new TypeToken<List<UserCategory>>() {}.getType();
        InputStream input = context.getAssets().open("usercat.json");
        Reader reader = new InputStreamReader(input, "UTF-8");
        Map<Integer,List<UserCategory>> userCategoryMap = new HashMap<Integer, List<UserCategory>>();

        List<UserCategory> userCategories = new Gson().fromJson(reader,listType);
        for (UserCategory userCategory : userCategories){
            if(!userCategoryMap.containsKey(userCategory.usercat_id)){
                userCategoryMap.put(userCategory.usercat_id,new ArrayList<UserCategory>());
            }
            userCategoryMap.get(userCategory.usercat_id).add(userCategory);
        }
        return userCategoryMap;
    }

    @Override
    public String toString() {
        return usercat.trim();
    }


}
