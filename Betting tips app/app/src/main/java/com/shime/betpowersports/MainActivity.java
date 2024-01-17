package com.shime.betpowersports;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.appupdate.AppUpdateOptions;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.onesignal.Continue;
import com.onesignal.OneSignal;
import com.onesignal.debug.LogLevel;

import org.json.JSONArray;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

     TabLayout tableLayout;
    private ViewPager viewPager;

    private ImageView toggle_menu;
    private FirebaseAnalytics mFirebaseAnalytics;

//    nav dr
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;
    private static final String ONESIGNAL_APP_ID = "d136e4a7-1080-46b6-838c-afe69d7d96c4";


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
//        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
//        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        // Verbose Logging set to help debug issues, remove before releasing your app.
        OneSignal.getDebug().setLogLevel(LogLevel.VERBOSE);

        // OneSignal Initialization
        OneSignal.initWithContext(this, ONESIGNAL_APP_ID);

        // requestPermission will show the native Android notification permission prompt.
        // NOTE: It's recommended to use a OneSignal In-App Message to prompt instead.
        OneSignal.getNotifications().requestPermission(true, Continue.with(r -> {
            if (r.isSuccess()) {
                if (r.getData()) {
                    // `requestPermission` completed successfully and the user has accepted permission
                }
                else {
                    // `requestPermission` completed successfully but the user has rejected permission
                }
            }
            else {
                // `requestPermission` completed unsuccessfully, check `r.getThrowable()` for more info on the failure reason
            }
        }));


//        checkVersion();
        this.tableLayout = findViewById(R.id.tabLayout);
        this.viewPager = findViewById(R.id.viewPager);
        this.toggle_menu =(ImageView) findViewById(R.id.toggle_imageView);

        toggle_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        // tableLayout
        tableLayout.setupWithViewPager(viewPager);

        VPAdabter vpAdabter = new VPAdabter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
//        vpAdabter.addFragment(new FreeFragment(), "Free");
        vpAdabter.addFragment(new VIPFragment(), "VIP");
        viewPager.setAdapter(vpAdabter);

        Bundle extras = getIntent().getExtras();
        String tabPosition="-1";
        if (extras != null) {
            tabPosition = extras.getString("tabPosition");

            if (tabPosition != null) {
            int pos = Integer.parseInt(tabPosition+"");
            tableLayout.getTabAt(pos).select();}
        }
        // Creates instance of the manager.
        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(getApplicationContext());

// Returns an intent object that you use to check for an update.
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

// Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    // For a flexible update, use AppUpdateType.FLEXIBLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                // Request the update.
                updateAlertDialog();
            }

        });





        tableLayout.setupWithViewPager(viewPager);


        // drawerLayout
        this.drawerLayout = findViewById(R.id.drawer_layout);
        this.navigationView = findViewById(R.id.nav_view);
        this.drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                Toast.makeText(MainActivity.this, ""+ tableLayout.getSelectedTabPosition(), Toast.LENGTH_SHORT).show();
                if(R.id.primeSafeTips_nav == item.getItemId())
                {
                    Intent intent = new Intent(drawerLayout.getContext(), FreeMatchResultActivity.class);
                    intent.putExtra("category","Prime Safe Tips");
                    intent.putExtra("categoryid","primeSafeTips");
                    intent.putExtra("tabPosition",tableLayout.getSelectedTabPosition()+"");
                    FreeResultFragment fragobj = new FreeResultFragment();
                    fragobj.setArguments(intent.getExtras());
                    startActivity(intent);
                }
                if(R.id.daily5Odds_nav == item.getItemId())
                {
                    Intent intent = new Intent(drawerLayout.getContext(), FreeMatchResultActivity.class);
                    intent.putExtra("category","Daily 5O Odds");
                    intent.putExtra("categoryid","daily50odds");
                    intent.putExtra("tabPosition",tableLayout.getSelectedTabPosition()+"");
                    startActivity(intent);
                }
                if(R.id.daily1Odds_nav == item.getItemId())
                {
                    Intent intent = new Intent(drawerLayout.getContext(), FreeMatchResultActivity.class);
                    intent.putExtra("category","Daily 10 Odds");
                    intent.putExtra("categoryid","daily10odds");
                    intent.putExtra("tabPosition",tableLayout.getSelectedTabPosition()+"");
                    startActivity(intent);
                }
                if(R.id.overUnderTips_nav == item.getItemId())
                {
                    Intent intent = new Intent(drawerLayout.getContext(), FreeMatchResultActivity.class);
                    intent.putExtra("category","Over-Under Tips");
                    intent.putExtra("categoryid","overUnderTips");

                    intent.putExtra("tabPosition",tableLayout.getSelectedTabPosition()+"");
                    startActivity(intent);
                }
                if(R.id.doubleTips_nav == item.getItemId())
                {
                    Intent intent = new Intent(drawerLayout.getContext(), FreeMatchResultActivity.class);
                    intent.putExtra("category","Double Tips");
                    intent.putExtra("categoryid","doubleTips");
                    
                    intent.putExtra("tabPosition",tableLayout.getSelectedTabPosition()+"");
                    startActivity(intent);
                }
                if(R.id.singleGame_nav == item.getItemId())
                {
                    Intent intent = new Intent(drawerLayout.getContext(), FreeMatchResultActivity.class);
                    intent.putExtra("category","Single Game");
                    intent.putExtra("categoryid","singleGame");
                    intent.putExtra("tabPosition",tableLayout.getSelectedTabPosition()+"");
                    startActivity(intent);
                }
                if(R.id.bennisTips_nav == item.getItemId())
                {
                    Intent intent = new Intent(drawerLayout.getContext(), FreeMatchResultActivity.class);
                    intent.putExtra("category","Tennis Tips");
                    intent.putExtra("categoryid","tennisTips");
                    intent.putExtra("tabPosition",tableLayout.getSelectedTabPosition()+"");
                    startActivity(intent);
                }
                if(R.id.basketball_nav == item.getItemId())
                {
                    Intent intent = new Intent(drawerLayout.getContext(), FreeMatchResultActivity.class);
                    intent.putExtra("category","Basketball");
                    intent.putExtra("categoryid","basketball");
                    intent.putExtra("tabPosition",tableLayout.getSelectedTabPosition()+"");
                    startActivity(intent);
                }
                if(R.id.eliteVip_nav == item.getItemId())
                {
                    Intent intent = new Intent(drawerLayout.getContext(), VIPMatchResultActivity.class);
                    intent.putExtra("category","Elite VIP");
                    intent.putExtra("categoryid","eliteVip");
                    intent.putExtra("tabPosition",tableLayout.getSelectedTabPosition()+"");
                    startActivity(intent);
                }
                if(R.id.fixedVip_nav == item.getItemId())
                {
                    Intent intent = new Intent(drawerLayout.getContext(), VIPMatchResultActivity.class);
                    intent.putExtra("category","Fixed VIP");
                    intent.putExtra("categoryid","fixedVip");
                    intent.putExtra("tabPosition",tableLayout.getSelectedTabPosition()+"");
                    startActivity(intent);
                }
                if(R.id.overUnderVip_nav == item.getItemId())
                {
                    Intent intent = new Intent(drawerLayout.getContext(), VIPMatchResultActivity.class);
                    intent.putExtra("category","Over-Under VIP");
                    intent.putExtra("categoryid","overUnderVip");
                    intent.putExtra("tabPosition",tableLayout.getSelectedTabPosition()+"");
                    startActivity(intent);
                }
                if(R.id.htFtVip_nav == item.getItemId())
                {
                    Intent intent = new Intent(drawerLayout.getContext(), VIPMatchResultActivity.class);
                    intent.putExtra("category","HT-FT VIP");
                    intent.putExtra("categoryid","htFtVip");
                    intent.putExtra("tabPosition",tableLayout.getSelectedTabPosition()+"");
                    startActivity(intent);
                }
                if(R.id.correctScoreVIP_nav == item.getItemId())
                {
                    Intent intent = new Intent(drawerLayout.getContext(), VIPMatchResultActivity.class);
                    intent.putExtra("category","Correct Score VIP");
                    intent.putExtra("categoryid","correctScoreVIP");
                    intent.putExtra("tabPosition",tableLayout.getSelectedTabPosition()+"");
                    startActivity(intent);
                }
                if(R.id.daily50OddsVip_nav == item.getItemId())
                {
                    Intent intent = new Intent(drawerLayout.getContext(), VIPMatchResultActivity.class);
                    intent.putExtra("category","Daily 50+ Odds VIP");
                    intent.putExtra("categoryid","daily50OddsVip");
                    intent.putExtra("tabPosition",tableLayout.getSelectedTabPosition()+"");
                    startActivity(intent);
                }
                if(R.id.primePlusVip_nav == item.getItemId())
                {
                    Intent intent = new Intent(drawerLayout.getContext(), VIPMatchResultActivity.class);
                    intent.putExtra("category","Prime Plus VIP");
                    intent.putExtra("categoryid","primePlusVip");
                    intent.putExtra("tabPosition",tableLayout.getSelectedTabPosition()+"");
                    startActivity(intent);
                }
                if(R.id.premiumVip_nav == item.getItemId())
                {
                    Intent intent = new Intent(drawerLayout.getContext(), VIPMatchResultActivity.class);
                    intent.putExtra("category","Premium VIP");
                    intent.putExtra("categoryid","premiumVip");
                    intent.putExtra("tabPosition",tableLayout.getSelectedTabPosition()+"");
                    startActivity(intent);
                }

                return false;
            }
        });

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.open,
                R.string.close) {
            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                if (item != null && item.getItemId() == android.R.id.home) {
                    if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                        drawerLayout.closeDrawer(GravityCompat.END);
                    }
                    else {
                        drawerLayout.openDrawer(GravityCompat.END);
                    }
                }
                return false;
            }
        };



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.END)){
            drawerLayout.closeDrawer(GravityCompat.END);
        }else{
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        }
    }

    public String getTabPosition() {
        return tableLayout.getSelectedTabPosition()+"";
    }



    private void updateAlertDialog() {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(MainActivity.this).inflate(R.layout.updatedialog, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        alertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                // Prevent dialog close on back press button
                return keyCode == KeyEvent.KEYCODE_BACK;
            }
        });

        final Button cancel_btn = dialogView.findViewById(R.id.cancelBtn);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        final Button update_btn = dialogView.findViewById(R.id.updateBtn);
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.shime.betpowersports"));
                startActivity(intent);
            }
        });
    }



    public void checkVersion(){


            // Instantiate the RequestQueue.
            RequestQueue queue2 = Volley.newRequestQueue(this.getApplicationContext());
            String url2 = "url" ;

            JsonArrayRequest jsonObjectRequest2 = new JsonArrayRequest
                    (Request.Method.GET, url2, null, new Response.Listener<JSONArray>() {

                        @Override
                        public void onResponse(JSONArray response) {
                            if (response != null) {
                                String version="";
                                if(version=="")    {
                                    try {
                                        version=response.getJSONObject(2).getString("version");
                                    } catch (JSONException e) {
                                        throw new RuntimeException(e);
                                    }  if(version.equals("update"))    {
                                    updateAlertDialog();}
                                }
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    });
            queue2.add(jsonObjectRequest2);
        }





}