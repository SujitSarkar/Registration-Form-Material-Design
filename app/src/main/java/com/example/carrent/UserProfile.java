package com.example.carrent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserProfile extends AppCompatActivity {

    TextInputEditText fullName2,userEmail,userPhone;
    TextView fullName,userName;

    private  String user_name,full_name,user_email,user_phone,user_password,user_id;
    //Create Database Reference...
    DatabaseReference customerReference = FirebaseDatabase.getInstance().getReference("Customer");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Hide Status Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_profile);

        fullName2 = findViewById(R.id.fullName2);
        userEmail = findViewById(R.id.userEmail);
        userPhone = findViewById(R.id.userPhone);
        fullName = findViewById(R.id.fullName);
        userName = findViewById(R.id.userName);

        //Show All Data in fields...
        ShowAllUserData();
    }

    private void ShowAllUserData(){
        //Receive data from Login class by Intent...
        Intent intent = getIntent();
        user_name = intent.getStringExtra("username");
        full_name = intent.getStringExtra("name");
        user_email = intent.getStringExtra("email");
        user_phone = intent.getStringExtra("phone");
        user_password = intent.getStringExtra("password");
        user_id = intent.getStringExtra("id");

        //Set data to fields...
        fullName.setText(full_name);
        userName.setText(user_name);
        fullName2.setText(full_name);
        userEmail.setText(user_email);
        userPhone.setText(user_phone);

    }

    public void UserProfileUpdate(View view) {
        //Create Database Reference...
        //DatabaseReference customerReference = FirebaseDatabase.getInstance().getReference("Customer");

        final String editedName = fullName2.getText().toString();
        final String editedEmail = userEmail.getText().toString();
        final String editedPhone = userPhone.getText().toString();

       CustomerInfo customerInfo = new CustomerInfo(user_id,user_name,editedName,editedPhone,editedEmail,user_password);
       customerReference.child(user_name).setValue(customerInfo);

        Toast.makeText(UserProfile.this, "Updated Successfully", Toast.LENGTH_LONG).show();

        fullName.setText(editedName);
    }

}