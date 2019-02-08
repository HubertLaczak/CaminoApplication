package com.example.hlaczak.caminoapplication.DuringWalk;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.hlaczak.caminoapplication.R;
import com.example.hlaczak.caminoapplication.VolleySingleton;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class WeatherActivity extends AppCompatActivity {

    public static String API_KEY = "32830dacb0424637f399d1b37cfe05b4";
    TextView tv_City, tv_Temperature, tv_Description, tv_WindSpeed, tv_SunSet, tv_SunRise;
    Button btn_Get_Weather, btn_GetCoords;
    Context mContext;

    private FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        mContext = getApplicationContext();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sprawdź Pogodę");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        client = LocationServices.getFusedLocationProviderClient(getApplicationContext());

        tv_City = findViewById(R.id.tv_City);
        tv_Temperature = findViewById(R.id.tv_Temperature);
        tv_Description = findViewById(R.id.tv_Description);
        tv_WindSpeed = findViewById(R.id.tv_WindSpeed);
        tv_SunRise = findViewById(R.id.tv_SunRise);
        tv_SunSet = findViewById(R.id.tv_SunSet);

        btn_Get_Weather = findViewById(R.id.btn_Get_Weather);
        btn_Get_Weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCurrentWeather();
            }
        });

        btn_GetCoords = findViewById(R.id.btn_GetCoords);
        btn_GetCoords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });
    }

    private void checkCurrentWeather() {
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
