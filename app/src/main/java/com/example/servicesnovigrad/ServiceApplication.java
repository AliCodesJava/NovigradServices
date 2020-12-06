package com.example.servicesnovigrad;

import java.util.ArrayList;
import java.util.HashMap;

public class ServiceApplication {
    private Service service;
    private Client applicant;
    private HashMap<String, ImageDocument> imageDocMap;
    private FormDocument form;

    public ServiceApplication(){

    }
    public ServiceApplication(Client applicant, Service service) {
        this.service = service;
        this.applicant = applicant;
        imageDocMap = new HashMap<String, ImageDocument>();
        for (DocumentType docType:
              service.getRequiredDocument()) {
            imageDocMap.put(docType.toString(), null); //todo
        }
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

    public void setName(Service name) {
        this.service = name;
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
