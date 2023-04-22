package com.shurish.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

     EditText editText_name;
     EditText editText_email;
     EditText editText_password;
     EditText editTet_password2;
     Button signup_btn;
     TextView login_text;

     ProgressBar progressBar;

     FirebaseAuth firebaseAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getSupportActionBar().hide();




        editText_name = findViewById(R.id.name_text);
         editText_email = findViewById(R.id.email_text);
         editText_password = findViewById(R.id.password_text);
        editTet_password2 = findViewById(R.id.password2_text);
        signup_btn = findViewById(R.id.signup_btn);
         login_text= findViewById(R.id.login_text);

         progressBar = findViewById(R.id.progressbar);



       firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(SignupActivity.this, MainActivity.class));
            finish();
        }

        login_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createaccount();






            }
        });











    }

    void createaccount(){

        String name = editText_name.getText().toString();
        String email = editText_email.getText().toString();
        String password = editText_password.getText().toString();
        String password2 = editTet_password2.getText().toString();

        boolean isvalidated = validatedata(email, password, password2);
        if (!isvalidated){
            return;
        }
        
        createaccountinfirebase(email, password);





    }

     void createaccountinfirebase(String email, String password) {
        changeinprogress(true);


         firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
             @Override
             public void onComplete(@NonNull Task<AuthResult> task) {

                 if (task.isSuccessful()){

                     Toast.makeText(SignupActivity.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                    // firebaseAuth.getCurrentUser().sendEmailVerification();
                     Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                     intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                     startActivity(intent);
                     changeinprogress(false);
                     finish();


                 }
                 else {
                     Toast.makeText(SignupActivity.this,task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                     progressBar.setVisibility(View.GONE);
                     signup_btn.setVisibility(View.VISIBLE);

                 }

             }
         });




    }

    void  changeinprogress(boolean inprogress){

        if(inprogress){

            progressBar.setVisibility(View.VISIBLE);
            signup_btn.setVisibility(View.GONE);
        }
        else {

            progressBar.setVisibility(View.GONE);
            signup_btn.setVisibility(View.VISIBLE);
        }


    }

    boolean validatedata( String email, String password, String password2){
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

            editText_email.setError("Email is invalid");
            return false;
        }

        if(password.length()<6){

            editText_password.setError("Password length must be greater than 6");
            return false;
        }

        if (!password.equals(password2)){
            editTet_password2.setError("Password not Matched");
            return  false;
        }

        return true;

    }
}