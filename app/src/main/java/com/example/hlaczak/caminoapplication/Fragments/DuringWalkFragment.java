package com.example.hlaczak.caminoapplication.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.hlaczak.caminoapplication.DuringWalk.WeatherActivity;
import com.example.hlaczak.caminoapplication.LoginActivity;
import com.example.hlaczak.caminoapplication.R;
import com.example.hlaczak.caminoapplication.WelcomeActivity;

public class DuringWalkFragment extends Fragment {
    Button btn_Weather;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_during_walk, container, false);

        btn_Weather = view.findViewById(R.id.btn_Weather);
        btn_Weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WeatherActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        return view;
    }

}
