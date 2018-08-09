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
import com.example.mateusjose.newchatos.Objects.Person;
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

import java.util.ArrayList;
import java.util.List;


public class ItemDetail extends AppCompatActivity {

    public static final String ITEM_ID_STRING = "ITEM_ID";
    public static final String ItemBoutique = "ItemBoutique";
    public static final String itemID = "itemID";

    ItemBoutique itemBoutique;

    public String ITEM_ID;






    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    //ItemBoutique itemBoutique;
    Person posterPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);


        itemBoutique = new ItemBoutique();
        Intent intent = getIntent();
        // get the item_id from the past activity
        ITEM_ID = intent.getStringExtra(ITEM_ID_STRING);

        itemBoutique.setItemID(intent.getStringExtra(itemID));

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
                    //logedBoutiqueUser.setUserID(user.getUid());
                    fillDetailPage();

                    /*if(logedBoutiqueUser.getImagePath()!=null){

                        StorageReference storageReference = ConfigurationFirebase.getStorageReference().child(logedBoutiqueUser.getImagePath());
                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                //store the url for the picture
                                logedBoutiqueUser.setPhotoUrl(uri);
                                Glide.with(getBaseContext())
                                        .load(logedBoutiqueUser.getPhotoUrl())
                                        .into(circleImageView);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                //do something
                            }
                        });
                    }*/
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });

        }
/*

        mAuth = FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();

*/





























        //ItemBoutique itemBoutique= new ItemBoutique();
        //Query query = mDatabase.child("ItemBoutique").orderByChild("itemID").equalTo(ITEM_ID).limitToLast(50);

        //get the information about the product
/*
        mDatabase.child("ItemBoutique").child(ITEM_ID).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        itemBoutique = dataSnapshot.getValue(ItemBoutique.class);
                        TextView price = (TextView) findViewById(R.id.tv_price);
                        TextView title = (TextView) findViewById(R.id.tv_title);
                        price.setText("Preco : "+Double.toString(itemBoutique.getPrice()));
                        title.setText("Tilulo : "+itemBoutique.getTitle());
                        //location.setText();
                        //boutique.setText();
                        //openHours.setText();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(ItemDetail.this, "erro de conexao a internet", Toast.LENGTH_SHORT).show();
                        //Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                    }
                });
*/


        //get the information about the poster's name........ itemBoutique.getPosterID()
       /* mDatabase.child("Users").child("qTMOgIg5trYOQOD3Z20cgdu0fHA2").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        posterPerson = dataSnapshot.getValue(Person.class);
                        *//*TextView location = (TextView) findViewById(R.id.tv_location);
                        TextView boutique = (TextView) findViewById(R.id.tv_boutique);

                        //location.setText("Localizacao: "+ posterPerson.getAddress().getProvince() +", "+ posterPerson.getAddress().getMonicipio());
                        location.setText("Localizacao: Luanda, Benfica");
                        //boutique.setText("Boutique: "+posterPerson.getBoutiqueName());
                        boutique.setText("Boutique: Dabeleza");
*//*
                        //openHours.setText();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(ItemDetail.this, "erro de conexao a internet", Toast.LENGTH_SHORT).show();
                        //Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                    }
                });*/

        /*TextView back = (TextView) findViewById(R.id.tv_back);
        ImageView imageItem = (ImageView) findViewById(R.id.iv_image_item);
        TextView forward = (TextView) findViewById(R.id.tv_forward);*/

    }

    public void fillDetailPage(){

        TextView title = (TextView)findViewById(R.id.tv_title);
        TextView price = (TextView)findViewById(R.id.tv_price);
        TextView description = (TextView)findViewById(R.id.tv_description);
        TextView addToTheCart = (TextView)findViewById(R.id.tv_add_to_the_cart);

        title.setText(itemBoutique.getTitle());
        price.setText(String.valueOf(itemBoutique.getPrice()));
        description.setText(itemBoutique.getDescription());

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
    }


    public void addListOfImages(){


        final List<Uri> listOfImages=new ArrayList<>();
        listOfImages.add(Uri.parse("https://www.google.com/search?q=clothes&safe=strict&rlz=1C5CHFA_enUS791US791&source=lnms&tbm=isch&sa=X&ved=0ahUKEwi0ycftudbcAhVLEawKHd2dBDoQ_AUIDCgD&biw=1280&bih=726&safe=high#imgrc=SqVWjNy9iIBJFM:"));
        listOfImages.add(Uri.parse("https://www.google.com/search?q=clothes&safe=strict&rlz=1C5CHFA_enUS791US791&source=lnms&tbm=isch&sa=X&ved=0ahUKEwi0ycftudbcAhVLEawKHd2dBDoQ_AUIDCgD&biw=1280&bih=726&safe=high#imgdii=40M8k3k9zjpp1M:&imgrc=SqVWjNy9iIBJFM:"));

        //Mateus: call and set the card adaptor
        AdaptorForListOfImages adaptor = new AdaptorForListOfImages(getBaseContext(),listOfImages);

        final ListView listView = (ListView)findViewById(R.id.lv_list_image);
        listView.setAdapter(adaptor);

        //mateus: set onclick listner for the list of items
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




    public void SaveItem(View view){

        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        if(firebaseUser==null){
            Toast.makeText(this, "para salvar roupas e acessorios precisa de estar login", Toast.LENGTH_SHORT).show();
        }
        else{
            //save item for each user
            //mDatabase.child("ItemSaved").child(firebaseUser.getUid()).setValue(ITEM_ID);
        }
    }
}
