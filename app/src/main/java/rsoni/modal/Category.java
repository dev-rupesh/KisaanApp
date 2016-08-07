package rsoni.modal;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class Category {
	
	int cat_id;
	public String category,filename;
	
	public static List<Category> getDealsFromJsonAray(JSONArray json_categories){		
		List<Category> categories = new ArrayList<Category>();		
		for (int i = 0; i < json_categories.length(); i++) {			
			categories.add(Category.getCategoryFromJson(json_categories.optJSONObject(i)));			
		}		
		return categories;		
	}
	
	public static Category getCategoryFromJson(JSONObject json_deal){
		Category category = new Category();		
		category.cat_id = json_deal.optInt("cat_id");
		category.category = json_deal.optString("category");		
		category.filename = json_deal.optString("filename");			
		return category;
	}
	
	
	
	
}
