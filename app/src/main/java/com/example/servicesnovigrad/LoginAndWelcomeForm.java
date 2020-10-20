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

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!usernameExists(username.getText().toString(), dataSnapshot)) return;

                Snackbar snackbar = Snackbar.make(findViewById(R.id.textUsername),
                        "Username or password not found, " + username, Snackbar.LENGTH_LONG);
                snackbar.show();
                return;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        startActivity(new Intent(this, Accueil.class));
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