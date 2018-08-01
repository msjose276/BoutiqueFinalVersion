package com.example.mateusjose.newchatos.Objects;

import android.graphics.Bitmap;
import android.net.Uri;

public class ItemBoutique {

    private String Title;
    private String typeOfItem;
    private String size;
    private double price;
    private int stokeSize;
    private Bitmap image;
    private Uri fileImage;
    private String gender;
    private String itemID;
    private String description;
    private Address address;
    private String posterID;

    private Uri photoUrl;
    private String imagePath;

    public ItemBoutique(){
    }

    public ItemBoutique(String Title,double price){
        this.price=price;
        this.Title= Title;
    }
    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getType() {
        return typeOfItem;
    }

    public void setType(String typeOfItem) {
        this.typeOfItem = typeOfItem;
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

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
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

    public String getTypeOfItem() {
        return typeOfItem;
    }

    public void setTypeOfItem(String typeOfItem) {
        this.typeOfItem = typeOfItem;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPosterID() {
        return posterID;
    }

    public void setPosterID(String posterID) {
        this.posterID = posterID;
    }

    public Uri getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(Uri photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
