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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.mateusjose.newchatos.Activities.ItemDetail;
import com.example.mateusjose.newchatos.Activities.NavegationDrawerActivity;
import com.example.mateusjose.newchatos.Adaptor.ItemAdaptor;
import com.example.mateusjose.newchatos.Objects.ItemBoutique;
import com.example.mateusjose.newchatos.Objects.Shoes;
import com.example.mateusjose.newchatos.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mateusjose on 1/9/18.
 */

public class FragmentTabHomem extends android.support.v4.app.Fragment{

    private DatabaseReference mDatabase;
    FirebaseListAdapter<ItemBoutique> firebaseListAdapter;
    ListView mListView;
    public static final String ITEM_ID_STRING = "ITEM_ID";


    private String mUsername;
    public static final String ANONYMOUS = "anonymous";
    private ItemBoutique itemBoutique;
    private ItemAdaptor itemAdaptor;


    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;
    private ChildEventListener mChildEventListener;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mChatPhotosStorageReference;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View page=inflater.inflate(R.layout.tab_general,container,false);
        page.setBackgroundResource(R.color.blue1);

/*

        mUsername = ANONYMOUS;

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        //mFirebaseAuth = FirebaseAuth.getInstance();
        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("ItemBoutique");

        mFirebaseStorage = FirebaseStorage.getInstance();


        mListView=(ListView) page.findViewById(R.id.lvItem);
        mDatabase= FirebaseDatabase.getInstance().getReference();
        //Query query = mDatabase.child("ItemBoutique").limitToLast(50);

*/


       /* // Initialize message ListView and its adapter
        List<ItemBoutique> itemBoutiques = new ArrayList<>();
        itemAdaptor= new ItemAdaptor(this, R.layout.card, itemBoutiques);
        mListView=(ListView) page.findViewById(R.id.lvItem);
        mListView.setAdapter(itemAdaptor);


        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                FriendlyMessage friendlyMessage=dataSnapshot.getValue(FriendlyMessage.class);
                mMessageAdapter.add(friendlyMessage);
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



*/







        /*mListView.setAdapter(firebaseListAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getContext(), ItemDetail.class);
                //pass the user email and password to the next activity
                intent.putExtra(ITEM_ID_STRING,firebaseListAdapter.getItem(position).getItemID());
                startActivity(intent);
                startActivity(intent);
            }
        });*/





        return page;
    }
}
