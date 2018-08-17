package com.example.mateusjose.newchatos.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mateusjose.newchatos.Adaptor.AdaptorForListOfImages;
import com.example.mateusjose.newchatos.Adaptor.AdaptorForSavedItem;
import com.example.mateusjose.newchatos.Objects.BoutiqueUser;
import com.example.mateusjose.newchatos.Objects.ConfigurationFirebase;
import com.example.mateusjose.newchatos.Objects.ItemBoutique;
import com.example.mateusjose.newchatos.Objects.SingletonPatternForItemsSaved;
import com.example.mateusjose.newchatos.R;
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

public class SavedItems extends AppCompatActivity {

    final String users = "Users";
    final String savedItems = "SavedItems";
    final String itemBoutique = "ItemBoutique";

    DatabaseReference database = ConfigurationFirebase.getDatabaseReference();
    DatabaseReference refForSavedItems = database.child(users).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(savedItems);
    DatabaseReference refForItemBoutique = database.child(itemBoutique);

    List<ItemBoutique> listItemBoutques=new ArrayList<>();
    List<String> listItemBoutquesID=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_items);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // check if the user is signed in. if it is not get out of this page
        if(FirebaseAuth.getInstance().getCurrentUser()==null){
            Toast.makeText(this, "precisa estar logado para ver os seus produtos favoritos", Toast.LENGTH_SHORT).show();
            finish();
        }
        // get the list and set the adptor

        //TODO: need to delete this later
        /*listItemBoutques.add(new ItemBoutique());
        listItemBoutques.add(new ItemBoutique());*/

        listItemBoutques = new ArrayList<ItemBoutique>();

        if(FirebaseAuth.getInstance().getCurrentUser()!=null) {

            // My top posts by number of stars
            refForSavedItems.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        // get every itemID from the saved items
                        listItemBoutquesID.add(postSnapshot.getValue(String.class));
                        Log.e("list of saved ",postSnapshot.getValue(String.class));
                        // set the reference for each item and get the itemBoutique
                        refForItemBoutique.child(postSnapshot.getValue(String.class)).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                //save the data
                                ItemBoutique itemBoutique = new ItemBoutique();
                                itemBoutique = dataSnapshot.getValue(ItemBoutique.class);
                                //add each item into the list
                                listItemBoutques.add(itemBoutique);

                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }

                        });
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Post failed, log a message
                }

            });
        }

        //Mateus: call and set the card adaptor
        AdaptorForSavedItem adaptor = new AdaptorForSavedItem(getBaseContext(),listItemBoutques);

        final ListView listView = (ListView)findViewById(R.id.lv_saved_items);
        listView.setAdapter(adaptor);

        //mateus: set onclick listner for the list of items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //call the itemDetail activity for more detail about the item
                Intent intent = new Intent(getBaseContext(), ItemDetail.class);
                ItemBoutique newItemBoutique = (ItemBoutique)parent.getItemAtPosition(position);
                intent.putExtra("itemID",newItemBoutique.getItemID());

                startActivity(intent);
            }
        });
    }

}
