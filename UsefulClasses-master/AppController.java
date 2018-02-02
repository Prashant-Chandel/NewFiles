package taxi.driverApp.taxiApp;

import android.content.Context;
import android.provider.Settings;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.crashlytics.android.Crashlytics;
import com.digits.sdk.android.Digits;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import java.util.regex.Pattern;

import io.fabric.sdk.android.Fabric;


public class AppController extends MultiDexApplication  {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    public static final String TWITTER_KEY = "uVOS6Vi2h5ZTptyc4F3ACh3Za";
    public static final String TWITTER_SECRET = "89i6SheLIoebz3tEyxs9RhUtUnZemE9a1qyPI7edIauHlLJMJy";

    public final static Pattern EMAIL_ADDRESS_PATTERN = Pattern
            .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    public static final String BASE_URL = "http://kwiktaxi-app.com/";
    public static final String TAG = AppController.class.getSimpleName();
    public static final String DEVICE_TYPE = "Android";

    private static AppController mInstance;
     public static String offline="no";
    private static Context mContext;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    public static Context getContext() {
        return AppController.mContext;
    }
    public static synchronized AppController getInstance() {
        return mInstance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new TwitterCore(authConfig), new Digits.Builder().withTheme(R.style.CustomDigitsTheme).build(), new Crashlytics());
        mInstance = this;
        AppController.mContext = getApplicationContext();


    }



    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }
    public com.android.volley.toolbox.ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new com.android.volley.toolbox.ImageLoader(this.mRequestQueue, new LruBitmapCache());
        }
        return this.mImageLoader;
    }
    public String getDeviceId()
    {
        return Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    public <T> void addToRequestQueue(com.android.volley.Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(com.android.volley.Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }



}
