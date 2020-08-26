package com.example.covidcounts.ui.Global;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.covidcounts.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GlobalFragment extends Fragment {

    public TextView confirmedCasesGlobal, activeCasesGlobal, recoveredCasesGlobal;
    ArrayList<CountryData> countryDataList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View globalRoot = inflater.inflate(R.layout.global_fragment, container, false);

        confirmedCasesGlobal = globalRoot.findViewById(R.id.confirmed_global_cases);
        activeCasesGlobal = globalRoot.findViewById(R.id.active_global_cases);
        recoveredCasesGlobal = globalRoot.findViewById(R.id.recovered_global_cases);

        RequestQueue requestQueue = Volley.newRequestQueue(globalRoot.getContext());
        JsonObjectRequest globalRequest = fetchDataForGlobal();
        requestQueue.add(globalRequest);



        RecyclerView rv = globalRoot.findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);

        final RVCountryAdapter rvAdapter = new RVCountryAdapter(countryDataList);
        rv.setAdapter(rvAdapter);

        RequestQueue requestQueueRV = Volley.newRequestQueue(getContext());

        String url = "https://disease.sh/v3/covid-19/countries";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    if (response.length() > 0) {
                        countryDataList.clear();
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = response.getJSONObject(i);
                            CountryData cd = new CountryData();
                            if (!jsonObject.isNull("country")){
                                cd.countryName = jsonObject.getString("country");
                            }
                            if (!jsonObject.isNull("cases")) {
                                cd.countryCases = jsonObject.getInt("cases");
                            }
                            if (!jsonObject.isNull("recovered")) {
                                cd.countryRecovered = jsonObject.getInt("recovered");
                            }
                            if (!jsonObject.isNull("deaths")) {
                                cd.countryDeath = jsonObject.getInt("deaths");
                            }
                            countryDataList.add(i, cd);
                        }
                        rvAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // do something
                Log.d("ERROR", error.getLocalizedMessage());
            }
        });

        requestQueueRV.add(jsonArrayRequest);
        return globalRoot;
    }

    public JsonObjectRequest fetchDataForGlobal(){
        String urlForGlobal = "https://disease.sh/v3/covid-19/all";
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlForGlobal, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int globalConfirmed = response.getInt("cases");
                    int globalActive = response.getInt("active");
                    int globalRecovered = response.getInt("recovered");

                    confirmedCasesGlobal.setText(String.valueOf(globalConfirmed));
                    activeCasesGlobal.setText(String.valueOf(globalActive));
                    recoveredCasesGlobal.setText(String.valueOf(globalRecovered));

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("Error Json", e.getLocalizedMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error Response", error.getLocalizedMessage());
            }
        });
        return jsonObjectRequest;
    }
}