package com.example.dogadoptionsystem.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dogadoptionsystem.Admin.AdminDashboard;
import com.example.dogadoptionsystem.Dashboard.AdminLogin;
import com.example.dogadoptionsystem.R;
import com.example.dogadoptionsystem.SuperAdmin.create_admin;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;
import java.util.Map;

public class CreateUserAccount extends AppCompatActivity {
EditText UserRegFullname,UserRegEmail,UserRegPassword,UserRegPhone;
Button UserReg,GotoUserLogin;
boolean valid = true;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user_account);
        getSupportActionBar().hide();
        fAuth = FirebaseAuth.getInstance();
        fStore =FirebaseFirestore.getInstance();

        UserRegFullname = findViewById(R.id.UserregisterName);
        UserRegEmail = findViewById(R.id.UserregisterEmail);
        UserRegPassword = findViewById(R.id.UserregisterPassword);
        UserRegPhone = findViewById(R.id.UserregisterPhone);

        UserReg = findViewById(R.id.UserregisterBtn);
        GotoUserLogin = findViewById(R.id.UsergotoLogin);
        GotoUserLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UserLogin.class));
                finish();
            }
        });

        UserReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkField(UserRegFullname);
                checkField(UserRegEmail);
                checkField(UserRegPassword);
                checkField(UserRegPhone);

                if(valid){
                    fAuth.createUserWithEmailAndPassword(UserRegEmail.getText().toString(),UserRegPassword.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser user = fAuth.getCurrentUser();
                            Toast.makeText(CreateUserAccount.this, "Account Created ", Toast.LENGTH_SHORT).show();
                            DocumentReference df = fStore.collection("Users").document(user.getUid());
                            Map<String,Object> userInfo = new HashMap<>();
                            userInfo.put("UserFullname", UserRegFullname.getText().toString());
                            userInfo.put("UserEmail", UserRegEmail.getText().toString());
                            userInfo.put("UserPassword",UserRegPassword.getText().toString());
                            userInfo.put("UserPhoneNumber",UserRegPhone.getText().toString());
                            userInfo.put("isUser","1");
                            df.set(userInfo);
                            startActivity(new Intent(getApplicationContext(), UserLogin.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull  Exception e) {
                            Toast.makeText(CreateUserAccount.this,"Failed to Create Account",Toast.LENGTH_SHORT).show();
                        }
                    });

                }
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
        super.onBackPressed();
        moveTaskToBack(true);
        finish();
    }
}