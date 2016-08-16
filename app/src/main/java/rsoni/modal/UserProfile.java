package rsoni.modal;

import org.json.JSONObject;

/**
 * Created by rupesh.soni on 08-08-2016.
 */

public class UserProfile {

    public int id = 0;
    public String mobile = "";
    public String fname = "";
    public String lname = "";
    public int state_id = -1;
    public int districtid = -1;
    public int marketid = -1;
    public int usersubcatid = -1;
    public String address = "";
    public int pincode = 0;

    public static UserProfile getUserProfileByJsonObject(JSONObject json_userProfile){
        UserProfile userProfile = new UserProfile();
        userProfile.id = json_userProfile.optInt("id");
        userProfile.fname = json_userProfile.optString("fname");
        userProfile.lname = json_userProfile.optString("lname");
        userProfile.districtid = json_userProfile.optInt("districtid");
        userProfile.marketid =  json_userProfile.optInt("marketid");
        userProfile.usersubcatid =  json_userProfile.optInt("usersubcatid");
        userProfile.address = json_userProfile.optString("address");
        userProfile.pincode =  json_userProfile.optInt("pincode");
        return userProfile;
    }

    public void print(){
        System.out.println("user_id : "+id);
        System.out.println("fname : "+fname);
        System.out.println("lname : "+lname);
        System.out.println("districtid : "+districtid);
        System.out.println("marketid : "+marketid);
        System.out.println("usersubcat : "+usersubcatid);
        System.out.println("address : "+address);
        System.out.println("pincode : "+pincode);
    }
}
