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
    private TextView inputText;
    private TextView selectedServiceStatus;

    private Service currentService;

    private Snackbar validationMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_service);

        inputText = findViewById(R.id.inputId);
        selectedServiceStatus = findViewById(R.id.selectedServiceTextView);
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
        if(inputText.getText().toString().length() == 0){ return; }

        validationMsg = Snackbar.make(inputText,
                "DocumentType " + inputText.getText().toString() + " does not exist/already exists",
                Snackbar.LENGTH_LONG);

        for(DocumentType docType : DocumentType.values()){
            if(docType.toString().equals(inputText.getText().toString())
            && !currentService.getRequiredDocument()
            .contains(DocumentType.valueOf(inputText.getText().toString()))){

                DatabaseHelper.dbr = DatabaseHelper.setToPath("Services/" + currentService.getServiceType() + "/requiredDocuments");
                currentService.addRequiredDoc(DocumentType.valueOf(inputText.getText().toString()));
                DatabaseHelper.dbr.setValue(currentService.getRequiredDocument());

                selectedServiceStatus.setText("Selected service : " + currentService.getServiceType());

                validationMsg = Snackbar.make(inputText,
                        "DocumentType " + inputText.getText().toString() + " added",
                        Snackbar.LENGTH_LONG);

                break;
            }
        }

        validationMsg.show();

        Log.d("lol", "" + currentService.toString());
    }

    public void removeRequiredDocument(View view){
        if(currentService == null){ return; }
        if(inputText.getText().toString().length() == 0){ return; }

        validationMsg = Snackbar.make(inputText,
                "DocumentType " + inputText.getText().toString() + " does not exist",
                Snackbar.LENGTH_LONG);

        DocumentType[] documentTypes = DocumentType.values();
        for(int i = 0; i<documentTypes.length; i++){
            Log.d("pls", "yo");
            if(documentTypes[i].toString().equals(inputText.getText().toString())
             && currentService.getRequiredDocument()
             .contains(DocumentType.valueOf(inputText.getText().toString()))){
                DatabaseHelper.dbr = DatabaseHelper.setToPath("Services/" + currentService.getServiceType()
                        + "/requiredDocuments/" + i);
                DatabaseHelper.dbr.setValue(null);

                currentService.removeRequiredDoc(currentService.getRequiredDocument().get(i));

                /*DatabaseHelper.dbr = DatabaseHelper.setToPath("Services/"
                                        + currentService.getServiceType() + );
                DatabaseHelper.dbr.child(currentService.getServiceType()).setValue(currentService);*/

                validationMsg = Snackbar.make(inputText,
                        "DocumentType " + inputText.getText().toString() + " deleted",
                        Snackbar.LENGTH_LONG);

                break;
            }
        }

        validationMsg.show();

        Log.d("lol", "" + currentService.toString());
    }
}