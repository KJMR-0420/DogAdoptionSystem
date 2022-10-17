package com.example.dogadoptionsystem.Dashboard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dogadoptionsystem.Admin.AdminDashboard;
import com.example.dogadoptionsystem.HelperClass.DogprofileHelper;
import com.example.dogadoptionsystem.R;
import com.example.dogadoptionsystem.SuperAdmin.SuperAdminDashboard;
import com.example.dogadoptionsystem.user.UserLogin;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.InputStream;
import java.util.Random;

public class CreateDogProfile extends AppCompatActivity {

    EditText DogName,DogBreed,DogLocation,DogDescription;
    Uri filepath;
    ImageView DogImg;
    Button BrowseImg,Post;
    Bitmap bitmap;
    boolean valid = true;
    FirebaseDatabase root;
    DatabaseReference ref;
    StorageReference storageRef;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_dog_profile);
        getSupportActionBar().hide();
        DogImg = findViewById(R.id.dog_img);
        Post  = findViewById(R.id.Post);
        BrowseImg = findViewById(R.id.browse_dogImage);
        DogName = findViewById(R.id.Dog_name);
        DogBreed =findViewById(R.id.Dog_Breed);
        DogLocation = findViewById(R.id.Dog_Location);
        DogDescription = findViewById(R.id.Dog_Background);

        BrowseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(CreateDogProfile.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent,"Select Image File"),1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                            token.continuePermissionRequest();
                            }
                        }).check();
            }
        });
     Post .setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

             String fdogname = DogName.getText().toString();
             String fdogbreed = DogBreed.getText().toString();
             String fdoglocation = DogLocation.getText().toString();
             String fdogdes = DogDescription.getText().toString();

             if (fdogname.isEmpty()){
                 DogName.setError("Need to fill up");
                 return;
             }
             if (fdogbreed.isEmpty()){
                 DogBreed.setError("Need to fill up");
                 return;
             }
             if (fdoglocation.isEmpty()){
                 DogLocation.setError("Need to fill up");
                 return;
             }
             if (fdogdes.isEmpty()){
                 DogDescription.setError("Need to fill up");
                 return;
             }
             uploadFirebase();
         }

     }
     );
        return;
    }
    public void uploadFirebase() {

        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("File Uploader");
        dialog.show();


        root = FirebaseDatabase.getInstance();
        ref = root.getReference("Doglist");

        storageRef = FirebaseStorage.getInstance().getReference("DogPicture"+new Random().nextInt(50));

        storageRef.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        dialog.dismiss();
                        storageRef = FirebaseStorage.getInstance().getReference("DogPicture");
                        root = FirebaseDatabase.getInstance();
                        ref = root.getReference("Doglist");

                        DogprofileHelper obj = new DogprofileHelper(DogName.getText().toString(),DogBreed.getText().toString(),DogLocation.getText().toString(),DogDescription.getText().toString(),uri.toString());
                        ref.push().child("").setValue(obj);

                        DogName.setText("");
                        DogBreed.setText("");
                        DogLocation.setText("");
                        DogDescription.setText("");

                        DogImg.setImageResource(R.drawable.ic_launcher_foreground);
                        Toast.makeText(getApplicationContext(),"Uploaded",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull  UploadTask.TaskSnapshot snapshot) {
                float percent = (100*snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                dialog.setMessage("Uploaded:" + (int)percent+ "%");

                startActivity(new Intent(getApplicationContext(), SuperAdminDashboard.class));
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode==1 && resultCode==RESULT_OK){
            filepath=data.getData();
            try{
                InputStream inputStream = getContentResolver().openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                DogImg.setImageBitmap(bitmap);
            }catch (Exception e){

            }
        }

            super.onActivityResult(requestCode, resultCode, data);

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
        startActivity(new Intent(getApplicationContext(), SuperAdminDashboard.class));
    }
}