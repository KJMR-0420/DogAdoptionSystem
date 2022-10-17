package com.example.dogadoptionsystem.Dashboard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.CaseMap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dogadoptionsystem.HelperClass.AdoptionFormHelper;
import com.example.dogadoptionsystem.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class AdoptionForm extends AppCompatActivity {
    private  static final int PICK_IMAGE_REQUEST = 1;
    private  static final int PICK_FILE = 1;
EditText fdogname,fdogloc,fdogbreed,fdogaddress,fdognum;
TextView fvalidId, Ffilledupform;
Button Proceed;
boolean valid = true;
Button BrowseValid;
ImageView formimage,validIdimage;
    FirebaseDatabase root;
    DatabaseReference ref;
    StorageReference storageRef;
    Uri imageuri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adoption_form);
        getSupportActionBar().hide();
        fdogname = findViewById(R.id.FDog_name);
        fdogbreed = findViewById(R.id.FDog_Breed);
        fdogloc = findViewById(R.id.FDog_Loc);
        fdogaddress = findViewById(R.id.FDog_Address);
        fdognum = findViewById(R.id.FDog_Num);

        fvalidId = findViewById(R.id.Fvalidphoto);
        Ffilledupform = findViewById(R.id.FfilledupForm);

        BrowseValid = findViewById(R.id.Fvalidbtn);
        formimage = findViewById(R.id.formimage);
        validIdimage = findViewById(R.id.validIdimage);
        Proceed = findViewById(R.id.CAdopt);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AdoptionForm.this);
        storageRef = FirebaseStorage.getInstance().getReference("Adoption Registry File");
        root = FirebaseDatabase.getInstance();
        ref = root.getReference("Adoption Registry");

        BrowseValid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validIDfile();
            }
        });
        Proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        checkField(fdogname);
        checkField(fdogbreed);
        checkField(fdogloc);
        checkField(fdogaddress);
        checkField(fdognum);
        uploadimage(imageuri);
                String dogname = fdogname.getText().toString();
                String dogbreed = fdogbreed.getText().toString();
                String dogloc = fdogloc.getText().toString();
                String dogaddress = fdogaddress.getText().toString();
                String dognum = fdognum.getText().toString();

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("key 1",dogname);
                editor.putString("key 2",dogbreed);
                editor.putString("key 3",dogloc);
                editor.putString("key 4",dogaddress);
                editor.putString("key 5", dognum);
                editor.commit();


            }
        });
    }
    public void uploadimage(Uri uri){
        StorageReference Folder = FirebaseStorage.getInstance().getReference("Adoption Registry File");
        if (imageuri != null) {
            StorageReference fileref = Folder.child(System.currentTimeMillis() + "." + getFileExtension(imageuri));
            fileref.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String ffdogname = fdogname.getText().toString();
                            String ffdogbreed = fdogbreed.getText().toString();
                            String ffdogloc = fdogloc.getText().toString();
                            String ffdogaddress = fdogaddress.getText().toString();
                            String ffdognum = fdognum.getText().toString();
                            String ffdogurl1 = fvalidId.getText().toString();
                            //String ffileurl2 = Ffilledupform.getText().toString();

                            AdoptionFormHelper uploadform = new AdoptionFormHelper(ffdogname,ffdogbreed,ffdogloc,ffdogaddress,ffdognum,ffdogurl1,uri.toString());
                            String uploadId = ref.push().getKey();
                            ref.push().setValue(uploadform);
                            Toast.makeText(AdoptionForm.this, "Upload Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AdoptionForm.this,AdoptionForm2.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AdoptionForm.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            });

        }
//        if (Fileuri != null){
//            StorageReference fileref2 = Folder.child(System.currentTimeMillis() + "." + getFileExtension(Fileuri));
//            fileref2.putFile(Fileuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    fileref2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
//                            String ffileurl2 = Ffilledupform.getText().toString();
//
//                            AdoptionFormHelper uploadfile = new AdoptionFormHelper(ffileurl2,uri.toString());
//                            ref.push().setValue(uploadfile);
//                            Toast.makeText(AdoptionForm.this, "Upload Successfully", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull  Exception e) {
//                    Toast.makeText(AdoptionForm.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
    }

//    public  void uploadfile(Uri uri2){
//        StorageReference Folder = FirebaseStorage.getInstance().getReference("Adoption Registry File");
//        if (Fileuri !=null){
//            StorageReference file_name= Folder.child("file"+Fileuri.getLastPathSegment());
//            file_name.putFile(Fileuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    file_name.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
////                         ;
//                            HashMap<String,String> hashMap = new HashMap<>();
//                            hashMap.put("fileLink",String.valueOf(uri));
//                            ref.setValue(hashMap);
//                            Toast.makeText(AdoptionForm.this, "Upload Successfully", Toast.LENGTH_SHORT).show();
//
//                        }
//                    });
//                }
//            });
//        }
//        else {
//            Toast.makeText(AdoptionForm.this, "No File Selected", Toast.LENGTH_SHORT).show();
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST) {
            imageuri = data.getData();
            validIdimage.setImageURI(imageuri);
            if (resultCode == RESULT_OK) {
                String path = data.getData().getPath();
                fvalidId.setText(path);
            }
        }

//        if(requestCode == PICK_FILE){
//            Fileuri = data.getData();
//            if (resultCode == RESULT_OK) {
//
//                Uri Fileuri = data.getData();
//                //Ffilledupform.setText(Fileuri.toString());
//                StorageReference Folder = FirebaseStorage.getInstance().getReference("Adoption Registry File");
//                StorageReference file_name= Folder.child("file"+Fileuri.getLastPathSegment());
//
//                file_name.putFile(Fileuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        file_name.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                            @Override
//                            public void onSuccess(Uri uri) {
//                                HashMap<String,String> hashMap = new HashMap<>();
//                                hashMap.put("fileLink",String.valueOf(uri));
//                                ref.push().setValue(hashMap);
//                           }
//                        });
//                    }
//                });
//
//
//            }
//        }

    }

    public void validIDfile(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
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

    public boolean checkField(EditText textField){
        if(textField.getText().toString().isEmpty()){
            textField.setError("Error");
            valid = false;
        }else {
            valid = true;
        }

        return valid;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }
}