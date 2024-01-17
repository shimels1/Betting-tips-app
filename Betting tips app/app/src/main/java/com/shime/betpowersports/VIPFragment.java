package com.shime.betpowersports;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class VIPFragment extends Fragment {

    CardView eliteVip;
    CardView fixedVip;
    CardView overUnderVip;
    CardView htFtVip;
    CardView correctScoreVIP;
    CardView daily50OddsVip;
    CardView primePlusVip;
    CardView premiumVip;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_v_i_p, container, false);

        eliteVip = (CardView)  view.findViewById(R.id.eliteVip);
        fixedVip = (CardView)  view.findViewById(R.id.fixedVip);
        overUnderVip = (CardView)  view.findViewById(R.id.overUnderVip);
        htFtVip = (CardView)  view.findViewById(R.id.htFtVip);
        correctScoreVIP = (CardView)  view.findViewById(R.id.correctScoreVIP);
        daily50OddsVip = (CardView)  view.findViewById(R.id.daily50OddsVip);
        primePlusVip = (CardView)  view.findViewById(R.id.primePlusVip);
        premiumVip = (CardView)  view.findViewById(R.id.premiumVip);


        TextView linkTextView = view.findViewById(R.id.aggrimentTVVip);
        linkTextView.setText(Html.fromHtml("<b </b>" + "Please Read Our <a href=\"https:///privacy_policy/\"><span style=color:red>\"Privacy Policy\"</span></a> "));
        linkTextView.setMovementMethod(LinkMovementMethod.getInstance());

        eliteVip.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                eliteVip.setEnabled(false);
                
                Intent intent = new Intent(getContext(), VIPMatchResultActivity.class);
                intent.putExtra("category","Elite VIP");
                intent.putExtra("categoryid","eliteVip");
                
                MainActivity activity = (MainActivity) getActivity();
                intent.putExtra("tabPosition",activity.getTabPosition()+"");    VIPFragment.this.startActivity(intent);
            }
        });

        fixedVip.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                fixedVip.setEnabled(false);
                Intent intent = new Intent(getContext(), VIPMatchResultActivity.class);
                intent.putExtra("category","Fixed VIP");
                intent.putExtra("categoryid","fixedVip");
                
                MainActivity activity = (MainActivity) getActivity();
                intent.putExtra("tabPosition",activity.getTabPosition()+"");    VIPFragment.this.startActivity(intent);
            }
        });

        overUnderVip.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                overUnderVip.setEnabled(false);
                Intent intent = new Intent(getContext(), VIPMatchResultActivity.class);
                intent.putExtra("category","Over-Under VIP");
                intent.putExtra("categoryid","overUnderVip");
                
                MainActivity activity = (MainActivity) getActivity();
                intent.putExtra("tabPosition",activity.getTabPosition()+"");    VIPFragment.this.startActivity(intent);
            }
        });

        htFtVip.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                htFtVip.setEnabled(false);
                Intent intent = new Intent(getContext(), VIPMatchResultActivity.class);
                intent.putExtra("category","HT-FT VIP");
                intent.putExtra("categoryid","htFtVip");
                
                MainActivity activity = (MainActivity) getActivity();
                intent.putExtra("tabPosition",activity.getTabPosition()+"");    VIPFragment.this.startActivity(intent);
            }
        });

        correctScoreVIP.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                correctScoreVIP.setEnabled(false);
                Intent intent = new Intent(getContext(), VIPMatchResultActivity.class);
                intent.putExtra("category","Correct Score VIP");
                intent.putExtra("categoryid","correctScoreVIP");
                
                MainActivity activity = (MainActivity) getActivity();
                intent.putExtra("tabPosition",activity.getTabPosition()+"");    VIPFragment.this.startActivity(intent);
            }
        });

        daily50OddsVip.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                daily50OddsVip.setEnabled(false);
                Intent intent = new Intent(getContext(), VIPMatchResultActivity.class);
                intent.putExtra("category","Daily 50+ Odds VIP");
                intent.putExtra("categoryid","daily50OddsVip");
                
                MainActivity activity = (MainActivity) getActivity();
                intent.putExtra("tabPosition",activity.getTabPosition()+"");    VIPFragment.this.startActivity(intent);
            }
        });

        primePlusVip.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                primePlusVip.setEnabled(false);
                Intent intent = new Intent(getContext(), VIPMatchResultActivity.class);
                intent.putExtra("category","Prime Plus VIP");
                intent.putExtra("categoryid","primePlusVip");
                
                MainActivity activity = (MainActivity) getActivity();
                intent.putExtra("tabPosition",activity.getTabPosition()+"");    VIPFragment.this.startActivity(intent);
            }
        });

        premiumVip.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                premiumVip.setEnabled(false);
                Intent intent = new Intent(getContext(), VIPMatchResultActivity.class);
                intent.putExtra("category","Premium VIP");
                intent.putExtra("categoryid","premiumVip");
                
                MainActivity activity = (MainActivity) getActivity();
                intent.putExtra("tabPosition",activity.getTabPosition()+"");
                VIPFragment.this.startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return view;

    }

}