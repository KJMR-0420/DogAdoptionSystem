package com.example.dogadoptionsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class pdfViewer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);
        PDFView pdfView=(PDFView)findViewById(R.id.pdfView);
        pdfView.fromAsset("Doc1.pdf") .load();
    }
}