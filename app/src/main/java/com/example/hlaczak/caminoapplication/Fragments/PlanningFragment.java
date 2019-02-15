package com.example.hlaczak.caminoapplication.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.hlaczak.caminoapplication.DuringPlanning.Backpack;
import com.example.hlaczak.caminoapplication.DuringPlanning.YourPlan;
import com.example.hlaczak.caminoapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlanningFragment extends Fragment {

    @BindView(R.id.btn_Backpack) Button btn_Backpack;
    @BindView(R.id.btn_YourPlan) Button btn_YourPlan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_planning, container, false);

        ButterKnife.bind(this, view);
        return view;
    }


    @OnClick(R.id.btn_Backpack)
    public void onbtn_Backpack(){
        Intent intent = new Intent(getActivity(), Backpack.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_YourPlan)
    public void onbtn_YourPlan(){
        Intent intent = new Intent(getActivity(), YourPlan.class);
        startActivity(intent);
    }


}
