package com.example.dogadoptionsystem.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ViewUtils;
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
import com.example.dogadoptionsystem.Dashboard.AboutUs;
import com.example.dogadoptionsystem.Dashboard.AdminLogin;
import com.example.dogadoptionsystem.Dashboard.AdoptionForm;
import com.example.dogadoptionsystem.Dashboard.Dashboard;
import com.example.dogadoptionsystem.R;
import com.example.dogadoptionsystem.SuperAdmin.SuperAdminDashboard;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.example.dogadoptionsystem.Dashboard.Dashboard.redirectActivity;

public class UserLogin extends AppCompatActivity {
    DrawerLayout drawerLayout;
    EditText UserLogin, UserPass;
    boolean valid = true;
    Button UserCreatebtn,UserLoginbtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    Button UserForm, UserAdopt;
    LinearLayout Toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        getSupportActionBar().hide();
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        UserForm = findViewById(R.id.Form);
        UserAdopt = findViewById(R.id.adopt);
        drawerLayout = findViewById(R.id.drawer_layout);
        UserLogin = findViewById(R.id.UserloginEmail);
        UserPass=findViewById(R.id.UserloginPassword);
        UserCreatebtn = findViewById(R.id.UserCreateAccount);
        UserLoginbtn = findViewById(R.id.UserloginBtn);
        Toolbar = findViewById(R.id.Maintoolbar);

        Toolbar.setVisibility(View.GONE);
        UserCreatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CreateUserAccount.class));
                finish();
            }
        });
        UserLoginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            checkField(UserLogin);
            checkField(UserPass);

            if (valid) {
                fAuth.signInWithEmailAndPassword(UserLogin.getText().toString(),UserPass.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(UserLogin.this, " Log In Successfully", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(getApplicationContext(),UserDashboard.class));
//                            finish();
                       CheckUserAccessLevel(authResult.getUser().getUid());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UserLogin.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
    public void  ClickMenu(View view){
        Dashboard.openDrawer(drawerLayout);
    }
    public void ClickLogo (View view){
        // Close Drawer

        Dashboard.closeDrawer(drawerLayout);
    }
    public void ClickDashboard(View view){
        // Redirect activity to Dashboard
        redirectActivity(this, Dashboard.class);
    }
    public  void ClickAboutUs(View view){
        // Redirect activity to about us
        redirectActivity(this, AboutUs.class);
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
    public void ClickUser(View view) {
        recreate();
    }
    private void CheckUserAccessLevel(String uid) {
        DocumentReference df = fStore.collection("Users").document(uid);
        // Extract data from the document
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG", "onSuccess: "+ documentSnapshot.getData());
                // identify the user access level
                if(documentSnapshot.getString("isUser") != null ){
                    // user is admin
                    startActivity(new Intent(getApplicationContext(), UserDashboard.class));
                    finish();
                }

            }
        });
    }
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            DocumentReference df = FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
            df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.getString("isUser") != null) {
                        startActivity(new Intent(getApplicationContext(),UserDashboard.class));
                        finish();
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getApplicationContext(), UserLogin.class));
                    finish();
                }
            });
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }


}