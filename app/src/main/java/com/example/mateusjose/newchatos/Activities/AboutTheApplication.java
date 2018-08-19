package com.example.mateusjose.newchatos.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.mateusjose.newchatos.Objects.ProjStrings;
import com.example.mateusjose.newchatos.R;

public class AboutTheApplication extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_the_application);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // set the text about the application and the application's title
        TextView tvTitle = (TextView)findViewById(R.id.tv_title);
        tvTitle.setText(ProjStrings.applicationName);
        TextView tvAbout = (TextView)findViewById(R.id.tv_about);
        tvAbout.setText(ProjStrings.aboutTheApplication);
    }

}
