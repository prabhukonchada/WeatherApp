package weatherapp.prabhukonchada.android.weatherapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    TextView weatherInfoItem;
    static String WEATHER_INFO="WEATHER_INFORMATION";
    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        data = getIntent().getStringExtra(WEATHER_INFO);
        weatherInfoItem = (TextView) findViewById(R.id.weatherInfoItem);
        weatherInfoItem.setText(data);
    }

}
