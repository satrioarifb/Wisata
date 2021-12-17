package com.example.wisata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wisata.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity implements View.OnClickListener {

    private Button regist;
    private EditText nama, gender, email, password, confirmPass;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        regist = (Button) findViewById(R.id.btnDaftar);
        regist.setOnClickListener(this);

        nama = (EditText) findViewById(R.id.Name);
        gender = (EditText) findViewById(R.id.Gender);
        email = (EditText) findViewById(R.id.Email);
        password = (EditText) findViewById(R.id.Password);
        confirmPass = (EditText) findViewById(R.id.Confirm);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnDaftar:
                RegistUser();
                break;
        }
    }

    private void RegistUser() {
        String Name = nama.getText().toString().trim();
        String Gender = gender.getText().toString().trim();
        String Email = email.getText().toString().trim();
        String Password = password.getText().toString().trim();
        String Confirm = confirmPass.getText().toString().trim();

        if(Name.isEmpty()){
            nama.setError("Full Name is required");
            nama.requestFocus();
            return;
        }

        if(Gender.isEmpty()){
            gender.setError("Gender is required");
            gender.requestFocus();
            return;
        }

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
        if(Confirm.isEmpty()){
            confirmPass.setError("Please confirm your Password");
            confirmPass.requestFocus();
            return;
        }
        if (!Password.equals(Confirm)){
            confirmPass.setError("Your password doesn't match");
            confirmPass.requestFocus();
        }

        mAuth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isComplete()) {
                            User user = new User(Name, Gender, Email);

                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(register.this, "User has been register successfully!", Toast.LENGTH_LONG).show();

                                        startActivity(new Intent(register.this, MainActivity.class));
                                    } else {
                                        Toast.makeText(register.this, "User failed to register. Try Again!", Toast.LENGTH_LONG).show();

                                    }
                                }
                            });
                        } else {
                            Toast.makeText(register.this, "User failed to register. Try Again!", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
}