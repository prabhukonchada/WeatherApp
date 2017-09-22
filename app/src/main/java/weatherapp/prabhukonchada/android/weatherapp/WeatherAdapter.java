package weatherapp.prabhukonchada.android.weatherapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by prabhukonchada on 22/09/17.
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.weatherItemViewHolder> {


    @Override
    public weatherItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public void onBindViewHolder(weatherItemViewHolder holder, int position) {

    }

    class weatherItemViewHolder extends RecyclerView.ViewHolder
    {

        public weatherItemViewHolder(View itemView)
        {
            super(itemView);
        }


    }

}
