package com.example.thehomechef.recipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import com.example.thehomechef.R;

public class ShowMenu extends AppCompatActivity {
    CardView Ftea, Fcoffee , Fdrink , Fbeverage , Fsweet , Fdessert , Fbreakfast , Fsavory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_menu);

        Ftea = findViewById(R.id.Ftea);
        Fcoffee = findViewById(R.id.Fcoffee);
        Fdrink = findViewById(R.id.Fdrink);
        Fbeverage = findViewById(R.id.Fbeverage);
        Fsweet = findViewById(R.id.Fsweet);
        Fdessert = findViewById(R.id.Fdessert);
        Fbreakfast = findViewById(R.id.Fbreakfast);
        Fsavory = findViewById(R.id.Fsavory);

        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.button);
        Ftea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                startActivity(new Intent(ShowMenu.this , UploadTea.class));
            }
        });
        Fcoffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                startActivity(new Intent(ShowMenu.this , UploadCoffee.class));
            }
        });
        Fdrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                startActivity(new Intent(ShowMenu.this , UploadDrink.class));
            }
        });
        Fbeverage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                startActivity(new Intent(ShowMenu.this , UploadBeverage.class));
            }
        });
        Fsweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                startActivity(new Intent(ShowMenu.this , UploadSweet.class));
            }
        });
        Fdessert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                startActivity(new Intent(ShowMenu.this , UploadDessert.class));
            }
        });
        Fbreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                startActivity(new Intent(ShowMenu.this , UploadBreakfast.class));
            }
        });
        Fsavory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                startActivity(new Intent(ShowMenu.this , UploadSavory.class));
            }
        });


    }
}