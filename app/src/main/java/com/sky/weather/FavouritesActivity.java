package com.sky.weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class FavouritesActivity extends AppCompatActivity implements WeatherApiResponse{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        WeatherApiAsync weatherApiAsync = new WeatherApiAsync(this);
        weatherApiAsync.execute("leeds");
    }

    private void showText(String data){
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void returnedData(String output) {
        Toast.makeText(this, output, Toast.LENGTH_SHORT).show();
    }
}
