package com.example.servicesnovigrad;

public class Document {
    private DocumentType docType;

    public Document(DocumentType docType){ this.docType = docType; }

    public DocumentType getDocType() { return docType; }

    public void setDocType(DocumentType docType) { this.docType = docType; }
}
