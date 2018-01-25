package com.free.ahmed.wallet.Model;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by ahmed on 10/14/2017.
 */

public class Specification implements Serializable{
    private UUID id;
    private String name;
    private int color;

    public Specification(UUID id){
        this.id = id;
    };

    public Specification(){
        this.id = UUID.randomUUID();
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
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
}
