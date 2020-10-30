package com.example.servicesnovigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class AddServiceForm extends AppCompatActivity {
    private User adminAcc = null;
    private Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service_form);

        // on prend l'objet du compte admin de l'autre activité a l'aide d'un intent
        intent = getIntent();
        adminAcc = (AdministratorAccount)intent.getSerializableExtra("adminAccountObj");

        // on s'assure que la liste est vide au cas ou la RAM a encore des éléments de stocker
        Service.serviceList.clear();

        // on va remplir notre serviceList de tous les services stockés dans la database
        DatabaseHelper.dbr = FirebaseDatabase.getInstance().getReference("Services");
        DatabaseHelper.dbr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Service.serviceList.add(child.getValue(Service.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    public void addService(View view){
        TextView serviceName = findViewById(R.id.serviceNameId);
        TextView servicePrice = findViewById(R.id.servicePriceId);
        
        if(serviceName.getText().toString().length() == 0 
            ||servicePrice.getText().toString().length() == 0){ return; }

        Snackbar successSnackbar = Snackbar.make(serviceName,
                "Service \"" + serviceName.getText().toString() + "\" added",
                Snackbar.LENGTH_LONG);

        Service newService = new Service(serviceName.getText().toString(),
                            Integer.parseInt(servicePrice.getText().toString()));

        // cherche si le nouveau service existe déja en regardant s'il le nom a déja été utilisé
        boolean serviceAlreadyExists = false;
        for(Object service : Service.serviceList.toArray()){
            Service currentService = (Service)service;

            if(currentService.getServiceType().equals(newService.getServiceType())){
                serviceAlreadyExists = true;

                successSnackbar = Snackbar.make(serviceName,
                        "Service \"" + serviceName.getText().toString() + "\" already exists",
                        Snackbar.LENGTH_LONG);

                break;
            }
        }

        // si le service est nouveau, on l'ajoute a la liste et a la database
        if(!serviceAlreadyExists){
            Service.serviceList.add(newService);

            DatabaseHelper.dbr = FirebaseDatabase.getInstance().getReference("Services");
            DatabaseHelper.dbr.child(serviceName.getText().toString()).setValue(newService);
        }

        successSnackbar.show();
    }
    public void removeService(View view){
        TextView serviceName = (TextView)findViewById(R.id.serviceNameId);

        if(serviceName.getText().toString().length() == 0){ return; }

        Snackbar successSnackbar = Snackbar.make(serviceName,
                "Service \"" + serviceName.getText().toString() + "\" does not exist",
                Snackbar.LENGTH_LONG);

        // on vérifie si le service existe, si oui on le supprime
        for(Object service : Service.serviceList.toArray()){
            Service currentService = (Service)service;

            if(currentService.getServiceType().equals(serviceName.getText().toString())){
                Service.serviceList.remove(currentService);

                DatabaseHelper.dbr = FirebaseDatabase.getInstance()
                        .getReference("Services/" + currentService.getServiceType());
                DatabaseHelper.dbr.setValue(null);

                successSnackbar = Snackbar.make(serviceName,  "Service \"" + serviceName.getText().toString() + "\" deleted",
                        Snackbar.LENGTH_LONG);

                break;
            }
        }

        successSnackbar.show();
    }
    public void modifyService(View view){
        intent = new Intent(this, ModifyService.class);

        intent.putExtra("adminAccountObj", adminAcc);

        startActivity(intent);
    }
}