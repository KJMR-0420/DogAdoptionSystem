package com.example.dogadoptionsystem.Dashboard;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dogadoptionsystem.MainActivity;
import com.example.dogadoptionsystem.R;
import com.example.dogadoptionsystem.pdfViewer;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class profilefragment extends Fragment {
    public Button btnForms,btnAdopt, btnMed;
    public LinearLayout searchL;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    StorageReference ref;

    String dogname,dogbreed,doglocation,dogbackground,pimage;
    public profilefragment() {
    }
    public profilefragment(String dogname,String dogbreed,String doglocation,String dogbackground,String pimage) {
    this.dogname=dogname;
        this.dogbreed=dogbreed;
        this.doglocation=doglocation;
        this.dogbackground=dogbackground;
        this.pimage=pimage;
    }
   public static profilefragment newInstance(String param1, String param2) {
        profilefragment fragment = new profilefragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profilefragment, container, false);
        btnAdopt = view.findViewById(R.id.adopt);
        btnForms = view.findViewById(R.id.Form);
        btnMed = view.findViewById(R.id.med);
        searchL = view.findViewById(R.id.searchEngineL);
        ImageView imageholder = view.findViewById(R.id.imagegholder);
        TextView dognameholder = view.findViewById(R.id.dognameholder);
        TextView dogbreedholder = view.findViewById(R.id.dogbreedholder);
        TextView doglocationholder = view.findViewById(R.id.doglocationholder);
        TextView dogbackgroundholder = view.findViewById(R.id.dogbackgroundholder);
//        btnForms.setVisibility(View.GONE);
//        btnAdopt.setVisibility(View.GONE);
        dognameholder.setText(dogname);
        dogbreedholder.setText(dogbreed);
        doglocationholder.setText(doglocation);
        dogbackgroundholder.setText(dogbackground);
        Glide.with(getContext()).load(pimage).into(imageholder);

        btnAdopt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AdoptionForm.class));

            }
        });

        btnForms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MainActivity.class));

            }
        });

        btnMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), pdfViewer.class));

            }
        });

        return view;
    }

//    public void download(){
//
//
//        storageReference = firebaseStorage.getInstance().getReference();
//        ref = storageReference.child("Form.pdf");
//
//        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                String url = uri.toString();
//               downloadfile(getContext(),"Form",".pdf",DIRECTORY_DOWNLOADS,url);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull  Exception e) {
//
//            }
//        });
//    }

//    public void downloadfile(Context context, String filename, String fileExtension, String destinationDirectory, String url){
//
//        DownloadManager downloadManager = (DownloadManager)context.
//                getSystemService(Context.DOWNLOAD_SERVICE);
//
//        Uri uri = Uri.parse(url);
//        DownloadManager.Request request = new DownloadManager.Request(uri);
//        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//        request.setDestinationInExternalFilesDir(context, destinationDirectory, filename + fileExtension);
//        downloadManager.enqueue(request);
//    }



    public void onBackPressed(){
        AppCompatActivity activity = (AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,new doglistfragment()).addToBackStack(null).commit();

    }

}