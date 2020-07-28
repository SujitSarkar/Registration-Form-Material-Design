package com.example.carrent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class

Signup extends AppCompatActivity {
    //Firebase database
    FirebaseDatabase database;
    DatabaseReference rootReference;
    DatabaseReference customerReference;

    ImageView logoImage;
    TextView logoName,sloganName;
    TextInputLayout regUserName,regPassword, regName, regPhoneNo, regEmail;
    Button signupBtn,signLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove Status Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);

        logoImage = findViewById(R.id.logoImage);
        logoName = findViewById(R.id.logoName);
        sloganName = findViewById(R.id.sloganName);
        regUserName = findViewById(R.id.regUserName);
        regPassword = findViewById(R.id.regPassword);
        signupBtn = findViewById(R.id.signupBtn);
        signLog = findViewById(R.id.signLog);
        regName = findViewById(R.id.regName);
        regPhoneNo = findViewById(R.id.regPhoneNo);
        regEmail = findViewById(R.id.regEmail);

        //Firebase Database create Root reference...
        database = FirebaseDatabase.getInstance();
        rootReference = database.getReference();
        customerReference = rootReference.child("Customer");

    }

    private boolean validateName(){
        String val = regName.getEditText().getText().toString();
        if (val.isEmpty()){
            regName.setError("Field can't be empty");
            return false;
        }
        else{
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validateUsername(){
        String val = regUserName.getEditText().getText().toString();
        //String noWhiteSpace = "(?=\\S+$)";

        if (val.isEmpty()){
            regUserName.setError("Field can't be empty");
            return false;
        } else if(val.length()>15){
            regUserName.setError("Username too long");
            return false;
        } /*else if (!val.matches(noWhiteSpace)) {
            regUserName.setError("White space are not allowed");
            return false;
        }*/
        else{
            regUserName.setError(null);
            regUserName.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validateEmail(){
        String val = regEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()){
            regEmail.setError("Field can't be empty");
            return false;
        } else if (!val.matches(emailPattern)){
            regEmail.setError("Invalid email address");
            return false;
        }
        else{
            regEmail.setError(null);
            regEmail.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validatePhoneNo(){
        String val = regPhoneNo.getEditText().getText().toString();

        if (val.isEmpty()){
            regPhoneNo.setError("Field can't be empty");
            return false;
        }
        else{
            regPhoneNo.setError(null);
            regPhoneNo.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validatePassword(){
        String val = regPassword.getEditText().getText().toString();

        String passwordVal = "^" +
                //"(?=.*[0-9])" +        //at least 1 digit
                "(?=.*[a-z])" +        //at least 1 lower case letter
                //"(?=.*[A-Z])" +        //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +       //any letter
                //"(?=.*[@#$%^&+=])" +     //at least 1 special character
                "(?=\\S+$)" +            //no white spaces
                ".{4,}" +                //at least 4 characters
                "$";

        if (val.isEmpty()) {
            regPassword.setError("Field can't be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            regPassword.setError("At least 4 characters, 1 lower case letter, no white space,");
            return false;
        } else {
            regPassword.setError(null);
            regPassword.setErrorEnabled(false);
            return true;
        }
    }


    public void signLog(View view) {
        Intent intent = new Intent(Signup.this, Login.class);

        Pair[] pairs = new Pair[7];
        pairs[0] = new Pair<View, String>(logoImage,"logo_image");
        pairs[1] = new Pair<View, String>(logoName,"logo_text");
        pairs[2] = new Pair<View, String>(sloganName,"logo_desc");
        pairs[3] = new Pair<View, String>(regUserName,"user_tran");
        pairs[4] = new Pair<View, String>(regPassword,"pass_tran");
        pairs[5] = new Pair<View, String>(signupBtn,"login_tran");
        pairs[6] = new Pair<View, String>(signLog,"signup_tran");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Signup.this,pairs);
        startActivity(intent, options.toBundle());
        finish();
    }


    public void userSignup(View view) {

        //check form validation...
        if (validateName() && validateUsername() && validateEmail() && validatePhoneNo() && validatePassword()){

            //Get input text from user and set them to String variable...
            final String userName = regUserName.getEditText().getText().toString();
            final String password = regPassword.getEditText().getText().toString();
            final String name = regName.getEditText().getText().toString();
            final String phone = regPhoneNo.getEditText().getText().toString();
            final String email = regEmail.getEditText().getText().toString();


            //check duplicate username with DB...
            if (!userName.isEmpty()){
                Query checkUser = customerReference.orderByChild("userName").equalTo(userName);
                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            regUserName.setError("This user name is already taken, try different one");
                        }
                        else {
                            /*
                            //Verify phone number...
                            Intent intent = new Intent(getApplicationContext(),VerifyPhoneNo.class);
                            intent.putExtra("phn",phone);
                            startActivity(intent);*/


                            //Get unique ID from Firebase for particular Customer...
                            String id = customerReference.push().getKey();
                            //Set value into firebase database...
                            CustomerInfo customerInfo = new CustomerInfo(id,userName,name,phone,email,password);
                            customerReference.child(userName).setValue(customerInfo);

                            Toast.makeText(getApplicationContext(), "Sign Up Successful", Toast.LENGTH_LONG).show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(Signup.this, Login.class);
                                    intent.putExtra("user",userName);
                                    intent.putExtra("pass",password);
                                    startActivity(intent);
                                }
                            },3000);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }else {
                return;
            }
        }
        else{
            return;

        }
    }
}