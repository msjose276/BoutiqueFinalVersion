package com.example.mateusjose.newchatos.Objects;

import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class BoutiqueUser {

    private String userID;

    private Address address;
    private String description;
    private String email;
    private String nationality;
    private String registrationDate;
    private String phoneNumber;
    private Uri photoUrl;

    private String fullName;
    private String creationDate;
    private String password;
    private String imagePath;

    // path for the users in the firebase
    public static final String users = "Users";
    public static final String personalInfo = "PersonalInfo";

    public void BoutiqueUser(){}

    public void saveBoutiqueUserOnFirebaseDatabase(){

        DatabaseReference databaseReference = ConfigurationFirebase.getDatabaseReference();
        databaseReference.child(users).child(getUserID()).child(personalInfo).setValue(this);
    }

    //TODO: the return value for this method needs to be changed. it has to return a boolean value to indicate whether the operation was successful of not
    public void saveNewImagePicture(Uri newUri){

        // save the path of the file that needs to be deleted after the update
        String fileToBeDeleted = this.getImagePath();
        //upload image to the server before storing its reference to the user profile
        StorageReference photoRef = ConfigurationFirebase.getStorageReference().child("UsersProfile").child(this.getUserID()).child(newUri.getLastPathSegment()+".jpg");
        photoRef.putFile(newUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                setImagePath(taskSnapshot.getMetadata().getPath());
            }
        });

        // Delete the profile picture of the user
        if(fileToBeDeleted!=null) {
            StorageReference photoRefDeletion = ConfigurationFirebase.getStorageReference().child(fileToBeDeleted);
            photoRefDeletion.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    // File deleted successfully
                }
            });
        }
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Uri getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(Uri photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
    @Exclude
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
