package com.example.admin.zoo2;

public class AnimalType implements DBClasses {

    private int typeId;
    private String typeTitle;

    public AnimalType(String typeTitle) {
        this.setTypeTitle(typeTitle);
    }

    public AnimalType(int typeId, String typeTitle) {
        this.setTypeId(typeId);
        this.setTypeTitle(typeTitle);
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeTitle() {
        return typeTitle;
    }

    public void setTypeTitle(String typeTitle) {
        this.typeTitle = typeTitle;
    }

    @Override
    public String getIdentyString() {
        return typeTitle;
    }
}
