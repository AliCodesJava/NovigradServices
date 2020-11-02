package com.example.servicesnovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

public class ModifyService extends AppCompatActivity {
    private TextView inputText;
    private TextView selectedServiceStatus;

    private Service currentService;

    private Snackbar validationMsg;

    private Spinner documentTypeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_service);

        inputText = findViewById(R.id.inputId);
        selectedServiceStatus = findViewById(R.id.selectedServiceTextView);

        documentTypeSpinner = findViewById(R.id.requiredDocumentSpinner);
        documentTypeSpinner.setAdapter(new ArrayAdapter<DocumentType>(this,
                android.R.layout.simple_spinner_item, DocumentType.values()));
    }

    public void selectService(View view){
        for(Service service : Service.serviceList){
            currentService = service;
            if(currentService.getServiceType().equals(inputText.getText().toString())){
                selectedServiceStatus.setText("Selected service : " + currentService.getServiceType());
                return;
            }
        }
        selectedServiceStatus.setText("This service does not exist.");

        Log.d("lol", "" + Arrays.toString(Service.serviceList.toArray()));
    }

    public void changeServiceName(View view){
        if(currentService == null){ return; }
        if(inputText.getText().toString().length() == 0){ return; }

        /*
            ID du noeud de notre service dans la database est le nom d'utilisateur
            on doit alors supprimé le noeud, faire la modification dans la servicesList
            puis remettre le noeud modifié (venant de la liste) car nous ne pouvons
            pas changé l'ID d'un noeud
        */
        DatabaseHelper.dbr = DatabaseHelper.setToPath("Services/" + currentService.getServiceType());
        DatabaseHelper.dbr.setValue(null);
        currentService.setServiceType(inputText.getText().toString());
        DatabaseHelper.dbr = DatabaseHelper.setToPath("Services");
        DatabaseHelper.dbr.child(currentService.getServiceType()).setValue(currentService);

        selectedServiceStatus.setText("Selected service : " + currentService.getServiceType());

        Log.d("lol", "" + Arrays.toString(Service.serviceList.toArray()));
    }
    public void changeServicePrice(View view){
        if(currentService == null){ return; }

        int newPrice;
        try { newPrice = Integer.parseInt(inputText.getText().toString()); }
        catch(NumberFormatException numberFormatException){ return; }

        // on change le prix dans la database puis dans le servicesList
        DatabaseHelper.dbr = DatabaseHelper.setToPath("Services/" + currentService.getServiceType() + "/servicePrice");
        DatabaseHelper.dbr.setValue(newPrice);
        currentService.setServicePrice(newPrice);

        selectedServiceStatus.setText("Selected service : " + currentService.getServiceType());

        Log.d("lol", "" + Arrays.toString(Service.serviceList.toArray()));
    }

    public void addRequiredDocument(View view){
        if(currentService == null){ return; }

        validationMsg = Snackbar.make(inputText,
                "DocumentType " + inputText.getText().toString() + " is already a required document of "
                 + currentService.getServiceType(), Snackbar.LENGTH_LONG);

        DocumentType newDocType = (DocumentType)documentTypeSpinner.getSelectedItem();
        if(!currentService.getRequiredDocument().contains(newDocType)){
            DatabaseHelper.dbr = DatabaseHelper.setToPath("Services/" + currentService.getServiceType()
                                 + "/requiredDocuments");
            currentService.addRequiredDoc(newDocType);
            DatabaseHelper.dbr.setValue(currentService.getRequiredDocument());

            validationMsg = Snackbar.make(inputText,
                    "DocumentType " + newDocType.toString() + " added",
                    Snackbar.LENGTH_LONG);
        }

        validationMsg.show();

        Log.d("lol", "" + currentService.serviceList);
    }
    public void removeRequiredDocument(View view){
        if(currentService == null){ return; }

        DocumentType removedDocType = (DocumentType)documentTypeSpinner.getSelectedItem();

        validationMsg = Snackbar.make(inputText,
                "DocumentType " + removedDocType.toString() + " does not exist",
                Snackbar.LENGTH_LONG);

        if(currentService.getRequiredDocument().contains(removedDocType)){
            DatabaseHelper.dbr = DatabaseHelper.setToPath("Services/" + currentService.getServiceType()
                    + "/requiredDocuments");
            currentService.removeRequiredDoc(removedDocType);
            DatabaseHelper.dbr.setValue(currentService.getRequiredDocument());

            validationMsg = Snackbar.make(inputText,
                    "DocumentType " + removedDocType.toString() + " removed",
                    Snackbar.LENGTH_LONG);
        }

        validationMsg.show();

        Log.d("lol", "" + currentService.serviceList);
    }
}