package com.example.dogadoptionsystem.SuperAdmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dogadoptionsystem.Admin.AdminDashboard;
import com.example.dogadoptionsystem.Dashboard.AdminLogin;
import com.example.dogadoptionsystem.Dashboard.CreateDogProfile;
import com.example.dogadoptionsystem.R;
import com.example.dogadoptionsystem.user.UserAdoptionList;
import com.google.firebase.auth.FirebaseAuth;

public class SuperAdminDashboard extends AppCompatActivity {
CardView logout,create_admin,S_create_dogProf,S_Adoption_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_admin_dashboard);
        getSupportActionBar().hide();
        logout = findViewById(R.id.S_Admin_Logout);
        create_admin = findViewById(R.id.S_create_admin);
        S_create_dogProf = findViewById(R.id.S_create_dogProf);
        S_Adoption_list = findViewById(R.id.S_adoption_list);

        S_create_dogProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SuperAdminDashboard.this, CreateDogProfile.class);
                startActivity(intent);
                finish();
            }
        });
        S_Adoption_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SuperAdminDashboard.this, UserAdoptionList.class);
                startActivity(intent);
                finish();
            }
        });
        create_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(SuperAdminDashboard.this, create_admin.class);
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
    }
}