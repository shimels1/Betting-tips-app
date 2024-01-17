package com.shime.betpowersports;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TermAndConditions extends AppCompatActivity {

    private ImageView back_menu;
    private TextView textTV;
    private String tabPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_and_conditions);

        textTV = (TextView) findViewById(R.id.TermAndConditionsTextview);

        textTV.setMovementMethod(LinkMovementMethod.getInstance());
        if (textTV != null) {
//            textTV.setMovementMethod(LinkMovementMethod.getInstance());
        }

        Bundle extras = getIntent().getExtras();
        tabPosition = extras.getString("tabPosition");

        this.back_menu = (ImageView) findViewById(R.id.TermAndConditionsBackButton);

        back_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("tabPosition",tabPosition);
                startActivity(intent);
            }

        });
    }

    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("tabPosition",tabPosition);
        startActivity(intent);
    }
}