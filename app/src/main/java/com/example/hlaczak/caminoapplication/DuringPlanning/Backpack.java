package com.example.hlaczak.caminoapplication.DuringPlanning;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import com.example.hlaczak.caminoapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Backpack extends AppCompatActivity {
    @BindView(R.id.btn_First) Button btn_First;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backpack);
        ButterKnife.bind(this);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Title of Activity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @OnClick(R.id.btn_First)
    public void onbtn_First(){
        Intent intent = new Intent(Backpack.this, OneCategoryBackpack.class);
        intent.putExtra("Category", "First");
        startActivity(intent);
    }
}
