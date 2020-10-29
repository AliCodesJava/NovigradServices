package com.example.servicesnovigrad;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;

public class Service {
    public static ArrayList<Service> serviceList;
    private String serviceType;
    private int servicePrice;
    private ArrayList<DocumentType> requiredDocument;
    private ArrayList<String> requiredInformation;

    public Service(){}
    public Service(String serviceType, int servicePrice){
        serviceList = new ArrayList<Service>();
        this.serviceType = serviceType;
        this.servicePrice = servicePrice;
        requiredDocument = new ArrayList<DocumentType>();
        requiredInformation = new ArrayList<String>();
    }

    public String getServiceType() { return serviceType; }
    public int getServicePrice() { return servicePrice; }
    public ArrayList<DocumentType> getRequiredDocument() { return requiredDocument; }
    public ArrayList<String> getRequiredInformation() { return requiredInformation; }

    public void setServiceType(String serviceType) { this.serviceType = serviceType; }
    public void setServicePrice(short servicePrice) { this.servicePrice = servicePrice; }

    public boolean addRequiredDoc(DocumentType documentType){ return requiredDocument.add(documentType); }
    public boolean removeRequiredDoc(DocumentType documentType){
        return requiredDocument.remove(documentType);
    }
    public boolean addRequiredInfo(String info){ return requiredInformation.add(info); }
    public boolean removeRequiredInfo(String info){ return requiredInformation.remove(info); }

    @NonNull
    @Override
    public String toString() {
        String serviceToString = "(" + serviceType + "," + servicePrice + ")";

        serviceToString += (Arrays.toString(requiredDocument.toArray()));
        serviceToString += (Arrays.toString(requiredInformation.toArray()));

        return serviceToString;
    }
}