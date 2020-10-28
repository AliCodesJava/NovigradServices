package com.example.servicesnovigrad;

import java.util.ArrayList;

public class Service {
    public static ArrayList<Service> serviceList;
    private String serviceType;
    private short servicePrice;
    private ArrayList<DocumentType> requiredDocument;
    private ArrayList<String> requiredInformation;

    public Service(){}
    public Service(String serviceType, short servicePrice){
        serviceList = new ArrayList<Service>();
        this.serviceType = serviceType;
        this.servicePrice = servicePrice;
        requiredDocument = new ArrayList<DocumentType>();
        requiredInformation = new ArrayList<String>();
    }

    public String getServiceType() { return serviceType; }
    public short getServicePrice() { return servicePrice; }
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
}
