package com.sky.weather;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hac10 on 13/04/2016.
 */
public class ParseJsonAsync extends AsyncTask<String, Void, Location> {
    public ParseJsonResponse delagate = null;

    public  ParseJsonAsync(ParseJsonResponse delagate){
        this.delagate = delagate;
    }

    @Override
    protected Location doInBackground(String... params) {
        try {
            JSONObject inputObject = new JSONObject(params[0]);
            JSONObject cityObject = inputObject.getJSONObject("city");
            JSONArray forecastArray = inputObject.getJSONArray("list");

            String cityName = cityObject.getString("name");

            List<Days> daysList = new ArrayList<Days>();

            for (int i = 0; i < forecastArray.length() ; i++) {

                JSONObject dayJsonObject = new JSONObject(forecastArray.get(i).toString());
                JSONObject windObject = dayJsonObject.getJSONObject("wind");

                String time = dayJsonObject.getString("dt_txt");
                String windSpeed = windObject.getString("speed");
                String windDirection = windObject.getString("deg");
                Days tempDay = new Days(time, windSpeed, windDirection);

                daysList.add(tempDay);
            }




            Location location = new Location(cityName, daysList);

            return location;

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(Location location) {
        super.onPostExecute(location);
        if(location !=null){
            delagate.parsedJson(location);
        }
    }
}
