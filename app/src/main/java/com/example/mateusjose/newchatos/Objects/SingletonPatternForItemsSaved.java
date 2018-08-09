package com.example.mateusjose.newchatos.Objects;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class SingletonPatternForItemsSaved {



    private static SingletonPatternForItemsSaved mInstance= null;

    private ItemBoutique itemBoutique = null;

    private List<ItemBoutique>  ListOfSavedItemBoutique = new ArrayList<ItemBoutique>();

    // path for the users in the firebase
    public static final String users = "Users";
    public static final String savedItems = "SavedItems";


    private SingletonPatternForItemsSaved(){
        if(FirebaseAuth.getInstance().getCurrentUser()!=null) {
            DatabaseReference database = ConfigurationFirebase.getDatabaseReference();
            DatabaseReference ref = database.child(users).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(savedItems);
            //DatabaseReference ref = database.child("ItemBoutique");

            // initiate the arraylist
            ListOfSavedItemBoutique = new ArrayList<ItemBoutique>();
            // My top posts by number of stars
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        // TODO: handle the post Comment movedComment = dataSnapshot.getValue(Comment.class);
                        ListOfSavedItemBoutique.add(postSnapshot.getValue(ItemBoutique.class));
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                }

            });
        }

    }


    public static synchronized SingletonPatternForItemsSaved getInstance() {
        if(null == mInstance){
            mInstance = new SingletonPatternForItemsSaved();
        }
        return mInstance;
    }

    public List<ItemBoutique> getListOfSavedItemBoutique() {
        return ListOfSavedItemBoutique;
    }

    public void setListOfSavedItemBoutique(List<ItemBoutique> listOfSavedItemBoutique) {
        ListOfSavedItemBoutique = listOfSavedItemBoutique;
    }

    public void uploadSavedItemBoutique(ItemBoutique itemBoutique) {

        DatabaseReference database = ConfigurationFirebase.getDatabaseReference();
        //set the path and save the values
        DatabaseReference ref = database.child(users).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(savedItems);
        ref.child(itemBoutique.getItemID()).setValue(itemBoutique);

    }

    public void updateAllSavedItemsBoutique() {
        DatabaseReference database = ConfigurationFirebase.getDatabaseReference();
        //set the path and save the list items
        DatabaseReference ref = database.child(users).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(savedItems);
        ref.setValue(ListOfSavedItemBoutique);
    }

    public void downloadSavedItemBoutique() {

    }

    public void addSavedItemBoutique(ItemBoutique itemBoutique) {

        if(ListOfSavedItemBoutique == null){
            ListOfSavedItemBoutique = new ArrayList<ItemBoutique>();
        }
        Log.e("addSavedItemBoutique","passou aqui");
        //check if the itemBoutique is not already in the list
        if(!ListOfSavedItemBoutique.contains(itemBoutique)){
            //update the list and update the firebase
            //ListOfSavedItemBoutique.add(itemBoutique);
            uploadSavedItemBoutique(itemBoutique);
            Log.e("addSavedItemBoutique","entrou");
        }
    }
    public boolean searchByItemId(String itemID) {

        if(ListOfSavedItemBoutique == null){
            return false;
        }

        Log.e("list lenght",Integer.toString(ListOfSavedItemBoutique.size()));
        for (ItemBoutique temp : ListOfSavedItemBoutique) {
            Log.e(itemID +"==",temp.getItemID());
            if(temp.getItemID().equals(itemID)){
                Log.e("searchByItemId","igualou");
                return true;
            }
        }
        return false;
    }


    public boolean deleteSavedItemBoutique(ItemBoutique itemBoutique) {

        DatabaseReference database = ConfigurationFirebase.getDatabaseReference();
        //set the path and save the values
        DatabaseReference ref = database.child(users).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(savedItems);

        ref.child(itemBoutique.getItemID()).removeValue().
                addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //return true;
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //return false;
                    }
                });

        // there is no need to delete the itemBoutique from the list because onDataChange will already do it for us
        //ListOfSavedItemBoutique.remove(itemBoutique);
        //TODO: change the return value to wheever the actual return
        return true;
    }

    public void printEverything(){
        Log.e("listItem: ","esta fora");
        for (ItemBoutique temp : ListOfSavedItemBoutique) {
            Log.e("listItem: ",temp.getItemID());
        }

    }
}
