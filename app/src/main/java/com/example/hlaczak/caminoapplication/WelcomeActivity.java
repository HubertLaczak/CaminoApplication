package com.example.hlaczak.caminoapplication;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

import com.example.hlaczak.caminoapplication.Fragments.DuringWalkFragment;
import com.example.hlaczak.caminoapplication.Fragments.PlanningFragment;
import com.example.hlaczak.caminoapplication.Fragments.ViewPagerAdapter;


public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.view_pager);

        ViewPagerAdapter viewPagerAdapter  = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new DuringWalkFragment(), "First");
        viewPagerAdapter.addFragments(new PlanningFragment(), "Second");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}
