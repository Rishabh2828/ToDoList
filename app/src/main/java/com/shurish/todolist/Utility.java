package com.shurish.todolist;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Collection;

public class Utility {
    static  CollectionReference getcollectionreference(){

        FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();
       return FirebaseFirestore.getInstance().collection("notes")
                .document(currentuser.getUid()).collection("mynotes");

    }
}
