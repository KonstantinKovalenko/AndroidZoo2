package com.example.admin.zoo2.database;


public class AnimalCaretaker {

    private int caretakerId;
    private String caretakerName;
    private String caretakerSurname;

    public AnimalCaretaker(int caretakerId, String caretakerName, String caretakerSurname) {
        this.setCaretakerId(caretakerId);
        this.setCaretakerName(caretakerName);
        this.setCaretakerSurname(caretakerSurname);
    }

    public AnimalCaretaker(String caretakerName, String caretakerSurname) {
        this.setCaretakerName(caretakerName);
        this.setCaretakerSurname(caretakerSurname);
    }

    public AnimalCaretaker(String caretakerName) {
        this.setCaretakerName(caretakerName);
    }

    public int getCaretakerId() {
        return caretakerId;
    }

    public void setCaretakerId(int caretakerId) {
        this.caretakerId = caretakerId;
    }

    public String getCaretakerName() {
        return caretakerName;
    }

    public void setCaretakerName(String caretakerName) {
        this.caretakerName = caretakerName;
    }

    public String getCaretakerSurname() {
        return caretakerSurname;
    }

    public void setCaretakerSurname(String caretakerSurname) {
        this.caretakerSurname = caretakerSurname;
    }
}
