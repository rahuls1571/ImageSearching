package com.bls.imagesearching;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Objects;

public class CommentsActivity extends AppCompatActivity implements View.OnClickListener {
      PhotoView photoView;
      TextView comment_Add;
      ListView comment_list;
      FirebaseDatabase database;
      DatabaseReference myRef;
      ProgressBar progress_Bar;
      String url,id;
      ArrayList<String> comment_array = new ArrayList<>();

      @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        photoView = findViewById(R.id.photo_view);
        comment_Add = findViewById(R.id.comment_add);
        comment_list = findViewById(R.id.comment_list);
        progress_Bar = findViewById(R.id.Progress_bar);
        comment_Add.setOnClickListener(this);

        url = getIntent().getStringExtra("url");
        id = getIntent().getStringExtra("id");
        Glide.with(CommentsActivity.this).load(url).into(photoView);

        database = FirebaseDatabase.getInstance();
        assert id != null;
        myRef = database.getReference(id);
        Read_Data();

      }


    @Override
    public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.comment_add:
                    Comment_Add();
                    break;
            }
    }

    public void Comment_Add()
    {
        AlertDialog.Builder dialog_box = new AlertDialog.Builder(CommentsActivity.this);
        final EditText editText = new EditText(CommentsActivity.this);
        editText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        dialog_box.setMessage("Enter Comment");
        dialog_box.setTitle("Add Comments");
        dialog_box.setView(editText);
        dialog_box.setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                myRef.push().setValue(editText.getText().toString());
                Read_Data();
            }
        })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
        });
        dialog_box.show();
    }

    public void Read_Data(){
        progress_Bar.setVisibility(View.VISIBLE);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()) {
                    comment_array.clear();
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        String Total_Comments = (Objects.requireNonNull(snap.getValue())).toString();
                        comment_array.add(Total_Comments);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(CommentsActivity.this,R.layout.support_simple_spinner_dropdown_item, comment_array);
                    comment_list.setAdapter(adapter);
                    progress_Bar.setVisibility(View.INVISIBLE);
                    adapter.notifyDataSetChanged();
                }
                else{
                    progress_Bar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}