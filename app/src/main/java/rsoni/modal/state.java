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
public class State {
    int id = 0;
    public String state_name = "";
    public int country_id = 0;
    public int state_id = 0;

    public static List<State> getStateList(Context context) throws IOException {
        Type listType = new TypeToken<List<State>>() {}.getType();
        InputStream input = context.getAssets().open("state.json");
        Reader reader = new InputStreamReader(input, "UTF-8");
        List<State> states = new Gson().fromJson(reader,listType);
        return states;    }

    @Override
    public String toString() {
        return state_name;
    }
}
