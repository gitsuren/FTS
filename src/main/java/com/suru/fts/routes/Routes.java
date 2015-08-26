package com.suru.fts.routes;

public class Routes {

	public static final String TOGGLE_SYSTEM_HOME_URI = "/admin";
	
	public static final String TOGGLE_FEATURES_SYSTEM_URI = TOGGLE_SYSTEM_HOME_URI + "/systems";
	public static final String SYSTEM_URI = TOGGLE_SYSTEM_HOME_URI + "/system/{systemName}";
	public static final String SYSTEM_CREATE_URI = TOGGLE_SYSTEM_HOME_URI + "/systems/create";
	public static final String SYSTEM_NAME_EDIT_URI = TOGGLE_SYSTEM_HOME_URI + "/system/{systemName}/edit";
	public static final String SYSTEM_DELETE_URI = TOGGLE_SYSTEM_HOME_URI + "/system/delete";
	public static final String SYSTEM_NAME_DELETE_URI = TOGGLE_SYSTEM_HOME_URI + "/system/{systemName}/delete";
	public static final String FEATURE_URI = SYSTEM_URI + "/feature/{featureName}";
	public static final String FEATURE_DELETE_URI = SYSTEM_URI + "/feature/{featureName}/delete";
	public static final String FEATURE_ADD_URI = SYSTEM_URI + "/features/add";
	public static final String STRATEGY_URI_ADD = FEATURE_URI + "/strategies/add";
	public static final String GROUP_STRATEGY_URI = FEATURE_URI + "/strategy/group/{strategyName}";
	public static final String GROUP_STRATEGY_URI_DELETE = FEATURE_URI + "/strategy/group/{strategyName}/delete";
	public static final String GROUP_STRATEGY_URI_ADD = FEATURE_URI + "/strategy/group/{strategyName}/add";
	public static final String GROUP_STRATEGY_URI_ADD_FEATURE_GROUP = GROUP_STRATEGY_URI + "/groups";
	public static final String GROUP_STRATEGY_URI_REMOVE_FEATURE_GROUP = GROUP_STRATEGY_URI + "/featuregroup/{groupName}/delete";
	
	public static final String PUBLIC_GROUP_URI = TOGGLE_SYSTEM_HOME_URI + "/group/{groupName}";
	public static final String PUBLIC_GROUP_URI_DELETE = TOGGLE_SYSTEM_HOME_URI + "/group/{groupName}/delete";
	public static final String PUBLIC_GROUP_URI_ADD = TOGGLE_SYSTEM_HOME_URI + "/groups";
	public static final String PUBLIC_GROUP_URI_MEMBER_ADD = PUBLIC_GROUP_URI + "/members";
	public static final String PUBLIC_GROUP_URI_MEMBER_DELETE = PUBLIC_GROUP_URI + "/member/{memberName}/delete";
	
	public static final String SYSTEM_FEATURES_FOR_USER_URI = TOGGLE_SYSTEM_HOME_URI + "/getfeatures";
	
	public static final String API_GET_AVAILABLE_FEATURES = "/api/system/{systemName}/features/{id}";
	
    public static final String ERRORS_401_PATH = "/errors/401";
    public static final String ERRORS_403_PATH = "/errors/403";
    public static final String ERRORS_404_PATH = "/errors/404";
    public static final String ERRORS_500_PATH = "/errors/500";
    public static final String ERRORS_OAUTH = "/errors/oautherror";    
}

