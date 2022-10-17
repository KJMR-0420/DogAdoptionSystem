package com.example.dogadoptionsystem.Dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.dogadoptionsystem.Admin.AdminDashboard;
import com.example.dogadoptionsystem.R;
import com.example.dogadoptionsystem.SuperAdmin.SuperAdminDashboard;
import com.example.dogadoptionsystem.SuperAdmin.create_admin;
import com.example.dogadoptionsystem.user.UserLogin;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AdminLogin extends AppCompatActivity {
    DrawerLayout drawerLayout;
    EditText email, password;
    Button loginBtn, gotoRegister;
    boolean valid = true;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    LinearLayout Toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        getSupportActionBar().hide();
        drawerLayout = findViewById(R.id.drawer_layout);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        email = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPassword);
        loginBtn = findViewById(R.id.loginBtn);
        Toolbar = findViewById(R.id.Maintoolbar);

        //Toolbar.setVisibility(View.GONE);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkField(email);
                checkField(password);

                if (valid) {
                    fAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(AdminLogin.this, " Log In Successfully", Toast.LENGTH_SHORT).show();
                            CheckUserAccessLevel(authResult.getUser().getUid());

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AdminLogin.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
  }

    public void ClickMenu(View view) {
        Dashboard.openDrawer(drawerLayout);
    }

    public void ClickLogo(View view) {
        // Close Drawer

        Dashboard.closeDrawer(drawerLayout);
    }

    public void ClickDashboard(View view) {
        // Redirect activity to Dashboard
        Dashboard.redirectActivity(this, Dashboard.class);
    }

    public void ClickAboutUs(View view) {
        // Redirect activity to Dashboard
        Dashboard.redirectActivity(this, AboutUs.class);
    }
    public void ClickUser(View view) {
        Dashboard.redirectActivity(this, UserLogin.class);
    }
    public void ClickAdminLogin(View view) {
        recreate();
    }

    protected void onPause() {
        super.onPause();
        Dashboard.closeDrawer(drawerLayout);
    }

    private void CheckUserAccessLevel(String uid) {
        DocumentReference df = fStore.collection("Users").document(uid);
        // Extract data from the document
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG", "onSuccess: "+ documentSnapshot.getData());
                // identify the user access level
                if(documentSnapshot.getString("isSuperAdmin") != null ){
                    // user is admin

                    startActivity(new Intent(getApplicationContext(), SuperAdminDashboard.class));
                    finish();
                }
                if(documentSnapshot.getString("isRegularAdmin")!=null){
                    startActivity(new Intent(getApplicationContext(), AdminDashboard.class));
                    finish();
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
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            DocumentReference df = FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
            df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.getString("isSuperAdmin") != null) {
                        startActivity(new Intent(getApplicationContext(), SuperAdminDashboard.class));
                        finish();
                    }
                    if (documentSnapshot.getString("isRegularAdmin") != null) {
                        startActivity(new Intent(getApplicationContext(), AdminDashboard.class));
                        finish();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getApplicationContext(),AdminLogin.class));
                    finish();
                }
            });

            //startActivity(new Intent(getApplicationContext(),MainActivity.class));
            //finish();
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }
}