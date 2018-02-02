package taxi.driverApp.taxiApp.util;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by prashant on 2/19/2016.
 */
public class JSONDecoder {
    public List<List<HashMap<String, Double>>> parse(JSONObject jObject) {
        List<List<HashMap<String, Double>>> routes = new ArrayList<List<HashMap<String, Double>>>();

        JSONArray jSteps = null;
        try {
            jSteps = jObject.getJSONArray("steps");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {


                List<HashMap<String, Double>> path = new ArrayList<HashMap<String, Double>>();



                    /** Traversing all steps */
                    for (int k = 0; k < jSteps.length(); k++) {
                        String polyline = "";
                        polyline = (String) ((JSONObject) ((JSONObject) jSteps
                                .get(k)).get("polyline")).get("points");
                        List<LatLng> polyList = decodePoly(polyline);

                        /** Traversing all points */
                        for (int l = 0; l < polyList.size(); l++) {
                            HashMap<String, Double> hm = new HashMap<String, Double>();
                            hm.put("lat",
                                    (Double)(((LatLng) polyList.get(l)).latitude));
                            hm.put("lng",
                                    (Double)(((LatLng) polyList.get(l)).longitude));
                            path.add(hm);
                        }
                    }
                    routes.add(path);
                }


         catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
        return routes;
    }
    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }
        return poly;
    }
}


