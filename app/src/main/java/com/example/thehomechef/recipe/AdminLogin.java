package com.example.thehomechef.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.thehomechef.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AdminLogin extends AppCompatActivity {
    FirebaseAuth auth1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        EditText Demail, Dpassword;
        Button Dlogin;

        Demail = findViewById(R.id.Demail);
        Dpassword = findViewById(R.id.Dpassword);
        Dlogin = findViewById(R.id.Dlogin);
        auth1 = FirebaseAuth.getInstance();

            Dlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String txt_email1 = Demail.getText().toString();
                    String txt_password1 = Dpassword.getText().toString();
                    loginadmin(txt_email1 , txt_password1);
                }
            });
    }

    private void loginadmin(String Demail, String Dpassword) {
        auth1.signInWithEmailAndPassword(Demail , Dpassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            if(auth1.getCurrentUser().getEmail().equals(getString(R.string.developer)))
                            startActivity(new Intent(AdminLogin.this, ShowMenu.class));
                        }else {
                            Toast.makeText(AdminLogin.this, "You are not a developer", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}