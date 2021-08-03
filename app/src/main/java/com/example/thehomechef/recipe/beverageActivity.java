package com.example.thehomechef.recipe;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import com.example.thehomechef.R;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;


public class beverageActivity extends AppCompatActivity
{
    FloatingActionButton upload;
    RecyclerView filerecycler;
    fileadapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beverage);


        upload = findViewById(R.id.upload);
        filerecycler = findViewById(R.id.filerecycler);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AdminLogin.class));
            }
        });

        filerecycler.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<fileinfomodel> options =
                new FirebaseRecyclerOptions.Builder<fileinfomodel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("RecipesFile").child("beverage").getRef(), fileinfomodel.class)
                        .build();

        adapter = new fileadapter(options);
        filerecycler.setAdapter(adapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }


}
