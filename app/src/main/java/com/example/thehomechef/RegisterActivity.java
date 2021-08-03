package com.example.thehomechef;

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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText username, fullname, email, password, confirm;
    Button register;
    TextView txt_login;

    FirebaseAuth auth;
    DatabaseReference reference;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.username);
        fullname = findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirm = findViewById(R.id.confirm);
        register = findViewById(R.id.register);
        txt_login = findViewById(R.id.txt_login);

        auth = FirebaseAuth.getInstance();

        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.button);
        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
                startActivity(new Intent(com.example.thehomechef.RegisterActivity.this, LoginActivity.class));
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();

                String str_username = username.getText().toString();
                String str_fullname = fullname.getText().toString();
                String str_email = email.getText().toString();
                String str_password = password.getText().toString();
                String str_confirm = confirm.getText().toString();

                if(TextUtils.isEmpty(str_username) || TextUtils.isEmpty(str_fullname) || TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_password)){
                    Toast.makeText(com.example.thehomechef.RegisterActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();

                } else if(str_password.length() < 6){
                    Toast.makeText(com.example.thehomechef.RegisterActivity.this, "Password must have at least 6 characters", Toast.LENGTH_SHORT).show();

                } else if(!str_password.equals(str_confirm)){
                    Toast.makeText(com.example.thehomechef.RegisterActivity.this, "Passwords mismatch", Toast.LENGTH_SHORT).show();

                }else {
                    register(str_username, str_fullname, str_email, str_password);
                    pd = new ProgressDialog(com.example.thehomechef.RegisterActivity.this);
                    pd.setMessage("Registering..");
                    pd.show();
                }
            }
        });

    }

    private void register(final String username, final String fullname, String email, String password){
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(com.example.thehomechef.RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser firebaseUser = auth.getCurrentUser();
                                        String userid = firebaseUser.getUid();

                                        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);

                                        HashMap<String, Object> hashMap = new HashMap<>();
                                        hashMap.put("id", userid);
                                        hashMap.put("username", username.toLowerCase());
                                        hashMap.put("fullname", fullname);
                                        hashMap.put("imageurl", "https://firebasestorage.googleapis.com/v0/b/thehomechef-cead2.appspot.com/o/profileimage.png?alt=media&token=5947ace4-b531-4caf-90a5-3e9defd8de70");

                                        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    pd.dismiss();
                                                    Intent intent = new Intent(com.example.thehomechef.RegisterActivity.this, LoginActivity.class);
//                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            }
                                        });
                                       // startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                        Toast.makeText(RegisterActivity.this, "Check your MailBox for verification link", Toast.LENGTH_SHORT).show();
                                    }else
                                        Toast.makeText(RegisterActivity.this, "Email not sent .", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            pd.dismiss();
                            Toast.makeText(com.example.thehomechef.RegisterActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
