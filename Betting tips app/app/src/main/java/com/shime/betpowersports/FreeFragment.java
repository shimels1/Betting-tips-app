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

public class FreeFragment extends Fragment {

    CardView primeSafeTips;
    CardView daily5Odds;
    CardView daily1Odds;
    CardView overUnderTips;
    CardView doubleTips;
    CardView singleGame;
    CardView tennisTips;
    CardView basketball;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_free, container, false);

        primeSafeTips = (CardView)  view.findViewById(R.id.primeSafeTips);
        daily5Odds = (CardView)  view.findViewById(R.id.daily5Odds);
        daily1Odds = (CardView)  view.findViewById(R.id.daily10odds);
        overUnderTips = (CardView)  view.findViewById(R.id.overUnderTips);
        doubleTips = (CardView)  view.findViewById(R.id.doubleTips);
        singleGame = (CardView)  view.findViewById(R.id.singleGame);
        tennisTips = (CardView)  view.findViewById(R.id.tennisTips);
        basketball = (CardView)  view.findViewById(R.id.basketball);

        TextView linkTextView = view.findViewById(R.id.aggrimentTV);
        linkTextView.setText(Html.fromHtml("<b </b>" + "Please Read Our <a href=\"https://apiforapps.com/privacy_policy/\"><span style=color:red>\"Privacy Policy\"</span></a> "));
        linkTextView.setMovementMethod(LinkMovementMethod.getInstance());

        primeSafeTips.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                primeSafeTips.setEnabled(false);

                Intent intent = new Intent(getContext(), FreeMatchResultActivity.class);
                intent.putExtra("category","Prime Safe Tips");
                intent.putExtra("categoryid","primeSafeTips");
                MainActivity activity = (MainActivity) getActivity();
                intent.putExtra("tabPosition",activity.getTabPosition()+"");
                FreeFragment.this.startActivity(intent);
            }
        });

        daily5Odds.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                daily5Odds.setEnabled(false);
                Intent intent = new Intent(getContext(), FreeMatchResultActivity.class);
                intent.putExtra("category","Daily 5O Odds");
                intent.putExtra("categoryid","daily50odds");
                MainActivity activity = (MainActivity) getActivity();
                intent.putExtra("tabPosition",activity.getTabPosition()+""); FreeFragment.this.startActivity(intent);
            }
        });

        daily1Odds.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                daily1Odds.setEnabled(false);
                Intent intent = new Intent(getContext(), FreeMatchResultActivity.class);
                intent.putExtra("category","Daily 10 Odds");
                intent.putExtra("categoryid","daily10odds");
                MainActivity activity = (MainActivity) getActivity();
                intent.putExtra("tabPosition",activity.getTabPosition()+""); FreeFragment.this.startActivity(intent);
            }
        });

        overUnderTips.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                overUnderTips.setEnabled(false);
                Intent intent = new Intent(getContext(), FreeMatchResultActivity.class);
                intent.putExtra("category","Over-Under Tips");
                intent.putExtra("categoryid","overUnderTips");
                MainActivity activity = (MainActivity) getActivity();
                intent.putExtra("tabPosition",activity.getTabPosition()+""); FreeFragment.this.startActivity(intent);
            }
        });

        doubleTips.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                doubleTips.setEnabled(false);
                Intent intent = new Intent(getContext(), FreeMatchResultActivity.class);
                intent.putExtra("category","Double Tips");
                intent.putExtra("categoryid","doubleTips");
                MainActivity activity = (MainActivity) getActivity();
                intent.putExtra("tabPosition",activity.getTabPosition()+""); FreeFragment.this.startActivity(intent);
            }
        });

        singleGame.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                singleGame.setEnabled(false);
                Intent intent = new Intent(getContext(), FreeMatchResultActivity.class);
                intent.putExtra("category","Single Game");
                intent.putExtra("categoryid","singleGame");
                MainActivity activity = (MainActivity) getActivity();
                intent.putExtra("tabPosition",activity.getTabPosition()+"");
                getActivity().startActivity(intent);
            }
        });

        tennisTips.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                tennisTips.setEnabled(false);
                Intent intent = new Intent(getContext(), FreeMatchResultActivity.class);
                intent.putExtra("category","Tennis Tips");
                intent.putExtra("categoryid","tennisTips");
                MainActivity activity = (MainActivity) getActivity();
                intent.putExtra("tabPosition",activity.getTabPosition()+"");
                FreeFragment.this.startActivity(intent);
            }
        });

        basketball.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                basketball.setEnabled(false);
                Intent intent = new Intent(getContext(), FreeMatchResultActivity.class);
                intent.putExtra("category","Basketball");
                intent.putExtra("categoryid","basketball");
                MainActivity activity = (MainActivity) getActivity();
                intent.putExtra("tabPosition",activity.getTabPosition()+""); FreeFragment.this.startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return view;

    }

}