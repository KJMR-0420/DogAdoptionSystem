package com.example.dogadoptionsystem.Dashboard;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.example.dogadoptionsystem.HelperClass.AdoptionFormHelper;
import com.example.dogadoptionsystem.HelperClass.DogprofileHelper;
import com.example.dogadoptionsystem.R;
import com.example.dogadoptionsystem.SuperAdmin.SuperAdminDashboard;
import com.example.dogadoptionsystem.SuperAdmin.create_admin;
import com.example.dogadoptionsystem.user.UserAdapter;
import com.example.dogadoptionsystem.user.UserLogin;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Dashboard extends AppCompatActivity {
// Initialize variable
    LinearLayout searchL;
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
//        getSupportActionBar().hide();
       getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new doglistfragment()).commit();
        drawerLayout=findViewById(R.id.drawer_layout);
        FirebaseAuth.getInstance().signOut();
    }

    public void ClickMenu (View view){
        // Open drawer
        openDrawer(drawerLayout);

    }
    public void ClickSearch(View view){

    }
    public static void openDrawer(DrawerLayout drawerLayout) {
        // open drawer layout
        drawerLayout.openDrawer(GravityCompat.START);

    }
    public void ClickLogo(View view){
        // Close drawer
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        // close drawer
        //check condition
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            //when drawer open
            // close drawer
            drawerLayout.closeDrawer(GravityCompat.START);
        }

    }

    public void ClickDashboard(View view){
        // Redirect activity to dashboard
        recreate();
    }
    public  void ClickAboutUs(View view){
        // Redirect activity to about us

    redirectActivity(this,AboutUs.class);
}
    public void ClickProcess(View view){
        redirectActivity(this, ProcessOfDogAdoption.class);
    }
    public  void ClickUser(View view){
        // Redirect activity to about user
        redirectActivity(this, UserLogin.class);


    }

    public void ClickAdminLogin (View view){
        Admin(this);
    }

    private void Admin(Dashboard dashboard) {
// initialize alert dialog box
        AlertDialog.Builder builder = new AlertDialog.Builder(dashboard);
        //set title
        builder.setTitle("Unauthorized");
        ///set Message
        builder.setMessage("Improper use or mishandling of this section may result to serious penalties and other offense that may lead to legal matters");
        //positive yes button
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getApplicationContext(), AdminLogin.class));
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


    public static void redirectActivity(Activity activity, Class aClass) {
        // initialize intent
        Intent intent = new Intent(activity,aClass);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        activity.startActivity(intent);

    }
    protected void  onPause(){
        super.onPause();
        //close drawer
        closeDrawer(drawerLayout);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }
}