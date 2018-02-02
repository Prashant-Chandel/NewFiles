package taxi.driverApp.taxiApp.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import taxi.driverApp.taxiApp.HomeActivity;

/**
 * Created by PC-DESTOP on 2/1/2017.
 */
public class ClosingService extends Service {

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public void onTaskRemoved(Intent rootIntent) {
            super.onTaskRemoved(rootIntent);

            // Handle application closing
            HomeActivity homeActivity=HomeActivity.getInstance();
            homeActivity.sharedPrefrences_manager.setValues(homeActivity.sharedPrefrences_manager.NOTIFICATION_STATUS,"killed");

            // Destroy the service
            stopSelf();
        }
    }

