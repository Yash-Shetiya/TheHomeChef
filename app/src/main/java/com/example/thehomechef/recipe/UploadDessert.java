package com.example.thehomechef.recipe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.thehomechef.R;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class UploadDessert extends AppCompatActivity {

    Button fileupload ;
    ImageView filebrowse, filelogo, filecancel;
    EditText filename;
    Uri filepath;

    StorageReference storageReference;
    DatabaseReference fileReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_dessert);

        filebrowse = findViewById(R.id.filebrowse);
        filelogo = findViewById(R.id.filelogo);
        filecancel = findViewById(R.id.filecancel);

        filename = findViewById(R.id.filename);
        fileupload = findViewById(R.id.fileupload);

        storageReference = FirebaseStorage.getInstance().getReference();
        fileReference = FirebaseDatabase.getInstance().getReference("RecipesFile").child("dessert").getRef();

        filelogo.setVisibility(View.INVISIBLE);
        filecancel.setVisibility(View.INVISIBLE);

        filecancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filelogo.setVisibility(View.INVISIBLE);
                filecancel.setVisibility(View.INVISIBLE);
                filebrowse.setVisibility(View.VISIBLE);
            }
        });

        filebrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withContext(getApplicationContext())
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent = new Intent();
                                intent.setType("application/pdf");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent,"Select recipe file to upload"), 101);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                                Toast.makeText(UploadDessert.this, "Please give permission to upload files", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });

        fileupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processupload(filepath);
            }
        });
    }

    private void processupload(Uri filepath) {
        ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Recipe uploading");
        pd.show();

        StorageReference reference = storageReference.child("recipes/").child("dessert/"+System.currentTimeMillis()+".pdf");
        reference.putFile(filepath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                fileinfomodel obj = new fileinfomodel(filename.getText().toString(), uri.toString());
                                fileReference.child(fileReference.push().getKey()).setValue(obj);

                                pd.dismiss();
                                Toast.makeText(UploadDessert.this, "Recipe added successfully.", Toast.LENGTH_SHORT).show();
                                filelogo.setVisibility(View.INVISIBLE);
                                filecancel.setVisibility(View.INVISIBLE);
                                filebrowse.setVisibility(View.VISIBLE);
                                filename.setText("");
                            }
                        });
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        long percent = (100 * snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                        pd.setMessage("Uploaded "+ (int)percent+"%");
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK){
            filepath = data.getData();
            filelogo.setVisibility(View.VISIBLE);
            filecancel.setVisibility(View.VISIBLE);
            filebrowse.setVisibility(View.INVISIBLE);

        }
    }
}