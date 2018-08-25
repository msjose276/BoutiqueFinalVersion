package com.example.mateusjose.newchatos.Fragments;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.mateusjose.newchatos.Activities.ItemDetail;
import com.example.mateusjose.newchatos.Activities.NavegationDrawerActivity;
import com.example.mateusjose.newchatos.Adaptor.ItemAdaptor;
import com.example.mateusjose.newchatos.Objects.ConfigurationFirebase;
import com.example.mateusjose.newchatos.Objects.ItemBoutique;
import com.example.mateusjose.newchatos.Objects.ItemFeatured;
import com.example.mateusjose.newchatos.Objects.ProjStrings;
import com.example.mateusjose.newchatos.Objects.Shoes;
import com.example.mateusjose.newchatos.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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



    DatabaseReference database = ConfigurationFirebase.getDatabaseReference();
    DatabaseReference refForItemBoutique = database.child(ProjStrings.ItemBoutique);
    DatabaseReference refForFeatured = database.child("Featured");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View page=inflater.inflate(R.layout.tab_general,container,false);
        page.setBackgroundResource(R.color.white);


        /*String itemID1=refForFeatured.child("Kids").push().getKey();
        String itemID2=refForFeatured.child("Kids").push().getKey();
        String itemID3=refForFeatured.child("Kids").push().getKey();

        ItemFeatured itemFeatured1 = new ItemFeatured("accessorios",123);
        ItemFeatured itemFeatured2 = new ItemFeatured("calcados",343);
        ItemFeatured itemFeatured3 = new ItemFeatured("blusas",982);
        //refForFeatured.child("Woman");
        refForFeatured.child("Kids").child(itemID1).setValue(itemFeatured1);
        refForFeatured.child("Kids").child(itemID2).setValue(itemFeatured2);
        refForFeatured.child("Kids").child(itemID3).setValue(itemFeatured3);
*/
        //refForSavedItems.child(this.itemID).setValue(this.itemID);
        return page;
    }
}
