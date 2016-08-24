package rsoni.modal;

import android.database.Cursor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by DS1 on 18/08/16.
 */
public class BuyNode {


    public int id;
    public String buy_note;
    public int user_id = 0;
    public int usercat = 0;
    public int business_id = 0;
    public int state_id = 0;
    public int district_id = 0;
    public int market_id;
    public int commodity_id = 0;
    public int commodity_cat_id = 0;
    public long note_date;

    public BuyNode(int id, String buy_note,
                    int user_id, int usercat,int business_id,int state_id,int district_id,int market_id,int commodity_id,int commodity_cat_id,
                    long note_date) {
        this.id = id;
        this.buy_note = buy_note;
        this.user_id = user_id;
        this.usercat = usercat;
        this.business_id = business_id;
        this.state_id = state_id;
        this.district_id = district_id;
        this.market_id = market_id;
        this.commodity_id = commodity_id;
        this.commodity_cat_id = commodity_cat_id;
        this.note_date = note_date;
    }

    public BuyNode() {

    }


    public static BuyNode getBuyNode(JSONObject data){

        BuyNode buyNode = new BuyNode(data.optInt("id"),
                data.optString("buy_note"),
                data.optInt("user_id"),
                data.optInt("usercat"),
                data.optInt("business_id"),
                data.optInt("state_id"),
                data.optInt("district_id"),
                data.optInt("market_id"),
                data.optInt("commodity_id"),
                data.optInt("commodity_cat_id"),
                data.optLong("note_date"));
        return buyNode;

    }

    public static ArrayList<BuyNode> getBuyNodeNodeItems(JSONArray json_array_buy_nodes) {
        ArrayList<BuyNode> buyNodes = new ArrayList<BuyNode>();
        try {
            JSONObject json_buy_node;
            for (int i = 0; i < json_array_buy_nodes.length(); i++) {
                json_buy_node = (JSONObject) json_array_buy_nodes.get(i);
                System.out.println("json_comment : "+json_buy_node.toString());
                buyNodes.add(getBuyNode(json_buy_node));

            }
        } catch (JSONException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        return buyNodes;
    }

    public static BuyNode getBuyNode(Cursor cursor){
        BuyNode buyNode = new BuyNode(cursor.getInt(cursor.getColumnIndex("id")),
                cursor.getString(cursor.getColumnIndex("buy_note")),
                cursor.getInt(cursor.getColumnIndex("user_id")),
                cursor.getInt(cursor.getColumnIndex("usercat")),
                cursor.getInt(cursor.getColumnIndex("business_id")),
                cursor.getInt(cursor.getColumnIndex("state_id")),
                cursor.getInt(cursor.getColumnIndex("district_id")),
                cursor.getInt(cursor.getColumnIndex("market_id")),
                cursor.getInt(cursor.getColumnIndex("commodity_id")),
                cursor.getInt(cursor.getColumnIndex("commodity_cat_id")),
                cursor.getInt(cursor.getColumnIndex("note_date")));
        return buyNode;
    }

}
