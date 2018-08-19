package com.example.mateusjose.newchatos.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.mateusjose.newchatos.Adaptor.ItemAdapter2;
import com.example.mateusjose.newchatos.Objects.ConfigurationFirebase;
import com.example.mateusjose.newchatos.Objects.ItemBoutique;
import com.example.mateusjose.newchatos.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class SearchItems extends AppCompatActivity {


    public ItemAdapter2 itemAdaptor ;
    private ChildEventListener mChildEventListener;

    DatabaseReference database = ConfigurationFirebase.getDatabaseReference();
    DatabaseReference refForItemBoutique = database.child("ItemBoutique");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_items);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //get the edittext and search the firebase database whenever the edittext changes
        EditText searchBar = (EditText) findViewById(R.id.et_search_bar);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0){

                    List<ItemAdapter2> listOfItemAdaptor = new ArrayList<>();
                    final GridView gvView = (GridView) findViewById(R.id.gvItem);
                    itemAdaptor = new ItemAdapter2(getBaseContext());
                    gvView.setAdapter(itemAdaptor);

                    // create a new event listener
                    mChildEventListener = new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            // create a new ItemBoutique and store the data
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
                    // set the reference for the item to be search
                    refForItemBoutique.orderByChild("title").startAt(s.toString()).addChildEventListener(mChildEventListener);

                    //set onclick listener for the list of items
                    gvView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //call the itemDetail activity for more detail about the item
                            Intent intent = new Intent(getBaseContext(), ItemDetail.class);
                            ItemBoutique newItemBoutique = (ItemBoutique)parent.getItemAtPosition(position);
                            intent.putExtra("itemID",newItemBoutique.getItemID());

                            startActivity(intent);
                        }
                    });

                    itemAdaptor.notifyDataSetChanged();
                }
            }
        });

    }

}
