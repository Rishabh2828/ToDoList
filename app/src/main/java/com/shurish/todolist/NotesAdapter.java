package com.shurish.todolist;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

public class NotesAdapter extends FirestoreRecyclerAdapter<Note, NotesAdapter.noteviewholder > {
    Context context;

    public NotesAdapter(@NonNull FirestoreRecyclerOptions<Note> options, Context context) {
        super(options);
        this.context= context;

    }

    @Override
    protected void onBindViewHolder(@NonNull noteviewholder holder, int position, @NonNull Note note) {


            holder.titletext.setText(note.title);
            holder.contenttext.setText(note.content);
           String docID = getSnapshots().getSnapshot(position).getId();


        holder.cardview.setCardBackgroundColor(ContextCompat.getColor(context, note.color));



            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, NotesActivity.class);
                    intent.putExtra("title", note.title);
                    intent.putExtra("content", note.content);

                    intent.putExtra("docId", docID);
                    context.startActivity(intent);
                }
            });



            holder.color_orange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int col = R.color.orange;

                    note.setColor(col);


                    DocumentReference documentReference;
                    documentReference= Utility.getcollectionreference().document(docID);


                    documentReference.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){

                               // Toast.makeText(context, "", Toast.LENGTH_SHORT).show();


                            }

                            else {

                                Toast.makeText(context, "Could not change color", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });








                }
            });



        holder.color_blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int col = R.color.lavender;

                note.setColor(col);


                DocumentReference documentReference;
                documentReference= Utility.getcollectionreference().document(docID);


                documentReference.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()){

                          //  Toast.makeText(context, "", Toast.LENGTH_SHORT).show();


                        }

                        else {

                            Toast.makeText(context, "Could not change color", Toast.LENGTH_SHORT).show();
                        }

                    }
                });








            }
        });


        holder.color_green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int col = R.color.green;

                note.setColor(col);


                DocumentReference documentReference;
                documentReference= Utility.getcollectionreference().document(docID);


                documentReference.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()){

                       //     Toast.makeText(context, "", Toast.LENGTH_SHORT).show();


                        }

                        else {

                            Toast.makeText(context, "Could not change color", Toast.LENGTH_SHORT).show();
                        }

                    }
                });








            }
        });


        holder.color_white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int col = R.color.white;

                note.setColor(col);


                DocumentReference documentReference;
                documentReference= Utility.getcollectionreference().document(docID);


                documentReference.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()){

                         //   Toast.makeText(context, "", Toast.LENGTH_SHORT).show();


                        }

                        else {

                            Toast.makeText(context, "Could not change color", Toast.LENGTH_SHORT).show();
                        }

                    }
                });








            }
        });


















        holder.deletebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    DocumentReference documentReference;
                    documentReference= Utility.getcollectionreference().document(docID);


                    documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){

                                Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();


                            }

                            else {

                                Toast.makeText(context, "Could not deleted", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });


                }
            });
        }





    @NonNull
    @Override
    public noteviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = (LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_layout,parent, false));
        return new noteviewholder(view);
    }

    @Override
    public void onDataChanged() {
        notifyDataSetChanged(); // notify the adapter of the data set changes
    }


    public  class  noteviewholder extends RecyclerView.ViewHolder {
        TextView titletext, contenttext;
        ImageView deletebtn;

        ImageView color_orange;
        ImageView color_green;
        ImageView color_blue;
        ImageView color_white;

        CardView cardview;

        public noteviewholder(@NonNull View itemView) {
            super(itemView);
          titletext = itemView.findViewById(R.id.title_layout);
          contenttext = itemView.findViewById(R.id.content_layout);
          deletebtn = itemView.findViewById(R.id.deletebtn);

          color_orange = itemView.findViewById(R.id.orange);
          color_green = itemView.findViewById(R.id.green);
          color_blue= itemView.findViewById(R.id.blue);
          color_white = itemView.findViewById(R.id.white);
          cardview = itemView.findViewById(R.id.cardview);


        }
    }
}
