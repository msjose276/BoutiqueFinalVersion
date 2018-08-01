package com.example.mateusjose.newchatos.Objects;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ConfigurationFirebase {

    private static DatabaseReference databaseReference;
    private static FirebaseAuth firebaseAuth;
    private static FirebaseStorage firebaseStorage;
    private static StorageReference storageReference;

    public static DatabaseReference getDatabaseReference() {
        if(databaseReference == null){
            databaseReference = FirebaseDatabase.getInstance().getReference();
        }
        return databaseReference;
    }



    public static FirebaseAuth getFirebaseAuth() {
        if(firebaseAuth == null){
            firebaseAuth = FirebaseAuth.getInstance();
        }
        return firebaseAuth;
    }

    public static FirebaseStorage getFirebaseStorage() {
        if(firebaseStorage == null){
            firebaseStorage = FirebaseStorage.getInstance();
        }
        return firebaseStorage;
    }

    public static StorageReference getStorageReference() {
        if(storageReference == null){
            storageReference = getFirebaseStorage().getReference();
        }
        return storageReference;
    }

}
