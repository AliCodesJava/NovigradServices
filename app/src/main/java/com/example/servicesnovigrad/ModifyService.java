package com.example.servicesnovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

public class ModifyService extends AppCompatActivity {
    private User adminAcc = null;
    private TextView input;
    private Service currentService = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_service);

        input = findViewById(R.id.inputId);

        Intent intent = getIntent();
        adminAcc = (User)intent.getSerializableExtra("adminAccountObj");
    }

    public void selectService(View view){
        TextView status = findViewById(R.id.selectedServiceTextView);

        for(Object service : Service.serviceList.toArray()){
            currentService = (Service)service;
            if(currentService.getServiceType().equals(input.getText().toString())){
                status.setText("Selected service : " + currentService.getServiceType());
                return;
            }
        }

        status.setText("This service does not exist.");

        Log.d("lol", "" + Arrays.toString(Service.serviceList.toArray()));
    }
    public void changeServiceName(View view){
        if(currentService == null){ return; }

        TextView input = findViewById(R.id.inputId);
        if(input.getText().toString().length() == 0){ return; }

        DatabaseHelper.dbr = FirebaseDatabase.getInstance()
                .getReference("Services/" + currentService.getServiceType());
        DatabaseHelper.dbr.setValue(null);

        currentService.setServiceType(input.getText().toString());

        DatabaseHelper.dbr = FirebaseDatabase.getInstance().getReference("Services/");
        DatabaseHelper.dbr.child(currentService.getServiceType()).setValue(currentService);

        TextView status = findViewById(R.id.selectedServiceTextView);
        status.setText("Selected service : " + currentService.getServiceType());

        Log.d("lol", "" + Arrays.toString(Service.serviceList.toArray()));
    }
    public void changeServicePrice(View view){
        if(currentService == null){ return; }

        TextView input = findViewById(R.id.inputId);
        int newPrice;
        try {
            newPrice = Integer.parseInt(input.getText().toString());
        }catch(NumberFormatException numberFormatException){
            return;
        }

        DatabaseHelper.dbr = FirebaseDatabase.getInstance()
                .getReference("Services/" + currentService.getServiceType());
        DatabaseHelper.dbr.setValue(null);

        currentService.setServicePrice(newPrice);

        DatabaseHelper.dbr = FirebaseDatabase.getInstance().getReference("Services/");
        DatabaseHelper.dbr.child(currentService.getServiceType()).setValue(currentService);

        TextView status = findViewById(R.id.selectedServiceTextView);
        status.setText("Selected service : " + currentService.getServiceType());

        Log.d("lol", "" + Arrays.toString(Service.serviceList.toArray()));
    }
    
    public void addRequiredDocument(View view){
        if(currentService == null){ return; }

        TextView input = findViewById(R.id.inputId);
        if(input.getText().toString().length() == 0){ return; }

        boolean enumInputExists = false;
        for(DocumentType docType : DocumentType.values()){
            if(docType.toString().equals(input.getText().toString())){
                enumInputExists = true;
                break;
            }
        }

        Snackbar statusSnackbar = Snackbar.make(input,
                "DocumentType " + input.getText().toString() + " does not exist/already exists",
                Snackbar.LENGTH_LONG);
        if(enumInputExists && !currentService.getRequiredDocument().contains(DocumentType.valueOf(input.getText().toString()))){
            statusSnackbar = Snackbar.make(input,
                    "DocumentType " + input.getText().toString() + " added",
                    Snackbar.LENGTH_LONG);

            DatabaseHelper.dbr = FirebaseDatabase.getInstance()
                    .getReference("Services/" + currentService.getServiceType());
            DatabaseHelper.dbr.setValue(null);

            currentService.addRequiredDoc(DocumentType.valueOf(input.getText().toString()));

            DatabaseHelper.dbr = FirebaseDatabase.getInstance().getReference("Services/");
            DatabaseHelper.dbr.child(currentService.getServiceType()).setValue(currentService);

            TextView status = findViewById(R.id.selectedServiceTextView);
            status.setText("Selected service : " + currentService.getServiceType());
        }
        statusSnackbar.show();

        Log.d("lol", "" + currentService.toString());
    }
}