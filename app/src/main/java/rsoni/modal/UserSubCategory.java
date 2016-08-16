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
public class UserSubCategory {

    int id;
    String usersubcat;
    int usercat_id;
    public int usersubcat_id;
    int active;

    public UserSubCategory(){}
    public UserSubCategory(boolean root){
        this.usersubcat_id = -1;
        this.usersubcat = " -- Select One -- ";
    }

    public static Map<Integer,List<UserSubCategory>> getUserSubCategoryMap(Context context) throws IOException {
        Type listType = new TypeToken<List<UserSubCategory>>() {}.getType();
        InputStream input = context.getAssets().open("usersubcat.json");
        Reader reader = new InputStreamReader(input, "UTF-8");
        Map<Integer,List<UserSubCategory>> userSubCategoryMap = new HashMap<Integer, List<UserSubCategory>>();

        List<UserSubCategory> userSubCategories = new Gson().fromJson(reader,listType);
        for (UserSubCategory userSubCategory : userSubCategories){

            if(!userSubCategoryMap.containsKey(userSubCategory.usercat_id)){
                userSubCategoryMap.put(userSubCategory.usercat_id,new ArrayList<UserSubCategory>());
            }
            userSubCategoryMap.get(userSubCategory.usercat_id).add(userSubCategory);
        }
        return userSubCategoryMap;
    }

    @Override
    public String toString() {
        return usersubcat.trim();
    }


}
