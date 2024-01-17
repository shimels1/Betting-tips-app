package com.shime.betpowersports;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class VIPResultHistoryFragment extends Fragment {
    RecyclerView recyclerView;
    private ResultAdapter adapter;

    private int selectedDateIndex = 0;
    private  String catagory = "";
    private TextView dateTV;
    private LinearLayout buttonContenerLL;
    private ProgressBar progressBar;
    private Button next;
    private Button prv;
    List<String> dateLists = new ArrayList<>();
    List<Result> matchs = new ArrayList<>();
    Timer timer = new Timer("jj");

    private InterstitialAd mInterstitialAd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_v_i_p_result_history, container, false);

        this.next = (Button) view.findViewById(R.id.vipfrh_next);
        this.prv = (Button) view.findViewById(R.id.vipfrh_prv);
        next.setBackgroundColor(next.getContext().getResources().getColor(R.color.black));
        prv.setBackgroundColor(next.getContext().getResources().getColor(R.color.black));
        dateTV = view.findViewById(R.id.vipfr_date);
        progressBar = view.findViewById(R.id.vipfrh_progressBar);
        buttonContenerLL = view.findViewById(R.id.vipfrh_bottonContener);

        recyclerView = view.findViewById(R.id.vipfrh_resultRecyclerView2);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        adapter = new ResultAdapter(matchs);

        runTheAdmob();
        recyclerView.setAdapter(adapter);

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                getResult();
                Log.i("dddd","ddddddddddddddddddddddddddddddddddd");
            }
        }, 0, 1000);

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


        VIPMatchResultActivity activity = (VIPMatchResultActivity) getActivity();
        catagory = activity.getMyCatagory();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                getResult();
            }
        }, 0, 1000);

        // Inflate the layout for this fragment
        return view;
    }


    public boolean getResult(){

        if (true) {
            dateLists.clear();
            // Instantiate the RequestQueue.
            RequestQueue queue2 = Volley.newRequestQueue(getContext());
            String url2 = "url"+catagory;

            JsonArrayRequest jsonObjectRequest2 = new JsonArrayRequest
                    (Request.Method.GET, url2, null, new Response.Listener<JSONArray>() {

                        @Override
                        public void onResponse(JSONArray response) {
                            if (response != null) {
                                dateLists.clear();
                                for (int i = 0; i < response.length(); i++) {

                                    try {
                                        if(i != 0){
                                            dateLists.add(response.getJSONObject(i).getString("date"));
                                            String dateString = dateLists.get(0).toString();
                                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                            java.util.Date date;
                                            try {
                                                date = df.parse(dateString);
                                                Timestamp datetime = new Timestamp(date.getTime());
                                                dateTV.setText(datetime.toString().substring(0,10)+"");
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        if(dateLists.size() == 1){
                                            getMatchByDate(dateLists.get(0),catagory);
                                            timer.cancel();
                                        }
                                    } catch (JSONException e) {
                                        throw new RuntimeException(e);
                                    }
                               }

                                prv.setBackgroundColor(next.getContext().getResources().getColor(R.color.black));
                                if(response.length()>1){
                                    next.setBackgroundColor(next.getContext().getResources().getColor(R.color.md_grey_700));
                                }

//                                adapter.notifyDataSetChanged();
                                timer.cancel();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {}
                    });
            queue2.add(jsonObjectRequest2);
        }


        return false;
    }
    public void getMatchByDate(String date,String category) {

        RequestQueue MyRequestQueue = Volley.newRequestQueue(getContext());

        String url = "url";
        StringRequest eventoReq = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if (response != null) {
                                matchs.clear();
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

                                next.setEnabled(true);
                                prv.setEnabled(true);
                                next.setBackgroundColor(next.getContext().getResources().getColor(R.color.md_grey_700));
                                if(selectedDateIndex  == 0){
                                    prv.setBackgroundColor(next.getContext().getResources().getColor(R.color.black));

                                    prv.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                                } else if (selectedDateIndex >=1) {
                                    prv.setBackgroundColor(next.getContext().getResources().getColor(R.color.md_grey_700));

                                    prv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_arrow_back_ios_24, 0, 0, 0);
                                }

                                if (selectedDateIndex == dateLists.size() - 1) {
                                    next.setBackgroundColor(next.getContext().getResources().getColor(R.color.black));
                                    next.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

                                }else  if (dateLists.size() > 1) {
                                    next.setBackgroundColor(next.getContext().getResources().getColor(R.color.md_grey_700));
                                    next.setCompoundDrawablesWithIntrinsicBounds(0 ,0, R.drawable.baseline_navigate_next_24, 0);

                                }
                                progressBar.setVisibility(View.GONE);
                                buttonContenerLL.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.VISIBLE);
                                dateTV.setVisibility(View.VISIBLE);
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
                params.put("catagory", catagory);
                return params;
            }
        };

        MyRequestQueue.add(eventoReq);


    }
    public void previous() {

        prv.setEnabled(false);
        if (selectedDateIndex == 0 || dateLists.size() == 0) {
            prv.setBackgroundColor(prv.getContext().getResources().getColor(R.color.black));
            prv.setEnabled(true);
            return;
        }else{
            selectedDateIndex = selectedDateIndex - 1;
            String currentDate = dateLists.get(selectedDateIndex);

            String dateString = dateLists.get(selectedDateIndex).toString();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date;
            try {
                date = df.parse(dateString);
                Timestamp datetime = new Timestamp(date.getTime());
                dateTV.setText(datetime.toString().substring(0,10)+"");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            this.getMatchByDate(currentDate, catagory);
        }

    }
    int nextClickCounter = 0;


    public void next(){
        nextClickCounter ++;
        if (nextClickCounter == 2){
            nextClickCounter = 0;

//            showInterstitialAd();
        }
        next.setEnabled(false);
        if (selectedDateIndex == dateLists.size() - 1 || dateLists.size() == 0) {
            next.setBackgroundColor(next.getContext().getResources().getColor(R.color.black));
            next.setEnabled(true);
            return;
        }

        selectedDateIndex = selectedDateIndex + 1;

        String dateString = dateLists.get(selectedDateIndex).toString();


        String currentDate = dateLists.get(selectedDateIndex);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date;
        try {
            date = df.parse(dateString);
            Timestamp datetime = new Timestamp(date.getTime());
            dateTV.setText(datetime.toString().substring(0,10)+"");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.getMatchByDate(currentDate, catagory);

    }


    String sdUnitId = null;
    public void runTheAdmob(){
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
                               if(sdUnitId==null)    {
                                        sdUnitId=response.getJSONObject(0).getString("Interstitial");

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
        if(sdUnitId==null)    {
            runTheAdmob();
        }else{
            //        "ca-app-pub-3940256099942544/1033173712"
            AdRequest adRequest = new AdRequest.Builder().build();
            InterstitialAd.load(getContext(),sdUnitId, adRequest,
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
                            mInterstitialAd.show(getActivity());
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
    public void onStop() {
        super.onStop();
        if (timer != null) timer.cancel();
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(timer != null) timer.cancel();
    }
}