package com.example.dogadoptionsystem.Dashboard;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dogadoptionsystem.HelperClass.AdoptionFormHelper;
import com.example.dogadoptionsystem.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class AdoptionForm2 extends AppCompatActivity {
    private  static final int PICK_FILE = 1;
    TextView dogname,dogbreed,dogloc,dogaddress,dognum;
    TextView Fffileform;
    Uri Fileuri;
    Button browseform;
    Button cancel,Accept;
    Button ConfirmAdoption;
    FirebaseDatabase root;
    DatabaseReference ref;
    StorageReference storageRef;

    TextView TermsH, TermsC;
    AlertDialog.Builder dialogbuilder;
    AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adoption_form2);

        dogname = findViewById(R.id.txtDog_name);
        dogbreed = findViewById(R.id.txtDog_Breed);
        dogloc = findViewById(R.id.txtDog_Loc);
        dogaddress = findViewById(R.id.txtDog_Address);
        dognum = findViewById(R.id.txtDog_Num);
        Fffileform = findViewById(R.id.FfilledupForm);
        browseform = findViewById(R.id.FfilledupFormbtn);
        ConfirmAdoption =findViewById(R.id.CAdopt2);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        storageRef = FirebaseStorage.getInstance().getReference("Adoption Registry File");
        root = FirebaseDatabase.getInstance();
        ref = root.getReference("Adoption Registry");
        String DogName =sharedPref.getString("key 1","");
        String DogBreed =sharedPref.getString("key 2","");
        String DogLocation =sharedPref.getString("key 3","");
        String DogAddress =sharedPref.getString("key 4","");
        String DogNumber =sharedPref.getString("key 5","");

        dogname.setText(""+DogName);
        dogbreed.setText(""+DogBreed);
        dogloc.setText(""+DogLocation);
        dogaddress.setText(""+DogAddress);
        dognum.setText(""+DogNumber);

        browseform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Formfile();
            }
        });
        ConfirmAdoption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogbuilder = new AlertDialog.Builder(AdoptionForm2.this);
                View termspop = getLayoutInflater().inflate(R.layout.termsandcondition,null);
                TermsH = termspop.findViewById(R.id.terms_header);
                TermsC = termspop.findViewById(R.id.terms_content);
                cancel = termspop.findViewById(R.id.cancel);
                Accept = termspop.findViewById(R.id.accept);

                dialogbuilder.setView(termspop);
                dialog = dialogbuilder.create();
                dialog.show();

                Accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(AdoptionForm2.this, "Adoption Success",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AdoptionForm2.this, Contactyousoon.class);
                        startActivity(intent);
                        finish();
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_FILE){
            Fileuri = data.getData();
            if (resultCode == RESULT_OK) {

                Uri Fileuri = data.getData();
                Fffileform.setText(Fileuri.toString());
                StorageReference Folder = FirebaseStorage.getInstance().getReference("Adoption Registry File");
                StorageReference file_name= Folder.child("file"+Fileuri.getLastPathSegment());

                file_name.putFile(Fileuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        file_name.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                HashMap<String,String> hashMap = new HashMap<>();
                                hashMap.put("fileLink",String.valueOf(uri));
                                ref.push().setValue(hashMap);
                           }
                        });
                    }
                });


            }
        }
    }
    public void Formfile(){
        Intent intent = new Intent();
        intent.setType("application/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_FILE );
    }
    private String getFileExtension(Uri uri) {
        //get the extension of the photo
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }
}