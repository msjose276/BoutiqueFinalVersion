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
    public int someValueIWantToKeep;

    // path for the users in the firebase
    public static final String users = "Users";


    private LoggedUserSingleton(){
        if(FirebaseAuth.getInstance().getCurrentUser()!=null) {
            DatabaseReference database = ConfigurationFirebase.getDatabaseReference();
            DatabaseReference ref = database.child(users);
            //int d;
            Log.e("entour","LoggedUserSingleton");

            //get the user data from the database
            ref.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //save the data
                    //d=0;
                    Log.e("entour","LoggedUserSingleton222");
                    //boutiqueUser = new BoutiqueUser();
                    Log.e("entour","LoggedUserSingleton3333");
                    boutiqueUser = dataSnapshot.getValue(BoutiqueUser.class);

                    /*boutiqueUser.setUserID(mboutiqueUser.getUserID());
                    boutiqueUser.setEmail(mboutiqueUser.getEmail());
                    boutiqueUser.setPhoneNumber(mboutiqueUser.getPhoneNumber());
                    boutiqueUser.setFullName(mboutiqueUser.getFullName());*/
                    //logedBoutiqueUser.setUserID(user.getUid());
                    //Log.e("entour",boutiqueUser.getFullName());
                    //sayHiToMe(mboutiqueUser);
                    //get the uri path for the profile image
                    if (boutiqueUser.getImagePath() != null) {
                        StorageReference storageReference = ConfigurationFirebase.getStorageReference().child(boutiqueUser.getImagePath());
                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
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
        //Log.e("entour",boutiqueUser.getFullName());

        }
    }

    public void sayHiToMe(BoutiqueUser outiqueUser) {
        boutiqueUser = new BoutiqueUser();
        boutiqueUser.setFullName(new String(outiqueUser.getFullName()));
        boutiqueUser.setFullName("dddd");
        Log.d("hi there, " ,boutiqueUser.getFullName());
    }

    public static synchronized LoggedUserSingleton getInstance() {
        if(null == mInstance){
            mInstance = new LoggedUserSingleton();
        }
        return mInstance;
    }

    /*public static synchronized boolean isLoggedUserSingletonNull(){
        if(mInstance==null)
            return true;
        else
            return false;
    }*/

    public BoutiqueUser getBoutiqueUser() {
        return boutiqueUser;
    }

    public void setBoutiqueUser(BoutiqueUser boutiqueUser) {
        this.boutiqueUser = boutiqueUser;
    }
}
