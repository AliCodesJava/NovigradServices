package com.example.servicesnovigrad;

public class FormDocument extends Document{
    private String firstName;
    private String lastName;
    private int birthday;
    private LicenseType licenseType;

    public FormDocument() {
    }

    public FormDocument(DocumentType docType, String docName) {
        super(docType, docName);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getBirthday() {
        return birthday;
    }

    public void setBirthday(int birthday) {
        this.birthday = birthday;
    }

    public LicenseType getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(LicenseType licenseType) {
        this.licenseType = licenseType;
    }
}
