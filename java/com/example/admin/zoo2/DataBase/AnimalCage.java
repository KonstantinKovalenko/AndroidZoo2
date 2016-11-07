package com.example.admin.zoo2.database;

public class AnimalCage {

    private int cageId;
    private String cageTitle;

    public AnimalCage(String cageTitle) {
        this.setCageTitle(cageTitle);
    }

    public AnimalCage(int cageId, String cageTitle) {
        this.setCageId(cageId);
        this.setCageTitle(cageTitle);
    }

    public int getCageId() {
        return cageId;
    }

    public void setCageId(int cageId) {
        this.cageId = cageId;
    }

    public String getCageTitle() {
        return cageTitle;
    }

    public void setCageTitle(String cageTitle) {
        this.cageTitle = cageTitle;
    }
}
