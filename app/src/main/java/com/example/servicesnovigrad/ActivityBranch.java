package com.example.servicesnovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.servicesnovigrad.adapters.ServiceListAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.FirebaseDatabase;

import java.time.DayOfWeek;
import java.util.ArrayList;

public class ActivityBranch extends AppCompatActivity {
    Employee user = null;
    Intent intent = null;
    Snackbar statusMsg = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch);

        intent = getIntent();
        user = (Employee)intent.getSerializableExtra(RegisterForm.EXTRA_USER);

        Spinner spinnerDay = (Spinner)findViewById(R.id.spinnerId);
        spinnerDay.setAdapter(new ArrayAdapter<DayOfWeek>(this,
                android.R.layout.simple_spinner_item, DayOfWeek.values()));
        Spinner serviceSpinner = (Spinner)findViewById(R.id.spinnerId2);
        serviceSpinner.setAdapter(new ArrayAdapter<Service>(this,
                android.R.layout.simple_spinner_item, Service.serviceList));
    }

    public void submitBranch(View view) {
        EditText streetNum = findViewById(R.id.streetNum);
        EditText appNum = findViewById(R.id.appNum);
        EditText streetName = findViewById(R.id.streetName);
        EditText city = findViewById(R.id.city);
        EditText postalCode = findViewById(R.id.postalCode);

        if(streetNum.getText().toString().length() == 0 ||
           appNum.getText().toString().length() == 0 ||
           streetName.getText().toString().length() == 0 ||
           city.getText().toString().length() == 0 ||
           postalCode.getText().toString().length() == 0
        ){
            statusMsg = Snackbar.make(findViewById(android.R.id.content),
                    "Invalid username/password combination, please try again.",
                    Snackbar.LENGTH_LONG);
        }
        else if(user.getMainBranch() == null){
            user.resetBranch();

            Address branchAddress = new Address(streetNum.getText().toString(),
                    Integer.parseInt(appNum.getText().toString()), streetName.getText().toString(),
                    city.getText().toString(), postalCode.getText().toString());
            user.getMainBranch().setAddress(branchAddress);
            user.getMainBranch().setSchedule(new WeeklySchedule());
            user.getMainBranch().getSchedule().addOpenHours(DayOfWeek.FRIDAY, 0, 300);
            user.getMainBranch().setApplicationList(new ArrayList<ServiceApplication>());

<<<<<<< HEAD
            DatabaseHelper.dbr = DatabaseHelper.setToPath("Users/Employees/" + user.getUsername());
            DatabaseHelper.dbr.child("mainBranch").setValue(user.getMainBranch());
=======
        Address branchAddress = new Address(streetNum.getText().toString(),
                Integer.parseInt(appNum.getText().toString()), streetName.getText().toString(),
                city.getText().toString(), postalCode.getText().toString());
        user.getMainBranch().setAddress(branchAddress);
        user.getMainBranch().setApplicationList(new ArrayList<ServiceApplication>());
>>>>>>> c825d984b01bcf536a1e55cd007f787139eb89e7

            statusMsg = Snackbar.make(findViewById(android.R.id.content),
                    "Created Main Branch with given address",
                    Snackbar.LENGTH_LONG);
        }
        else{
            statusMsg = Snackbar.make(findViewById(android.R.id.content),
                    "Modified Main Branch with given information",
                    Snackbar.LENGTH_LONG);
        }

        statusMsg.show();
    }
    public void addSchedule(View view){
        EditText time1 = findViewById(R.id.editTextTime);
        EditText time2 = findViewById(R.id.editTextTime2);
        Spinner spin = findViewById(R.id.spinnerId);
        user.getMainBranch().getSchedule().addOpenHours(
                (DayOfWeek) spin.getSelectedItem(),(Integer.parseInt(""+time1.getText().charAt(0)+time1.getText().charAt(1))) * 60
                                                + Integer.parseInt(""+time1.getText().charAt(3)+time1.getText().charAt(4)) ,
                                (Integer.parseInt(""+time2.getText().charAt(0)+time2.getText().charAt(1))) * 60
                                                + Integer.parseInt(""+time2.getText().charAt(3)+time2.getText().charAt(4)));

        DatabaseHelper.dbr = DatabaseHelper.setToPath("Users/Employees/" + user.getUsername());
        DatabaseHelper.dbr.child("mainBranch").setValue(user.getMainBranch());
    }
}