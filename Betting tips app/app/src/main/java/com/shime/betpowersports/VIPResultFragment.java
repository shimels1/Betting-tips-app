package com.shime.betpowersports;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class VIPResultFragment extends Fragment {
    RecyclerView recyclerView;
    private ResultAdapter adapter;
    private TextView dateTV;
    String category = "";
    List<String> dateLists = new ArrayList<>();
    List<Result> matchs = new ArrayList<>();
    ProgressBar progressBar;
    Timer timer = new Timer();
    VIPMatchResultActivity activity;
    private InterstitialAd mInterstitialAd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_v_i_p_result, container, false);



        recyclerView = view.findViewById(R.id.vipfr_resultRecyclerView2);
        dateTV = view.findViewById(R.id.vipfr_date);
        progressBar = view.findViewById(R.id.vipfr_progressBar);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        adapter = new ResultAdapter(matchs);


        recyclerView.setAdapter(adapter);

         activity = (VIPMatchResultActivity) getActivity();
        category = activity.getMyCatagory();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                getResult();
                Log.i("Ddddddd","dddddddddddddd");
            }
        }, 0, 1000);

        // Inflate the layout for this fragment
        return view;
    }


    public  void getResult(){

        if (true) {

            // Instantiate the RequestQueue.
            RequestQueue queue2 = Volley.newRequestQueue(activity.getApplicationContext());
            String url2 = "url" +category;

            JsonArrayRequest jsonObjectRequest2 = new JsonArrayRequest
                    (Request.Method.GET, url2, null, new Response.Listener<JSONArray>() {

                        @Override
                        public void onResponse(JSONArray response) {
                            if (response != null) {
                                dateLists.clear();
                                adapter.notifyDataSetChanged();
                                for (int i = 0; i < response.length(); i++) {

                                    try {
                                        dateLists.add(response.getJSONObject(i).getString("date"));
                                        if(i == 0){
                                            if (dateLists.size()>0){
                                                getByDateAndCat(dateLists.get(0),category);
                                                timer.cancel();
                                            }
                                            String dateString = dateLists.get(0).toString();
                                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                            Date date;
                                            try {
                                                date = df.parse(dateString);
                                                Timestamp datetime = new Timestamp(date.getTime());
                                                dateTV.setText(datetime.toString().substring(0,10)+"");
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }
                                            timer.cancel();
                                            return;
                                        }
                                    } catch (JSONException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
//                                adapter.notifyDataSetChanged();

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

    public void getByDateAndCat(String date,String catagory) {
//        RequestQueue queue = Volley.newRequestQueue(this);
        RequestQueue MyRequestQueue = Volley.newRequestQueue(getContext());
        String url = "url";
        StringRequest eventoReq = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if (response != null) {
                                matchs.clear();
                                adapter.notifyDataSetChanged();
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
                                        result.setOdd(obj.getString("odd"));
                                        result.setResult(obj.getString("result"));
                                        result.setType(obj.getString("type"));

                                        matchs.add(result);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                adapter.notifyDataSetChanged();

//                                timer.cancel();
                                progressBar.setVisibility(View.GONE);
                                dateTV.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.VISIBLE);
                                activity.OnTheDataLoaded();
                                runTheAdmobInterstitial();
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
                params.put("catagory",catagory);
                return params;
            }
        };
        MyRequestQueue.add(eventoReq);
    }



    String sdUnitIdInterstitial = null;
    public void runTheAdmobInterstitial(){
        RequestQueue queue2 = Volley.newRequestQueue(getContext());
        String url2 = "url" ;

        JsonArrayRequest jsonObjectRequest2 = new JsonArrayRequest
                (Request.Method.GET, url2, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        if (response != null) {
                            if (response.length() > 0) {

//                            timer.cancel();
                                try {
                                    Log.i("D","ddddddddddddddddddddd"+response.getJSONObject(0).getString("Interstitial"));
                                    if(sdUnitIdInterstitial==null)    {
                                        sdUnitIdInterstitial=response.getJSONObject(0).getString("Interstitial");
                                        showInterstitialAd();
//                                        Toast.makeText(VIPMatchResultActivity.this, "d", Toast.LENGTH_SHORT).show();
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
    String TAG="";
    public void showInterstitialAd(){
        if(sdUnitIdInterstitial==null)    {
            runTheAdmobInterstitial();
        }else{
            //        "ca-app-pub-3940256099942544/1033173712"
            AdRequest adRequest = new AdRequest.Builder().build();
            InterstitialAd.load(getContext(),sdUnitIdInterstitial, adRequest,
                    new InterstitialAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                            // The mInterstitialAd reference will be null until
                            // an ad is loaded.
                            mInterstitialAd = interstitialAd;
                            Log.i("Tagbbbbbbb"
                                    , "onAdLoaded");

                            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                                @Override
                                public void onAdClicked() {
                                    // Called when a click is recorded for an ad.
                                    Log.d(TAG, "Ad was clicked.");
                                }

                                @Override
                                public void onAdDismissedFullScreenContent() {
                                    // Called when ad is dismissed.
                                    // Set the ad reference to null so you don't show the ad a second time.
                                    Log.d(TAG, "Ad dismissed fullscreen content.");
                                    mInterstitialAd = null;
                                }

                                @Override
                                public void onAdFailedToShowFullScreenContent(AdError adError) {
                                    // Called when ad fails to show.
                                    Log.e(TAG, "Ad failed to show fullscreen content.");
                                    mInterstitialAd = null;
                                }

                                @Override
                                public void onAdImpression() {
                                    // Called when an impression is recorded for an ad.
                                    Log.d(TAG, "Ad recorded an impression.");
                                }

                                @Override
                                public void onAdShowedFullScreenContent() {
                                    // Called when ad is shown.
                                    Log.d(TAG, "Ad showed fullscreen content.");
                                }
                            });
                            mInterstitialAd.show((Activity) getContext());
                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            // Handle the error
                            Log.d("Tagbbbbbbb", loadAdError.toString());
                            mInterstitialAd = null;
                        }
                    });

        }


    }

    @Override
    public void onPause() {
        super.onPause();
        if(timer != null) timer.cancel();
    }
}