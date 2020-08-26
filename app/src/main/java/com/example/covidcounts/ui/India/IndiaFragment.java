package com.example.covidcounts.ui.India;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.covidcounts.R;

import org.json.JSONException;
import org.json.JSONObject;

public class IndiaFragment extends Fragment {

    public TextView confirmedCasesIndia, activeCasesIndia, recoveredCasesIndia, deceasedCasesIndia, confirmedToday, recoveredToday, criticalCase, deathToday;
    public Button stateMapBtn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View indiaRoot = inflater.inflate(R.layout.india_fragment, container, false);

        RequestQueue requestQueue = Volley.newRequestQueue(indiaRoot.getContext());

        confirmedCasesIndia = indiaRoot.findViewById(R.id.confirmed_cases);
        activeCasesIndia = indiaRoot.findViewById(R.id.active_cases);
        recoveredCasesIndia = indiaRoot.findViewById(R.id.recovered_cases);
        deceasedCasesIndia = indiaRoot.findViewById(R.id.deceased_cases);
        confirmedToday = indiaRoot.findViewById(R.id.todayConfirmed_india);
        recoveredToday = indiaRoot.findViewById(R.id.todayRecovered_india);
        deathToday = indiaRoot.findViewById(R.id.todayDeath_india);
        stateMapBtn = indiaRoot.findViewById(R.id.stateMapOpenBtn);

        JsonObjectRequest indiaRequest = fetchDataForIndia();
        requestQueue.add(indiaRequest);

        stateMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), IndiaMap.class);
                startActivity(intent);
            }
        });

        return indiaRoot;
    }

    public JsonObjectRequest fetchDataForIndia() {

        String urlForIndia = "https://disease.sh/v2/countries/india";

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlForIndia, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    int cases = response.getInt("cases");
                    int active = response.getInt("active");
                    int recovered = response.getInt("recovered");
                    int deaths = response.getInt("deaths");
                    int todayCase = response.getInt("todayCases");
                    int todayDeath = response.getInt("todayDeaths");
                    int todayRecovered = response.getInt("todayRecovered");

                    confirmedCasesIndia.setText(String.valueOf(cases));
                    activeCasesIndia.setText(String.valueOf(active));
                    recoveredCasesIndia.setText(String.valueOf(recovered));
                    deceasedCasesIndia.setText(String.valueOf(deaths));
                    confirmedToday.setText("[+"+todayCase+"]");
                    deathToday.setText("[+"+todayDeath+"]");
                    recoveredToday.setText("[+"+ todayRecovered +"]");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error Occured", Toast.LENGTH_SHORT).show();
            }
        });
        return request;
    }

}