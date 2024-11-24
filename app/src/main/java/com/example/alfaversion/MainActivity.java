package com.example.alfaversion;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        if(st.equals("Act4")){
            intent = new Intent(this, Act4.class);
        }
        if(st.equals("Act5")){
            intent = new Intent(this, Act5.class);
        }
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}