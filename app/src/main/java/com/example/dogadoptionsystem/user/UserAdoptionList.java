package com.example.dogadoptionsystem.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.example.dogadoptionsystem.HelperClass.AdoptionFormHelper;
import com.example.dogadoptionsystem.R;
import com.example.dogadoptionsystem.SuperAdmin.SuperAdminDashboard;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class UserAdoptionList extends AppCompatActivity {

    RecyclerView rv_adoptionlist;
    UserAdapter userAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_adoption_list);

        rv_adoptionlist=findViewById(R.id.RV_Adoptionlist);
        rv_adoptionlist.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<AdoptionFormHelper> useroption = new FirebaseRecyclerOptions.Builder<AdoptionFormHelper>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Adoption Registry"),AdoptionFormHelper.class)
                .build();

        userAdapter = new UserAdapter(useroption);
        rv_adoptionlist.setAdapter(userAdapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        userAdapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        userAdapter.stopListening();
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
        FirebaseRecyclerOptions<AdoptionFormHelper> useroption = new FirebaseRecyclerOptions.Builder<AdoptionFormHelper>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Adoption Registry").orderByChild("fdogloc").startAt(s).endAt(s+"\uf8ff"),AdoptionFormHelper.class)
                .build();

        userAdapter = new UserAdapter(useroption);
        userAdapter.startListening();
        rv_adoptionlist .setAdapter(userAdapter);
}
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), SuperAdminDashboard.class));
    }
}