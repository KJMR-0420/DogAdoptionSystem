package com.example.dogadoptionsystem.Dashboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.dogadoptionsystem.R;
import com.firebase.ui.database.FirebaseListOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.zip.Inflater;


public class doglistfragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    RecyclerView dl_recv;
    doglistAdapter Adapter;
    Toolbar toolbar;
    public doglistfragment() {

    }
    public static doglistfragment newInstance(String param1, String param2) {
        doglistfragment fragment = new doglistfragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doglistfragment,container,false);
        dl_recv = view.findViewById(R.id.dl_rv);
        dl_recv.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Doglist"), model.class)
                        .build();

        Adapter = new doglistAdapter(options);
        dl_recv.setAdapter(Adapter);

        return view;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }
    @Override
    public void onCreateOptionsMenu( Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_menu,menu);
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


    }




    @Override
    public void onStart() {
        super.onStart();
        Adapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        Adapter.stopListening();
    }


    private void processSearch(String s) {

        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Doglist").orderByChild("doglocation").startAt(s).endAt(s+ "\uf8ff"), model.class)
                        .build();

        Adapter = new doglistAdapter(options);
        Adapter.startListening();
        dl_recv.setAdapter(Adapter);


//        FirebaseRecyclerOptions<> senioroption = new FirebaseRecyclerOptions.Builder<seniorhelper>()
//                .setQuery(FirebaseDatabase.getInstance().getReference().child("Senior Citizen Registry").orderByChild("doglpcatopn").startAt(s).endAt(s+"\uf8ff"),seniorhelper.class)
//                .build();
//
//        seniorAdapter = new SeniorAdapter(senioroption);
//        seniorAdapter.startListening();
//        rv_senior .setAdapter(seniorAdapter);
    }
}