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


    private String mUsername;
    public static final String ANONYMOUS = "anonymous";
    private ItemBoutique itemBoutique;
    private ItemAdaptor itemAdaptor;


    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;
    private ChildEventListener mChildEventListener;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mChatPhotosStorageReference;

    View intemView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View page=inflater.inflate(R.layout.tab_general,container,false);
        page.setBackgroundResource(R.color.white);


        final GridView mListView = (GridView) page.findViewById(R.id.gvItem);

        mDatabase= FirebaseDatabase.getInstance().getReference();
        Query query = ConfigurationFirebase.getDatabaseReference().child("ItemBoutique").limitToLast(50);

        FirebaseListOptions<ItemBoutique> options=new FirebaseListOptions.Builder<ItemBoutique>()
//                .setLayout(R.layout.activity_main2)
                .setLayout(R.layout.item_card)
                .setQuery(query, ItemBoutique.class)
                .setLifecycleOwner(this)
                .build();

        firebaseListAdapter = new FirebaseListAdapter<ItemBoutique>(options){

            @Override
            protected void populateView(View v, ItemBoutique model, int position) {

                intemView = v;
                ImageView ivItemImage= (ImageView) v.findViewById(R.id.iv_item_image);
                TextView tvBrand= (TextView) v.findViewById(R.id.tv_brand);
                TextView tvTitle= (TextView) v.findViewById(R.id.tv_title);
                TextView tvPrice= (TextView) v.findViewById(R.id.tv_price);
                itemBoutique = model;

                // ******************** set title, price and brand. Also, decrease the number of characters if it is longer than 20
                if(itemBoutique.getTitle().length()>20)
                    tvBrand.setText(itemBoutique.getTitle().substring(0,10)+"...");
                else
                    tvBrand.setText(itemBoutique.getTitle());

                if(Double.toString(itemBoutique.getPrice()).length()>20)
                    tvPrice.setText(Double.toString(itemBoutique.getPrice()).substring(0,10)+"...");
                else
                    tvPrice.setText(Double.toString(itemBoutique.getPrice()));

                if (Integer.toString(itemBoutique.getItemPosition()).length()>20)
                    tvTitle.setText(Integer.toString(itemBoutique.getItemPosition()).substring(0,10)+"...");
                else
                    tvTitle.setText(Integer.toString(itemBoutique.getItemPosition()));

                //********************* check if the item has some photo url associated with it
                if(itemBoutique.getImagePath()!=null){

                    StorageReference storageReference = ConfigurationFirebase.getStorageReference().child(itemBoutique.getImagePath());
                    storageReference.getDownloadUrl().
                            addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    //set the user profile image
                                    ImageView ivItemImage= (ImageView) intemView.findViewById(R.id.iv_item_image);
                                    TextView tvTitle= (TextView) intemView.findViewById(R.id.tv_title);
                                    tvTitle.setText("entou aqui");
                                    //boutiqueUser.setPhotoUrl(uri);
                                    Glide.with(getContext())
                                            .load(uri)
                                            .into(ivItemImage);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //do something
                        }

                    });
                    /*if (itemBoutique.getItemPosition()==position) {
                        Glide.with(getContext())
                                .load(itemBoutique.getPhotoUrl())
                                .into(ivItemImage);
                    } else {
                        Glide.with(getContext()).clear(ivItemImage);
                        ivItemImage.setImageResource(R.drawable.bonita);
                    }*/
                }
                else{
                    ivItemImage.setImageResource(R.drawable.roupa1);
                }

            }
        };

        mListView.setAdapter(firebaseListAdapter);


        return page;
    }
}
