package rsoni.modal;

import android.database.Cursor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by DS1 on 05/09/16.
 */
public class CommodityPrice {

    public int id;
    public String price_note;
    public int user_id = 0;
    public int state_id = 0;
    public int district_id = 0;
    public int market_id;
    public int commodity_id = 0;
    public String commodity_name ="";
    public int commodity_cat_id = 0;
    public long price_date;

    public CommodityPrice(int id, String price_note,
                   int user_id, int state_id,int district_id,int market_id,int commodity_id,String commodity_name,int commodity_cat_id,
                   long price_date) {
        this.id = id;
        this.price_note = price_note;
        this.user_id = user_id;
        this.state_id = state_id;
        this.district_id = district_id;
        this.market_id = market_id;
        this.commodity_id = commodity_id;
        this.commodity_name = commodity_name;
        this.commodity_cat_id = commodity_cat_id;
        this.price_date = price_date;
    }

    public CommodityPrice() {

    }


    public static CommodityPrice getBuyNode(JSONObject data){

        CommodityPrice commodityPrice = new CommodityPrice(data.optInt("id"),
                data.optString("price_note"),
                data.optInt("user_id"),
                data.optInt("state_id"),
                data.optInt("district_id"),
                data.optInt("market_id"),
                data.optInt("commodity_id"),
                data.optString("commodity_name"),
                data.optInt("commodity_cat_id"),
                data.optLong("price_date"));
        return commodityPrice;

    }

    public static ArrayList<CommodityPrice> getCommodityPrice(JSONArray json_array_buy_nodes) {
        ArrayList<CommodityPrice> buyNodes = new ArrayList<CommodityPrice>();
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

    public static CommodityPrice getCommodityPrice(Cursor cursor){
        CommodityPrice commodityPrice = new CommodityPrice(cursor.getInt(cursor.getColumnIndex("id")),
                cursor.getString(cursor.getColumnIndex("price_note")),
                cursor.getInt(cursor.getColumnIndex("user_id")),
                cursor.getInt(cursor.getColumnIndex("state_id")),
                cursor.getInt(cursor.getColumnIndex("district_id")),
                cursor.getInt(cursor.getColumnIndex("market_id")),
                cursor.getInt(cursor.getColumnIndex("commodity_id")),
                cursor.getString(cursor.getColumnIndex("commodity_name")),
                cursor.getInt(cursor.getColumnIndex("commodity_cat_id")),
                cursor.getLong(cursor.getColumnIndex("price_date")));
        return commodityPrice;
    }
}
