package com.example.mateusjose.newchatos.Fragments;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;


import com.example.mateusjose.newchatos.Adaptor.MaterialAdaptor;
import com.example.mateusjose.newchatos.Objects.Contact;
import com.example.mateusjose.newchatos.Activities.ExchangeMessageActivity;
import com.example.mateusjose.newchatos.Adaptor.ItemAdaptor;
import com.example.mateusjose.newchatos.Objects.ItemBoutique;
import com.example.mateusjose.newchatos.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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

    public static final String ANONYMOUS = "anonymous";
    private String mUsername;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View page=inflater.inflate(R.layout.tab_general,container,false);
        page.setBackgroundResource(R.color.blue2);


        mUsername = ANONYMOUS;

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("ItemBoutique");


        // Initialize message ListView and its adapter
        List<ItemAdaptor> listOfItemAdaptor = new ArrayList<>();

        final GridView gvView = (GridView) page.findViewById(R.id.gvItem);
        itemAdaptor = new ItemAdaptor(this.getContext(), listOfItemAdaptor);
        gvView.setAdapter(itemAdaptor);

        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                ItemBoutique itemBoutique = dataSnapshot.getValue(ItemBoutique.class);
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



        /*final List<ItemBoutique> listOfItemBoutique=new ArrayList<>();

        listOfItemBoutique.add(new ItemBoutique("1111",(double) 134));
        listOfItemBoutique.add(new ItemBoutique("222",(double) 9764));
        listOfItemBoutique.add(new ItemBoutique("333",(double) 246));
        listOfItemBoutique.add(new ItemBoutique("444",(double) 765));
        listOfItemBoutique.add(new ItemBoutique("555",(double) 234));

        //ItemAdaptor adaptor = new ItemAdaptor(getContext(),listOfContacts,1);
        MaterialAdaptor adaptor = new MaterialAdaptor(getContext(),listOfItemBoutique);
        //final ListView listView = (ListView) page.findViewById(R.id.lvItem);

        final GridView gvView = (GridView) page.findViewById(R.id.gvItem);
        gvView.setAdapter(adaptor);

        //mateus: set onclick listner for the list of items
        gvView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getContext(), ExchangeMessageActivity.class);
                startActivity(intent);
            }
        });*/

        return page;
    }
}
