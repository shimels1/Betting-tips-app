package com.shime.betpowersports;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

public class VIPMatchResultActivity extends AppCompatActivity {


    private TabLayout tableLayout;
    private ImageView toggle_menu;
    private ImageView back_menu;
    private TextView toolbar_title;

    private ProgressBar progressBar;
    private ViewPager viewPager;
    private String catagory ="";
    private  String tabPosition = "";
    //    nav dr
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;
//    private AdView mAdView;
    int counter = 0;


//    private static Timer timer = new Timer();


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vipmatch_result);


        runTheAdmob();

        this.tableLayout = findViewById(R.id.vipmr_tabLayout);
        this.viewPager = findViewById(R.id.vipmr_viewPager);
        this.toggle_menu = (ImageView) findViewById(R.id.vipmr_result_toggle_imageView);
        this.back_menu = (ImageView) findViewById(R.id.vipmr_result_back_imageView);
        this.toolbar_title = (TextView) findViewById(R.id.vipmr_toolbar_title);

        this.progressBar = (ProgressBar) findViewById(R.id.vipmra_progressBar);
        toggle_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        back_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(toggle_menu.getContext(), MainActivity.class);
                intent.putExtra("tabPosition",tabPosition);
                startActivity(intent);
            }

        });

        // tableLayout
        tableLayout.setupWithViewPager(viewPager);

        VPAdabter vpAdabter = new VPAdabter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdabter.addFragment(new VIPResultFragment(), "TODAY TIPS");
        vpAdabter.addFragment(new VIPResultHistoryFragment(), "OLD TIPS");
        viewPager.setAdapter(vpAdabter);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String catagoryName = extras.getString("category");
            String categoryId = extras.getString("categoryid");
            tabPosition = extras.getString("tabPosition");

            catagory = categoryId;
            toolbar_title.setText(catagoryName);
            // drawerLayout
            this.drawerLayout = findViewById(R.id.vip_result_drawer_layout);
            this.navigationView = findViewById(R.id.vipmr_result_nav_view);
            this.drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
            drawerLayout.addDrawerListener(drawerToggle);
            drawerToggle.syncState();
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    if(R.id.menu_home == item.getItemId())
                    {
                        Intent intent = new Intent(drawerLayout.getContext(), MainActivity.class);
                        intent.putExtra("tabPosition",tabPosition);
                        startActivity(intent);

                    }
                    if(R.id.primeSafeTips_nav == item.getItemId())
                    {
                        Intent intent = new Intent(drawerLayout.getContext(), FreeMatchResultActivity.class);
                        intent.putExtra("category","Prime Safe Tips");
                        intent.putExtra("categoryid","primeSafeTips");
                        catagory = "primeSafeTips";
                        startActivity(intent);

                    }
                    if(R.id.daily1Odds_nav == item.getItemId())
                    {
                        Intent intent = new Intent(drawerLayout.getContext(), FreeMatchResultActivity.class);
                        intent.putExtra("category","Daily 10 Odds");
                        intent.putExtra("categoryid","daily10odds");
                        startActivity(intent);
                    }
                    if(R.id.daily5Odds_nav == item.getItemId())
                    {
                        Intent intent = new Intent(drawerLayout.getContext(), FreeMatchResultActivity.class);
                        intent.putExtra("category","Daily 50 Odds");
                        intent.putExtra("categoryid","daily50odds");
                        catagory = "daily50odds";
                        startActivity(intent);
                    }
                    if(R.id.overUnderTips_nav == item.getItemId())
                    {
                        Intent intent = new Intent(drawerLayout.getContext(), FreeMatchResultActivity.class);
                        intent.putExtra("category","Over-Under Tips");
                        intent.putExtra("categoryid","overUnderTips");
                        startActivity(intent);
                    }
                    if(R.id.doubleTips_nav == item.getItemId())
                    {
                        Intent intent = new Intent(drawerLayout.getContext(), FreeMatchResultActivity.class);
                        intent.putExtra("category","Double Tips");
                        intent.putExtra("categoryid","doubleTips");
                        startActivity(intent);
                    }
                    if(R.id.singleGame_nav == item.getItemId())
                    {
                        Intent intent = new Intent(drawerLayout.getContext(), FreeMatchResultActivity.class);
                        intent.putExtra("category","Single Game");
                        intent.putExtra("categoryid","singleGame");
                        startActivity(intent);
                    }
                    if(R.id.bennisTips_nav == item.getItemId())
                    {
                        Intent intent = new Intent(drawerLayout.getContext(), FreeMatchResultActivity.class);
                        intent.putExtra("category","Tennis Tips");
                        intent.putExtra("categoryid","tennisTips");
                        startActivity(intent);
                    }

                    if(R.id.basketball_nav == item.getItemId())
                    {
                        Intent intent = new Intent(drawerLayout.getContext(), FreeMatchResultActivity.class);
                        intent.putExtra("category","Basketball");
                        intent.putExtra("categoryid","basketball");
                        startActivity(intent);
                    } if(R.id.eliteVip_nav == item.getItemId())
                    {
                        Intent intent = new Intent(drawerLayout.getContext(), VIPMatchResultActivity.class);
                        intent.putExtra("category","Elite VIP");
                        intent.putExtra("categoryid","eliteVip");
                        startActivity(intent);
                    }
                    if(R.id.fixedVip_nav == item.getItemId())
                    {
                        Intent intent = new Intent(drawerLayout.getContext(), VIPMatchResultActivity.class);
                        intent.putExtra("category","Fixed VIP");
                        intent.putExtra("categoryid","fixedVip");
                        startActivity(intent);
                    }
                    if(R.id.overUnderVip_nav == item.getItemId())
                    {
                        Intent intent = new Intent(drawerLayout.getContext(), VIPMatchResultActivity.class);
                        intent.putExtra("category","Over-Under VIP");
                        intent.putExtra("categoryid","overUnderVip");
                        startActivity(intent);
                    }
                    if(R.id.htFtVip_nav == item.getItemId())
                    {
                        Intent intent = new Intent(drawerLayout.getContext(), VIPMatchResultActivity.class);
                        intent.putExtra("category","HT-FT VIP");
                        intent.putExtra("categoryid","htFtVip");
                        startActivity(intent);
                    }
                    if(R.id.correctScoreVIP_nav == item.getItemId())
                    {
                        Intent intent = new Intent(drawerLayout.getContext(), VIPMatchResultActivity.class);
                        intent.putExtra("category","Correct Score VIP");
                        intent.putExtra("categoryid","correctScoreVIP");
                        startActivity(intent);
                    }
                    if(R.id.daily50OddsVip_nav == item.getItemId())
                    {
                        Intent intent = new Intent(drawerLayout.getContext(), VIPMatchResultActivity.class);
                        intent.putExtra("category","Daily 50+ Odds VIP");
                        intent.putExtra("categoryid","daily50OddsVip");
                        startActivity(intent);
                    }
                    if(R.id.primePlusVip_nav == item.getItemId())
                    {
                        Intent intent = new Intent(drawerLayout.getContext(), VIPMatchResultActivity.class);
                        intent.putExtra("category","Prime Plus VIP");
                        intent.putExtra("categoryid","primePlusVip");
                        startActivity(intent);
                    }
                    if(R.id.premiumVip_nav == item.getItemId())
                    {
                        Intent intent = new Intent(drawerLayout.getContext(), VIPMatchResultActivity.class);
                        intent.putExtra("category","Premium VIP");
                        intent.putExtra("categoryid","premiumVip");
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
                        } else {
                            drawerLayout.openDrawer(GravityCompat.END);
                        }
                    }
                    return false;
                }
            };
        }
    }
    FragmentTransaction fragmentTransaction;
    //    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.END)){
            drawerLayout.closeDrawer(GravityCompat.END);
        }else{
            Intent intent = new Intent(drawerLayout.getContext(), MainActivity.class);
            intent.putExtra("tabPosition",tabPosition);

//            timer.cancel();
            startActivity(intent);
        }
    }

    String result = "";

    String sdUnitId = null;
    public void runTheAdmob(){
        RequestQueue queue2 = Volley.newRequestQueue(this.getApplicationContext());
        String url2 = "url" ;

        JsonArrayRequest jsonObjectRequest2 = new JsonArrayRequest
                (Request.Method.GET, url2, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        if (response != null) {
                            if (response.length() > 0) {

//                            timer.cancel();
                            try {
                              if(sdUnitId==null)    {
                                  Log.i("s","Sssssss"+response.toString());
                                    sdUnitId=response.getJSONObject(0).getString("banner");
//                                  Toast.makeText(VIPMatchResultActivity.this, ""+sdUnitId, Toast.LENGTH_SHORT).show();
                                    bannerControll(sdUnitId);
                                }
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
//

                            }
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                                Toast.makeText(FreeMatchResultActivity.this, ">> "+error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
        queue2.add(jsonObjectRequest2);
    }
    public void bannerControll(String bid)
    {
        SharedPreferences sp = getSharedPreferences("Counter", MODE_PRIVATE);
        counter = sp.getInt("count", 0);
//        Toast.makeText(this, "s "+counter, Toast.LENGTH_SHORT).show();
        if(counter <=2)
        {
            bannerShow(bid);
        }
        SharedPreferences.Editor editorAdsControl = sp.edit();
        if (sp.getLong("ExpiredDate",-1)>System.currentTimeMillis())
        {
            //Log.
        }else
        {
            editorAdsControl.clear();
            editorAdsControl.apply();
        }
    }

    public void bannerShow(String banner_id)
    {
        LinearLayout layout = findViewById(R.id.VIPadView);
        AdView mAdView = new AdView(this);
        mAdView.setAdSize(AdSize.SMART_BANNER);
        mAdView.setAdUnitId(banner_id);
//        Toast.makeText(this, "a"+banner_id, Toast.LENGTH_SHORT).show();

        AdRequest.Builder adRequestBuilder = new AdRequest.Builder();
        layout.addView(mAdView);
        if (counter <=2)
        {
            mAdView.loadAd(adRequestBuilder.build());
        }
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdOpened() {
                counter++;
                SharedPreferences sp = getSharedPreferences("Counter", MODE_PRIVATE);
                SharedPreferences.Editor editorAdsControl = sp.edit();
                editorAdsControl.putInt("count",counter);
                editorAdsControl.putLong("ExpiredDate",System.currentTimeMillis()+ TimeUnit.MINUTES.toMillis(720));
                editorAdsControl.apply();}
        });

    }


    public String getMyCatagory() {
        return catagory;
    }
    public void OnTheDataLoaded() {
        progressBar.setVisibility(View.GONE);
    }
}