package com.example.servicesnovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.time.DayOfWeek;

public class ActivityBranch extends AppCompatActivity {
    Employee user = null;
    Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch);

        intent = getIntent();
        user = (Employee)intent.getSerializableExtra(RegisterForm.EXTRA_USER);

        Spinner spinner = (Spinner) findViewById(R.id.spinnerId);
        spinner.setAdapter(new ArrayAdapter<DayOfWeek>(this, android.R.layout.simple_spinner_item, DayOfWeek.values()));
    }

    public void submit(View view) {
        /*EditText appNum = findViewById(R.id.appNumber);
        EditText streetNum = findViewById(R.id.streetNumber);
        EditText streetName = findViewById(R.id.streetName);
        EditText postalCode = findViewById(R.id.postalCode);
        EditText city = findViewById(R.id.city);
        EditText openHrs = findViewById(R.id.openHoursId);
        EditText closingHrs = findViewById(R.id.closingHours);*/

        /*Address branchAddress = new Address(streetNum,
                    Short.parseShort(appNum.getText().toString()), streetName.getText().toString(),
                    city.getText().toString(), postalCode.getText().toString());*/


    }
}



