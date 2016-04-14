package com.sky.weather;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class SearchResultsActivity extends AppCompatActivity implements WeatherApiResponse, ParseJsonResponse{

    private TextView windSpeedTextView;
    private TextView windDirectionTextView;
    private TextView currentDateTextView;
    private TextView cityNameTextView;
    private RecyclerView forecastRecyclerView;
    private RecyclerAdapter adapter;
    private ImageView compassPointImageView;

    private Location currentLocation;
    private String returnedJson;



    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        handleIntent(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);


        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);//Attempt using stored data??

        handleIntent(getIntent());


        setTitle("");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);

        cityNameTextView = (TextView) findViewById(R.id.cityNameTextView);
        windSpeedTextView = (TextView) findViewById(R.id.windSpeedTextView);
        windDirectionTextView = (TextView) findViewById(R.id.windDirectionTextView);
        currentDateTextView = (TextView) findViewById(R.id.currentDateTextView);
        forecastRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        compassPointImageView = (ImageView) findViewById(R.id.compass_pointer);

        if(savedInstanceState != null){
            currentLocation = savedInstanceState.getParcelable("currentLocation");
            setLocationView();
        }
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            WeatherApiAsync weatherApiAsync = new WeatherApiAsync(this);
            weatherApiAsync.execute(query);
        } else{
            String cityTitle = intent.getStringExtra("cityTitle");

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            String locationInfo = sharedPreferences.getString(cityTitle, null);

            ParseJsonAsync parseJsonResponse = new ParseJsonAsync(this);
            parseJsonResponse.execute(locationInfo);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Inflate menu to add items to action bar if it is present.
        inflater.inflate(R.menu.detail_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.addFavourite:
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(currentLocation.getName(), returnedJson);
                editor.commit();
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("currentLocation", currentLocation);
    }

    public void setCompass(float direction){
        AnimationSet animSet = new AnimationSet(true);
        //animSet.setInterpolator(new DecelerateInterpolator());

        final RotateAnimation rotateAnimation = new RotateAnimation(0.0f, direction,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);

        rotateAnimation.setDuration(1000);
        //animSet.addAnimation(rotateAnimation);

        rotateAnimation.setRepeatCount(0);
        rotateAnimation.setFillAfter(true);
        compassPointImageView.startAnimation(rotateAnimation);
    }

    public void setLocationView(){
        String cityTitle = currentLocation.getName();

        List<Days> forecast = currentLocation.getDays();

        windSpeedTextView.setText(forecast.get(0).getWindSpeed());
        windDirectionTextView.setText(forecast.get(0).getWindDirection());


        adapter = new RecyclerAdapter(this, forecast, R.layout.forecast_view, null);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        forecastRecyclerView.setAdapter(adapter);
        forecastRecyclerView.setLayoutManager(linearLayoutManager);
        currentDateTextView.setText(getString(R.string.last_updated) + forecast.get(0).getTime());
        cityNameTextView.setText(cityTitle);

        setCompass(Float.parseFloat(forecast.get(0).getWindDirection()));
    }


    @Override
    public void returnedData(String output) {
        if(output !=null){
            returnedJson = output;
            ParseJsonAsync parseJsonResponse = new ParseJsonAsync(this);
            parseJsonResponse.execute(output);
        }
    }

    @Override
    public void parsedJson(Location output) {
        if(output !=null){
            currentLocation = output;
            setLocationView();
        }
    }
}
