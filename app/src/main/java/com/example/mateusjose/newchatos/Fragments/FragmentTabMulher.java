package com.example.mateusjose.newchatos.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;


import com.example.mateusjose.newchatos.Activities.ItemDetail;
import com.example.mateusjose.newchatos.Adaptor.ItemAdapter2;
import com.example.mateusjose.newchatos.Objects.ConfigurationFirebase;
import com.example.mateusjose.newchatos.Objects.ItemBoutique;
import com.example.mateusjose.newchatos.Objects.ProjStrings;
import com.example.mateusjose.newchatos.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mateusjose on 1/9/18.
 */

public class FragmentTabMulher extends android.support.v4.app.Fragment{

    public ItemAdapter2 itemAdaptor ;
    public ItemBoutique itemBoutique;

    DatabaseReference database = ConfigurationFirebase.getDatabaseReference();
    DatabaseReference refForItemBoutique = database.child(ProjStrings.ItemBoutique);
    private ChildEventListener mChildEventListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View page=inflater.inflate(R.layout.tab_general,container,false);
        page.setBackgroundResource(R.color.white);


        List<ItemAdapter2> listOfItemAdaptor = new ArrayList<>();
        final GridView gvView = (GridView) page.findViewById(R.id.gvItem);

        itemAdaptor = new ItemAdapter2(this.getContext());
        gvView.setAdapter(itemAdaptor);

        // create a new event listener
        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //create a new item and store the data on it
                final ItemBoutique itemBoutique = dataSnapshot.getValue(ItemBoutique.class);
                if(itemBoutique.getImagePath()!=null){
                    //get the reference for the storage
                    StorageReference storageReference = ConfigurationFirebase.getFirebaseStorage().getReference().child(itemBoutique.getImagePath());
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            //store the url for the picture
                            itemBoutique.setPhotoUrl(uri);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //do something
                        }
                    });
                }
                itemAdaptor.addItem(itemBoutique);
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        };
        // attach the event listener to the database reference
        refForItemBoutique.addChildEventListener(mChildEventListener);

        //set onclick listener for the list of items
        gvView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //call the itemDetail activity for more detail about the item
                Intent intent = new Intent(getContext(), ItemDetail.class);
                ItemBoutique newItemBoutique = (ItemBoutique)parent.getItemAtPosition(position);
                intent.putExtra(ProjStrings.itemID,newItemBoutique.getItemID());

                startActivity(intent);
            }
        });

        itemAdaptor.notifyDataSetChanged();
        return page;
    }
}
