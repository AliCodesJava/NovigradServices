package com.example.servicesnovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;

import com.example.servicesnovigrad.adapters.ServiceListAdapter;
import com.example.servicesnovigrad.listeners.BtnClickListener;
import com.google.firebase.database.FirebaseDatabase;

public class ViewServices extends AppCompatActivity {
    private ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_services);
        final Employee employee = (Employee)getIntent().getSerializableExtra(RegisterForm.EXTRA_USER);
         adapter = new ServiceListAdapter(
                this,
                employee.getMainBranch().getServiceList(),
                employee,
                new BtnClickListener(){
                    @Override
                    public void onBtnClick(int position) {

                    }
                },
                new BtnClickListener(){
                    @Override
                    public void onBtnClick(int position) {
                        employee.getMainBranch().getServiceList().remove(position);
                        DatabaseHelper.dbr = FirebaseDatabase.getInstance().getReference("Users/Employees");
                        DatabaseHelper.dbr.child(employee.getUsername()).setValue(employee);
                        adapter.notifyDataSetChanged();
                    }
                },
                new BtnClickListener(){
                    @Override
                    public void onBtnClick(int position) {

                    }
                },
                new BtnClickListener(){
                    @Override
                    public void onBtnClick(int position) {

                    }
                }
        );
    }
}