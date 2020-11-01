package com.example.servicesnovigrad;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;

public class Service {
    public static ArrayList<Service> serviceList = new ArrayList();
    private String serviceType;
    private int servicePrice;
    private ArrayList<DocumentType> requiredDocument;
    private ArrayList<String> requiredInformation;

    // For Firebase purposes
    public Service(){
        serviceType = "";
        servicePrice = 0;
        requiredDocument = new ArrayList<DocumentType>();
        requiredInformation = new ArrayList<String>();
        requiredDocument = new ArrayList<DocumentType>();
        requiredInformation = new ArrayList<String>();
    }
    public Service(String serviceType, int servicePrice){
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
    public void setServicePrice(int servicePrice) { this.servicePrice = servicePrice; }
    public void setRequiredDocument(ArrayList<DocumentType> requiredDocument) {
        this.requiredDocument = requiredDocument;
    }
    public void setRequiredInformation(ArrayList<String> requiredInformation) {
        this.requiredInformation = requiredInformation;
    }

    public boolean addRequiredDoc(DocumentType documentType){ return requiredDocument.add(documentType); }
    public boolean removeRequiredDoc(DocumentType documentType){
        return requiredDocument.remove(documentType);
    }
    public boolean addRequiredInfo(String info){ return requiredInformation.add(info); }
    public boolean removeRequiredInfo(String info){ return requiredInformation.remove(info); }

    @NonNull
    @Override
    public String toString() {
        return ("(" + serviceType + "," + servicePrice +
                "," + (Arrays.toString(requiredDocument.toArray()) +
                "," + (Arrays.toString(requiredInformation.toArray()))) + ")");
    }
}