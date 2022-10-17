package com.example.dogadoptionsystem.Dashboard;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.dogadoptionsystem.MainActivity;
import com.example.dogadoptionsystem.R;
import com.example.dogadoptionsystem.user.UserLogin;

import static com.example.dogadoptionsystem.Dashboard.Dashboard.redirectActivity;

public class AboutUs extends AppCompatActivity {
DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
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
    public void ClickProcess(View view){
        redirectActivity(this, ProcessOfDogAdoption.class);
    }
    public void ClickAboutUs(View view){
        recreate();
    }
    protected void  onPause(){
        super.onPause();
        Dashboard.closeDrawer(drawerLayout);
    }
    public void ClickAdminLogin (View view){
        Admin(this);
}

    private void Admin(AboutUs AboutUs) {
// initialize alert dialog box
        AlertDialog.Builder builder = new AlertDialog.Builder(AboutUs);
    //set title
        builder.setTitle("Unauthorized");
                ///set Message
        builder.setMessage("Improper use or mishandling of this section may result to serious penalties and other offense that may lead to legal matters");
        //positive yes button
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(AboutUs.this, AdminLogin.class));
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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}