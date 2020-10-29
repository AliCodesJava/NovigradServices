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

        name.setText(intent.getStringExtra(RegisterForm.EXTRA_USERNAME));
        type.setText(intent.getStringExtra(RegisterForm.EXTRA_ROLE));
    }
}