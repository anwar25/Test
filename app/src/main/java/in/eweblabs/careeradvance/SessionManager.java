package in.eweblabs.careeradvance;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;

import java.util.HashMap;

import in.eweblabs.careeradvance.Entity.UserInfo;
import in.eweblabs.careeradvance.StaticData.StaticConstant;

public class SessionManager {
	// Shared Preferences
	SharedPreferences pref;

	// Editor for Shared preferences
	Editor editor;

	// Context
	Context _context;

	// Shared pref mode
	int PRIVATE_MODE = 0;

	// User name (make variable public to access from outside)
	public static final String KEY_USER_NAME = "name";

	// Email address (make variable public to access from outside)
	public static final String KEY_USER_ID = "user_id";

	// Constructor
	public SessionManager(Context context){
		this._context = context;
		pref = _context.getSharedPreferences(StaticConstant.PREF_FILE_NAME, PRIVATE_MODE);
		editor = pref.edit();
	}

	private static  final String KEY_DEVICE_ARN = "deviceARN";

	public void setDeviceARN(String deviceARN) {

		editor.putString(KEY_DEVICE_ARN, deviceARN);

		// commit changes
		editor.commit();

	}

	public String getDeviceARN(){ return pref.getString(KEY_DEVICE_ARN,"");}


	/**
	 * Create login session
	 * */
	public void createLoginSession(String name, String userId){

		// Storing name in pref
		editor.putString(KEY_USER_NAME, name);

		// Storing email in pref
		editor.putString(KEY_USER_ID, userId);

		// commit changes
		editor.commit();
	}

	public void putString(String key , String value ){
		editor.putString(key,value);
		editor.commit();
	}

	public void putBoolean(String key , boolean value ){
		editor.putBoolean(key,value);
		editor.commit();
	}

	public String getString(String key){
			return pref.getString(key,"");
	}
	/**
	 * Check login method wil check user login status
	 * If false it will redirect user to login page
	 * Else won't do anything
	 * */
	public void checkLogin(){
		// Check login status
	/*	if(!this.isLoggedIn()){
			// user is not logged in redirect him to Login Activity
			Intent i = new Intent(_context, LoginActivity.class);
			// Closing all the Activities
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

			// Add new Flag to start new Activity
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

			// Staring Login Activity
			_context.startActivity(i);
		}*/

	}



	public  UserInfo getUserInfoFromShPref(){
		String userInfoString = pref.getString(StaticConstant.USER_INFO,"");
		Gson gson = new Gson();
		return  gson.fromJson(userInfoString, UserInfo.class);
	}
	/**
	 * Get stored session data
	 * */
	public HashMap<String, String> getUserDetails(){
		HashMap<String, String> user = new HashMap<String, String>();
		// user name
		user.put(KEY_USER_NAME, pref.getString(KEY_USER_NAME, null));

		// user email id
		user.put(KEY_USER_ID, pref.getString(KEY_USER_ID, null));

		// return user
		return user;
	}

	/**
	 * Clear session details
	 * */
	public void logoutUser(){
		// Clearing all data from Shared Preferences
		editor.clear();
		editor.commit();

		/*// After logout redirect user to Loing Activity
		Intent i = new Intent(_context, LoginActivity.class);
		// Closing all the Activities
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		// Add new Flag to start new Activity
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		// Staring Login Activity
		_context.startActivity(i);*/
	}

	/**
	 * Quick check for login
	 * **/
	// Get Login State
	public boolean isLoggedIn(){
		return pref.getBoolean(StaticConstant.IS_LOGGED_IN, false);
	}
}