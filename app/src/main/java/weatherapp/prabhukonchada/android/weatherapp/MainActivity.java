package weatherapp.prabhukonchada.android.weatherapp;

import android.content.SharedPreferences;
import android.os.AsyncTask;
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

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

import weatherapp.prabhukonchada.android.weatherapp.data.SunshinePreferences;
import weatherapp.prabhukonchada.android.weatherapp.utilities.NetworkUtils;
import weatherapp.prabhukonchada.android.weatherapp.utilities.OpenWeatherJsonUtils;

public class MainActivity extends AppCompatActivity {

    RecyclerView weatherList;
    private TextView errorMessageView;
    private ProgressBar progressBar;
    WeatherAdapter weatherAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherList = (RecyclerView) findViewById(R.id.weatherList);
        errorMessageView = (TextView) findViewById(R.id.errorMessageView);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        weatherList.setLayoutManager(layoutManager);
        weatherAdapter = new WeatherAdapter();
        weatherList.setAdapter(weatherAdapter);
        loadWeatherData();
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

    class RetreiveWeatherFromNetwork extends AsyncTask<String ,Void,String[]>
    {
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String[] doInBackground(String... params) {
            String location = params[0];
            Log.d("Loc",location);
            URL url = NetworkUtils.buildUrl(location);

            try {
                String weatherResponse = NetworkUtils.getResponseFromHttpUrl(url);
                String[] jsonWeatherData = OpenWeatherJsonUtils.getSimpleWeatherStringsFromJson(getApplicationContext(),weatherResponse);
                return jsonWeatherData;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String[] weatherData) {
            Log.d("Weather Data",String.valueOf(weatherData));
            progressBar.setVisibility(View.INVISIBLE);
            if(weatherData != null)
            {
                showWeatherData(weatherData);
            }
            else
            {
                showErrorMessage();
            }
        }
    }

    private void loadWeatherData()
    {
        String location = SunshinePreferences.getPreferredWeatherLocation(this);
        new RetreiveWeatherFromNetwork().execute(location);
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
