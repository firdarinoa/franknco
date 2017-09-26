package co.id.franknco.ui.main;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.franknco.R;
import co.id.franknco.adapter.CardAdapter;
import co.id.franknco.fragment.BottomNav1;
import co.id.franknco.fragment.BottomNav2;
import co.id.franknco.fragment.BottomNav3;
import co.id.franknco.ui.faq.FAQActivity;
import co.id.franknco.ui.login.LoginActivity;
import co.id.franknco.ui.nearby.NearbyActivity;
import co.id.franknco.ui.settings.SettingsActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.view_pager)AHBottomNavigationViewPager viewPager;
    @BindView(R.id.bottom_navigation)AHBottomNavigation bottomNavigation;

    private FragmentManager fragmentManager;
    private MainPagerAdapter2 mPagerAdapter;
    private boolean useMenuResource = true;
    private AHBottomNavigationAdapter navigationAdapter;
    private int[] tabColors;
    private ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();

    RecyclerView mRecyclerView;
    CardAdapter cardAdapter;
    ArrayList<String> myList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupToolbar();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);

        ImageView imgHeader =  (ImageView)header.findViewById(R.id.img_header);
        TextView txtName = (TextView)header.findViewById(R.id.txt_header_name);

        //RecyclerView
        myList = new ArrayList<>();
        for(int i = 0;i<20;i++){
            myList.add(i+" ");
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        cardAdapter=new CardAdapter(myList,MainActivity.this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        mRecyclerView.setAdapter(cardAdapter);



        fragmentManager = getFragmentManager();

//        initUI();

        mPagerAdapter = new MainPagerAdapter2(getSupportFragmentManager());
        viewPager.setAdapter(mPagerAdapter);

    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (getSupportActionBar() == null) {
            throw new IllegalStateException("Activity must implement toolbar");
        }
    }

//    private void initUI() {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
//        }
//
//        if (useMenuResource) {
//            tabColors = getApplicationContext().getResources().getIntArray(R.array.tab_colors);
//            navigationAdapter = new AHBottomNavigationAdapter(this, R.menu.bottom_navigation);
//            navigationAdapter.setupWithBottomNavigation(bottomNavigation, tabColors);
//
//            bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FEFEFE"));
//            bottomNavigation.setAccentColor(Color.parseColor("#870040"));
//            bottomNavigation.setInactiveColor(Color.parseColor("#CCCCCC"));
//            bottomNavigation.setForceTint(true);
//            bottomNavigation.setBehaviorTranslationEnabled(false);
//
//        } else {
//            AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab_1, R.drawable.ic_bus, R.color.color_tab_1);
//            AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab_2, R.drawable.ic_calendar, R.color.color_tab_2);
//            AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tab_3, R.drawable.ic_driver2, R.color.color_tab_3);
//
//            bottomNavigationItems.add(item1);
//            bottomNavigationItems.add(item2);
//            bottomNavigationItems.add(item3);
//
//            bottomNavigation.addItems(bottomNavigationItems);
//
//            bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FEFEFE"));
//            bottomNavigation.setAccentColor(Color.parseColor("#870040"));
//            bottomNavigation.setInactiveColor(Color.parseColor("#999999"));
//            bottomNavigation.setForceTint(true);
//            bottomNavigation.setBehaviorTranslationEnabled(false);
//
//        }
//
//        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
//            @Override
//            public boolean onTabSelected(int position, boolean wasSelected) {
//
//                if (position == 0){
//                    BottomNav1 bottomNav1 = new BottomNav1();
//                    Bundle arguments = new Bundle();
//                    //                    arguments.putString("Id", txtId);
//                    bottomNav1.setArguments(arguments);
//                    getSupportFragmentManager().beginTransaction()
//                            .setCustomAnimations(R.anim.activityslidein, R.anim.activityslideinout, R.anim.activityslideoutpop, R.anim.activityslideout)
//                            .replace(R.id.root_frame, bottomNav1, "bottomNav1")
//                            .commit();
//
//                }
//                if (position == 1) {
//                    BottomNav2 bottomNav2 = new BottomNav2();
//                    Bundle arguments = new Bundle();
//                    //                    arguments.putString("Id", txtId);
//                    bottomNav2.setArguments(arguments);
//                    getSupportFragmentManager().beginTransaction()
//                            .setCustomAnimations(R.anim.activityslidein, R.anim.activityslideinout, R.anim.activityslideoutpop, R.anim.activityslideout)
//                            .replace(R.id.root_frame, bottomNav2, "bottomNav2")
//                            .commit();
//
//                }
//                if (position == 2){
//                    BottomNav3 bottomNav3 = new BottomNav3();
//                    Bundle arguments = new Bundle();
//                    //                    arguments.putString("Id", txtId);
//                    bottomNav3.setArguments(arguments);
//                    getSupportFragmentManager().beginTransaction()
//                            .setCustomAnimations(R.anim.activityslidein, R.anim.activityslideinout, R.anim.activityslideoutpop, R.anim.activityslideout)
//                            .replace(R.id.root_frame, bottomNav3, "bottomNav3")
//                            .commit();
//
//                } else {
//
//                }
//                return true;
//            }
//        });
//
//    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

//        if (id == R.id.nav_drawer_nearby) {
//            startActivity(new Intent(this, NearbyActivity.class));
//        } else if (id == R.id.nav_drawer_faq) {
//            startActivity(new Intent(this, FAQActivity.class));
//        } else if (id == R.id.nav_drawer_logout) {
//            startActivity(new Intent(this, LoginActivity.class));
//            finish();
//        }   else if (id == R.id.nav_drawer_settings) {
//            startActivity(new Intent(this, SettingsActivity.class));
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    public class addtoarray{
//
//        if (null == ) {
//            currentTasks = new ArrayList<task>();
//        }
//
//        //		load tasks from preference
//        SharedPreferences prefs = getSharedPreferences(, Context.MODE_PRIVATE);
//
//        try {
//            currentTasks = (ArrayList<task>) ObjectSerializer.deserialize(prefs.getString(TASKS, ObjectSerializer.serialize(new ArrayList<task>())));
//
//        } catch (IOException e) {
//
//            e.printStackTrace();
//
//        } catch (ClassNotFoundException e) {
//
//            e.printStackTrace();
//        }
//    }
}
