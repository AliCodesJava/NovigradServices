package com.example.servicesnovigrad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ServiceApplicationForm extends AppCompatActivity {

    private EditText edtTxtFirstName;
    private EditText edtTxtLastName;
    private EditText edtTxtAddress;
    private CalendarView cldViewDOB;
    private ImageView imgViewAddDocs;
    private ImageView imgViewPhotoID;
    private Button btnApplyService;

    private Service service;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_application_form);

        Intent intent = getIntent();
        currentUser = (User) intent.getSerializableExtra(RegisterForm.EXTRA_USER);
        String clientUserName = currentUser.getUsername();

        service = (Service) intent.getSerializableExtra(ModifyService.EXTRA_SERVICE);

        edtTxtFirstName = (EditText)findViewById(R.id.lastNameEditText_service);
        edtTxtLastName = (EditText)findViewById(R.id.firstNameEditText_service);
        edtTxtAddress = (EditText)findViewById(R.id.addressEditText);

        cldViewDOB = (CalendarView) findViewById(R.id.calendarViewDOB);
        imgViewAddDocs = (ImageView) findViewById(R.id.imgAddDocs);
        imgViewPhotoID = (ImageView)findViewById(R.id.imgPhotoID);
        btnApplyService = (Button) findViewById(R.id.btn_apply_service);

        if(currentUser != null && currentUser instanceof Client){
            edtTxtFirstName.setText(((Client) currentUser).getFirstName());
            edtTxtLastName.setText(((Client) currentUser).getLastName());
        }

        /*
        if(service.getServiceType() == "Pièce d'identité avec photo"){
            imgViewPhotoID.setVisibility(View.VISIBLE);
        }
        else{
            imgViewPhotoID.setVisibility(View.GONE);
        }
        
         */
    }


    /**
     *
     *  A IMPLEMENTER
     *
     */

    // Lorsque l'utilisateur clique sur le icon des documents
    public void addDocument(View view){

    }

    // Lorsque l'utilisateur clique sur le icon du photo id
    public void addPhotoID(View view){

    }

    /**
     * Lorsque l'utilisateur clique sur apply
     * Information necessaire comme le user et service sont deja disponible et initialiser
     * dans onCreate()
     * les variables des input sont deja initialiser
     */
    public void applyService(View view){

    }

}
