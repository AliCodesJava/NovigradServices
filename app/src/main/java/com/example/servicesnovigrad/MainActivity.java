package com.example.servicesnovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void openRegistrationFormOnClick(View view){
        startActivity(new Intent(this, register_form.class));
    }
    public void checkLogInInformationOnClick(View view){ //TODO
        startActivity(new Intent(this, register_form.class));
    }
}