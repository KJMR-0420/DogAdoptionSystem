package com.example.dogadoptionsystem.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.dogadoptionsystem.Dashboard.doglistAdapter;
import com.example.dogadoptionsystem.Dashboard.doglistfragment;
import com.example.dogadoptionsystem.Dashboard.model;
import com.example.dogadoptionsystem.HelperClass.DogprofileHelper;
import com.example.dogadoptionsystem.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class UserDashboard extends AppCompatActivity {

    RecyclerView rv_user;
    doglistAdapter doglistAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
//        rv_user=findViewById(R.id.RV_User);
//        rv_user.setLayoutManager(new LinearLayoutManager(this));
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//
//        FirebaseRecyclerOptions<model> options =
//                new FirebaseRecyclerOptions.Builder<model>()
//                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Doglist"), model.class)
//                        .build();
//
//        doglistAdapter = new doglistAdapter(options);
//        rv_user.setAdapter(doglistAdapter);
//
////        FirebaseRecyclerOptions<DogprofileHelper> doglistoption = new FirebaseRecyclerOptions.Builder<DogprofileHelper>()
////                .setQuery(FirebaseDatabase.getInstance().getReference().child("Senior Citizen Registry"),DogprofileHelper.class)
////                .build();
////
////        doglistAdapter = new doglistAdapter(doglistoption);
////        rv_user.setAdapter(doglistAdapter);
getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new doglistfragment()).commit();
        FirebaseAuth.getInstance().signOut();
    }
    @Override
    protected void onStart() {
        super.onStart();
        doglistAdapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        doglistAdapter.stopListening();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);

        MenuItem item = menu.findItem(R.id.search);

        SearchView searchView= (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                processSearch(s);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                processSearch(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void processSearch(String s) {

        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Doglist").orderByChild("doglocation").startAt(s).endAt(s+ "\uf8ff"), model.class)
                        .build();

        doglistAdapter = new doglistAdapter(options);
        doglistAdapter.startListening();
        rv_user.setAdapter(doglistAdapter);
//
//
////        FirebaseRecyclerOptions<> senioroption = new FirebaseRecyclerOptions.Builder<seniorhelper>()
////                .setQuery(FirebaseDatabase.getInstance().getReference().child("Senior Citizen Registry").orderByChild("doglpcatopn").startAt(s).endAt(s+"\uf8ff"),seniorhelper.class)
////                .build();
////
////        seniorAdapter = new SeniorAdapter(senioroption);
////        seniorAdapter.startListening();
////        rv_senior .setAdapter(seniorAdapter);
    }
}