package com.example.nitmz.model;

public class PdfData {

    private String pdfTitle;
    private String pdfUrl;

    // Required default constructor for Firebase
    public PdfData() {
    }

    public PdfData(String pdfTitle, String pdfUrl) {
        this.pdfTitle = pdfTitle;
        this.pdfUrl = pdfUrl;
    }

    // Getter and setter methods
    public String getPdfTitle() {
        return pdfTitle;
    }

    public void setPdfTitle(String pdfTitle) {
        this.pdfTitle = pdfTitle;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }
}
