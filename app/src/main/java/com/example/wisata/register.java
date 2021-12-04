package com.example.wisata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class register extends AppCompatActivity implements View.OnClickListener {

    private Button regist;
    private EditText nama, gender, email, password;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();

        regist = (Button) findViewById(R.id.btnDaftar);
        regist.setOnClickListener(this);

        nama = (EditText) findViewById(R.id.Name);
        gender = (EditText) findViewById(R.id.Gender);
        email = (EditText) findViewById(R.id.Email);
        password = (EditText) findViewById(R.id.Password);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnDaftar:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }

    private void RegistUSer() {
        String inputnama = nama.getText().toString().trim();
        String inputgender = gender.getText().toString().trim();
        String inputemail = email.getText().toString().trim();
        String inputpass = password.getText().toString().trim();

        if(inputnama.isEmpty()){
            nama.setError("Full Name is required");
            nama.requestFocus();
            return;
        }

        if(inputgender.isEmpty()){
            gender.setError("Gender is required");
            gender.requestFocus();
            return;
        }

        if(inputemail.isEmpty()){
            email.setError("Email is required");
            email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(inputemail).matches()){
            email.setError("Please provide valid email");
            email.requestFocus();
            return;
        }

        if(inputpass.isEmpty()){
            password.setError("Password is required");
            password.requestFocus();
            return;
        }

        if(inputpass.length() <6 ){
            password.setError("Password should min 6 characters");
            password.requestFocus();
            return;
        }
    }
}