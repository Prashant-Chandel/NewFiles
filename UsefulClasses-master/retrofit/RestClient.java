package com.underxs.app.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by priya on 5/9/17.
 */

public class RestClient {

    private static APIService apiService = null;
    public static final String MAP_BASE = "https://maps.googleapis.com/maps/api/";

    public static APIService getNetworkService(Context context) {

        return getNetworkService(context, APIConstants.BASE_URL);
    }

    public static APIService getNetworkService(Context context, String baseURl) {


        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .readTimeout(2, TimeUnit.MINUTES)
                .connectTimeout(2, TimeUnit.MINUTES)
                .retryOnConnectionFailure(true)
                .addInterceptor(logging)
                .build();


//            httpClient.networkInterceptors().add(new MyOkHttpInterceptor(context));

//        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient)
                .baseUrl(baseURl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(APIService.class);

        return apiService;
    }


    public static class MyOkHttpInterceptor implements Interceptor {

        private Context mContext;

        public MyOkHttpInterceptor(Context context) {
            mContext = context;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {

            if (!NetworkUtil.isOnline(mContext)) {
                throw new NoConnectivityException();
            }

            Request originalRequest = chain.request();

            /*Request newRequest = originalRequest.newBuilder()
                    .header("Content-Type","application/json").build();*/
            //return chain.proceed(newRequest)  .header(  "Authorization", SessionManager.getInstance(mContext).getValue(SessionManager.API_TOKEN,""))
            return chain.proceed(originalRequest);
        }
    }

    public static class NoConnectivityException extends IOException {

        @Override
        public String getMessage() {
            return "No connectivity exception";
        }

    }

    public static class NetworkUtil {
        public static boolean isOnline(Context context) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            return (netInfo != null && netInfo.isConnected());
        }
    }


}
