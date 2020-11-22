package com.example.servicesnovigrad;

import java.util.ArrayList;

public class ServiceApplication {
    public static ArrayList<ServiceApplication> applications = new ArrayList<>();
    private String serviceName;



    private Client applicant;
    public ServiceApplication(Client applicant, String name) {
        this.serviceName = name;
        this.applicant = applicant;
        this.applications.add(this);
    }

    public Client getApplicant() {
        return applicant;
    }

    public void setApplicant(Client applicant) {
        this.applicant = applicant;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setName(String name) {
        this.serviceName = name;
    }
}
