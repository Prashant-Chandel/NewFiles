String Url = "https://maps.googleapis.com/maps/api/directions/json?"
               + "origin=" + passenger_model.getPickup_lat()+","+passenger_model.getPickup_long()
               //+ "origin=" + passenger_model.getFrom_address().replace(" ", "%20")
               + "&destination=" + home_activity.latude + "," + home_activity.lngtude
               + "&mode=driving"
                +"&sensor=true"
                +"&key=" + DIRECTION_API_KEY;
				
				
				jsonPolyLineRequest(Url, 1);
				
				 ///////get polyLines steps for direction
    static void jsonPolyLineRequest(String Url, final int value) {

        String tag_json_obj = "polly steps";
        StringRequest jsObjRequest = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObj = new JSONObject(response);
                    JSONArray jsonArrRoutes = jsonObj.getJSONArray("routes");//traversing routes
                    JSONObject jsonObjsRoutes = jsonArrRoutes.getJSONObject(0);
                    JSONArray jsonArrLegs = jsonObjsRoutes.optJSONArray("legs");// traversing legs
                    JSONObject jsonObjLegs = jsonArrLegs.getJSONObject(0);
                    JSONObject distance = jsonObjLegs.getJSONObject("distance");//get path Distance
                    JSONObject duration = jsonObjLegs.getJSONObject("duration");//get path duration
                    Home_fragment.distance = distance.getString("text");
                    Home_fragment.duration = duration.getString("text");
                    Log.e("diatance", Home_fragment.distance + Home_fragment.duration);
                    //MainActivity.setData(lati,longi, Home_fragment.distance,Duration);
                    //JSONArray jsonArrSteps=jsonObjLegs.getJSONArray("steps");
                    ArrayList<LatLng> points = null;

                    PolylineOptions polyLineOptions = null;
                    List<List<HashMap<String, Double>>> PointsFromStepsObj = new JSONDecoder().parse(jsonObjLegs);

                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    // traversing through routes
                    for (int i = 0; i < PointsFromStepsObj.size(); i++) {
                        points = new ArrayList<LatLng>();
                        polyLineOptions = new PolylineOptions();
                        List<HashMap<String, Double>> path = PointsFromStepsObj.get(i);

                        for (int j = 0; j < path.size(); j++) {
                            HashMap<String, Double> point = path.get(j);

                            double lat = (point.get("lat"));
                            double lng = (point.get("lng"));
                            LatLng position = new LatLng(lat, lng);
                            builder.include(position);
                            points.add(position);
                            Log.e("points : ", "" + points.get(j).latitude + " " + points.get(j).longitude);

                        }
                    }

                    polyLine = mMap.addPolyline(polyLineOptions.addAll(points).width(13.5f).color(Color.BLUE).visible(true).zIndex(30));
                    if (value == 1)// drvr-psngr
                    {
                        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.psngr_marker);
                        home_activity.destMrkr = mMap.addMarker(new MarkerOptions().position(points.get(points.size()-1)).title(passenger_model.getPassenger_name()).snippet("Navigate to passenger location").icon(icon));
                    } else // psngr-dstnsn
                    {
                        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.cab_des_mrker);
                        home_activity.destMrkr = mMap.addMarker(new MarkerOptions().position(points.get(points.size() - 1)).title("Destination").snippet("Destination Location").icon(icon));
                    }
                    if (home_activity.mMarker == null) {
                        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.cab_marker);
                        home_activity.mMarker = mMap.addMarker(new MarkerOptions().position(points.get(0)).title(home_activity.sharedPrefrences_manager.getValue(home_activity.sharedPrefrences_manager.USER_NAME, "my")).snippet("Current Location").icon(icon));
                    }/*CameraPosition cameraPosition = new CameraPosition.Builder()
                             // Sets the center of the map to Mountain View
                            .zoom(17)                   // Sets the zoom
                            .bearing(90)                // Sets the orientation of the camera to east
                            .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                            .build();*/
                    // mMap.animateCamera(CameraUpdateFactory.zoomTo(13.5f));
                    //if(value!=3)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 10));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(15.2f));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.e("Error", "" + error.getStackTrace());
            }
        });
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(jsObjRequest, tag_json_obj);


    }