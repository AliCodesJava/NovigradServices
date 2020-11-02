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
    }
    public void changeServicePrice(View view){
        if(currentService == null){ return; }

        String sPrice = inputText.getText().toString();
        String[] priceParts = sPrice.split("\\.", 2);
        int newPrice = 0;
        try{
            //checking the price format
            if(priceParts.length == 2)
                if(priceParts[1].length() == 2)
                    newPrice = Integer.parseInt(priceParts[0])*100+Integer.parseInt(priceParts[1]);
                else
                    throw new Exception("The price should only 2 decimal digits.");
            else
                newPrice = Integer.parseInt(priceParts[0])*100;

            // on change le prix dans la database puis dans le servicesList
            DatabaseHelper.dbr = DatabaseHelper.setToPath("Services/" + currentService.getServiceType() + "/servicePrice");
            DatabaseHelper.dbr.setValue(newPrice);
            currentService.setServicePrice(newPrice);

            selectedServiceStatus.setText("Selected service : " + currentService.getServiceType());

            validationMsg = Snackbar.make(view,
                            "This service has successfully been added to the database."
                            , Snackbar.LENGTH_LONG);
            validationMsg.show();
        }
        catch (NumberFormatException e){
            Snackbar errorMessage = Snackbar.make(view,
                                "Please make sure your price is in the right format!"
                                    + priceParts[0] + " " + priceParts[1],
                                    Snackbar.LENGTH_LONG);
            errorMessage.show();
        }
        catch (Exception e){
            Snackbar errorMessage = Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG);
            errorMessage.show();
        }
    }

    public void addRequiredDocument(View view){
        if(currentService == null){ return; }

        DocumentType newDocType = (DocumentType)documentTypeSpinner.getSelectedItem();

        validationMsg = Snackbar.make(inputText,
                "DocumentType " + newDocType.toString() + " is already a required document of "
                 + currentService.getServiceType(), Snackbar.LENGTH_LONG);

        if(!currentService.getRequiredDocument().contains(newDocType)){
            DatabaseHelper.dbr = DatabaseHelper.setToPath("Services/" + currentService.getServiceType()
                                 + "/requiredDocument");
            currentService.addRequiredDoc(newDocType);
            DatabaseHelper.dbr.setValue(currentService.getRequiredDocument());

            validationMsg = Snackbar.make(inputText,
                    "DocumentType " + newDocType.toString() + " added",
                    Snackbar.LENGTH_LONG);
        }

        validationMsg.show();
    }
    public void removeRequiredDocument(View view){
        if(currentService == null){ return; }

        DocumentType removedDocType = (DocumentType)documentTypeSpinner.getSelectedItem();

        validationMsg = Snackbar.make(inputText,
                "DocumentType " + removedDocType.toString() + " does not exist",
                Snackbar.LENGTH_LONG);

        if(currentService.getRequiredDocument().contains(removedDocType)){
            DatabaseHelper.dbr = DatabaseHelper.setToPath("Services/" + currentService.getServiceType()
                    + "/requiredDocument");
            currentService.removeRequiredDoc(removedDocType);
            DatabaseHelper.dbr.setValue(currentService.getRequiredDocument());

            validationMsg = Snackbar.make(inputText,
                    "DocumentType " + removedDocType.toString() + " removed",
                    Snackbar.LENGTH_LONG);
        }

        validationMsg.show();
    }

    public void addRequiredInformation(View view){
        if(currentService == null){ return; }
        if(inputText.getText().toString().length() == 0){ return; }

        String newInfo = inputText.getText().toString();

        validationMsg = Snackbar.make(inputText,
                "Information " + newInfo + " is already a required information of "
                + currentService.getServiceType(), Snackbar.LENGTH_LONG);

        if(!currentService.getRequiredInformation().contains(newInfo)){
            DatabaseHelper.dbr = DatabaseHelper.setToPath("Services/" + currentService.getServiceType()
                    + "/requiredInformation");
            currentService.addRequiredInfo(newInfo);
            DatabaseHelper.dbr.setValue(currentService.getRequiredInformation());

            validationMsg = Snackbar.make(inputText,
                    "Information " + newInfo.toString() + " added",
                    Snackbar.LENGTH_LONG);
        }

        validationMsg.show();
    }
    public void removeRequiredInformation(View view){
        if(currentService == null){ return; }
        if(inputText.getText().toString().length() == 0){ return; }

        String removedInfo = inputText.getText().toString();

        validationMsg = Snackbar.make(inputText,
                "Information " + removedInfo + " does not exist in "
                + currentService.getServiceType() + " service", Snackbar.LENGTH_LONG);

        if(currentService.getRequiredInformation().contains(removedInfo)){
            DatabaseHelper.dbr = DatabaseHelper.setToPath("Services/" + currentService.getServiceType()
                                 + "/requiredInformation");
            currentService.removeRequiredInfo(removedInfo);
            DatabaseHelper.dbr.setValue(currentService.getRequiredInformation());

            validationMsg = Snackbar.make(inputText,
                    "Information " + removedInfo + " removed",
                    Snackbar.LENGTH_LONG);
        }

        validationMsg.show();
    }
}