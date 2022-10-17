package com.example.dogadoptionsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class MainActivity extends AppCompatActivity {
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    StorageReference ref;
    public Button btnForms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://dbdogadoption-a699b.appspot.com/Form.pdf");

        ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("FORMS.pdf");
        pd.setMessage("Downloading Please Wait!");
        pd.setIndeterminate(true);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.show();


        final File rootPath = new File(Environment.getExternalStorageDirectory(), "DOWNLOADS");

        if (!rootPath.exists()) {
            rootPath.mkdirs();
        }


        final File localFile = new File(rootPath, "FORMS.pdf");

        storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener <FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Log.e("firebase ", ";local tem file created  created " + localFile.toString());


                if (localFile.canRead()){

                    pd.dismiss();
                }

                Toast.makeText(MainActivity.this, "Download Completed", Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, "Internal storage/DOWNLOADS/FORMS.pdf", Toast.LENGTH_LONG).show();
                finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e("firebase ", ";local tem file not created  created " + exception.toString());
                Toast.makeText(MainActivity.this, "Download Incompleted", Toast.LENGTH_LONG).show();
                finish();
            }
        });

//        btnForms.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                download();
//            }
//        });
    }

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
}