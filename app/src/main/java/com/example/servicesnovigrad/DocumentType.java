package com.example.servicesnovigrad;

public enum DocumentType {
    PREUVE_DE_DOMICILE("Preuve de domicile"),
    PREUVE_DE_STATUS("Preuve de status"),
    PHOTO("Photo"),
    FORM("Form");

    private String desc;

    DocumentType(String desc) {
        this.desc=desc;
    }

    @Override
    public String toString() {
        return desc;
    }
}