package com.example.servicesnovigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class LoginAndWelcomeForm extends AppCompatActivity {
    private User user = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openRegistrationFormOnClick(View view) {
        startActivity(new Intent(this, RegisterForm.class));
    }

    public void checkLogInInformationOnClick(View view) {
        DatabaseHelper.dbr = FirebaseDatabase.getInstance().getReference("Users");
        DatabaseHelper.dbr.addListenerForSingleValueEvent(new ValueEventListener() {
            EditText username = findViewById(R.id.textUsername);
            EditText password = findViewById(R.id.textPassword);
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                AdministratorAccount admin;
                Client client;
                Employee employee;
                for (DataSnapshot child :
                dataSnapshot.getChildren()){
                    for (DataSnapshot child2 :
                            child.getChildren()) {
                        if(child.getKey().equals("AdministratorAccount")){
                            admin = child2.getValue(AdministratorAccount.class);
                            if(admin.getUsername().equals(username.getText().toString()) && admin.checkPassword(password.getText().toString())){
                                user = admin;
                            }
                        }
                        if(child.getKey().equals("Clients")){
                            client = child2.getValue(Client.class);
                            if(client.getUsername().equals(username.getText().toString()) && client.checkPassword(password.getText().toString())){
                                user = client;
                            }
                        }
                        if(child.getKey().equals("Employees")){
                            employee = child2.getValue(Employee.class);
                            if(employee.getUsername().equals(username.getText().toString()) && employee.checkPassword(password.getText().toString())){
                                user = employee;
                            }
                        }
                    }
                }
                if(user == null){
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                            "Invalid username/password combination, please try again, noob.",
                            Snackbar.LENGTH_LONG);
                    snackbar.show();
                    return;
                }

                Intent intent = new Intent(LoginAndWelcomeForm.this, Accueil.class);
                intent.putExtra(RegisterForm.EXTRA_USERNAME, user.getUsername());
                String role = user.getClass().getName();
                intent.putExtra(RegisterForm.EXTRA_ROLE, role.substring(role.lastIndexOf(".") + 1));
                user = null;
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

    }

    public boolean usernameExists(String username, DataSnapshot snapshot){
        User currentUser = new User("", "", "");
        for(DataSnapshot child : snapshot.getChildren()){
            currentUser.setUsername(child.getValue(User.class).getUsername());

            Log.d("exists: ", currentUser.getUsername());

            if(currentUser.getUsername().equals(username)){
                return true;
            }
        }

        return false;
    }
}


/*


DatabaseHelper.dbr.child(((TextView)findViewById(R.id.textUsername)).getText().toString())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    User user;
                    boolean idExists = false;

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot child : snapshot.getChildren()) {

                        }

                        if (!idExists) {
                            Snackbar snackbar = Snackbar.make(findViewById(R.id.textUsername),
                                    "Username or password not found, " + ((TextView) findViewById(R.id.textUsername))
                                            .getText(), Snackbar.LENGTH_LONG);
                            snackbar.show();
                            return;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });


 */