package com.example.servicesnovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Accueil extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        Intent intent = getIntent();

        TextView name = findViewById(R.id.nameTextView);
        TextView type = findViewById(R.id.accountTypeTextView);
        User currentUser = (User)intent.getSerializableExtra(RegisterForm.EXTRA_USER);
        name.setText(currentUser.getUsername());
        String userType = currentUser.getClass().toString();
        type.setText(userType.substring(userType.lastIndexOf(".") + 1));
    }
}