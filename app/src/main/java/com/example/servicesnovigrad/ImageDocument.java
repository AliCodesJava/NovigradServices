package com.example.servicesnovigrad;

public class ImageDocument extends Document{

    private String docName;

    public ImageDocument() {
    }

    public ImageDocument(DocumentType docType, String docName) {
        super(docType);
        this.docName = docName;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }
}
