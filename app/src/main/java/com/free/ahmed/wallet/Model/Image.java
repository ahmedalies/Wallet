package com.free.ahmed.wallet.Model;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by ahmed on 10/15/2017.
 */

public class Image implements Serializable {

    private String path;
    private UUID id;

    public Image(){
        this.id = UUID.randomUUID();
    }

    public Image(UUID id){
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public UUID getId() {
        return id;
    }

    public String getImageFileName(){
        return "IMG_" + getId().toString();
    }
}
