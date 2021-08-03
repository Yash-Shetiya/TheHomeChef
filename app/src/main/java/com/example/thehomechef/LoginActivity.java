package com.example.thehomechef;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button login, forgot;
    TextView txt_signup;
    ProgressDialog pd;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        login = findViewById(R.id.login);
        password = findViewById(R.id.password);
        txt_signup = findViewById(R.id.txt_signup);
        forgot = findViewById(R.id.forgot);
        auth = FirebaseAuth.getInstance();

        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.button);
        txt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
                ProgressDialog pd = new ProgressDialog(LoginActivity.this);
                pd.setMessage("Please wait...");
                pd.show();

                String str_email = email.getText().toString();
                String str_password = password.getText().toString();

                if (TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_password)) {
                    Toast.makeText(com.example.thehomechef.LoginActivity.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else if (str_password.length() < 6) {
                    Toast.makeText(LoginActivity.this, "Password must have at least 6 characters", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    loginuser(str_email, str_password);
                }
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                startActivity(new Intent(LoginActivity.this, ForgotPassword.class));
            }
        });
    }

    private void loginuser(String email, String password) {
        auth.signInWithEmailAndPassword(email , password).addOnCompleteListener(LoginActivity.this , new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    if(auth.getCurrentUser().isEmailVerified()) {
                        //pd.dismiss();
                        Toast.makeText(LoginActivity.this, "Logging in...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }else
                        Toast.makeText(LoginActivity.this, "Please Verify Your email address .", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "User not registered yet!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
