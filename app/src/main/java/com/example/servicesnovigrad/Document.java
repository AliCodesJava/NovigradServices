package com.example.servicesnovigrad;

import java.io.Serializable;

public class Document implements Serializable {
    private DocumentType docType;
    private String docName;

    public Document(){}
    public Document(DocumentType docType, String docName){ this.docName = docName; this.docType = docType; }

    public DocumentType getDocType() { return docType; }

    public void setDocType(DocumentType docType) { this.docType = docType; }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }
}
