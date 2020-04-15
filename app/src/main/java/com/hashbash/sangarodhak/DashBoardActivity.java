package com.hashbash.sangarodhak;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.hashbash.sangarodhak.Adapters.SlidePageAdapter;
import com.hashbash.sangarodhak.Fragments.FragmentHome;
import com.hashbash.sangarodhak.Fragments.FragmentLocationHistory;
import com.hashbash.sangarodhak.Fragments.FragmentNotice;
import com.hashbash.sangarodhak.Fragments.FragmentProfile;
import com.hashbash.sangarodhak.Fragments.FragmentSupplies;

import java.util.ArrayList;


public class DashBoardActivity extends AppCompatActivity {

    public static ViewPager viewPage;
    public static PagerAdapter adapter;

    BottomNavigationView bottomNavigationView;

    boolean openAttendance, openAnnouncement, doubleTap;

    //ArrayList<Fragment> resultList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);


        openAttendance = getIntent().getBooleanExtra("openAttendance", false);
        openAnnouncement = getIntent().getBooleanExtra("openAnnouncement", false);


        final ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new FragmentNotice());
        fragments.add(new FragmentLocationHistory());
        fragments.add(new FragmentHome());
        fragments.add(new FragmentSupplies());
        fragments.add(new FragmentProfile());

        viewPage = findViewById(R.id.main_fragment_pager);
        bottomNavigationView = findViewById(R.id.bottom_nav);

        adapter = new SlidePageAdapter(getSupportFragmentManager(), fragments);

        viewPage.setAdapter(adapter);

//        if (openAttendance) {
//            viewPage.setCurrentItem(3);
//            bottomNavigationView.setSelectedItemId(R.id.nav_attendance);
//        }
//        else if (openAnnouncement) {
//            viewPage.setCurrentItem(0);
//            bottomNavigationView.setSelectedItemId(R.id.nav_announcements);
//        } else {
//            viewPage.setCurrentItem(2);
//            bottomNavigationView.setSelectedItemId(R.id.nav_home);
//        }


//        viewPage.setOffscreenPageLimit(4);
//
//        viewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                int id = R.id.nav_home;
//                if (position == 0)
//                    id = R.id.nav_announcements;
//                else if (position == 1)
//                    id = R.id.nav_results;
//                else if (position == 2)
//                    id = R.id.nav_home;
//                else if (position == 3)
//                    id = R.id.nav_attendance;
//                else if (position == 4)
//                    id = R.id.nav_profile;
//
//                bottomNavigationView.setSelectedItemId(id);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()) {
//
//                    case R.id.nav_announcements:
//                        viewPage.setCurrentItem(0, false);
//                        item.setChecked(true);
//                        break;
//                    case R.id.nav_results:
//                        viewPage.setCurrentItem(1, false);
//                        item.setChecked(true);
//                        break;
//                    case R.id.nav_home:
//                        viewPage.setCurrentItem(2, false);
//                        item.setChecked(true);
//                        break;
//                    case R.id.nav_attendance:
//                        viewPage.setCurrentItem(3, false);
//                        item.setChecked(true);
//                        break;
//                    case R.id.nav_profile:
//                        viewPage.setCurrentItem(4, false);
//                        item.setChecked(true);
//                        break;
//
//
//                }
//
//
//
//
//
//                return false;
//            }
//        });

    }

    @Override
    public void onBackPressed() {
        if(viewPage.getCurrentItem() != 2){
            viewPage.setCurrentItem(2);
            bottomNavigationView.setSelectedItemId(R.id.nav_home);
        }
        else {
            if (doubleTap)
                super.onBackPressed();
            else {
                doubleTap = true;
                Snackbar.make(findViewById(R.id.snack_bar_holder), "Tap again to exit!", Snackbar.LENGTH_SHORT)
                        .setBackgroundTint(getColor(R.color.colorAccent))
                        .show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleTap = false;
                    }
                }, 1800);
            }
        }
    }


}

