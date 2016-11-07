package com.example.admin.zoo2.database;

public class Animal {

    private String animalName;
    private int animalId;
    private int animalTypeId;
    private int animalAge;
    private int animalCageId;
    private int animalCaretakerId;

    public Animal(String animalName) {
        this.setAnimalName(animalName);
    }

    public Animal(int animalId, String animalName, int animalTypeId, int animalAge, int animalCageId, int animalCaretakerId) {
        this.setAnimalId(animalId);
        this.setAnimalName(animalName);
        this.setAnimalTypeId(animalTypeId);
        this.setAnimalAge(animalAge);
        this.setAnimalCageId(animalCageId);
        this.setAnimalCaretakerId(animalCaretakerId);
    }

    public Animal(String animalName, int animalTypeId, int animalAge, int animalCageId, int animalCaretakerId) {
        this.setAnimalName(animalName);
        this.setAnimalTypeId(animalTypeId);
        this.setAnimalAge(animalAge);
        this.setAnimalCageId(animalCageId);
        this.setAnimalCaretakerId(animalCaretakerId);
    }

    public String getAnimalName() {
        return animalName;
    }

    public void setAnimalName(String animalName) {
        this.animalName = animalName;
    }

    public int getAnimalId() {
        return animalId;
    }

    public void setAnimalId(int animalId) {
        this.animalId = animalId;
    }

    public int getAnimalTypeId() {
        return animalTypeId;
    }

    public void setAnimalTypeId(int animalTypeId) {
        this.animalTypeId = animalTypeId;
    }

    public int getAnimalAge() {
        return animalAge;
    }

    public void setAnimalAge(int animalAge) {
        this.animalAge = animalAge;
    }

    public int getAnimalCageId() {
        return animalCageId;
    }

    public void setAnimalCageId(int animalCageId) {
        this.animalCageId = animalCageId;
    }

    public int getAnimalCaretakerId() {
        return animalCaretakerId;
    }

    public void setAnimalCaretakerId(int animalCaretakerId) {
        this.animalCaretakerId = animalCaretakerId;
    }
}
