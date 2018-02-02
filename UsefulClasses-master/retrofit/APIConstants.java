package com.underxs.app.network;

/**
 * Created by priya on 5/9/17.
 */

public class APIConstants {


    public static final String BASE_URL = "http://52.63.205.171/api/v1/"; //Live
//    public static final String BASE_URL = "http://52.63.205.171:8005/api/v1/"; //Staging
//    public static final String BASE_URL = "http://192.168.1.94:8003/api/v1/"; //Local harjeet

    //public static final String BASE_URL = "http://192.168.1.186:8003/api/v1/"; //Local nitin


    public static class AddressVariables {
        public static final String SUBPREMISE = "subpremise";
        public static final String STREET_NUMBER = "street_number";
        public static final String ROUTE = "route";
        public static final String LOCALITY = "locality";
        public static final String SUBLOCALITY_LEVEL_1 = "sublocality_level_1";
        public static final String SUBLOCALITY_LEVEL_2 = "sublocality_level_2";

        public static final String ADMINISTRATIVE_AREA_LEVEL_2 = "administrative_area_level_2";
        public static final String ADMINISTRATIVE_AREA_LEVEL_1 = "administrative_area_level_1";
        public static final String COUNTRY = "country";
        public static final String POSTAL_CODE = "postal_code";

    }

}
