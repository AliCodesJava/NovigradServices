package com.example.servicesnovigrad;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
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
    private static final int IMAGE_RESULT1 = 1;
    private static final int IMAGE_RESULT2 = 2;
    private static final int IMAGE_RESULT3 = 3;
    private ImageDocument doc1;
    private ImageDocument doc2;
    private ImageDocument doc3;
    private File img1;
    private File img2;
    private File img3;

    {
        try {
            img1 = File.createTempFile("images", "jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    {
        try {
            img2 = File.createTempFile("images", "jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    {
        try {
            img3 = File.createTempFile("images", "jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


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


        ((TextView)findViewById(R.id.textView16)).setVisibility(View.VISIBLE);
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

    }


    /**
     *
     *  A IMPLEMENTER
     *
     */

    public static StorageReference imageStorage = FirebaseStorage.getInstance().getReference();

    public void btnAddImg1(View view){
        Intent i = new Intent(
                Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, IMAGE_RESULT1);
    }
    public void btnAddImg2(View view){
        Intent i = new Intent(
                Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, IMAGE_RESULT2);
    }
    public void btnAddImg3(View view){
        Intent i = new Intent(
                Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, IMAGE_RESULT3);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data !=null){
            if(requestCode == IMAGE_RESULT1){
                //image 1
                Uri image = data.getData();
                String imagePath = "images/"+LoginForm.user.getUsername()+"/"+"photo";
                doc1 = new ImageDocument(DocumentType.PHOTO, imagePath);
                StorageReference ref = imageStorage.child(imagePath);
                ref.putFile(image).addOnCompleteListener(
                        new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                updateImg();
                            }
                        }
                );
            }
            else if(requestCode == IMAGE_RESULT2){
                //image 2
                Uri image = data.getData();
                String imagePath = "images/"+LoginForm.user.getUsername()+"/"+"domicile";
                doc2 = new ImageDocument(DocumentType.PREUVE_DE_DOMICILE, imagePath);
                StorageReference ref = imageStorage.child(imagePath);
                ref.putFile(image).addOnCompleteListener(
                        new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                updateImg();
                            }
                        }
                );
            }
            else if(requestCode == IMAGE_RESULT3){
                //image 3
                Uri image = data.getData();
                String imagePath = "images/"+LoginForm.user.getUsername()+"/"+"status";
                doc3 = new ImageDocument(DocumentType.PREUVE_DE_STATUS, imagePath);
                StorageReference ref = imageStorage.child(imagePath);
                ref.putFile(image).addOnCompleteListener(
                        new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                updateImg();
                            }
                        }
                );
            }
        }

    }
    public void updateImg() {
        try {
            // image 1
            if(doc1 != null && image1.getVisibility() == View.VISIBLE)
            {
                img1 = File.createTempFile("images", "jpg");
                StorageReference ref = imageStorage.child(doc1.getDocName());
                ref.getFile(img1).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        ImageView imageView = (ImageView) findViewById(R.id.image1);
                        imageView.setImageURI(Uri.fromFile(img1));
                    }
                });
            }
            if(doc2 != null && image2.getVisibility() == View.VISIBLE)
            {
                img2 = File.createTempFile("images", "jpg");
                StorageReference ref = imageStorage.child(doc2.getDocName());
                ref.getFile(img2).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        ImageView imageView = (ImageView) findViewById(R.id.image2);
                        imageView.setImageURI(Uri.fromFile(img2));
                    }
                });
            }
            if(doc3 != null && image3.getVisibility() == View.VISIBLE)
            {
                img3 = File.createTempFile("images", "jpg");
                StorageReference ref = imageStorage.child(doc3.getDocName());
                ref.getFile(img3).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        ImageView imageView = (ImageView) findViewById(R.id.image3);
                        imageView.setImageURI(Uri.fromFile(img3));
                    }
                });
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
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

        if(edtTxtAddress.getText().toString().equals("") || edtTxtFirstName.getText().toString().equals("") || edtTxtLastName.getText().toString().equals("")){
            Snackbar.make(view, "Please make sure to fill in the blanks", Snackbar.LENGTH_LONG).show();
            return;
        }
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

        if(image1.getVisibility() == View.VISIBLE)
        {
            if(doc1 == null){
                Snackbar.make(view, "Please make sure to upload all of the required documents", Snackbar.LENGTH_LONG).show();
                return;
            }
            application.getImageDocMap().put(DocumentType.PHOTO.toString(), doc1);
        }
        if(image2.getVisibility() == View.VISIBLE)
        {
            if(doc2 == null){
                Snackbar.make(view, "Please make sure to upload all of the required documents", Snackbar.LENGTH_LONG).show();
                return;
            }
            application.getImageDocMap().put(DocumentType.PREUVE_DE_DOMICILE.toString(), doc2);
        }
        if(image3.getVisibility() == View.VISIBLE)
        {
            if(doc3 == null){
                Snackbar.make(view, "Please make sure to upload all of the required documents", Snackbar.LENGTH_LONG).show();
                return;
            }
            application.getImageDocMap().put(DocumentType.PREUVE_DE_STATUS.toString(), doc3);
        }

        application.setForm(form);
        application.setService((Service) getIntent().getSerializableExtra(ModifyService.EXTRA_SERVICE));
        application.setApplicant((Client)LoginForm.user);

        ((Branch)getIntent().getSerializableExtra(RegisterForm.EXTRA_BRANCH)).getApplicationList().add(application);
        DatabaseHelper.dbr = FirebaseDatabase.getInstance().getReference("Users/Employees/" + ((Branch)getIntent().getSerializableExtra(RegisterForm.EXTRA_BRANCH)).getEmployeeUserName());
        DatabaseHelper.dbr.child("mainBranch").setValue(((Branch)getIntent().getSerializableExtra(RegisterForm.EXTRA_BRANCH)));
        Snackbar.make(view, "Application sent!", Snackbar.LENGTH_LONG).show();
    }

}
