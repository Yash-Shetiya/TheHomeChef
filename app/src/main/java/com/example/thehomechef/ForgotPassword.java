package com.example.thehomechef;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    Toolbar toolbar;
    EditText email;
    Button sendpass;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        toolbar = findViewById(R.id.toolbar);
        email = findViewById(R.id.email);
        sendpass = findViewById(R.id.sendpass);


        auth=FirebaseAuth.getInstance();
        MediaPlayer mediaPlayer;
        mediaPlayer = MediaPlayer.create(this, R.raw.button);

        sendpass.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
                if (TextUtils.isEmpty(email.getText().toString())) {
                    Toast.makeText(ForgotPassword.this, "Enter email ID", Toast.LENGTH_SHORT).show();
                } else{
                auth.sendPasswordResetEmail(email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgotPassword.this, "Link sent to your email", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ForgotPassword.this, "Error in sending password ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            }
        });
    }
}