package taxi.driverApp.taxiApp.util;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by SFS-009 on 3/19/2016.
 */
public class SessionManager {

    static SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    public static final String NOTIFICATION = "notification";
    static final String SHARED_PREFRANCES_NAME = "sharedPrefrances";
    public static final String  LANGUAGE_TYPE= "lang";
    public static final String USER_ID = "user_id";
    public static final String USER_DATA = "user_data";
    public static final String USER_IMAGE = "user_image";
    public static final String USER_NAME = "user_name";
    public static final String CAR_TYPE = "car type";
    public static final String CAR_SUB_TYPE = "car sub type";
    public static final String NOTIFICATION_SOUND = "notification sound";
    public static final String NOTIFICATION_STATUS = "notification status";
    public static final String ONLINE_STATUS = "onLine status";
    public static final String USER_RATINGS= "user_rating";
    public static final String USER_LOGIN_STATE = "login_state";
    public static final String REGISTOR_TAXI_TYPE = "taxi type";
    public static final String DEVICE_TOKEN = "token";
    public static final String PASSENGER_INFO = "passenger_info";
    public static final String REQ_STATE = "requested_state";





    public static final String FCM_ID = "fcmId";



    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFRANCES_NAME, context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setValues(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }
    public String getValue(String key, String defaultValue) {
        String st_value = sharedPreferences.getString(key, defaultValue);
        return st_value;
    }

    public void clearValues() {
        editor.clear();
        editor.commit();
    }


    public void clearCetainValue(String key) {
        editor.remove(key);
        editor.apply();
    }

}
