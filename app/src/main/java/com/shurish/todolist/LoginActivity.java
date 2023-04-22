package com.shurish.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    TextView create_account_text;
    EditText editText_email;
    EditText editText_password;
    EditText editText_password2;

    Button login_btn;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();


        create_account_text = findViewById(R.id.createaccount_text);
        editText_email = findViewById(R.id.email_login);
        editText_password = findViewById(R.id.password_login);

        login_btn = findViewById(R.id.login_btn);
        progressBar = findViewById(R.id.progressbar_login);


        create_account_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });



    }

    void  login(){


        String email = editText_email.getText().toString();
        String password = editText_password.getText().toString();


        boolean isvalidate = validate(email, password);
        if (!isvalidate){
            return;
        }


        logininacccountinfirebase(email, password);


    }

    Boolean validate(String email, String password){

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

            editText_email.setError("Email is invalid");
            return false;
        }

        if(password.length()<6) {

            editText_password.setError("Password length must be greater than 6");
            return false;

        }

        return true;


    }


      void logininacccountinfirebase( String email, String password){

          changeinprogress(true);

          FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
          firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
              @Override
              public void onComplete(@NonNull Task<AuthResult> task) {


                  if (task.isSuccessful()){
                      Toast.makeText(LoginActivity.this, "Welcome Back", Toast.LENGTH_SHORT).show();
                      Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                      intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                      startActivity(intent);
                      changeinprogress(false);
                      finish();

                  }
                  else {
                      Toast.makeText(LoginActivity.this,task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                      progressBar.setVisibility(View.GONE);
                      login_btn.setVisibility(View.VISIBLE);
                  }

              }
          });




      }



      void  changeinprogress(boolean inprogress){

        if (inprogress){

            progressBar.setVisibility(View.VISIBLE);
            login_btn.setVisibility(View.GONE);
        }

        else {

            progressBar.setVisibility(View.GONE);
            login_btn.setVisibility(View.VISIBLE);


        }

      }
}