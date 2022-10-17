package com.example.dogadoptionsystem.user;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dogadoptionsystem.HelperClass.AdoptionFormHelper;
import com.example.dogadoptionsystem.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class UserAdapter extends FirebaseRecyclerAdapter<AdoptionFormHelper, UserAdapter.UserViewHolder> {


    public UserAdapter(@NonNull  FirebaseRecyclerOptions<AdoptionFormHelper> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull  UserAdapter.UserViewHolder holder, int position, @NonNull  AdoptionFormHelper model) {
        //holder.Adoptionlistlinear.setVisibility(View.GONE);
        holder.dogname.setText(model.getFdogname());
        holder.dogbreed.setText(model.getFdogbreed());
        holder.doglocation.setText(model.getFdogloc());
        holder.dogaddress.setText(model.getFdogaddress());
        holder.dogcontactnum.setText(model.getFdogNum());
        //
        holder.Adoptionlistedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.dogname.getContext())
                      .setContentHolder(new ViewHolder(R.layout.adoptionlistdialogcontent))
                      .setExpanded(true, 700)
                      .create();

                dialogPlus.show();

                View adoptionview = dialogPlus.getHolderView();

                EditText sname = adoptionview.findViewById(R.id.dog_name_edit);
                EditText sbreed = adoptionview.findViewById(R.id.dog_breed_edit);
                EditText sloc = adoptionview.findViewById(R.id.dog_location_edit);
                EditText saddress = adoptionview.findViewById(R.id.dog_address_edit);
                EditText scontact = adoptionview.findViewById(R.id.dog_contact_edit);

                Button supdate = adoptionview.findViewById(R.id.Adoptionlistupdate);


                sname.setText(model.getFdogname());
                sbreed.setText(model.getFdogbreed());
                sloc.setText(model.getFdogloc());
                saddress.setText(model.getFdogaddress());
                scontact.setText(model.getFdogNum());

                dialogPlus.show();

                supdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Object> map = new HashMap<>();

                        map.put("Dogname",sname.getText().toString());
                        map.put("Dogbreed",sbreed.getText().toString());
                        map.put("Doglocation",sloc.getText().toString());
                        map.put("senioraddress",saddress.getText().toString());
                        map.put("seniorcontact",scontact.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Adoption Registry")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        dialogPlus.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                dialogPlus.dismiss();
                            }
                        });
                    }
                });
            }
        });
    holder.Adoptionlistdel.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(holder.dogname.getContext());
            builder.setTitle("Delete Data");
            builder.setMessage("Are you sure you want to delete? it will Delete Permanently");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FirebaseDatabase.getInstance().getReference().child("Senior Citizen Registry")
                            .child(getRef(position).getKey()).removeValue();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();
        }
    });
    }

    @NonNull
    @Override
    public UserAdapter.UserViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adoptionlistlayout,parent, false);

        return new UserViewHolder(view);
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        LinearLayout Adoptionlistlinear;
        ImageView Adoptionlistedit,Adoptionlistdel;
        TextView dogname,dogbreed,doglocation,dogaddress,dogcontactnum;
        public UserViewHolder(View view) {
        super(view);

        Adoptionlistlinear = view.findViewById(R.id.AdoptionListL);
        dogname = view.findViewById(R.id.DogName_txt);
        dogbreed = view.findViewById(R.id.Breed_text);
        doglocation = view.findViewById(R.id.Location_txt);
        dogaddress = view.findViewById(R.id.Address_txt);
        dogcontactnum = view.findViewById(R.id.Number_txt);

        Adoptionlistedit = view.findViewById(R.id.AdoptionlistEdit);
        Adoptionlistdel = view.findViewById(R.id.AdoptionlisteDel);
        }
    }
}
