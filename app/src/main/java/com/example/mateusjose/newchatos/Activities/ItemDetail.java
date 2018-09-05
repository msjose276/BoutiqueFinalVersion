package com.example.mateusjose.newchatos.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mateusjose.newchatos.Adaptor.AdaptorForListOfImages;
import com.example.mateusjose.newchatos.Adaptor.ItemAdaptor;
import com.example.mateusjose.newchatos.Objects.BoutiqueUser;
import com.example.mateusjose.newchatos.Objects.ConfigurationFirebase;
import com.example.mateusjose.newchatos.Objects.Contact;
import com.example.mateusjose.newchatos.Objects.ItemBoutique;
import com.example.mateusjose.newchatos.Objects.LoggedUserSingleton;
import com.example.mateusjose.newchatos.Objects.Person;
import com.example.mateusjose.newchatos.Objects.ProjStrings;
import com.example.mateusjose.newchatos.Objects.SingletonPatternForItemsSaved;
import com.example.mateusjose.newchatos.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class ItemDetail extends AppCompatActivity {

    DatabaseReference database = ConfigurationFirebase.getDatabaseReference();
    DatabaseReference refForItemBoutique = database.child(ProjStrings.ItemBoutique);
    ItemBoutique itemBoutique;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        itemBoutique = new ItemBoutique();
        Intent intent = getIntent();
        // get the item_id from the past activity
        itemBoutique.setItemID(intent.getStringExtra(ProjStrings.itemID));

        // see if the item has an ID
        if (itemBoutique.getItemID()!=null) {

            //get the user data from the database
            refForItemBoutique.child(itemBoutique.getItemID()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    itemBoutique = new ItemBoutique();
                    itemBoutique = dataSnapshot.getValue(ItemBoutique.class);
                    fillDetailPage();
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });

            // set onclick listener fo the like button
            final SparkButton spark_button = (SparkButton) findViewById(R.id.spark_button);
            spark_button.setEventListener(new SparkEventListener() {
                @Override
                public void onEvent(ImageView button, boolean buttonState) {

                    if (spark_button.isChecked()) {
                        LoggedUserSingleton.getInstance().getBoutiqueUser().addSavedItem(itemBoutique.getItemID());
                        Toast.makeText(ItemDetail.this, ProjStrings.FavariteItemAdd, Toast.LENGTH_SHORT).show();
                    } else {
                        LoggedUserSingleton.getInstance().getBoutiqueUser().deleteSavedItem(itemBoutique.getItemID());
                        Toast.makeText(ItemDetail.this, ProjStrings.FavariteItemDelete, Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onEventAnimationStart(ImageView button, boolean buttonState) { }
                @Override
                public void onEventAnimationEnd(ImageView button, boolean buttonState) { }
            });


        }//if it does not, closes the page. an big error has occurred
        else{
            Toast.makeText(this, ProjStrings.ErrorHappened, Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    public void fillDetailPage(){
        TextView title = (TextView)findViewById(R.id.tv_title);
        TextView price = (TextView)findViewById(R.id.tv_price);
        TextView description = (TextView)findViewById(R.id.tv_description);
        TextView addToTheCart = (TextView)findViewById(R.id.tv_add_to_the_cart);
        CircleImageView ciBoutiquePoster = (CircleImageView)findViewById(R.id.ci_boutique_poster);

        final SparkButton spark_button = (SparkButton) findViewById(R.id.spark_button);


        title.setText(itemBoutique.getTitle());
        price.setText(String.valueOf(itemBoutique.getPrice()));
        description.setText(itemBoutique.getDescription());
        // check ig the item has an image and sets it
        if(itemBoutique.getImagePath()!=null){

            StorageReference storageRef = ConfigurationFirebase.getStorageReference();
            StorageReference storageReference = storageRef.child(itemBoutique.getImagePath());

            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    //set the picture into the imageview
                    ImageView mainImage = (ImageView)findViewById(R.id.iv_main_image);
                    itemBoutique.setPhotoUrl(uri);
                    Glide.with(getBaseContext())
                            .load(itemBoutique.getPhotoUrl())
                            .into(mainImage);
                    //call the function to show the detailed images
                    addListOfImages();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    //do something
                }
            });
        }

        //TODO: download boutiquePoster Image
        //ciBoutiquePoster.setImageResource();

        // check if the item is on the saved items from the user, if the user is logged
        if (LoggedUserSingleton.getInstance().getBoutiqueUser() != null) {

            if(LoggedUserSingleton.getInstance().getBoutiqueUser().isItemSaved(itemBoutique.getItemID())){
                spark_button.setChecked(true);            }
            else{
                spark_button.setChecked(false);
            }
        }
    }


    public void addListOfImages(){

        if(itemBoutique.getListOfImagePath()!=null) {

            for (int i = 0; i < itemBoutique.getListOfImagePath().size(); i++) {
                //set the reference for each image path
                StorageReference storageRef = ConfigurationFirebase.getStorageReference();
                StorageReference storageReference = storageRef.child(itemBoutique.getListOfImagePath().get(i));

                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        itemBoutique.addListOfPhotoUrl(uri);

                        //call and set the card adaptor
                        AdaptorForListOfImages adaptor = new AdaptorForListOfImages(getBaseContext(),itemBoutique.getListOfPhotoUrl());
                        final ListView listView = (ListView)findViewById(R.id.lv_list_image);
                        listView.setAdapter(adaptor);

                        // set onclick listner for the list of items
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                ImageView mainImage = (ImageView)findViewById(R.id.iv_main_image);
                                Uri uri = (Uri) parent.getItemAtPosition(position);
                                Glide.with(getBaseContext())
                                        .load(uri)
                                        .into(mainImage);
                            }
                        });
                        adaptor.notifyDataSetChanged();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        //do something
                    }
                });
            }
        }

    }

    //ci_boutique_poster

    public void BoutiquePoster(View view){

        Intent intent = new Intent(getBaseContext(), BoutiqueDetail.class);
        intent.putExtra(ProjStrings.itemID,"boutiqueUserID");
        startActivity(intent);
    }
}
