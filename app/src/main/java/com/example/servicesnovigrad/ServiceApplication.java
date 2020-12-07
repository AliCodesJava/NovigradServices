package com.example.servicesnovigrad;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class ServiceApplication implements Serializable {
    private Service service;
    private Client applicant;
    private HashMap<String, ImageDocument> imageDocMap;
    private FormDocument form;

    public ServiceApplication(){
        imageDocMap = new HashMap<String, ImageDocument>();
    }
    public ServiceApplication(Client applicant, Service service) {
        this.service = service;
        this.applicant = applicant;
        imageDocMap = new HashMap<String, ImageDocument>();
    }


    public Client getApplicant() {
        return applicant;
    }

    public void setApplicant(Client applicant) {
        this.applicant = applicant;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public HashMap<String, ImageDocument> getImageDocMap() {
        return imageDocMap;
    }

    public void setImageDocMap(HashMap<String, ImageDocument> imageDocMap) {
        this.imageDocMap = imageDocMap;
    }

    public FormDocument getForm() {
        return form;
    }

    public void setForm(FormDocument form) {
        this.form = form;
    }
}
