package rsoni.modal;

import org.json.JSONObject;

/**
 * Created by rupesh.soni on 08-08-2016.
 */

public class UserProfile {

    public int id = 0;
    public String mobile = "";
    public String company_name = "";
    public String owner_name = "";
    public int state_id = -1;
    public String state_name = "";
    public int district_id = -1;
    public String district_name = "";
    public int market_id = -1;
    public String market_name = "";
    public int usersubcat_id = -1;
    public String address = "";
    public int pincode = 0;
    public String business_id = "";

    public static UserProfile getUserProfileByJsonObject(JSONObject json_userProfile){
        UserProfile userProfile = new UserProfile();
        userProfile.id = json_userProfile.optInt("id");
        userProfile.mobile = json_userProfile.optString("user_name");
        userProfile.company_name = json_userProfile.optString("company_name");
        userProfile.owner_name = json_userProfile.optString("owner_name");
        userProfile.state_id = json_userProfile.optInt("state_id");
        userProfile.state_name = json_userProfile.optString("state_name");
        userProfile.district_id = json_userProfile.optInt("district_id");
        userProfile.district_name = json_userProfile.optString("district_name");
        userProfile.market_id =  json_userProfile.optInt("market_id");
        userProfile.market_name = json_userProfile.optString("market_name");
        userProfile.usersubcat_id =  json_userProfile.optInt("usersubcat_id");
        userProfile.address = json_userProfile.optString("address");
        userProfile.pincode =  json_userProfile.optInt("pincode");
        userProfile.business_id =  json_userProfile.optString("business_id");
        return userProfile;
    }

    public void print(){
        System.out.println("user_id : "+id);
        System.out.println("company_name : "+ company_name);
        System.out.println("owner_name : "+ owner_name);
        System.out.println("district_id : "+ district_id);
        System.out.println("market_id : "+ market_id);
        System.out.println("usersubcat : "+ usersubcat_id);
        System.out.println("address : "+address);
        System.out.println("pincode : "+pincode);
    }

    public void copy(UserProfile userProfile){

        this.company_name = userProfile.company_name;
        this.owner_name = userProfile.owner_name;
        this.state_id = userProfile.state_id;
        this.state_name = userProfile.state_name;
        this.district_id = userProfile.district_id;
        this.district_name = userProfile.district_name;
        this.market_id = userProfile.market_id;
        this.market_name = userProfile.market_name;
        this.address = userProfile.address;
        this.pincode = userProfile.pincode;
        this.usersubcat_id = userProfile.usersubcat_id;
        this.business_id = userProfile.business_id;
    }
}
