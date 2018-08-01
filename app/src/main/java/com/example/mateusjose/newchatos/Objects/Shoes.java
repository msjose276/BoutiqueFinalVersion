package com.example.mateusjose.newchatos.Objects;

import android.graphics.Bitmap;
import android.net.Uri;

public class Shoes {
    private String Title;
    private String type;
    private String size;
    private double price;
    private int stokeSize;
    private Bitmap image;
    private Uri fileImage;
    private String gender;
    private String ID;
    private String description;


    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStokeSize() {
        return stokeSize;
    }

    public void setStokeSize(int stokeSize) {
        this.stokeSize = stokeSize;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Uri getFileImage() {
        return fileImage;
    }

    public void setFileImage(Uri fileImage) {
        this.fileImage = fileImage;
    }
}
