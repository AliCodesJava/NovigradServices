package com.example.servicesnovigrad;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Service implements Serializable {
    public static ArrayList<Service> serviceList = new ArrayList();
    private String serviceType;
    private int servicePrice; // as number of cents
    private ArrayList<DocumentType> requiredDocument;

    // For Firebase purposes
    public Service(){
        serviceType = "";
        servicePrice = 0;
        requiredDocument = new ArrayList<DocumentType>();
    }
    public Service(String serviceType, int servicePrice){
        this.serviceType = serviceType;
        this.servicePrice = servicePrice;
        requiredDocument = new ArrayList<DocumentType>();
    }
    public String getPriceString(){
        return this.getServicePrice()/100 +"."+this.getServicePrice()%100/10+""+this.getServicePrice()%10;
    }

    public String getServiceType() { return serviceType; }
    public int getServicePrice() { return servicePrice; }
    public ArrayList<DocumentType> getRequiredDocument() { return requiredDocument; }

    public void setServiceType(String serviceType) { this.serviceType = serviceType; }
    public void setServicePrice(int servicePrice) { this.servicePrice = servicePrice; }
    public void setRequiredDocument(ArrayList<DocumentType> requiredDocument) {
        this.requiredDocument = requiredDocument;
    }
    @Override
    public boolean equals(Object o){
        return this.getServiceType().equals(((Service)o).getServiceType());
    }
    public boolean addRequiredDoc(DocumentType documentType){ return requiredDocument.add(documentType); }
    public boolean removeRequiredDoc(DocumentType documentType){
        return requiredDocument.remove(documentType);
    }
    @NonNull
    @Override
    public String toString() {
        return serviceType;
    }
}