package com.example.servicesnovigrad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EmployeeMenu extends AppCompatActivity {
    private User employeeAccount = null;
    private Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_menu);

        intent = getIntent();
        employeeAccount = (User)intent.getSerializableExtra(RegisterForm.EXTRA_USER);

        TextView welcomeMsg = findViewById(R.id.employee_info);
        welcomeMsg.setText(welcomeMsg.getText().toString()
                .replace("sir", employeeAccount.getUsername()));
    }

    public void manageBranch(View view){
        intent = new Intent(this, ActivityBranch.class);

        intent.putExtra(RegisterForm.EXTRA_USER, employeeAccount);

        startActivity(intent);
    }

    public void manageServices(View view){
        intent = new Intent(this, ModifyService.class);

        intent.putExtra(RegisterForm.EXTRA_USER, employeeAccount);

        startActivity(intent);
    }
}
