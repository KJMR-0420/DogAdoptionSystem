package com.example.dogadoptionsystem.Dashboard;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.dogadoptionsystem.R;
import com.example.dogadoptionsystem.user.UserLogin;

import static com.example.dogadoptionsystem.Dashboard.Dashboard.redirectActivity;

public class ProcessOfDogAdoption extends AppCompatActivity {
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_of_dog_adoption);
        getSupportActionBar().hide();
        drawerLayout = findViewById(R.id.drawer_layout);
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
    public  void ClickUser(View view){
        // Redirect activity to about us
        redirectActivity(this, UserLogin.class);
    }
    public void ClickAboutUs(View view){
        redirectActivity(this, AboutUs.class);
    }
    public void ClickProcess(View view){
        recreate();
    }
    protected void  onPause(){
        super.onPause();
        Dashboard.closeDrawer(drawerLayout);
    }
    public void ClickAdminLogin (View view){
        Admin(this);
    }

    private void Admin(ProcessOfDogAdoption processOfDogAdoption) {
        // initialize alert dialog box
        AlertDialog.Builder builder = new AlertDialog.Builder(processOfDogAdoption);
        //set title
        builder.setTitle("Unauthorized");
        ///set Message
        builder.setMessage("Improper use or mishandling of this section may result to serious penalties and other offense that may lead to legal matters");
        //positive yes button
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(ProcessOfDogAdoption.this, AdminLogin.class));
                finish();
            }
        });
        //negative button
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    public void onBackPressed() {
        super.onBackPressed();
    }
}