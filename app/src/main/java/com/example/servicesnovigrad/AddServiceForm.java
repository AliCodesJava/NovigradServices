package com.example.servicesnovigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class AddServiceForm extends AppCompatActivity {
    private User adminAcc = null;
    private Intent intent = null;
    private ArrayList<Service> servList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service_form);

        servList = new ArrayList<Service>();

        intent = getIntent();
        adminAcc = (AdministratorAccount)intent.getSerializableExtra("adminAccountObj");

        DatabaseHelper.dbr = FirebaseDatabase.getInstance().getReference("Services");
        DatabaseHelper.dbr.addListenerForSingleValueEvent(new ValueEventListener() {
            TextView serviceType = findViewById(R.id.serviceNameId);
            TextView servicePrice = findViewById(R.id.servicePriceId);
            
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren()) {
                    Service service = child.getValue(Service.class);

                    servList.add(service);

                    Log.d("boo", "" + service);
                }
                Service.serviceList = servList;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Recreate serviceList error", databaseError.getMessage());
            }
        });

    }

    public void addService(View view){
        Log.d("boo", "" + Arrays.toString(servList.toArray()));

        TextView serviceName = findViewById(R.id.serviceNameId);
        TextView servicePrice = findViewById(R.id.servicePriceId);

        Service newService = new Service(serviceName.getText().toString(),
                            Integer.parseInt(servicePrice.getText().toString()));
        Service.serviceList.add(newService);

        //if(Service.serviceList.contains()){
            DatabaseHelper.dbr = FirebaseDatabase.getInstance().getReference("Services");
            DatabaseHelper.dbr.child(serviceName.getText().toString()).setValue(newService);
        //}
    }
    public void removeService(View view){
        /*TextView textView = (TextView)findViewById(R.id.serviceNameId);
        DatabaseHelper.dbr = FirebaseDatabase.getInstance().getReference("Users/Employees")
                .child(textView.getText().toString());
        DatabaseHelper.dbr.setValue(null);*/
    }
    public void modifyService(View view){
        intent = new Intent(this, ModifyService.class);

        intent.putExtra("adminAccountObj", adminAcc);

        startActivity(intent);
    }
}