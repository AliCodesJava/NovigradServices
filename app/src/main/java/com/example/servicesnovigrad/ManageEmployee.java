package com.example.servicesnovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.FirebaseDatabase;

public class ManageEmployee extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_employee);
    }

    public void deleteEmployeeAccount(View view){
        TextView textView = (TextView)findViewById(R.id.inputId);
        if(!textView.getText().toString().equals("")){
            DatabaseHelper.dbr = FirebaseDatabase.getInstance().getReference("Users/Employees")
                    .child(textView.getText().toString());
            DatabaseHelper.dbr.setValue(null);}
        else{
            Snackbar problemBar = Snackbar.make(view, "Please enter the username of the user to delete ", Snackbar.LENGTH_LONG);
            problemBar.show();
        }
    }
}