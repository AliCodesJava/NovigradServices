package com.example.servicesnovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AdminMenu extends AppCompatActivity {
    private User adminAcc = null;
    private Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);

        intent = getIntent();

        adminAcc = (User)intent.getSerializableExtra("adminAccountObj");

        TextView welcomeMsg = findViewById(R.id.adminInfo2);
        welcomeMsg.setText(welcomeMsg.getText().toString()
                            .replace("administrator", adminAcc.getUsername()));
    }

    public void manageServices(View view){
        intent = new Intent(this, AddServiceForm.class);

        intent.putExtra("adminAccountObj", adminAcc);
        
        startActivity(intent);
    }
    public void manageClients(View view){
        intent = new Intent(this, ManageClients.class);

        intent.putExtra("adminAccountObj", adminAcc);

        startActivity(intent);
    }
    public void manageEmployees(View view){
        intent = new Intent(this, ManageEmployee.class);

        intent.putExtra("adminAccountObj", adminAcc);

        startActivity(intent);
    }
}