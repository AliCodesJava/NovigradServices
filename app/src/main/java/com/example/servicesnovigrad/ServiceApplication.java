package com.example.servicesnovigrad;

import java.util.ArrayList;

public class ServiceApplication {
    //todo make application lists in branch, not here
    public static ArrayList<ServiceApplication> applications = new ArrayList<>();
    private Service service;
    private Client applicant;
    public ServiceApplication(Client applicant, Service service) {
        this.service = service;
        this.applicant = applicant;
        this.applications.add(this);
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
}
