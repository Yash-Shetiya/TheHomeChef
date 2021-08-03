package com.example.thehomechef;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.example.thehomechef.Model.Post;
import com.example.thehomechef.Model.User;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ImageActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    ImageView back_button, save_button, like, delete_image, more;
    PhotoView postActivity_image;
    TextView post, likes, description, titleN;
    CircleImageView image_profile;

    static Integer currentid;

    private static final String TAG = "ImageActivity";
    private FirebaseUser firebaseUser;
    private FirebaseStorage mFirebaseStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        back_button = findViewById(R.id.back_button);
        save_button = findViewById(R.id.save_button);
        like = findViewById(R.id.like);
        likes = findViewById(R.id.likes);
        post = findViewById(R.id.post);
        postActivity_image = findViewById(R.id.postActivity_image);
        image_profile = findViewById(R.id.image_profile);
        description = findViewById(R.id.description);

        delete_image = findViewById(R.id.delete_image);
        more = findViewById(R.id.more);

        final String postUrl = getIntent().getStringExtra("postUrl");
        final String postDescription = getIntent().getStringExtra("postDescription");
        final String postUserUid = getIntent().getStringExtra("postUserUid");
        final String postId = getIntent().getStringExtra("postId");

        Glide.with(this).load(postUrl).into(postActivity_image);
        description.setText(postDescription);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        userPostImage(image_profile, postUserUid);
        isLiked(postId, like);
        nrLikes(likes, postId);
        // Get Image in comment Edit Text
        isSaved(postId, save_button);

        if(firebaseUser.getUid().equals(postUserUid)){
            save_button.setVisibility(View.GONE);
            delete_image.setVisibility(View.VISIBLE);
        } else {
            save_button.setVisibility(View.VISIBLE);
            delete_image.setVisibility(View.GONE);
        }
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
                finish();
            }
        });

        image_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
                Intent intent = new Intent(ImageActivity.this, MainActivity.class);
                intent.putExtra("publisherid", postUserUid);
                startActivity(intent);
            }
        });

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
                if(like.getTag().equals("like")){
                    FirebaseDatabase.getInstance().getReference().child("Likes").child(postId).child(firebaseUser.getUid()).setValue(true);
                } else {
                    FirebaseDatabase.getInstance().getReference().child("Likes").child(postId).child(firebaseUser.getUid()).removeValue();
                }
            }
        });

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
                if(save_button.getTag().equals("save")){
                    FirebaseDatabase.getInstance().getReference().child("Saves").child(firebaseUser.getUid()).child(postId).setValue(true);
                } else {
                    FirebaseDatabase.getInstance().getReference().child("Saves").child(firebaseUser.getUid()).child(postId).removeValue();
                }
            }
        });

        delete_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
                new FancyGifDialog.Builder(ImageActivity.this)
                        .setTitle("Are you sure you want to delete this item?")
                        .setMessage("You can't undo this after deleted.")
                        .setPositiveBtnBackground("#19CB94")
                        .setPositiveBtnText("Yes")
                        .setNegativeBtnText("Cancel")
                        .setNegativeBtnBackground("#EF6973")
                        .setGifResource(R.drawable.delete_gif)
                        .isCancellable(true)
                        .OnPositiveClicked(new FancyGifDialogListener() {
                            @Override
                            public void OnClick() {
                                FirebaseDatabase.getInstance().getReference().child("Posts").child(postId).removeValue();
                                startActivity(new Intent(ImageActivity.this, MainActivity.class));
                                finish();
                            }
                        })
                        .OnNegativeClicked(new FancyGifDialogListener() {
                            @Override
                            public void OnClick() {
                            }
                        })
                        .build();
            }
        });

        likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
                Intent intent = new Intent(getApplicationContext(), FollowersActivity.class);
                intent.putExtra("id", postId);
                intent.putExtra("title", "Likes");
                startActivity(intent);
            }
        });

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
                PopupMenu popupMenu = new PopupMenu(getApplicationContext(), view);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.edit:
                                editPost(postId);
                                return true;
                            case R.id.delete:
                                FirebaseDatabase.getInstance().getReference("Posts").child(postId).removeValue()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(getApplicationContext(), "Post Deleted!", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                return true;
                            case R.id.report:
                                Toast.makeText(getApplicationContext(), "Report has been made!", Toast.LENGTH_SHORT).show();
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popupMenu.inflate(R.menu.post_menu);
                if(!postUserUid.equals(firebaseUser.getUid())){
                    popupMenu.getMenu().findItem(R.id.edit).setVisible(false);
                    popupMenu.getMenu().findItem(R.id.delete).setVisible(false);
                }
                popupMenu.show();
            }
        });

    }

    private void isLiked(String postid, final ImageView imageView){
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Likes").child(postid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(firebaseUser.getUid()).exists()){
                    imageView.setImageResource(R.drawable.ic_liked);
                    imageView.setTag("liked");
                } else {
                    imageView.setImageResource(R.drawable.ic_favorite);
                    imageView.setTag("like");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void nrLikes(final TextView likes, String postid){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Likes").child(postid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                likes.setText(dataSnapshot.getChildrenCount() + " Likes");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void userPostImage(final ImageView image_profile, final String userid){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                Glide.with(getApplicationContext()).load(user.getImageurl()).into(image_profile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void isSaved(final String postid, final ImageView imageView){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Saves").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(postid).exists()){
                    imageView.setImageResource(R.drawable.ic_save_color);
                    imageView.setTag("saved");
                } else {
                    imageView.setImageResource(R.drawable.ic_savee);
                    imageView.setTag("save");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void editPost(final String postid){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Edit Post");

        final EditText editText = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        editText.setLayoutParams(lp);
        alertDialog.setView(editText);

        getText(postid, editText);

        alertDialog.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("ingredients", editText.getText().toString());

                FirebaseDatabase.getInstance().getReference("Posts").child(postid).updateChildren(hashMap);
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();
    }

    private void getText(String postid, final EditText editText){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts").child(postid);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                editText.setText(dataSnapshot.getValue(Post.class).getDescription());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
