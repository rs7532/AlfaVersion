package com.example.alfaversion;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Act1 extends AppCompatActivity {

    public static FirebaseAuth refAuth = FirebaseAuth.getInstance();
    EditText email, password;
    Button btn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act1);

        email = findViewById(R.id.ET_email);
        password = findViewById(R.id.ET_password);
        btn = findViewById(R.id.Btn);

    }

    public void Clicked(View view) {
        String email1 = email.getText().toString();
        String password1 = password.getText().toString();

        if (email1.isEmpty() || password1.isEmpty()){
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_LONG).show();
        }
        else{
            ProgressDialog pd = new ProgressDialog(this);
            pd.setTitle("Connecting...");
            pd.setMessage("Creating user...");
            pd.show();
            refAuth.createUserWithEmailAndPassword(email1, password1).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    pd.dismiss();
                    if(task.isSuccessful()){
                        Log.i("Act1", "create user with email and password:success");
                    }
                }
            });
        }
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