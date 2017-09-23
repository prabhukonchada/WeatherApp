package weatherapp.prabhukonchada.android.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String mimeType = "text/plain";
        String title = "Weather Data";

        if(item.getItemId() == R.id.shareMenu)
        {
            Log.d("Share","Share Clicked");
            Intent shareIntent = ShareCompat.IntentBuilder.from(this).setType(mimeType).setChooserTitle(title).setText(data).getIntent();
            startActivity(shareIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}
