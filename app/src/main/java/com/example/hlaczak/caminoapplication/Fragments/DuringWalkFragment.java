package com.example.hlaczak.caminoapplication.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.hlaczak.caminoapplication.DuringWalk.StatisticsActivity;
import com.example.hlaczak.caminoapplication.DuringWalk.WeatherActivity;
import com.example.hlaczak.caminoapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DuringWalkFragment extends Fragment {

    @BindView(R.id.btn_Weather) Button btn_Weather;
    @BindView(R.id.btn_Statistics) Button btn_Statistics;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_during_walk, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.btn_Weather)
    public void onbtn_Weather(){
        Intent intent = new Intent(getActivity(), WeatherActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_Statistics)
    public void onbtn_Statistics(){
        Intent intent = new Intent(getActivity(), StatisticsActivity.class);
        startActivity(intent);
    }

}
