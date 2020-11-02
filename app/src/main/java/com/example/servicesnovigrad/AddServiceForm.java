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

import java.io.IOException;
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

        /*
            on s'assure que la liste est vide au cas ou la RAM
            a encore des éléments de stocker durant l'utilisation de l'app
        */
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
        TextView serviceName = findViewById(R.id.inputId);
        TextView servicePrice = findViewById(R.id.servicePriceId);
        String sPrice = servicePrice.getText().toString();
        String[] priceParts = sPrice.split("\\.", 2);
        int price;
        if(serviceName.getText().toString().length() == 0
                ||servicePrice.getText().toString().length() == 0){ Snackbar errorMessage = Snackbar.make(view, "Please fill in both fields to define your service. ", Snackbar.LENGTH_LONG);
            errorMessage.show(); }
        try{
            //checking the price format
            if(priceParts.length == 2)
                if(priceParts[1].length() == 2)
                    price = Integer.parseInt(priceParts[0])*100+Integer.parseInt(priceParts[1]);
                else
                    throw new Exception("The price should only 2 decimal digits.");
            else
                price = Integer.parseInt(priceParts[0])*100;
            addService(serviceName.getText().toString(), price);
            Snackbar successBar = Snackbar.make(view, "This service has successfully been added to the database.", Snackbar.LENGTH_LONG);
            successBar.show();
        }
        catch (NumberFormatException e){
            Snackbar errorMessage = Snackbar.make(view, "Please make sure your price is in the right format!" + priceParts[0] + " " + priceParts[1], Snackbar.LENGTH_LONG); //TODO make different kinds of inputs valid (,00 .00 etc.)
            errorMessage.show();
        }
        catch (Exception e){
            Snackbar errorMessage = Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG);
            errorMessage.show();
        }
    }
    public static void addService(String serviceName, int servicePrice) throws Exception {
        Service newService = new Service(serviceName,servicePrice);

        // cherche si le nouveau service existe déja en regardant s'il le nom a déja été utilisé
        for(Service service : Service.serviceList){
            if(service.getServiceType().equals(newService.getServiceType())){
                throw new Exception("Service \"" + serviceName + "\" already exists");
            }
        }

        // si le service est nouveau, on l'ajoute a la liste et a la database
        Service.serviceList.add(newService);

        DatabaseHelper.dbr = FirebaseDatabase.getInstance().getReference("Services");
        DatabaseHelper.dbr.child(serviceName).setValue(newService);
    }

    public void removeService(View view){
        TextView serviceName = findViewById(R.id.inputId);
        try{
            removeService(serviceName.getText().toString());
            Snackbar successBar = Snackbar.make(view, "You have successfully removed the service \"" + serviceName.getText().toString() + "\"", Snackbar.LENGTH_LONG);
            successBar.show();
        }
        catch (Exception e){
            Snackbar errorMessage = Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG);
            errorMessage.show();
        }
    }
    public static Service removeService(String serviceName) throws Exception {
        if(serviceName.length() == 0){ throw new Exception("Please write the name of the service you wish to remove. "); }

        // on vérifie si le service existe, si oui on le supprime
        for(Service currentService : Service.serviceList){

            if(currentService.getServiceType().equals(serviceName)){
                Service.serviceList.remove(currentService);

                DatabaseHelper.dbr = FirebaseDatabase.getInstance()
                        .getReference("Services/" + currentService.getServiceType());
                DatabaseHelper.dbr.setValue(null);
                return currentService;
            }
        }
        throw new Exception("There is no such service! ");
    }

    public void modifyService(View view){
        intent = new Intent(this, ModifyService.class);

        intent.putExtra("adminAccountObj", adminAcc);

        startActivity(intent);
    }
}