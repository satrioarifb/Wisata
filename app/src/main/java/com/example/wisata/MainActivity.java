package com.example.wisata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView createAcc;
    private EditText email, password;
    private Button signin;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        createAcc = (TextView) findViewById(R.id.btnCreate);
        createAcc.setOnClickListener(this);

        email = (EditText) findViewById(R.id.Email);
        password = (EditText) findViewById(R.id.Password);
        signin = (Button) findViewById(R.id.btnLogin);
        signin.setOnClickListener(this);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnCreate:
                startActivity(new Intent(this, register.class));
                break;

            case R.id.btnLogin:
                userLogin();
//                startActivity(new Intent(this, MenuUtama.class));
                break;
        }
    }

    private void userLogin() {
        String Email = email.getText().toString().trim();
        String Password = password.getText().toString().trim();

        if(Email.isEmpty()){
            email.setError("Email is required");
            email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            email.setError("Please provide valid email");
            email.requestFocus();
            return;
        }

        if(Password.isEmpty()){
            password.setError("Password is required");
            password.requestFocus();
            return;
        }

        if(Password.length() < 6 ){
            password.setError("Password should min 6 characters");
            password.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override   
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if (user.isEmailVerified()){
                        startActivity(new Intent(MainActivity.this, profile.class));
                    }else{
                        user.sendEmailVerification();
                        Toast.makeText(MainActivity.this, "Please check your email", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(MainActivity.this, "Password/Email incorrect. Try Again!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}