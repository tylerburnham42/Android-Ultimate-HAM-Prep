package com.tylerburnham42.ultimateHAMPrep;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity {
    private ToggleButton twoMin;
    private ToggleButton oneMin;
    private ToggleButton thritySec;
    private ToggleButton tenSec;
    private ToggleButton orderedOrRandom;
    private boolean ordered = true;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        QuestionHandler.init(this);

        Button btncalcu=(Button)findViewById(R.id.imReadyButton);
        btncalcu.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, QuestionActivity.class);
                startActivity(intent);
            }

        });

        twoMin =(ToggleButton)findViewById(R.id.twoMinButton);
        oneMin =(ToggleButton)findViewById(R.id.oneMinButton);
        thritySec =(ToggleButton)findViewById(R.id.thirtySecButton);
        tenSec =(ToggleButton)findViewById(R.id.tenSecButton);
        twoMin.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                twoMin.setChecked(true);
                oneMin.setChecked(false);
                thritySec.setChecked(false);
                tenSec.setChecked(false);
                QuestionHandler.setTime(120000);
            }
        });
        oneMin.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                twoMin.setChecked(false);
                oneMin.setChecked(true);
                thritySec.setChecked(false);
                tenSec.setChecked(false);
                QuestionHandler.setTime(60000);
            }
        });
        thritySec.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                twoMin.setChecked(false);
                oneMin.setChecked(false);
                thritySec.setChecked(true);
                tenSec.setChecked(false);
                QuestionHandler.setTime(30000);
            }
        });
        tenSec.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                twoMin.setChecked(false);
                oneMin.setChecked(false);
                thritySec.setChecked(false);
                tenSec.setChecked(true);
                QuestionHandler.setTime(10000);
            }
        });

        orderedOrRandom =(ToggleButton)findViewById(R.id.orderedOrRandomButton);
        orderedOrRandom.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                ordered = !orderedOrRandom.isChecked();
                QuestionHandler.setIsRandom(!ordered);
                return;
            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}


