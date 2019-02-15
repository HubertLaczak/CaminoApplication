package com.example.hlaczak.caminoapplication.DuringWalk;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.hlaczak.caminoapplication.Others.VolleySingleton;
import com.example.hlaczak.caminoapplication.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WeatherActivity extends AppCompatActivity {

    public static String API_KEY = "32830dacb0424637f399d1b37cfe05b4";
    @BindView(R.id.tv_City) TextView tv_City;
    @BindView(R.id.tv_Temperature) TextView tv_Temperature;
    @BindView(R.id.tv_Description) TextView tv_Description;
    @BindView(R.id.tv_WindSpeed) TextView tv_WindSpeed;
    @BindView(R.id.tv_SunSet) TextView tv_SunSet;
    @BindView(R.id.tv_SunRise) TextView tv_SunRise;

    @BindView(R.id.btn_Get_Weather) Button btn_Get_Weather;
    @BindView(R.id.btn_GetCoords) Button btn_GetCoords;
    @BindView(R.id.toolbar) Toolbar toolbar;

    Context mContext;
    private FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        mContext = getApplicationContext();
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.weatherActivityTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        client = LocationServices.getFusedLocationProviderClient(getApplicationContext());
    }

    @OnClick(R.id.btn_GetCoords)
    public void setBtn_GetCoords(){
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        client.getLastLocation().addOnSuccessListener(WeatherActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location o) {
                if(o != null){
                    tv_Temperature.setText(o.toString());
                }
            }
        });
    }

    @OnClick (R.id.btn_Get_Weather)
    public void checkCurrentWeather() {
        String url = "http://api.openweathermap.org/data/2.5/weather?q=";
        String city = "Mielec";
        String URI = url + city + "&appid=" + API_KEY;

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, URI,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject main_object = null;
                        try {
                            main_object = response.getJSONObject("main");
                            JSONArray array = response.getJSONArray("weather");
                            JSONObject object = array.getJSONObject(0);
                            String temp = String.valueOf(Math.round(main_object.getDouble("temp") - 273));  //Kelvin to Celsius
                            String description = object.getString("description");
                            String city = response.getString("name");

                            main_object = response.getJSONObject("wind");
                            String speed = main_object.getString("speed");

                            main_object = response.getJSONObject("sys");
                            Long sunRise = main_object.getLong("sunrise");
                            Long sunSet = main_object.getLong("sunset");

                            Date date = new java.util.Date(sunRise*1000L);
                            SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm:ss");
                            String sunRiseString = sdf.format(date);

                            date = new java.util.Date(sunSet*1000L);
                            sdf = new java.text.SimpleDateFormat("HH:mm:ss");
                            String sunSetString = sdf.format(date);

                            tv_City.setText(city);
                            tv_Description.setText(description);
                            tv_Temperature.setText(temp + " °C");
                            tv_WindSpeed.setText(speed + " m/s");
                            tv_SunRise.setText("Wschód słońca: " + sunRiseString);
                            tv_SunSet.setText("Zachód słońca: " + sunSetString);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                viewResponse.setText(error.toString());
            }
        });
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }
}
