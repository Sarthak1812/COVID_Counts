package com.example.covidcounts.ui.Global;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidcounts.R;

import java.util.List;

class CountryData{
    String countryName;
    Integer countryCases;
    Integer countryRecovered;
    Integer countryDeath;

    CountryData(){
    }
}

public class RVCountryAdapter extends RecyclerView.Adapter<RVCountryAdapter.CountryDataViewHolder> {

    List<CountryData> countryData;

    RVCountryAdapter(List<CountryData> countryData) {
        this.countryData = countryData;
    }

    @NonNull
    @Override
    public RVCountryAdapter.CountryDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_rv_layout, parent, false);
        CountryDataViewHolder cdvh = new CountryDataViewHolder(v);
        return cdvh;
    }

    @Override
    public void onBindViewHolder(@NonNull RVCountryAdapter.CountryDataViewHolder holder, int position) {
        holder.country_Name.setText(countryData.get(position).countryName);
        holder.country_Case.setText(String.valueOf(countryData.get(position).countryCases));
        holder.country_Recovered.setText(String.valueOf(countryData.get(position).countryRecovered));
        holder.country_Death.setText(String.valueOf(countryData.get(position).countryDeath));
    }

    @Override
    public int getItemCount() {
        if (countryData != null) {
            return countryData.size();
        }
        return 0;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class CountryDataViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView country_Name;
        TextView country_Case;
        TextView country_Recovered;
        TextView country_Death;

        CountryDataViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cv_Country);
            country_Name = itemView.findViewById(R.id.country_name);
            country_Case = itemView.findViewById(R.id.country_cases);
            country_Recovered = itemView.findViewById(R.id.country_recovered);
            country_Death = itemView.findViewById(R.id.country_death);
        }
    }

}

