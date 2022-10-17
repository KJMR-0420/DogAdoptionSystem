package com.example.dogadoptionsystem.Dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dogadoptionsystem.R;

public class Contactyousoon extends AppCompatActivity {
    Button backtodashboard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactyousoon);
        backtodashboard = findViewById(R.id.gobacktodash);

        backtodashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Contactyousoon.this, Dashboard.class);
                startActivity(intent);
                finish();
            }
        });
    }
}