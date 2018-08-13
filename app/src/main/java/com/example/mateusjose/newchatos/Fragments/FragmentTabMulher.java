package com.example.mateusjose.newchatos.Fragments;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;


import com.bumptech.glide.Glide;
import com.example.mateusjose.newchatos.Activities.ItemDetail;
import com.example.mateusjose.newchatos.Adaptor.MaterialAdaptor;
import com.example.mateusjose.newchatos.Objects.ConfigurationFirebase;
import com.example.mateusjose.newchatos.Objects.Contact;
import com.example.mateusjose.newchatos.Activities.ExchangeMessageActivity;
import com.example.mateusjose.newchatos.Adaptor.ItemAdaptor;
import com.example.mateusjose.newchatos.Objects.ItemBoutique;
import com.example.mateusjose.newchatos.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkButtonBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mateusjose on 1/9/18.
 */

public class FragmentTabMulher extends android.support.v4.app.Fragment{

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;

    private ChildEventListener mChildEventListener;


    private FirebaseStorage mFirebaseStorage;
    private StorageReference mChatPhotosStorageReference;


    public MaterialAdaptor materialAdaptor;
    public ItemAdaptor itemAdaptor ;
    public ItemBoutique itemBoutique;
    public static final String ANONYMOUS = "anonymous";
    public static final String itemID = "itemID";



    private String mUsername;
    private int position=0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View page=inflater.inflate(R.layout.tab_general,container,false);
        page.setBackgroundResource(R.color.blue2);

        mUsername = ANONYMOUS;
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("ItemBoutique");

        //************************************* list random itens from the database *********************
        // Initialize message ListView and its adapter
        List<ItemAdaptor> listOfItemAdaptor = new ArrayList<>();
        //final GridView gvView = (GridView) page.findViewById(R.id.gvItem);
        final GridView gvView = (GridView) page.findViewById(R.id.gvItem);

                itemAdaptor = new ItemAdaptor(this.getContext(), listOfItemAdaptor);
        gvView.setAdapter(itemAdaptor);

        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
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
                itemAdaptor.add(itemBoutique);

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
        mMessagesDatabaseReference.addChildEventListener(mChildEventListener);

        //set onclick listener for the list of items
        gvView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //call the itemDetail activity for more detail about the item
                Intent intent = new Intent(getContext(), ItemDetail.class);
                ItemBoutique newItemBoutique = (ItemBoutique)parent.getItemAtPosition(position);
                intent.putExtra("itemID",newItemBoutique.getItemID());

                startActivity(intent);
            }
        });

        itemAdaptor.notifyDataSetChanged();
        return page;

    }
}
