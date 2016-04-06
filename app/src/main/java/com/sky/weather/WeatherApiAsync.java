package com.sky.weather;

import android.net.Uri;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by hac10 on 05/04/2016.
 */
public class WeatherApiAsync extends AsyncTask<String, Void, String> {

    public WeatherApiResponse delagate = null;

    public  WeatherApiAsync(WeatherApiResponse delagate){
        this.delagate = delagate;
    }

    @Override
    protected String doInBackground(String... params) {


        String weatherJsonString = "WHATTT";

        if (params.length == 0) {
            return null;
        }

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        final String MODE = "json";
        final String API_ID = "1936c74f84aed1b5d7d314fffdaf3175";
        final String COUNTRY_CODE = ",UK";

        String city = params[0];


        try {
            Uri.Builder uriBuilder = new Uri.Builder();
            uriBuilder.scheme("http")
                    .authority("api.openweathermap.org")
                    .appendPath("data")
                    .appendPath("2.5")
                    .appendPath("forecast")
                    .appendQueryParameter("q", city + COUNTRY_CODE)
                    .appendQueryParameter("mode", MODE)
                    .appendQueryParameter("appid", API_ID);

            URL url = new URL(uriBuilder.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            if (inputStream == null) {
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;

            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }

            weatherJsonString = buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return weatherJsonString;
    }

    @Override
    protected void onPostExecute(String s) {
        if(s !=null){
            delagate.returnedData(s);
        }
    }
}
