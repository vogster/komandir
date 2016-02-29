package com.korovkin.komandir.activity;


import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.model.LatLng;
import com.korovkin.komandir.R;
import com.korovkin.komandir.adapter.TabPageAdapter;

public class MapsActivity extends AppCompatActivity implements ActionBar.TabListener {

    private ViewPager viewPager;
    private Button btnOk;
    private TabPageAdapter mAdapter;
    public Coordinates coordinates;

    public LatLng fromLatLng;
    public LatLng toLatLng;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        coordinates = new Coordinates();

        viewPager = (ViewPager) findViewById(R.id.pager);
        btnOk = (Button) findViewById(R.id.btnOk);
        String[] tabs = { getString(R.string.from), getString(R.string.to) };

        mAdapter = new TabPageAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);

        getSupportActionBar().setNavigationMode(getSupportActionBar().NAVIGATION_MODE_TABS);

        // Adding Tabs
        for (String tab_name : tabs) {
            getSupportActionBar().addTab(getSupportActionBar().newTab().setText(tab_name)
                    .setTabListener(this));
        }

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                getSupportActionBar().setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, ResultActivity.class);
                intent.putExtra(ResultActivity.KEY_FROMLATLNG, fromLatLng);
                intent.putExtra(ResultActivity.KEY_TOLATLNG, toLatLng);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

    }

    public Coordinates getCoordinates(){
        return coordinates;
    }

    static class Coordinates{
        private LatLng fromLatLng;
        private LatLng toLatLng;

        public void setFromLatLng(LatLng fromLatLng) {
            this.fromLatLng = fromLatLng;
        }

        public LatLng getFromLatLng() {
            return fromLatLng;
        }

        public void setToLatLng(LatLng toLatLng) {
            this.toLatLng = toLatLng;
        }

        public LatLng getToLatLng() {
            return toLatLng;
        }
    }
}
