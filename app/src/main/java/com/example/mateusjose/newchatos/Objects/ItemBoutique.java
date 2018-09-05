package com.example.mateusjose.newchatos.Objects;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Date;

public class ItemBoutique {

    private String Title;
    private String brand;
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
    private int itemPosition;

    private ArrayList<String> listOfTags;

    private ArrayList<String> listOfImagePath;
    private ArrayList<Uri> listOfPhotoUrl;


    private Date date;



    public ItemBoutique(){
    }

    public ItemBoutique(String Title,double price){
        this.price=price;
        this.Title= Title;
    }


    public void addSavedItemBoutique() {
        DatabaseReference database = ConfigurationFirebase.getDatabaseReference();
        DatabaseReference refForSavedItems = database.child(ProjStrings.Users).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(ProjStrings.SavedItems);

        refForSavedItems.child(this.itemID).setValue(this.itemID);
        //Toast.makeText(context, "item adicionado dos seus favoridos com sucesso ", Toast.LENGTH_SHORT).show();
    }

    public void deleteSavedItemBoutique() {

        DatabaseReference database = ConfigurationFirebase.getDatabaseReference();
        DatabaseReference refForSavedItems = database.child(ProjStrings.Users).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(ProjStrings.SavedItems);

        // delete itemID
        refForSavedItems.child(this.itemID).removeValue().
                addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //return true;
                        //Toast.makeText(context, "item deletado dos seus favoridos com sucesso ", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //return false;
                    }
                });
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

    public int getItemPosition() {
        return itemPosition;
    }

    public void setItemPosition(int itemPosition) {
        this.itemPosition = itemPosition;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public ArrayList<String> getListOfTags() {
        return listOfTags;
    }

    public void setListOfTags(ArrayList<String> listOfTags) {
        this.listOfTags = listOfTags;
    }

    public void addTag(String tag) {
        if(this.listOfTags==null)
            this.listOfTags = new ArrayList<String>();
        this.listOfTags.add(tag);
    }

    public boolean searchTag(String tag) {

        if(this.listOfTags==null)
            return false;
        if(this.listOfTags.contains(tag))
            return true;
        return false;
    }


    public ArrayList<String> getListOfImagePath() {
        if(listOfImagePath==null)
            return null;
        return listOfImagePath;
    }

    public void setListOfImagePath(ArrayList<String> listOfImagePath) {
        this.listOfImagePath = listOfImagePath;
    }

    public void addImagePath(String imagePath) {
        if(this.listOfImagePath==null)
            this.listOfImagePath = new ArrayList<String>();
        this.listOfImagePath.add(imagePath);
    }

    public ArrayList<Uri> getListOfPhotoUrl() {
        if(listOfPhotoUrl==null)
            return listOfPhotoUrl;
        return listOfPhotoUrl;
    }

    public void setListOfPhotoUrl(ArrayList<Uri> listOfPhotoUrl) {
        this.listOfPhotoUrl = listOfPhotoUrl;
    }

    public void addListOfPhotoUrl(Uri photoUrl) {
        if(this.listOfPhotoUrl==null)
            this.listOfPhotoUrl = new ArrayList<Uri>();
        this.listOfPhotoUrl.add(photoUrl);
    }

    public Date getDate() {
        if(date==null)
            return null;
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
