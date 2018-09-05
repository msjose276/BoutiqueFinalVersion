package com.example.mateusjose.newchatos.Objects;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

public class LoggedUserSingleton {

    private static LoggedUserSingleton mInstance= null;
    private BoutiqueUser boutiqueUser = null;

    // path for the users in the firebase
    public static final String users = "Users";


    DatabaseReference database = ConfigurationFirebase.getDatabaseReference();
    DatabaseReference refForPersonalInfo ;
    DatabaseReference refForSavedItems ;

    private LoggedUserSingleton(){
        if(FirebaseAuth.getInstance().getCurrentUser()!=null) {

            refForPersonalInfo = database.child(users).child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            //refForSavedItems = database.child(users).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(savedItems);

            //get the user data from the database
            refForPersonalInfo.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //save the data
                    boutiqueUser = new BoutiqueUser();
                    boutiqueUser = dataSnapshot.getValue(BoutiqueUser.class);
                    //get the uri path for the profile image
                    if (boutiqueUser.getImagePath() != null) {
                        StorageReference storageReference = ConfigurationFirebase.getStorageReference().child(boutiqueUser.getImagePath());
                        storageReference.getDownloadUrl().
                                addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                //set the user profile image
                                boutiqueUser.setPhotoUrl(uri);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                //do something
                            }

                        });
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }

            });
        }
        else{
            // after log out, the program arises everything user information
            boutiqueUser = null;
            refForPersonalInfo = null;
            refForSavedItems = null;
        }
    }

    public static synchronized LoggedUserSingleton getInstance() {
        if(null == mInstance){
            mInstance = new LoggedUserSingleton();
        }
        return mInstance;
    }

    public BoutiqueUser getBoutiqueUser() { return boutiqueUser; }

    public void setBoutiqueUser(BoutiqueUser boutiqueUser) { this.boutiqueUser = boutiqueUser; }
}
