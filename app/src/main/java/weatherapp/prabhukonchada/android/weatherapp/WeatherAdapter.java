package weatherapp.prabhukonchada.android.weatherapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by prabhukonchada on 22/09/17.
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherItemViewHolder> {


    String[] weatherData;

    public WeatherAdapter(String[] weatherData, Context context)
    {
        this.weatherData = weatherData;
    }

    @Override
    public WeatherItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.weather_item_view,parent,false);

        WeatherItemViewHolder itemViewHolder = new WeatherItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public int getItemCount() {
        return weatherData.length;
    }

    @Override
    public void onBindViewHolder(WeatherItemViewHolder holder, int position) {
        holder.weatherDataItem.setText(weatherData[position]);
    }

    class WeatherItemViewHolder extends RecyclerView.ViewHolder
    {
        TextView weatherDataItem;

        public WeatherItemViewHolder(View itemView)
        {
            super(itemView);
            weatherDataItem = (TextView)itemView.findViewById(R.id.weatherInfo);
        }

    }

}
