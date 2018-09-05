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

import com.example.mateusjose.newchatos.Adaptor.ItemAdapter2;
import com.example.mateusjose.newchatos.Adaptor.adapterBoutiques;
import com.example.mateusjose.newchatos.Objects.BoutiqueUser;
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

public class Boutiques extends AppCompatActivity {


    public adapterBoutiques itemAdaptor ;

    DatabaseReference database = ConfigurationFirebase.getDatabaseReference();
    DatabaseReference refForItemBoutique = database.child(ProjStrings.Users);
    private ChildEventListener mChildEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boutiques);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        List<ItemAdapter2> listOfItemAdaptor = new ArrayList<>();
        final ListView gvView = (ListView) findViewById(R.id.lv_list_of_items);

        itemAdaptor = new adapterBoutiques(getBaseContext());
        gvView.setAdapter(itemAdaptor);

        // create a new event listener
        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //create a new item and store the data on it
                final BoutiqueUser boutiqueUser = dataSnapshot.getValue(BoutiqueUser.class);

                if(boutiqueUser.getImagePath()!=null){
                    //get the reference for the storage
                    StorageReference storageReference = ConfigurationFirebase.getFirebaseStorage().getReference().child(boutiqueUser.getImagePath());
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            //store the url for the picture
                            boutiqueUser.setPhotoUrl(uri);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //do something
                        }
                    });
                }
                itemAdaptor.addItem(boutiqueUser);
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
        // attach the event listener to the database reference for every user that is company
        refForItemBoutique.orderByChild("company").equalTo(true).addChildEventListener(mChildEventListener);

        //set onclick listener for the list of items
        gvView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //call the itemDetail activity for more detail about the item
                Intent intent = new Intent(getBaseContext(), BoutiqueDetail.class);
                BoutiqueUser boutiqueUser = (BoutiqueUser)parent.getItemAtPosition(position);
                intent.putExtra(ProjStrings.itemID,boutiqueUser.getUserID());
                startActivity(intent);
            }
        });

        itemAdaptor.notifyDataSetChanged();
    }

}
