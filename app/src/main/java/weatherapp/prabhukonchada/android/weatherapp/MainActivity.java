package weatherapp.prabhukonchada.android.weatherapp;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    private TextView mWeatherTextView;
    private TextView errorMessageView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         * Using findViewById, we get a reference to our TextView from xml. This allows us to
         * do things like set the text of the TextView.
         */
        mWeatherTextView = (TextView) findViewById(R.id.tv_weather_data);
        errorMessageView = (TextView) findViewById(R.id.errorMessageView);
        progressBar = (ProgressBar) findViewById(R.id.progress);

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
            mWeatherTextView.setText("");
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
                showWeatherData();
                for (String weatherString : weatherData) {
                    mWeatherTextView.append((weatherString) + "\n\n\n");
                }
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

    private void showWeatherData()
    {
        mWeatherTextView.setVisibility(View.VISIBLE);
        errorMessageView.setVisibility(View.INVISIBLE);
    }

    private void showErrorMessage()
    {
        Log.d("Show","error");
        mWeatherTextView.setVisibility(View.INVISIBLE);
        errorMessageView.setVisibility(View.VISIBLE);
    }

}
