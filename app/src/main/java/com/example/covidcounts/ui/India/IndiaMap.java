package com.example.covidcounts.ui.India;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.covidcounts.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.maps.android.data.Feature;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.google.maps.android.data.geojson.GeoJsonPolygonStyle;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IndiaMap extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap map;
    List<String> stateNameKeyList = new ArrayList<>();
    List<Integer> confirmed = new ArrayList<>();
    List<Integer> recovered = new ArrayList<>();
    RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.state_map_layout);

        jsonData();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void jsonData(){
        String url = "https://api.covid19india.org/v4/data.json";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Iterator iterator = response.keys();
                while (iterator.hasNext())
                {
                    String stateNameKey = (String) iterator.next();
                    stateNameKeyList.add(stateNameKey);
                }

                try {
                    for (int i=0;i<stateNameKeyList.size();i++){
                        JSONObject state = response.getJSONObject(stateNameKeyList.get(i));
                        JSONObject total = state.getJSONObject("total");
                        confirmed.add(i, total.getInt("confirmed"));
                        recovered.add(i, total.getInt("recovered"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error Response "+ error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        mRequestQueue = Volley.newRequestQueue(this);
        mRequestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        try {
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_json));

            if (!success) {
                Log.e("TAG", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("TAG", "Can't find style. Error: ", e);
        }
        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(22.561308, 79.720808)));
        try {
            final GeoJsonLayer layer = new GeoJsonLayer(map, R.raw.states, this);
            GeoJsonPolygonStyle polygonStyle = layer.getDefaultPolygonStyle();
            polygonStyle.setStrokeColor(Color.BLACK);
            polygonStyle.setStrokeWidth(6);
            layer.addLayerToMap();

            layer.setOnFeatureClickListener(new GeoJsonLayer.GeoJsonOnFeatureClickListener() {
                @Override
                public void onFeatureClick(final Feature feature) {

                    for (int j=0;j<confirmed.size();j++){
                        if (feature.getProperty("st_nm").equals(stateNameKeyList.get(j))){
                            Toast.makeText(getApplicationContext(), "NAME : " + feature.getId() + "\nConfirmed : " + confirmed.get(j) + "\nRecovered : " + recovered.get(j),Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("ERROR", "GeoJSON file cannot be read");
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("ERROR", "GeoJSON file cannot be converted to Json Object");
        }
    }
}
