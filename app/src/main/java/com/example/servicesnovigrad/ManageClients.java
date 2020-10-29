package com.example.servicesnovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

public class ManageClients extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_clients);
    }

    public void deleteClientAccount(View view){
        TextView textView = (TextView)findViewById(R.id.serviceNameId);
        DatabaseHelper.dbr = FirebaseDatabase.getInstance().getReference("Users/Clients")
                            .child(textView.getText().toString());
        DatabaseHelper.dbr.setValue(null);
    }
}