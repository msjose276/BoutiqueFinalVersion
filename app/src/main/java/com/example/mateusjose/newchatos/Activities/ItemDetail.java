package com.example.mateusjose.newchatos.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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


public class ItemDetail extends AppCompatActivity {

    public static final String ITEM_ID_STRING = "ITEM_ID";
    public static final String ItemBoutique = "ItemBoutique";
    public static final String itemID = "itemID";

    ItemBoutique itemBoutique;

    public String ITEM_ID;

    final String users = "Users";
    final String savedItems = "SavedItems";
    DatabaseReference database = ConfigurationFirebase.getDatabaseReference();
    DatabaseReference refForSavedItems = database.child(users).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(savedItems);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);


        itemBoutique = new ItemBoutique();
        Intent intent = getIntent();
        // get the item_id from the past activity
        ITEM_ID = intent.getStringExtra(ITEM_ID_STRING);

        itemBoutique.setItemID(intent.getStringExtra(itemID));

        // see if the item has an ID
        if (itemBoutique.getItemID()!=null) {

            //set the data reference for the item boutique
            DatabaseReference database = ConfigurationFirebase.getDatabaseReference();
            DatabaseReference ref = database.child(ItemBoutique);

            //get the user data from the database
            ref.child(itemBoutique.getItemID()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    itemBoutique = new ItemBoutique();
                    itemBoutique = dataSnapshot.getValue(ItemBoutique.class);
                    fillDetailPage();
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });

        }//if it does not, closes the page. an big error has occured
        else{
            Toast.makeText(this, "aconteceu um erro", Toast.LENGTH_SHORT).show();
            finish();
        }

        // set onclick listener fo the like button
        final SparkButton spark_button = (SparkButton) findViewById(R.id.spark_button);
        spark_button.setEventListener(new SparkEventListener() {
            @Override
            public void onEvent(ImageView button, boolean buttonState) {
                if (SingletonPatternForItemsSaved.getInstance() != null) {
                    //see if the user is logged before saving the liked item
                    if (LoggedUserSingleton.getInstance().getBoutiqueUser() != null){
                        if (spark_button.isChecked()) {
                            addSavedItemBoutique(itemBoutique.getItemID());
                        } else {
                            deleteSavedItemBoutique(itemBoutique.getItemID());
                        }
                    }// else, do nothing
                    else{
                        Toast.makeText(ItemDetail.this, "faca o login pra poder salvar itens", Toast.LENGTH_SHORT).show();
                    }

                }
            }
            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) { }
            @Override
            public void onEventAnimationEnd(ImageView button, boolean buttonState) { }
        });

    }

    public void fillDetailPage(){
        TextView title = (TextView)findViewById(R.id.tv_title);
        TextView price = (TextView)findViewById(R.id.tv_price);
        TextView description = (TextView)findViewById(R.id.tv_description);
        TextView addToTheCart = (TextView)findViewById(R.id.tv_add_to_the_cart);
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
                    ImageView mainImage = (ImageView)findViewById(R.id.iv_main_image);

                    //store the url for the picture
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


        // check if the item is on the saved items from the user, if the user is logged
        if (LoggedUserSingleton.getInstance().getBoutiqueUser() != null) {
            refForSavedItems.child(itemBoutique.getItemID()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {
                        spark_button.setChecked(true);
                    }
                    else{
                        spark_button.setChecked(false);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }

    }


    public void addListOfImages(){

        //TODO: need to add new images coming from the firebase connected to the itemBoutique
        final List<Uri> listOfImages=new ArrayList<>();
        // these are only place holders
        listOfImages.add(Uri.parse("https://firebasestorage.googleapis.com/v0/b/boutiques-151ce.appspot.com/o/boutique_items%2F-LIl9RUnQ7j669zyGL1t%2Fimage%3A89?alt=media&token=b6976e77-0089-44e7-8af3-e85ffc23a40e"));
        listOfImages.add(Uri.parse("https://firebasestorage.googleapis.com/v0/b/boutiques-151ce.appspot.com/o/boutique_items%2F-LIn_8ToqZu_q2dqlY8q%2F154?alt=media&token=8772d551-94e7-4f29-8622-1dfe3bc229bc"));

        //call and set the card adaptor
        AdaptorForListOfImages adaptor = new AdaptorForListOfImages(getBaseContext(),listOfImages);
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

    }


    //delete the item from the firebase database
    public void deleteSavedItemBoutique(String ItemID) {
        // delete itemID
        refForSavedItems.child(ItemID).removeValue().
                addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //return true;
                        Toast.makeText(ItemDetail.this, "item deletado dos seus favoridos com sucesso", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //return false;
                    }
                });
    }

    // save the item into the firebase database
    public void addSavedItemBoutique(String ItemID) {
        //save itemID
        refForSavedItems.child(ItemID).setValue(ItemID);
        Toast.makeText(ItemDetail.this, "item adicionado dos seus favoridos com sucesso ", Toast.LENGTH_SHORT).show();
    }

}
