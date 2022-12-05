package com.example.newfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private EditText email, user, password, name;
    private Button register;
    private TextView already,shOrt;
    private DatabaseReference reference;
    private FirebaseAuth auth;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // link our ui components with the java code
        email = findViewById(R.id.email_ID);
        user = findViewById(R.id.username_ID);
        password = findViewById(R.id.password_ID);
        name =  findViewById(R.id.name_ID);
        already = findViewById(R.id.alredy_text_ID);
        reference = FirebaseDatabase.getInstance().getReference();
        shOrt = findViewById(R.id.short_ID);
        auth = FirebaseAuth.getInstance();
        register = findViewById(R.id.register_ID);
        pd = new ProgressDialog(this);
        already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
        // setting up when clicking the register button 
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get strings from editText
                String txtEmail = email.getText().toString();
                String txtUser = user.getText().toString();
                String txtfname = name.getText().toString();
                String txtPassword = password.getText().toString();
                // checking fields if they valid to regester
                if( TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtfname) ||
                        TextUtils.isEmpty(txtUser) || TextUtils.isEmpty(txtPassword)){
                    Toast.makeText(RegisterActivity.this, "Empty Entries !", Toast.LENGTH_SHORT).show();
                }else if(!TextUtils.isEmpty(txtPassword) && txtPassword.length()<6){
                  shOrt.setVisibility(View.VISIBLE);
                }else registerUser(txtUser,txtfname, txtEmail, txtPassword);
            }
        });

    }
    // method to register our user
    private void registerUser(String user, String name , String email, String password) {
        // to add waiting item while task is running
        pd.setMessage("Please wait");
        pd.show();
        auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                // to save our users and their names and passwords
                HashMap<String, Object> map = new HashMap<>();
                map.put("name",name);
                map.put("email",email);
                map.put("username",user);
                map.put("id", auth.getCurrentUser().getUid());

                reference.child("users").child(auth.getCurrentUser().getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            pd.dismiss(); // to stop the progress dialog //
                            Toast.makeText(RegisterActivity.this, "Created successfully, update the profile for est experience !",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this,CreateValid.class);
                            // to stop the current activity task and kill the activity, user can't return to this activity
                           intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss(); // to stop the progress dialog //
                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}