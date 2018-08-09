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
    public static final String personalInfo = "PersonalInfo";

    DatabaseReference database = ConfigurationFirebase.getDatabaseReference();
    DatabaseReference refForPersonalInfo = database.child(users).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(personalInfo);
    DatabaseReference refForSavedItems = database.child(users).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(savedItems);

    private SingletonPatternForItemsSaved(){

        if(FirebaseAuth.getInstance().getCurrentUser()!=null) {
            // initiate the arraylist
            ListOfSavedItemBoutique = new ArrayList<ItemBoutique>();
            // My top posts by number of stars
            refForSavedItems.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        // TODO: handle the post Comment movedComment = dataSnapshot.getValue(Comment.class);
                        ListOfSavedItemBoutique.add(postSnapshot.getValue(ItemBoutique.class));
                        Log.e("item number","dddd");
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
        refForSavedItems.child(itemBoutique.getItemID()).setValue(itemBoutique);
    }

    public void updateAllSavedItemsBoutique() { }

    public void downloadSavedItemBoutique() { }

    public void addSavedItemBoutique(ItemBoutique itemBoutique) {

        if(ListOfSavedItemBoutique == null){
            ListOfSavedItemBoutique = new ArrayList<ItemBoutique>();
        }
        Log.e("addSavedItemBoutique","passou aqui");
        //check if the itemBoutique is not already in the list
        if(!ListOfSavedItemBoutique.contains(itemBoutique)){
            //update the itemBoutique into firebase
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

        refForSavedItems.child(itemBoutique.getItemID()).removeValue().
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
        //TODO: change the return value to whatever the actual return
        return true;
    }

    public void printEverything(){
        Log.e("listItem: ","esta fora");
        for (ItemBoutique temp : ListOfSavedItemBoutique) {
            Log.e("listItem: ",temp.getItemID());
        }
    }
}
