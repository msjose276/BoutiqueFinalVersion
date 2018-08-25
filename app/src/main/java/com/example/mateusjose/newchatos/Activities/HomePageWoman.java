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
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mateusjose.newchatos.Adaptor.ItemAdapter2;
import com.example.mateusjose.newchatos.Adaptor.adapterFeatured;
import com.example.mateusjose.newchatos.Objects.ConfigurationFirebase;
import com.example.mateusjose.newchatos.Objects.ItemBoutique;
import com.example.mateusjose.newchatos.Objects.ItemFeatured;
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

public class HomePageWoman extends AppCompatActivity {


    public adapterFeatured itemAdaptor ;
    public ItemBoutique itemBoutique;

    DatabaseReference database = ConfigurationFirebase.getDatabaseReference();
    DatabaseReference refForItemBoutique = database.child("Featured");
    private ChildEventListener mChildEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_woman);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent intent = getIntent();
        // get the item_id from the past activity
        final String itemBoutiqueTag = intent.getStringExtra("ItemBoutiqueTag");

        if(itemBoutiqueTag==null){
            //close the activity because it came with an error. error: did not have an ItemBoutiqueTag
            Toast.makeText(this, "aconteceu algum erro", Toast.LENGTH_SHORT).show();
            finish();
        }

        //
        List<adapterFeatured> listOfItemAdaptor = new ArrayList<>();
        ListView gvView = (ListView) findViewById(R.id.lv_items);

        itemAdaptor = new adapterFeatured(getBaseContext());
        gvView.setAdapter(itemAdaptor);

        // create a new event listener
        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //create a new item and store the data on it
                final ItemFeatured itemFeatured = dataSnapshot.getValue(ItemFeatured.class);

                itemAdaptor.addItem(itemFeatured);
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
        refForItemBoutique.child(itemBoutiqueTag).addChildEventListener(mChildEventListener);

        //set onclick listener for the list of items
        gvView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //call the itemDetail activity for more detail about the item
                Intent intent = new Intent(getBaseContext(), GeneralActivity.class);
                ItemFeatured newItemBoutique = (ItemFeatured)parent.getItemAtPosition(position);
                intent.putExtra("ItemBoutiqueTag",newItemBoutique.getTopic());
                startActivity(intent);
            }
        });
        itemAdaptor.notifyDataSetChanged();
    }

}
