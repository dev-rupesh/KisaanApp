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
import java.util.List;

/**
 * Created by DS1 on 09/08/16.
 */
public class State {
    int id = 0;
    public String state_name = "";
    public int country_id = 0;
    public int state_id = -1;

    public State(){}
    public State(boolean root){
        this.state_id = -1;
        this.state_name = " -- Select One -- ";
    }


    public State(int id,int state_id, String state_name, int country_id) {
        this.id = id;
        this.state_name = state_name;
        this.country_id = country_id;
        this.state_id = state_id;
    }

    public static List<State> getStateList(Context context) throws IOException {
        Type listType = new TypeToken<List<State>>() {}.getType();
        InputStream input = context.getAssets().open("state.json");
        Reader reader = new InputStreamReader(input, "UTF-8");
        List<State> states = new Gson().fromJson(reader,listType);
        return states;
    }

    public static State getState(Cursor cursor){
        State state = new State(cursor.getInt(cursor.getColumnIndex("id")),
                cursor.getInt(cursor.getColumnIndex("state_id")),
                cursor.getString(cursor.getColumnIndex("state_name")),
                cursor.getInt(cursor.getColumnIndex("country_id")));
        return state;
    }

    @Override
    public String toString() {
        return state_name;
    }
}
