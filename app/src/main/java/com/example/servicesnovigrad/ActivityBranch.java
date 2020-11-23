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

import org.w3c.dom.Text;

import java.time.DayOfWeek;
import java.util.ArrayList;

public class ActivityBranch extends AppCompatActivity {
    private Employee user;
    private Intent intent;
    private Snackbar statusMsg;
    private TextView info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch);

        intent = getIntent();
        user = (Employee)LoginForm.user;

        Spinner spinnerDay = (Spinner)findViewById(R.id.spinnerId);
        spinnerDay.setAdapter(new ArrayAdapter<DayOfWeek>(this,
                android.R.layout.simple_spinner_item, DayOfWeek.values()));
        Spinner serviceSpinner = (Spinner)findViewById(R.id.spinnerId2);
        serviceSpinner.setAdapter(new ArrayAdapter<Service>(this,
                android.R.layout.simple_spinner_item, Service.serviceList));
        info = ((TextView)findViewById(R.id.schedule));
        info.setSingleLine(false);
        info.setText(user.getMainBranch().getSchedule().toString());
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
                    "Some or all fields may be invalid, please try again.",
                    Snackbar.LENGTH_LONG);
        }
        else if(user.getMainBranch() == null){
            user.resetBranch();

            statusMsg = Snackbar.make(findViewById(android.R.id.content),
                    "Created Main Branch with given address",
                    Snackbar.LENGTH_LONG);
        }
        else{
            statusMsg = Snackbar.make(findViewById(android.R.id.content),
                    "Modified Main Branch with given information",
                    Snackbar.LENGTH_LONG);
        }

        Address branchAddress = new Address(streetNum.getText().toString(),
                Integer.parseInt(appNum.getText().toString()), streetName.getText().toString(),
                city.getText().toString(), postalCode.getText().toString());
        user.getMainBranch().setAddress(branchAddress);
        user.getMainBranch().setApplicationList(new ArrayList<ServiceApplication>());

        DatabaseHelper.dbr = DatabaseHelper.setToPath("Users/Employees/" + user.getUsername());
        DatabaseHelper.dbr.child("mainBranch").setValue(user.getMainBranch());

        statusMsg.show();
    }
    public void removeServices(View view){
        Intent newIntent = new Intent(this, ViewServices.class);
        newIntent.putExtra(RegisterForm.EXTRA_USER, user);
        startActivity(newIntent);
    }
    public void addSchedule(View view){
        EditText time1 = findViewById(R.id.editTextTime);
        EditText time2 = findViewById(R.id.editTextTime2);
        Spinner spin = findViewById(R.id.spinnerId);
        try{
            user.getMainBranch().getSchedule().addOpenHours(
                    (DayOfWeek) spin.getSelectedItem(),
                    (Integer.parseInt(""+time1.getText().charAt(0)+time1.getText().charAt(1))) * 60
                            + Integer.parseInt(""+time1.getText().charAt(3)+time1.getText().charAt(4)) ,
                    (Integer.parseInt(""+time2.getText().charAt(0)+time2.getText().charAt(1))) * 60
                            + Integer.parseInt(""+time2.getText().charAt(3)+time2.getText().charAt(4)));

            DatabaseHelper.dbr = DatabaseHelper.setToPath("Users/Employees/" + user.getUsername());
            DatabaseHelper.dbr.child("mainBranch").setValue(user.getMainBranch());

            Snackbar.make(findViewById(android.R.id.content),
                        "Schedule has been succesfully added !",
                        Snackbar.LENGTH_LONG).show();
        }
        catch (Exception e){
            Snackbar.make(view, "Please make sure your times are in the right format. Ex: 02:30",
                          Snackbar.LENGTH_LONG).show();
        }
        info.setText(user.getMainBranch().getSchedule().toString());
    }

    public void addService(View view){
        Spinner spin = findViewById(R.id.spinnerId2);
        Service serviceToAdd = (Service)spin.getSelectedItem();
        if(!user.getMainBranch().getServiceList().contains(serviceToAdd)){
            user.getMainBranch().getServiceList().add(serviceToAdd);

            DatabaseHelper.dbr = DatabaseHelper.setToPath("Users/Employees/" + user.getUsername());
            DatabaseHelper.dbr.child("mainBranch").setValue(user.getMainBranch());

            Snackbar.make(findViewById(android.R.id.content),
                    "Service has been succesfully added !",
                    Snackbar.LENGTH_LONG).show();
            return;
        }

        Snackbar.make(findViewById(android.R.id.content),
                "Service is already offered by the branch !",
                Snackbar.LENGTH_LONG).show();
    }
}