package weatherapp.prabhukonchada.android.weatherapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by prabhukonchada on 22/09/17.
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherItemViewHolder>{

    OnWeatherListItemClicked weatherListItemClickedListener;
    String[] weatherData;


    public WeatherAdapter(OnWeatherListItemClicked weatherListItemClickedListener)
    {
            this.weatherListItemClickedListener = weatherListItemClickedListener;
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
        if(weatherData != null)
        {
            return weatherData.length;
        }
        return 0;
    }

    @Override
    public void onBindViewHolder(WeatherItemViewHolder holder, int position) {
        holder.weatherDataItem.setText(weatherData[position]);
    }


    class WeatherItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView weatherDataItem;

        public WeatherItemViewHolder(View itemView)
        {
            super(itemView);
            weatherDataItem = (TextView)itemView.findViewById(R.id.weatherInfo);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d("Click","Click Detected");
            int position = getAdapterPosition();
            weatherListItemClickedListener.onItemClicked(weatherData[position]);
        }
    }

    public void setWeatherData(String[] weatherData)
    {
        this.weatherData = weatherData;
        notifyDataSetChanged();
    }

    public interface OnWeatherListItemClicked
    {
        void onItemClicked(String data);
    }

}
