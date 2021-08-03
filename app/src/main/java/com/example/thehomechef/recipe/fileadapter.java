package com.example.thehomechef.recipe;

import android.content.Intent;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.thehomechef.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class fileadapter  extends FirebaseRecyclerAdapter<fileinfomodel, com.example.thehomechef.recipe.fileadapter.fileholder>
{
    public fileadapter(@NonNull FirebaseRecyclerOptions<fileinfomodel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull fileholder file_holder, int i, @NonNull fileinfomodel infomodel) {
        file_holder.header.setText(infomodel.getFilename());
                file_holder.header.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(file_holder.header.getContext(), ViewPdf.class);
                        intent.putExtra("filename", infomodel.getFilename());
                        intent.putExtra("fileurl", infomodel.getFileurl());

                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        file_holder.header.getContext().startActivity(intent);
                    }
                });
    }

    @NonNull
    @Override
    public fileholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_design,parent,false);
        return new fileholder(view);
    }

    public static class fileholder extends RecyclerView.ViewHolder
    {
        ImageView titlelogo;
        TextView header;
        ImageButton share;
        public fileholder(@NonNull View itemView) {
            super(itemView);

            titlelogo = itemView.findViewById(R.id.titlelogo);
            header = itemView.findViewById(R.id.header);

        }
    }
}
