package com.shurish.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;

public class NotesActivity extends AppCompatActivity {

    EditText title;
    EditText content;
    Button save_btn;

    String titles, contents, docId;
    boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);




        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
        save_btn = findViewById(R.id.button_save);

        titles = getIntent().getStringExtra("title");
        contents = getIntent().getStringExtra("content");
        docId = getIntent().getStringExtra("docId");

        if (titles != null) {
            title.setText(titles);
        }
        if (contents != null) {
            content.setText(contents);
        }



        if(docId != null ){


            isEditMode = true;

        }






        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                savenotes();

            }


        });



    }

    void savenotes(){

        String notetitle = title.getText().toString();
        String notecontent = content.getText().toString();


        if(notetitle.isEmpty()){
            title.setError("Title is Empty");
            return;
        }

        Note note = new Note();
        note.setTitle(notetitle);
        note.setContent(notecontent);

        note.setTimestamp(Timestamp.now());



        savetofirebase(note);



    }

    void  savetofirebase(Note note){

        DocumentReference documentReference;






        if(isEditMode){
            documentReference= Utility.getcollectionreference().document(docId);

        }else {

            documentReference= Utility.getcollectionreference().document();


        }

        documentReference.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                
                if (task.isSuccessful()){

                    Toast.makeText(NotesActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(NotesActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                
                else {

                    Toast.makeText(NotesActivity.this, "Failed While Adding Note", Toast.LENGTH_SHORT).show();
                }
                
            }
        });



    }




}