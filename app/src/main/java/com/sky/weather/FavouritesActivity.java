package com.sky.weather;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        searchView.setQueryHint(getString(R.string.search_hint));


        return true;
    }
}
