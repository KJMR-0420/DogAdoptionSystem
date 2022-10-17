package com.example.dogadoptionsystem.Dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dogadoptionsystem.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class doglistAdapter extends FirebaseRecyclerAdapter<model,doglistAdapter.myviewholder>
{

    public doglistAdapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull  doglistAdapter.myviewholder holder, int position, model model) {
    holder.dogname.setText(model.getDogname());
    holder.dogbreed.setText(model.getDogbreed());
    holder.doglocation.setText(model.getDoglocation());
    holder.dogbackground.setText(model.getDogbackground());
    Glide.with(holder.dogimage.getContext()).load(model.getPimage()).into(holder.dogimage);

    holder.dogimage.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AppCompatActivity activity = (AppCompatActivity)v.getContext();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,new profilefragment(model.getDogname(),model.getDogbreed(),model.getDoglocation(),model.getDogbackground(),model.getPimage())).addToBackStack(null).commit();
        }
    });
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowdesign,parent,false);

        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder
    {
        ImageView dogimage;
        TextView dogname,dogbreed,doglocation,dogbackground;
        public myviewholder(@NonNull  View itemView) {
            super(itemView);

            dogimage=itemView.findViewById(R.id.dog_image);
            dogname=itemView.findViewById(R.id.dognametext);
            dogbreed=itemView.findViewById(R.id.dogbreedtext);
            doglocation=itemView.findViewById(R.id.doglocationtext);
            dogbackground=itemView.findViewById(R.id.dogbackgroundtext);
        }
    }

}
