package com.free.ahmed.wallet.Model;

import java.io.Serializable;
import java.io.SerializablePermission;
import java.util.UUID;

/**
 * Created by ahmed on 10/14/2017.
 */

public class Resources implements Serializable {
    private UUID id;
    private double amount;
    private String name;
    private Specification specDetails;
    private String notes;
    private Image image;
    private boolean isFixed;
    private String created_at;
    private UUID specId;
    private UUID imageId;
    private boolean isIncome;
    private String month;
    private int year;


    public Resources(){
        this.id = UUID.randomUUID();
    }

    public Resources(UUID id){
        this.id = id;
    }

    public boolean isIncome() {
        return isIncome;
    }

    public void setIncome(boolean income) {
        isIncome = income;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public UUID getSpecId() {
        return specId;
    }

    public void setSpecId(UUID specId) {
        this.specId = specId;
    }

    public UUID getImageId() {
        return imageId;
    }

    public void setImageId(UUID imageId) {
        this.imageId = imageId;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFixed() {
        return isFixed;
    }

    public void setFixed(boolean fixed) {
        isFixed = fixed;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Specification getSpecDetails() {
        return specDetails;
    }

    public void setSpecDetails(Specification specDetails) {
        this.specDetails = specDetails;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
