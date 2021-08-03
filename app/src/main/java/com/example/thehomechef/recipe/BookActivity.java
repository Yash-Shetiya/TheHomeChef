package com.example.thehomechef.recipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.thehomechef.R;

public class BookActivity extends AppCompatActivity {
    CardView tea, coffee , drink , beverage , sweet , dessert , breakfast , savory;
    TextView ytbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        tea = findViewById(R.id.tea);
        coffee = findViewById(R.id.coffee);
        drink = findViewById(R.id.drink);
        beverage = findViewById(R.id.beverage);
        sweet = findViewById(R.id.sweet);
        dessert = findViewById(R.id.dessert);
        breakfast = findViewById(R.id.breakfast);
        savory = findViewById(R.id.savory);

       ytbutton = findViewById(R.id.ytbutton);

        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.button);
        tea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                startActivity(new Intent(BookActivity.this , teaActivity.class));
            }
        });
        coffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                startActivity(new Intent(BookActivity.this , coffeeActivity.class));
            }
        });
        drink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                startActivity(new Intent(BookActivity.this , drinkActivity.class));
            }
        });
        beverage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                startActivity(new Intent(BookActivity.this , beverageActivity.class));
            }
        });
        sweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                startActivity(new Intent(BookActivity.this , sweetActivity.class));
            }
        });
        dessert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                startActivity(new Intent(BookActivity.this , dessertActivity.class));
            }
        });
        breakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                startActivity(new Intent(BookActivity.this , breakfastActivity.class));
            }
        });
        savory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                startActivity(new Intent(BookActivity.this , savoryActivity.class));
            }
        });

        ytbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                gotourl("https://www.youtube.com/playlist?list=PLOAQWdcAWLEpMn_AHiBK5jCCRu5wUOYGS");
            }
        });

    }

    private void gotourl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW , uri));
    }
}