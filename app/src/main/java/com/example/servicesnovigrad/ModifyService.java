package com.example.servicesnovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.servicesnovigrad.adapters.ApplicationListAdapter;
import com.example.servicesnovigrad.adapters.DocumentsTypeAdapter;
import com.example.servicesnovigrad.adapters.ServiceListAdapter;
import com.example.servicesnovigrad.listeners.BtnClickListener;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;

public class ModifyService extends AppCompatActivity {
    private User currentUser;
    private Intent intent;

    private TextView inputText;
    private TextView selectedServiceStatus;
    private TextView userMessageTxtView;

    private Snackbar validationMsg;
    private Spinner documentTypeSpinner;

    private String clientUserName;

    private ListView listView;
    private ArrayAdapter adapter;
    private TextView welcomeMessagetxtView;
    private TextView clientTypeTxtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_service);

        /*
            on s'assure que la liste est vide au cas ou la RAM
            a encore des éléments de stocker durant l'utilisation de l'app
        */

        // on prend l'objet du compte admin de l'autre activité a l'aide d'un intent
        intent = getIntent();
        currentUser = (User) LoginForm.user;

        clientUserName = currentUser.getUsername();

        //clientUser = new Client("donald", "password", "d@t.ca", "Donald", "Trump");

        listView = (ListView) findViewById(R.id.lstView_services);
        userMessageTxtView = (TextView)findViewById(R.id.txtView_user_message) ;
        welcomeMessagetxtView = (TextView)findViewById(R.id.txtView_welcome) ;
        clientTypeTxtView = (TextView)findViewById(R.id.txtView_account_type) ;

        welcomeMessagetxtView.setText(String.format("Hi %s, welcome to Service Novigrad", (clientUserName == null ? "administrator" : clientUserName)));
        String userType = currentUser.getClass().toString();
        clientTypeTxtView.setText(String.format("Logged in as %s", userType.substring(userType.lastIndexOf(".") + 1)));

        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        final LayoutInflater inflater = this.getLayoutInflater();
        if(currentUser instanceof Employee){
            {   //TEMPORARY, FOR TESTING PURPOSES TODO Remove when client can apply
                new ServiceApplication(new Client("donald", "password", "d@t.ca", "Donald", "Trump"), Service.serviceList.get(0));
                new ServiceApplication(new Client("donald1", "password", "d@t.ca", "Donald", "Trump"), Service.serviceList.get(1));
                new ServiceApplication(new Client("donald2", "password", "d@t.ca", "Donald", "Trump"), Service.serviceList.get(0));
            }
            adapter = new ApplicationListAdapter(
                    this,
                    ServiceApplication.applications,
                    currentUser,
                    new BtnClickListener() {
                        @Override
                        public void onBtnClick(int position) {
                            String serviceName = ServiceApplication.applications.get(position).getService().getServiceType();
                            String applicantName = ServiceApplication.applications.get(position).getApplicant().getUsername();
                            userMessageTxtView.setVisibility(View.VISIBLE);
                            userMessageTxtView.setText("You have approved the application of " + applicantName + " for the service :" + serviceName + ".");
                            userMessageTxtView.setTextColor(Color.GREEN);
                            userMessageTxtView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                            //todo remove from DB
                            ServiceApplication.applications.remove(position);
                            adapter.notifyDataSetChanged();
                        }
                    },
                    new BtnClickListener() {
                        @Override
                        public void onBtnClick(int position) {
                            String serviceName = ServiceApplication.applications.get(position).getService().getServiceType();
                            String applicantName = ServiceApplication.applications.get(position).getApplicant().getUsername();
                            userMessageTxtView.setVisibility(View.VISIBLE);
                            userMessageTxtView.setText("You have rejected the application of " + applicantName + " for the service :" + serviceName + ".");
                            userMessageTxtView.setTextColor(Color.RED);
                            userMessageTxtView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                            //todo remove from DB
                            ServiceApplication.applications.remove(position);
                            adapter.notifyDataSetChanged();

                        }
                    },
                    new BtnClickListener() {
                        @Override
                        public void onBtnClick(int position) {
                            String serviceName = ServiceApplication.applications.get(position).getService().getServiceType();
                            String applicantFirstName = ServiceApplication.applications.get(position).getApplicant().getFirstName();
                            String applicantLastName = ServiceApplication.applications.get(position).getApplicant().getLastName();
                            String applicantEmail = ServiceApplication.applications.get(position).getApplicant().getEmailAddress();

                            final View userDialogView = inflater.inflate(R.layout.application_view, null);
                            dialog.setTitle("Application:");
                            dialog.setView(userDialogView);
                            ((TextView)userDialogView.findViewById(R.id.service_name)).setText("Service: " + serviceName);
                            ((TextView)userDialogView.findViewById(R.id.applicant_name)).setText("Applicant: " + applicantFirstName + " " + applicantLastName);
                            ((TextView)userDialogView.findViewById(R.id.applicant_email)).setText("Email: " + applicantEmail);
                            dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            dialog.show();
                        }
                    }
            );
            userMessageTxtView.setVisibility(View.GONE);
            listView.setAdapter(adapter);
        }
        else{

            if(Service.serviceList.size() == 0){
                userMessageTxtView.setVisibility(View.VISIBLE);
                userMessageTxtView.setTextColor(Color.BLACK);
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        (float) 1.0
                );
                userMessageTxtView.setLayoutParams(param);
                userMessageTxtView.setTextSize(TypedValue.COMPLEX_UNIT_SP,36);
                userMessageTxtView.setText("Sorry, we couldn't find any services.");
                listView.setVisibility(View.GONE);
            }
            else{
                adapter = new ServiceListAdapter(
                        this,
                        Service.serviceList,
                        currentUser,
                        new BtnClickListener() {
                            @Override
                            public void onBtnClick(int position) {
                                if(currentUser instanceof Client){
                                    String serviceName = Service.serviceList.get(position).getServiceType();
                                    userMessageTxtView.setVisibility(View.VISIBLE);
                                    userMessageTxtView.setText("You applied to " + serviceName + " service");
                                    userMessageTxtView.setTextColor(Color.GREEN);
                                    userMessageTxtView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14); //todo
                                }
                            }
                        },
                        new BtnClickListener() {
                            @Override
                            public void onBtnClick(int position) {
                                String serviceName = Service.serviceList.get(position).getServiceType();

                                /*
                                 **************************** DELETE FROM DATABASE ALSO *******************
                                 */
                                try {
                                    AddServiceForm.removeService(serviceName);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                userMessageTxtView.setVisibility(View.VISIBLE);
                                userMessageTxtView.setText("The service " + serviceName + " has been removed.");
                                userMessageTxtView.setTextColor(Color.GREEN);
                                userMessageTxtView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT
                                );
                                userMessageTxtView.setLayoutParams(param);
                                adapter.notifyDataSetChanged();
                            }
                        },
                        new BtnClickListener() {
                            @Override
                            public void onBtnClick(int position) {
                                final Service currentService = Service.serviceList.get(position);

                                final View userDialogView = inflater.inflate(R.layout.service_list_edit, null);
                                dialog.setTitle("Edit service");
                                dialog.setView(userDialogView);

                                final EditText editNameText = (EditText) userDialogView.findViewById(R.id
                                        .edtTxt_service_name);
                                editNameText.setText(currentService.getServiceType());
                                final EditText editPriceText = (EditText) userDialogView.findViewById(R.id
                                        .edtTxt_service_price);
                                editPriceText.setText(currentService.getPriceString());

                                dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        userMessageTxtView.setVisibility(View.VISIBLE);
                                        try{
                                            changeService(currentService, editNameText.getText().toString(), editPriceText.getText().toString());
                                            userMessageTxtView.setText("The service " + currentService.getServiceType() + " has been edited.");
                                            userMessageTxtView.setTextColor(Color.GREEN);
                                        }
                                        catch (Exception e){
                                            userMessageTxtView.setText("The service " + currentService.getServiceType() + " has not been edited.");
                                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                            userMessageTxtView.setTextColor(Color.RED);
                                        }
                                        userMessageTxtView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                                    }
                                });
                                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                dialog.show();
                            }
                        },
                        new BtnClickListener() {
                            @Override
                            public void onBtnClick(int position) {
                                final Service currentService = Service.serviceList.get(position);

                                final View userDialogView = getDocuments(currentService);

                                final CheckBox chkBox = (CheckBox) userDialogView.findViewById(R.id
                                        .chkBox_document_added);
                        /*
                        chkBox.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View view) {
                                addOrRemoveServiceDocument(currentService);
                            }
                        });

                         */

                                dialog.setTitle("Add or Edit required document");
                                dialog.setView(userDialogView);
                                dialog.setNegativeButton("Submit", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                dialog.show();
                            }
                        }
                );
                userMessageTxtView.setVisibility(View.GONE);
                listView.setAdapter(adapter);
            }
        }


        /*
        inputText = findViewById(R.id.inputId);
        selectedServiceStatus = findViewById(R.id.selectedServiceTextView);

        documentTypeSpinner = findViewById(R.id.requiredDocumentSpinner);
        documentTypeSpinner.setAdapter(new ArrayAdapter<DocumentType>(this,
                android.R.layout.simple_spinner_item, DocumentType.values()));

         */
    }

    public void changeService(Service service, String newName, String newPrice)throws Exception{
        if(service == null){ return; }
        changeServiceName(service, newName);
        changeServicePrice(service, newPrice);
        Toast.makeText(getApplicationContext(), "This service has successfully been changed in the database.",
                Toast.LENGTH_SHORT).show();
        adapter.notifyDataSetChanged();
    }

    public void changeServiceName(Service service, String newServiceName){
        /*
            ID du noeud de notre service dans la database est le nom d'utilisateur
            on doit alors supprimé le noeud, faire la modification dans la servicesList
            puis remettre le noeud modifié (venant de la liste) car nous ne pouvons
            pas changé l'ID d'un noeud
        */

        DatabaseHelper.dbr = DatabaseHelper.setToPath("Services/" + service.getServiceType());
        DatabaseHelper.dbr.setValue(null);
        service.setServiceType(newServiceName);
        DatabaseHelper.dbr = DatabaseHelper.setToPath("Services");
        DatabaseHelper.dbr.child(newServiceName).setValue(service);
        adapter.notifyDataSetChanged();
    }

    public void changeServicePrice(Service service, String newServicePrice)throws Exception{
        String[] priceParts = newServicePrice.split("\\.");
        int newPrice;
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
            DatabaseHelper.dbr = DatabaseHelper.setToPath("Services/" + service.getServiceType() + "/servicePrice");
            DatabaseHelper.dbr.setValue(newPrice);
            service.setServicePrice(newPrice);
        }
        catch (NumberFormatException e){
            throw new Exception("Please make sure your price is in the right format! (ex: 123.45)");
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public ListView getDocuments(Service service){
        final ListView requiredDocs = new ListView(this);

        try{
            final ArrayList<DocumentType> docTypes = new ArrayList<DocumentType>();
            Collections.addAll(docTypes, DocumentType.values());
            final DocumentsTypeAdapter docTypeAdapter = new DocumentsTypeAdapter(this, docTypes, service);
            requiredDocs.setAdapter(docTypeAdapter);
            return requiredDocs;
        }catch(Exception ex){
            Toast.makeText(getApplicationContext(), "Error Occured in getServiceDocuments.",
                    Toast.LENGTH_SHORT).show();
            return requiredDocs;
        }
    }
}