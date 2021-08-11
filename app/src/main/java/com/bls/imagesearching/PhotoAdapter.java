package com.bls.imagesearching;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder>  {
    private ArrayList<PhotoModel> photoModels;
    private Context context;

    public PhotoAdapter(ArrayList<PhotoModel> photoModels, Context context) {
        this.photoModels = photoModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.image_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        String id  = photoModels.get(position).getId();
        String title = photoModels.get(position).getTitle();
        Glide.with(context).load(photoModels.get(position).getImage()).into(holder.img);
        holder.title.setText(title);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context,
                        CommentsActivity.class).putExtra("url", photoModels.get(position).getImage())
                        .putExtra("id",photoModels.get(position).getId())
                );
            }
        });
    }

    @Override
    public int getItemCount() {
        return photoModels.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,comment;
        ImageView img;

        public  ViewHolder(@NonNull final View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.image_title);
            img = itemView.findViewById(R.id.imgur_image);
          //  comment = itemView.findViewById(R.id.image_comment);

        }

    }
}
