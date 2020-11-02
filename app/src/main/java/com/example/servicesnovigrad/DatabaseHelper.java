package com.example.servicesnovigrad;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseHelper {
    public static DatabaseReference dbr;

    public static DatabaseReference setToPath(String path){
        return FirebaseDatabase.getInstance().getReference(path);
    }
}
