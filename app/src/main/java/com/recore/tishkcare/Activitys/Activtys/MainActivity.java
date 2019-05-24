package com.recore.tishkcare.Activitys.Activtys;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.recore.tishkcare.Activitys.fragments.AppointmentListFragment;
import com.recore.tishkcare.Activitys.fragments.DoctorListFragment;
import com.recore.tishkcare.Activitys.fragments.ProfileFragment;
import com.recore.tishkcare.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CalligraphyConfig.initDefault(new CalligraphyConfig
                .Builder()
                .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                .build());

        setContentView(R.layout.activity_main);

        Toolbar toolbar =(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getColor(R.color.basePressColor));

        SectionPagerAdapter adapter = new SectionPagerAdapter(getSupportFragmentManager());


        ViewPager pager =(ViewPager)findViewById(R.id.pager);
        TabLayout tabs = (TabLayout)findViewById(R.id.tabs);

        tabs.setupWithViewPager(pager);
        pager.setAdapter(adapter);
        tabs.setTabTextColors(ColorStateList.valueOf(getResources().getColor(R.color.basePressColor)));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }



    //view pager class
    private class SectionPagerAdapter extends FragmentPagerAdapter{


        public SectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {

            switch (i){
                case 0:
                    return new DoctorListFragment();
                case 2:
                    return new AppointmentListFragment();
                case 1:
                    return new ProfileFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return getResources().getText(R.string.doctors_tab);
                case 2:
                    return getResources().getText(R.string.appointment_tab);
                case 1:
                    return getResources().getText(R.string.profile_tab);
            }
            return null;
        }
    }



}
