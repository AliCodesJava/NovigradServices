package com.example.servicesnovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterForm extends AppCompatActivity {
    public static String EXTRA_USERNAME = "com.example.servicesnovigrad.EXTRA_USERNAME";
    public static String EXTRA_ROLE = "com.example.servicesnovigrad.EXTRA_ROLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_form);
    }

    public void submitOnClick(View view) {
        EditText password1 = findViewById(R.id.passwordEditText);
        EditText password2 = findViewById(R.id.password2EditText);
        EditText firstName = findViewById(R.id.firstNameEditText);
        EditText lastName = findViewById(R.id.lastNameEditText);
        EditText email = findViewById(R.id.emailAddressEditText);
        EditText username = findViewById(R.id.usernameEditText);

        //checking if the inputs are correct
        if (username.getText().toString().length() == 0
                || firstName.getText().toString().length() == 0
                || lastName.getText().toString().length() == 0
                || email.getText().toString().length() == 0
                || password1.getText().toString().length() == 0) {
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Please fill the fields above. Thank you, " + firstName.getText(), Snackbar.LENGTH_LONG);
            snackbar.show();
            return;
        }
        //checks if the passwords entered match
        if (!password1.getText().toString().equals(password2.getText().toString())) {
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                                        "Passwords do not match, " + firstName.getText(),
                                        Snackbar.LENGTH_LONG);
            snackbar.show();
            return;
        }
        //checks if the email is a correctly formatted
        String sEmail = email.getText().toString();
        if (!sEmail.contains("@")
                || !sEmail.contains(".")
                || !(sEmail.lastIndexOf(".") > sEmail.indexOf("@") + 1)
                || !(sEmail.lastIndexOf(".") < sEmail.length() - 1)
                || !(sEmail.lastIndexOf("@") > 0)) {
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                                        "Email is invalid, " + firstName.getText(),
                                        Snackbar.LENGTH_LONG);
            snackbar.show();
            return;
        }


        CheckBox employee = findViewById(R.id.employeeCheckBox);
        User user;
        if (employee.isChecked()) {
            user = new Employee(username.getText().toString(), password1.getText().toString(), email.getText().toString(),
                                firstName.getText().toString(), lastName.getText().toString(), null);

            /*
            Pointage vers le path voulu pour stocker les données au bon endroit
            */
            DatabaseHelper.dbr = FirebaseDatabase.getInstance().getReference("Users/Employees");
        }
        else {
            user = new Client(username.getText().toString(), password1.getText().toString(), email.getText().toString(),
                            firstName.getText().toString(), lastName.getText().toString());

            DatabaseHelper.dbr = FirebaseDatabase.getInstance().getReference("Users/Clients");
        }

        /*
           Création d'un enfant dans le path pointé par dbr
           avec combinaison Key : Value tel que username : user
        */
        DatabaseHelper.dbr.child(user.getUsername()).setValue(user);

        Intent intent = new Intent(this, LoginForm.class);
        intent.putExtra(EXTRA_USERNAME, user.getUsername());
        String role = user.getClass().getName();
        intent.putExtra(EXTRA_ROLE, role.substring(role.lastIndexOf(".") + 1));
        startActivity(intent);
    }
}