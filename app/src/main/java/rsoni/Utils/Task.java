package rsoni.Utils;

public enum Task {
	email_login,fb_login,g_login,
	email_register,fb_register,g_register,
	forgot_pass,
	
	cuisines_search,areas_search,categories_search,features_search,filters_search,restaurants_search,
	cuisines_search_json,areas_search_json,categories_search_json,features_search_json,filters_search_json,restaurants_search_json,
	
	restaurant_click,restaurant_rate_post,restaurant_review_post,restaurant_rate_get,restaurant_review_get,
	
	deals,deal_info,
	group_list,group,team_list,team,match_events,fixture_list_of_tournament,last7days_fixtures,upcomming_fixtures,fixture,fixture_list_of_team,players_of_team,register,topscorers,
	get,post,
	get_comments,get_comments_db,post_comment,
	get_likes,post_like,
	likeMatch,unLikeMatch,likePlayer,unLikePlayer,likeTeam,unLikeTeam,
	followTeam,unfollowTeam,followMatch,unfollowMatch,
	commentOnMatch,commentOnTeam,viewTeamComments,viewMatchComments,
	turnament,
	profile,
	news_db,new_news_web,old_news_web
}