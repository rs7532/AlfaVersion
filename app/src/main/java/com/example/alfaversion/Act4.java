package com.example.alfaversion;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class Act4 extends AppCompatActivity {
    Timer timer;
    ImageView iv_act4;
    Button btn_act4;
    EditText eT_act4;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act4);

        iv_act4 = findViewById(R.id.iV_act4);
        btn_act4 = findViewById(R.id.btn_act4);
        eT_act4 = findViewById(R.id.eT_act4);


    }

    public void Clicked_4(View view) {
        if (eT_act4.getText().toString().isEmpty()) {
            Toast.makeText(Act4.this, "enter some text to make a qr code", Toast.LENGTH_LONG).show();
        } else {
//            timer.cancel();
            start_createQR();
        }

    }
    public void createQR() {
        BarcodeEncoder qr_code = new BarcodeEncoder();
        try {
            @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String date = df.format(Calendar.getInstance().getTime());
            Bitmap bitmap = qr_code.encodeBitmap(eT_act4.getText().toString() + date, BarcodeFormat.QR_CODE, 400, 400);
            iv_act4.setImageBitmap(bitmap);
        } catch (WriterException e) {
            Log.e("qr code", e.toString());
        }
    }

    public void start_createQR(){
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Act4.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        createQR();
                    }
                });
            }
        };
        timer.schedule(task, 0, 3000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String st = item.getTitle().toString();
        Intent intent = new Intent();
        if(st.equals("Act1")){
            intent = new Intent(this, Act1.class);
        }
        if(st.equals("Act2")){
            intent = new Intent(this, Act2.class);
        }
        if(st.equals("Act3")){
            intent = new Intent(this, Act3.class);
        }
        if(st.equals("Act5")){
            intent = new Intent(this, Act5.class);
        }
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}