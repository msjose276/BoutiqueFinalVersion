package com.example.mateusjose.newchatos.Objects;

import android.net.Uri;

public class ItemFeatured {

    private Uri photoUrl;
    private String imagePath;
    private  String topic;
    private int quantity;

    public ItemFeatured(){
    }

    public ItemFeatured(String topic, int quantity){
        this.topic = topic;
        this.quantity = quantity;
    }



    public ItemFeatured(String topic, int quantity, String imagePath){
        this.topic = topic;
        this.quantity = quantity;
        this.imagePath = imagePath;
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

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
