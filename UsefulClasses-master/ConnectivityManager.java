package taxi.driverApp.taxiApp.util;

import android.content.Context;
import android.net.NetworkInfo;

/**
 * Created by SFS on 5/14/16.
 */
public class ConnectivityManager {


    private Context context;

    public ConnectivityManager(Context context){
        this.context = context;
    }

    /**
     * Checking for all possible internet providers
     * **/
    public   boolean isConnectingToInternet(){
        android.net.ConnectivityManager connectivity = (android.net.ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }


}
