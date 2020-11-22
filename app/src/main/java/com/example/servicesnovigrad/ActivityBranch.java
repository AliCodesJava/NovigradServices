package com.example.servicesnovigrad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

        Spinner spinner = findViewById(R.id.spinner);
        spinner.setAdapter(new ArrayAdapter<DayOfWeek>(this, android.R.layout.simple_spinner_item, DayOfWeek.values()));
    }

    public void submit(View view){
        TextView appNum = findViewById(R.id.appNumber);
        TextView streetNum = findViewById(R.id.streetNumber);
        TextView streetName = findViewById(R.id.streetName);
        TextView postalCode = findViewById(R.id.postalCode);
        TextView city = findViewById(R.id.city);
        TextView openHrs = findViewById(R.id.openHoursId);
        TextView closingHrs = findViewById(R.id.closingHours);

        /*Address branchAddress = new Address(streetNum,
                    Short.parseShort(appNum.getText().toString()), streetName.getText().toString(),
                    city.getText().toString(), postalCode.getText().toString());*/

        WeeklySchedule ws = new WeeklySchedule();


        short appartmentNum, openHours, closingHours;
        try{
            openHours = Short.parseShort(openHrs.getText().toString());
            closingHours = Short.parseShort(closingHrs.getText().toString());
            appartmentNum = Short.parseShort(appNum.getText().toString());
        }catch(NumberFormatException e){

        }
    }
}