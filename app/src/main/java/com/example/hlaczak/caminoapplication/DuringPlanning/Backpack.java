package com.example.hlaczak.caminoapplication.DuringPlanning;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import com.example.hlaczak.caminoapplication.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Backpack extends AppCompatActivity {
    private final static String TAG_1 = "First";
    private final static String TAG_2 = "Second";
    private final static String TAG_3 = "Third";
    private final static String TAG_4 = "Fourth";

    @BindView(R.id.btn_First) Button btn_First;
    @BindView(R.id.btn_Second) Button btn_Second;
    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backpack);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Title of Activity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @OnClick(R.id.btn_First)
    public void onbtn_First(){
        Intent intent = new Intent(Backpack.this, OneCategoryBackpack.class);
        intent.putExtra("Category", TAG_1);
        startActivity(intent);
    }

    @OnClick(R.id.btn_Second)
    public void onbtn_Second(){
        Intent intent = new Intent(Backpack.this, OneCategoryBackpack.class);
        intent.putExtra("Category", TAG_2);
        startActivity(intent);
    }
}
