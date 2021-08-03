package com.example.thehomechef;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;

public class OptionsActivity extends AppCompatActivity {

    TextView logout, settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        logout = findViewById(R.id.logout);
        settings = findViewById(R.id.settings);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Options");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.button);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
                new FancyGifDialog.Builder(com.example.thehomechef.OptionsActivity.this)
                        .setTitle("Are you sure you want to Logout?")
                        .setPositiveBtnBackground("#19CB94")
                        .setPositiveBtnText("Yes")
                        .setNegativeBtnText("Cancel")
                        .setNegativeBtnBackground("#EF6973")
                        .setGifResource(R.drawable.logout)
                        .isCancellable(true)
                        .OnPositiveClicked(new FancyGifDialogListener() {
                            @Override
                            public void OnClick() {
                                mediaPlayer.start();
                                FirebaseAuth.getInstance().signOut();
                                Intent intent = new Intent(getApplicationContext(), com.example.thehomechef.StartActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .OnNegativeClicked(new FancyGifDialogListener() {
                            @Override
                            public void OnClick() {
                                mediaPlayer.start();
                            }
                        })
                        .build();
            }
        });
    }
}
