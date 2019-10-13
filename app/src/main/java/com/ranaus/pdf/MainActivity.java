package com.ranaus.pdf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.edt_text);
        button = findViewById(R.id.btn_submit);

        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}
        , PackageManager.PERMISSION_GRANTED);


    }
    public void createMyPDF(View view)
    {
        PdfDocument pdfDocument = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300,600,1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        Paint paint = new Paint();
        String string = editText.getText().toString();
        int x = 10, y = 25;
        page.getCanvas().drawText(string,x,y,paint);
        pdfDocument.finishPage(page);

        File fileCreate = new File(Environment.getExternalStorageDirectory()+File.separator+"Demo");
        fileCreate.mkdir();

        String filePath = Environment.getExternalStorageDirectory().getPath() + "/Demo/pdf_demo.pdf";
        File file = new File(filePath);
        try {
            pdfDocument.writeTo(new FileOutputStream(file));
        }

        catch (IOException e)
        {
            e.printStackTrace();
            editText.setText("Error");
        }
        pdfDocument.close();
    }
}
