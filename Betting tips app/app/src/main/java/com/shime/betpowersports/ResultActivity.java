package com.shime.betpowersports;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    private ResultAdapter adapter;
    private ImageView toggle_menu;
    private ImageView back_menu;
    private int selectedDateIndex = 0;

    private Button next;
    private Button prv;
    List<String> dateLists = new ArrayList<>();
    //    nav dr
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;
    List<Result> matchs = new ArrayList<>();



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        this.toggle_menu = (ImageView) findViewById(R.id.result_toggle_imageView);
        this.back_menu = (ImageView) findViewById(R.id.result_back_imageView);

        this.next = (Button) findViewById(R.id.next);
        this.prv = (Button) findViewById(R.id.prv);




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
                startActivity(intent);
            }

        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            next();
            }
        });
        prv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            previous();
            }
        });

        recyclerView = findViewById(R.id.resultRecyclerView);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        adapter = new ResultAdapter(matchs);


        recyclerView.setAdapter(adapter);
//        items.add(new Result("dddd"));
//        items.add(new Result("dddd"));
//        items.add(new Result("dddd"));
//        // Add items to the list
//        adapter = new ResultAdapter(matchs);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String category = extras.getString("category");
//
//            Toast.makeText(ResultActivity.this, category, Toast.LENGTH_SHORT).show();
//            Toast.makeText(ResultActivity.this, "pppp2: ", Toast.LENGTH_SHORT).show();

// Instantiate the RequestQueue.

            // Instantiate the RequestQueue.
            RequestQueue queue2 = Volley.newRequestQueue(this);
            String url2 = "url";

            JsonArrayRequest jsonObjectRequest2 = new JsonArrayRequest
                    (Request.Method.GET, url2, null, new Response.Listener<JSONArray>() {

                        @SuppressLint("SuspiciousIndentation")
                        @Override
                        public void onResponse(JSONArray response) {
                            //atvAnimalName.setText("Response: " + response.toString());

                            if (response != null) {
                                for (int i = 0; i < response.length(); i++) {

                                    try {
                                        dateLists.add(response.getJSONObject(i).getString("date"));
//                                        Toast.makeText(ResultActivity.this, "p: " + dateLists.get(i), Toast.LENGTH_SHORT).show();
                                        if(i == 0)
                                        getMatchByDate(dateLists.get(selectedDateIndex));
                                    } catch (JSONException e) {
                                        throw new RuntimeException(e);
                                    }

  }    }

      }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                             }
                    });
            queue2.add(jsonObjectRequest2);
        }


        // drawerLayout
        this.drawerLayout = findViewById(R.id.result_drawer_layout);
        this.navigationView = findViewById(R.id.result_nav_view);
        this.drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (R.id.menu_home == item.getItemId()) {
                    Intent intent = new Intent(drawerLayout.getContext(), MainActivity.class);
                    intent.putExtra("category", "PrimeSafeTips");
                    startActivity(intent);
                }
                if (R.id.primeSafeTips_nav == item.getItemId()) {
                    Intent intent = new Intent(drawerLayout.getContext(), ResultActivity.class);
                    intent.putExtra("category", "PrimeSafeTips");
                    startActivity(intent);
                }
                if (R.id.daily1Odds_nav == item.getItemId()) {
                    Intent intent = new Intent(drawerLayout.getContext(), ResultActivity.class);
                    intent.putExtra("category", "daily5Odds");
                    startActivity(intent);
                }
                if (R.id.daily5Odds_nav == item.getItemId()) {
                    Intent intent = new Intent(drawerLayout.getContext(), ResultActivity.class);
                    intent.putExtra("category", "daily10Odds");
                    startActivity(intent);
                }
                if (R.id.overUnderTips_nav == item.getItemId()) {
                    Intent intent = new Intent(drawerLayout.getContext(), ResultActivity.class);
                    intent.putExtra("category", "overUnderTips");
                    startActivity(intent);
                }
                if (R.id.doubleTips_nav == item.getItemId()) {
                    Intent intent = new Intent(drawerLayout.getContext(), ResultActivity.class);
                    intent.putExtra("category", "doubleTips");
                    startActivity(intent);
                }
                if (R.id.singleGame_nav == item.getItemId()) {
                    Intent intent = new Intent(drawerLayout.getContext(), ResultActivity.class);
                    intent.putExtra("category", "singleGame");
                    startActivity(intent);
                }
                if (R.id.bennisTips_nav == item.getItemId()) {
                    Intent intent = new Intent(drawerLayout.getContext(), ResultActivity.class);
                    intent.putExtra("category", "bennisTips");
                    startActivity(intent);
                }

                if (R.id.basketball_nav == item.getItemId()) {
                    Intent intent = new Intent(drawerLayout.getContext(), ResultActivity.class);
                    intent.putExtra("category", "basketball");
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


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        }
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        } else {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }



    public void getMatchByDate(String date) {
//        RequestQueue queue = Volley.newRequestQueue(this);
        matchs.clear();
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);

        String url = "url";
        StringRequest eventoReq = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            if (response != null) {
                                JSONArray j = new JSONArray(response);

                                // Parsea json
                                for (int i = 0; i < j.length(); i++) {
                                    try {
                                        JSONObject obj = j.getJSONObject(i);
                                        Result result = new Result();

                                        result.setIdbetting(obj.getString("idbetting"));
                                        result.setDate(obj.getString("date"));
                                        result.setTime(obj.getString("time"));
                                        result.setCatagory(obj.getString("catagory"));
                                        result.setLeague(obj.getString("league"));
                                        result.setClub1(obj.getString("club1"));
                                        result.setClub2(obj.getString("club2"));
                                        result.setClub1_score(obj.getString("club1_score"));
                                        result.setClub2_score(obj.getString("club2_score"));
                                        result.setPrediction(obj.getString("prediction"));
                                        result.setResult(obj.getString("result"));
                                        result.setType(obj.getString("type"));

                                        matchs.add(result);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                                adapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("date", date);
                return params;
            }
        };

        MyRequestQueue.add(eventoReq);
    }
    public void previous() {
        if (selectedDateIndex == 0) {
            Toast.makeText(this, "end p", Toast.LENGTH_SHORT).show();
            return;
        }else{
            selectedDateIndex = selectedDateIndex - 1;
            String currentDate = dateLists.get(selectedDateIndex);

            this.getMatchByDate(currentDate);
        }

    }

    public void next(){
        if (selectedDateIndex == dateLists.size() - 1) {
            Toast.makeText(this, "end n", Toast.LENGTH_SHORT).show();
            return;
        }
        selectedDateIndex = selectedDateIndex + 1;
        String currentDate = dateLists.get(selectedDateIndex);
        this.getMatchByDate(currentDate);

    }

}


