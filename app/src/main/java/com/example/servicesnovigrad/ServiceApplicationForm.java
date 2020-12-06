package com.example.servicesnovigrad;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServiceApplicationForm extends AppCompatActivity{

    private EditText edtTxtFirstName;
    private EditText edtTxtLastName;
    private EditText edtTxtAddress;
    private CalendarView cldViewDOB;
    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private TextView caption1;
    private TextView caption2;
    private TextView caption3;
    private Button btnApplyService;
    private Spinner licenseSpinner;


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

        licenseSpinner = (Spinner) findViewById(R.id.license_spinner);
        licenseSpinner.setAdapter(new ArrayAdapter<LicenseType>(this,
                android.R.layout.simple_spinner_item, LicenseType.values()));
        licenseSpinner.setVisibility(View.VISIBLE);
        if(!((Service)getIntent().getSerializableExtra(ModifyService.EXTRA_SERVICE)).getServiceType().toLowerCase().contains("permis")){
            ((TextView)findViewById(R.id.textView16)).setVisibility(View.GONE);
            licenseSpinner.setVisibility(View.GONE);
        }

        cldViewDOB = (CalendarView) findViewById(R.id.calendarViewDOB);
        btnApplyService = (Button) findViewById(R.id.btn_apply_service);

        image1 = (ImageView) findViewById(R.id.image1);
        image2 = (ImageView)findViewById(R.id.image2);
        image3 = (ImageView)findViewById(R.id.image3);
        caption1 = (TextView) findViewById(R.id.caption1);
        caption2 = (TextView) findViewById(R.id.caption2);
        caption3 = (TextView) findViewById(R.id.caption3);

        if(((Service)getIntent().getSerializableExtra(ModifyService.EXTRA_SERVICE)).getRequiredDocument().contains(DocumentType.PHOTO))
        {
            image1.setVisibility(View.VISIBLE);
            caption1.setVisibility(View.VISIBLE);
        }
        else {
            image1.setVisibility(View.GONE);
            caption1.setVisibility(View.GONE);
        }
        if(((Service)getIntent().getSerializableExtra(ModifyService.EXTRA_SERVICE)).getRequiredDocument().contains(DocumentType.PREUVE_DE_DOMICILE))
        {
            image2.setVisibility(View.VISIBLE);
            caption2.setVisibility(View.VISIBLE);
        }
        else {
            image2.setVisibility(View.GONE);
            caption2.setVisibility(View.GONE);
        }
        if(((Service)getIntent().getSerializableExtra(ModifyService.EXTRA_SERVICE)).getRequiredDocument().contains(DocumentType.PREUVE_DE_STATUS))
        {
            image3.setVisibility(View.VISIBLE);
            caption3.setVisibility(View.VISIBLE);
        }
        else {
            image3.setVisibility(View.GONE);
            caption3.setVisibility(View.GONE);
        }

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
        ServiceApplication application = new ServiceApplication();


        FormDocument form = new FormDocument();
        form.setAddress(new Address());
        form.getAddress().setStreetName(edtTxtAddress.getText().toString());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String selectedDate = sdf.format(new Date(cldViewDOB.getDate()));
        form.setBirthday(selectedDate);
        form.setFirstName(edtTxtFirstName.getText().toString());
        form.setLastName(edtTxtLastName.getText().toString());
        if(((Service)getIntent().getSerializableExtra(ModifyService.EXTRA_SERVICE)).getServiceType().toLowerCase().contains("permis")){
           form.setLicenseType((LicenseType) licenseSpinner.getSelectedItem());
        }
        form.setDocType(DocumentType.FORM);


        application.setForm(form);
        application.setService((Service) getIntent().getSerializableExtra(ModifyService.EXTRA_SERVICE));
        application.setApplicant((Client)LoginForm.user);

        ((Branch)getIntent().getSerializableExtra(RegisterForm.EXTRA_BRANCH)).getApplicationList().add(application);
        DatabaseHelper.dbr = FirebaseDatabase.getInstance().getReference("Users/Employees/" + ((Branch)getIntent().getSerializableExtra(RegisterForm.EXTRA_BRANCH)).getEmployeeUserName());
        DatabaseHelper.dbr.child("mainBranch").setValue(((Branch)getIntent().getSerializableExtra(RegisterForm.EXTRA_BRANCH)));
    }

}
