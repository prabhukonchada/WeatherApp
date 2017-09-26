package weatherapp.prabhukonchada.android.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

import weatherapp.prabhukonchada.android.weatherapp.data.SunshinePreferences;
import weatherapp.prabhukonchada.android.weatherapp.utilities.NetworkUtils;
import weatherapp.prabhukonchada.android.weatherapp.utilities.OpenWeatherJsonUtils;

public class MainActivity extends AppCompatActivity implements  WeatherAdapter.OnWeatherListItemClicked,LoaderManager.LoaderCallbacks<String[]>{

    RecyclerView weatherList;
    private TextView errorMessageView;
    private ProgressBar progressBar;
    WeatherAdapter weatherAdapter;
    Toast weatherToast;
    static String WEATHER_INFO="WEATHER_INFORMATION";
    static String LOCATION = "LOCATION";
    Bundle MainActivityArgs;
    public final int WEATHER_LOADER = 52;
    static String LOADER = "WEATHER LOADER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weatherList = (RecyclerView) findViewById(R.id.weatherList);
        errorMessageView = (TextView) findViewById(R.id.errorMessageView);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        weatherList.setLayoutManager(layoutManager);
        weatherAdapter = new WeatherAdapter(this);
        weatherList.setAdapter(weatherAdapter);
        String location = SunshinePreferences.getPreferredWeatherLocation(this);
        MainActivityArgs = new Bundle();
        MainActivityArgs.putString(LOCATION,location);
        getSupportLoaderManager().initLoader(WEATHER_LOADER,MainActivityArgs,this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.forecast,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.refresh)
        {
            loadWeatherData();
        }

        return true;
    }

    @Override
    public void onItemClicked(String data) {
        Intent detailIntent = new Intent(MainActivity.this,DetailActivity.class);
        detailIntent.putExtra(WEATHER_INFO,data);
        startActivity(detailIntent);

    }

    @Override
    public Loader<String[]> onCreateLoader(int id, final Bundle args) {
        Log.d(LOADER,"onCreateLoader");
        return new AsyncTaskLoader<String[]>(this) {

            String[] cacheData=null;

            @Override
            protected void onStartLoading() {
                Log.d(LOADER,"onStartLoading");
                progressBar.setVisibility(View.VISIBLE);
                if(cacheData != null) {
                    Log.d(LOADER, "--onStartLoading--deliverResult");
                    deliverResult(cacheData);
                }
                else {
                    Log.d(LOADER, "--onStartLoading--forceLoad");
                    forceLoad();
                }
            }

            @Override
            public void deliverResult(String[] data) {
                Log.d(LOADER,"deliverResult");
                cacheData = data;
                progressBar.setVisibility(View.INVISIBLE);
                super.deliverResult(data);
            }

            @Override
            public String[] loadInBackground() {
                Log.d(LOADER,"loadInBackground");
                if(args != null) {
                    String location = args.getString(LOCATION);
                    Log.d("Loc", location);
                    URL url = NetworkUtils.buildUrl(location);

                    try {
                        String weatherResponse = NetworkUtils.getResponseFromHttpUrl(url);
                        String[] jsonWeatherData = OpenWeatherJsonUtils.getSimpleWeatherStringsFromJson(getApplicationContext(), weatherResponse);
                        return jsonWeatherData;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<String[]> loader, String[] data) {
        Log.d(LOADER,"onLoadFinished");
        Log.d("Weather Data",String.valueOf(data));
        progressBar.setVisibility(View.INVISIBLE);
        if(data != null)
        {
            showWeatherData(data);
        }
        else
        {
            showErrorMessage();
        }
    }

    @Override
    public void onLoaderReset(Loader<String[]> loader) {Log.d(LOADER,"onLoadReset");}



    private void loadWeatherData()
    {
        LoaderManager weatherLoaderManager = getSupportLoaderManager();
        if(weatherLoaderManager == null)
        {
            weatherLoaderManager.initLoader(WEATHER_LOADER,MainActivityArgs,this);
        }
        else
        {
            weatherLoaderManager.restartLoader(WEATHER_LOADER,MainActivityArgs,this);
        }
    }

    private void showWeatherData(String[] weatherData)
    {
        weatherList.setVisibility(View.VISIBLE);
        weatherAdapter.setWeatherData(weatherData);
        errorMessageView.setVisibility(View.INVISIBLE);
    }

    private void showErrorMessage()
    {
        Log.d("Show","error");
        weatherList.setVisibility(View.INVISIBLE);
        errorMessageView.setVisibility(View.VISIBLE);
    }

}
