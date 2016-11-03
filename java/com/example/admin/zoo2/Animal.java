package com.example.admin.zoo2;

public class Animal {

    private int animalId;
    private String animalName;
    private String animalType;
    private int animalAge;
    private int animalCageId;
    private int animalCaretakerId;

    public Animal(String animalName) {
        this.animalName = animalName;
    }

    public Animal(int animalId, String animalName, String animalType, int animalAge, int animalCageId, int animalCaretakerId) {
        this.animalId = animalId;
        this.animalName = animalName;
        this.animalType = animalType;
        this.animalAge = animalAge;
        this.animalCageId = animalCageId;
        this.animalCaretakerId = animalCaretakerId;
    }

    public Animal(String animalName, String animalType, int animalAge, int animalCageId, int animalCaretakerId) {
        this.animalName = animalName;
        this.animalType = animalType;
        this.animalAge = animalAge;
        this.animalCageId = animalCageId;
        this.animalCaretakerId = animalCaretakerId;
    }

}
