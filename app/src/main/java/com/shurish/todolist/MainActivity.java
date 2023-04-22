package com.shurish.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.common.ChangeEventType;
import com.firebase.ui.firestore.ChangeEventListener;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton fab_btn;

    NotesAdapter notesAdapter;

    TextView write_something;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        fab_btn = findViewById(R.id.fab_btn);
        write_something = findViewById(R.id.write_something);


        fab_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NotesActivity.class);
                startActivity(intent);
            }
        });

        setupRecyclerView();


    }

    void setupRecyclerView() {


        Query query = Utility.getcollectionreference().orderBy("timestamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Note> options = new FirestoreRecyclerOptions.Builder<Note>()
                .setQuery(query, Note.class).build();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        notesAdapter = new NotesAdapter(options, this);
        recyclerView.setAdapter(notesAdapter);


        options.getSnapshots().addChangeEventListener(new ChangeEventListener() {
            @Override
            public void onChildChanged(@NonNull ChangeEventType type, @NonNull DocumentSnapshot snapshot, int newIndex, int oldIndex) {
                updateWriteSomethingVisibility();
            }

            @Override
            public void onDataChanged() {
                updateWriteSomethingVisibility();
            }

            @Override
            public void onError(@NonNull FirebaseFirestoreException e) {
                // handle error
            }
        });
    }

    void updateWriteSomethingVisibility() {
        if (notesAdapter.getItemCount() == 0) {
            write_something.setVisibility(View.VISIBLE);
        } else {
            write_something.setVisibility(View.GONE);
        }
    }





    @Override
    protected void onStart() {
        super.onStart();
        notesAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        notesAdapter.stopListening();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.logout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


       switch (item.getItemId()){

           case R.id.logout:
               firebaseAuth.signOut();
               Toast.makeText(this, "Log out successfully", Toast.LENGTH_SHORT).show();

               Intent intent = new Intent(MainActivity.this, LoginActivity.class);
               startActivity(intent);
               finish();




       }





        return super.onOptionsItemSelected(item);
    }
}