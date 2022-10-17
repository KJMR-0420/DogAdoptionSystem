package com.example.dogadoptionsystem.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dogadoptionsystem.Dashboard.AdminLogin;
import com.example.dogadoptionsystem.Dashboard.AdoptionForm2;
import com.example.dogadoptionsystem.Dashboard.Contactyousoon;
import com.example.dogadoptionsystem.Dashboard.CreateDogProfile;
import com.example.dogadoptionsystem.R;
import com.example.dogadoptionsystem.SuperAdmin.create_admin;
import com.example.dogadoptionsystem.user.UserAdoptionList;
import com.google.firebase.auth.FirebaseAuth;

public class AdminDashboard extends AppCompatActivity {
    CardView logout,R_create_dogProf,R_Adoption_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        getSupportActionBar().hide();
        logout = findViewById(R.id.R_Admin_Logout);
        R_create_dogProf = findViewById(R.id.R_create_dogProf);
        R_Adoption_list = findViewById(R.id.R_Adoption_list);

        R_create_dogProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashboard.this, CreateDogProfile.class);
                startActivity(intent);
                finish();
            }
        });
        R_Adoption_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashboard.this, UserAdoptionList.class);
                startActivity(intent);
                finish();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), AdminLogin.class));
                finish();
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }
}