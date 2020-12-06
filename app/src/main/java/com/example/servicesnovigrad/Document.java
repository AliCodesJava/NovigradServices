package com.example.servicesnovigrad;

import java.io.Serializable;

public class Document implements Serializable {
    private DocumentType docType;

    public Document(){}
    public Document(DocumentType docType){  this.docType = docType; }

    public DocumentType getDocType() { return docType; }

    public void setDocType(DocumentType docType) { this.docType = docType; }

}
