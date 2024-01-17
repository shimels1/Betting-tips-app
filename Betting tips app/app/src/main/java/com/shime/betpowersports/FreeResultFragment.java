package com.shime.betpowersports;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

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


public class FreeResultFragment extends Fragment {
    RecyclerView recyclerView;
    private ResultAdapter adapter;
    private TextView dateTV;
    private ProgressBar progressBar;
    private TextView contactlTV;
    String category = "";
    List<String> dateLists = new ArrayList<>();
    List<Result> matchs = new ArrayList<>();
    Timer timer = new Timer();
    FreeMatchResultActivity activity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_free_result, container, false);

        recyclerView = view.findViewById(R.id.ffr_resultRecyclerView2);
        dateTV = view.findViewById(R.id.ffr_date);
        contactlTV = view.findViewById(R.id.ffr_contact);
        progressBar = view.findViewById(R.id.ffr_progressBar);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter = new ResultAdapter(matchs);
        recyclerView.setAdapter(adapter);

        activity = (FreeMatchResultActivity) getActivity();
        category = activity.getMyCatagory();
       timer.scheduleAtFixedRate(new TimerTask() {
           @Override
          public void run() {
                getResult();
          }
        }, 0, 1000);

        // Inflate the layout for this fragment
        return view;
    }

    public  void getResult(){

        if (true) {

            // Instantiate the RequestQueue.
            RequestQueue queue2 = Volley.newRequestQueue(activity.getApplicationContext());
            String url2 = "url" +category ;

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
                                            getByDateAndCat(dateLists.get(0),category);
                                            timer.cancel();

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

        RequestQueue MyRequestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        String url = "url";
        StringRequest eventoReq = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            if (response != null) {
                                JSONArray j = new JSONArray(response);
                                matchs.clear();
                                adapter.notifyDataSetChanged();
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
                                progressBar.setVisibility(View.GONE);
                                dateTV.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.VISIBLE);
//                                timer.cancel();
                                activity.OnTheDataLoaded();
                            }else{

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
    @Override
    public void onDestroyView() {
        timer.cancel();
        super.onDestroyView();
    }
    @Override
    public void onDetach() {
        timer.cancel();
        super.onDetach();
    }


}