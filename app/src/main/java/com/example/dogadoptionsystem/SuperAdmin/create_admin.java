package com.example.dogadoptionsystem.SuperAdmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dogadoptionsystem.Admin.AdminDashboard;
import com.example.dogadoptionsystem.Dashboard.AdminLogin;
import com.example.dogadoptionsystem.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class create_admin extends AppCompatActivity {
    EditText fullName,email,password,phone;
    Button registerBtn,goToLogin;
    CheckBox isRegularAdmin,isSuperAdmin;
    boolean valid = true;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_admin);
        getSupportActionBar().hide();
        fAuth = FirebaseAuth.getInstance();
        fStore =FirebaseFirestore.getInstance();

        fullName = findViewById(R.id.registerName);
        email = findViewById(R.id.registerEmail);
        password = findViewById(R.id.registerPassword);
        phone = findViewById(R.id.registerPhone);
        isRegularAdmin = findViewById(R.id.isRegularAdmin);
        isSuperAdmin = findViewById(R.id.isSuperAdmin);
        registerBtn = findViewById(R.id.registerBtn);
        goToLogin = findViewById(R.id.gotoLogin);

        // checkbox logic

        isRegularAdmin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){
                    isSuperAdmin.setChecked(false);
                }
            }
        });

        isSuperAdmin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){
                    isRegularAdmin.setChecked(false);
                }
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkField(fullName);
                checkField(email);
                checkField(password);
                checkField(phone);
                // checkbox validation

                if(! ( isSuperAdmin.isChecked() || isRegularAdmin.isChecked()))
                {
                    Toast.makeText(create_admin.this, "Select Type Account", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(valid){
                    // start registration process

                    fAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser user = fAuth.getCurrentUser();
                            Toast.makeText(create_admin.this, "Account Created ", Toast.LENGTH_SHORT).show();
                            DocumentReference df = fStore.collection("Users").document(user.getUid());
                            Map<String,Object> userInfo = new HashMap<>();
                            userInfo.put("AdminFullname", fullName.getText().toString());
                            userInfo.put("AdminEmail", email.getText().toString());
                            userInfo.put("AdminPassword", password.getText().toString());
                            userInfo.put("AdminPhoneNumber",phone.getText().toString());
                            // Specify if user or admin
                            if(isSuperAdmin.isChecked()){
                                userInfo.put("isSuperAdmin", "1");

                            }
                            if(isRegularAdmin.isChecked()){
                                userInfo.put("isRegularAdmin","1");
                            }

                            //userInfo.put("ïsUser", "1");

                            // Save info to the firestore
                            df.set(userInfo);
                            if(isSuperAdmin.isChecked()){
                                startActivity(new Intent(getApplicationContext(),SuperAdminDashboard.class));
                                finish();
                            }
                            if(isRegularAdmin.isChecked()){
                                startActivity(new Intent(getApplicationContext(), AdminDashboard.class));
                                finish();
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(create_admin.this,"Failed to Create Account",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AdminLogin.class));
            }
        });

    }

    public boolean checkField(EditText textField){
        if(textField.getText().toString().isEmpty()){
            textField.setError("Error");
            valid = false;
        }else {
            valid = true;
        }

        return valid;
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), SuperAdminDashboard.class));
    }
    }
