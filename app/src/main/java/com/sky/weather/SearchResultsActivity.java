package com.sky.weather;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class SearchResultsActivity extends AppCompatActivity implements WeatherApiResponse {

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        handleIntent(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_search_results);

        handleIntent(getIntent());
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            WeatherApiAsync weatherApiAsync = new WeatherApiAsync(this);
            weatherApiAsync.execute(query);
        }
    }

    @Override
    public void returnedData(Location output) {
        Toast.makeText(getApplicationContext(), output.getName(), Toast.LENGTH_SHORT).show();
    }
}
