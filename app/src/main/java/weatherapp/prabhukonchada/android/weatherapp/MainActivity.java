package weatherapp.prabhukonchada.android.weatherapp;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

import weatherapp.prabhukonchada.android.weatherapp.data.SunshinePreferences;
import weatherapp.prabhukonchada.android.weatherapp.utilities.NetworkUtils;
import weatherapp.prabhukonchada.android.weatherapp.utilities.OpenWeatherJsonUtils;

public class MainActivity extends AppCompatActivity {

    private TextView mWeatherTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         * Using findViewById, we get a reference to our TextView from xml. This allows us to
         * do things like set the text of the TextView.
         */
        mWeatherTextView = (TextView) findViewById(R.id.tv_weather_data);


        loadWeatherData();
    }


    class RetreiveWeatherFromNetwork extends AsyncTask<String ,Void,String[]>
    {
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
            if(weatherData != null)
            {
                for (String weatherString : weatherData) {
                    mWeatherTextView.append((weatherString) + "\n\n\n");
                }
            }
        }
    }

    private void loadWeatherData()
    {
        String location = SunshinePreferences.getPreferredWeatherLocation(this);
        new RetreiveWeatherFromNetwork().execute(location);
    }
}
