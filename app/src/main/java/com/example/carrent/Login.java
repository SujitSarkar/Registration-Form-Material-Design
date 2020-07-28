package com.example.carrent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    ImageView logoImage;
    TextView logoName,sloganName;
    TextInputLayout logUserName,logPassword;
    Button loginBtn,logSign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Hide Status Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        logoImage = findViewById(R.id.logoImage);
        logoName = findViewById(R.id.logoName);
        sloganName = findViewById(R.id.sloganName);
        logUserName = findViewById(R.id.logUserName);
        logPassword = findViewById(R.id.logPassword);
        loginBtn = findViewById(R.id.loginBtn);
        logSign = findViewById(R.id.logSign);

        //Set username & password from sign up form...
        UserAndPasswordReceivedFromSignup();
    }

    private void UserAndPasswordReceivedFromSignup(){
        Intent intent = getIntent();
        String userFromSignUp = intent.getStringExtra("user");
        String passFromSignUp = intent.getStringExtra("pass");

        logUserName.getEditText().setText(userFromSignUp);
        logPassword.getEditText().setText(passFromSignUp);
    }

    private boolean validateName(){
        String val = logUserName.getEditText().getText().toString();
        if (val.isEmpty()){
            logUserName.setError("field Can't be empty");
            return false;
        }
        else{
            logUserName.setError(null);
            logUserName.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validatePassword(){
        String val = logPassword.getEditText().getText().toString();
        if (val.isEmpty()){
            logPassword.setError("field Can't be empty");
            return false;
        }
        else{
            logPassword.setError(null);
            logPassword.setErrorEnabled(false);
            return true;
        }
    }

    public void logSign(View view) {
        Intent intent = new Intent(Login.this, Signup.class);

        Pair[] pairs = new Pair[7];
        pairs[0] = new Pair<View, String>(logoImage,"logo_image");
        pairs[1] = new Pair<View, String>(logoName,"logo_text");
        pairs[2] = new Pair<View, String>(sloganName,"logo_desc");
        pairs[3] = new Pair<View, String>(logUserName,"user_tran");
        pairs[4] = new Pair<View, String>(logPassword,"pass_tran");
        pairs[5] = new Pair<View, String>(loginBtn,"login_tran");
        pairs[6] = new Pair<View, String>(logSign,"signup_tran");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this,pairs);
        startActivity(intent, options.toBundle());
        finish();
    }

    public void UserLogin(View view) {
        if (validateName() && validatePassword()){
            isUser();
        }
        else {
            return;
        }
    }

    private void isUser(){
        final String userEnteredUsername = logUserName.getEditText().getText().toString().trim();
        final String userEnteredPassword = logPassword.getEditText().getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Customer");

        //Query for matching between user entered UserName and database UserName...
        Query checkUser = reference.orderByChild("userName").equalTo(userEnteredUsername);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //If user entered wrong username then 2nd time give right username then this will remove the error message...
                logUserName.setError(null);
                logUserName.setErrorEnabled(false);

                if (snapshot.exists()){
                    //fetch Password from DB...
                    String passwordFromDB = snapshot.child(userEnteredUsername).child("password").getValue(String.class);

                    if (passwordFromDB.equals(userEnteredPassword)){
                        //If user entered wrong username then 2nd time give right username then this will remove the error message...
                        logPassword.setError(null);
                        logPassword.setErrorEnabled(false);

                        //Fetch Data from DB & assigned to a String...
                        String nameFromDB = snapshot.child(userEnteredUsername).child("name").getValue(String.class);
                        String userNameFromDB = snapshot.child(userEnteredUsername).child("userName").getValue(String.class);
                        String emailFromDB = snapshot.child(userEnteredUsername).child("email").getValue(String.class);
                        String phoneNoFromDB = snapshot.child(userEnteredUsername).child("phone").getValue(String.class);
                        String passFromDB = snapshot.child(userEnteredUsername).child("password").getValue(String.class);
                        String idFromDB = snapshot.child(userEnteredUsername).child("id").getValue(String.class);

                        //Transfer this data into UserProfile by intent, that we got from DB...
                        Intent intent = new Intent(getApplicationContext(),UserProfile.class);
                        intent.putExtra("name", nameFromDB);
                        intent.putExtra("username", userNameFromDB);
                        intent.putExtra("email", emailFromDB);
                        intent.putExtra("phone", phoneNoFromDB);
                        intent.putExtra("password", passFromDB);
                        intent.putExtra("id", idFromDB);
                        startActivity(intent);
                    }
                    else {
                        logPassword.setError("Wrong Password");
                        logPassword.requestFocus();
                    }
                }
                else {
                    logUserName.setError("No such user exist");
                    logUserName.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}