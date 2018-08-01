package com.example.mateusjose.newchatos.Nav_activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mateusjose.newchatos.Activities.ItemDetail;
import com.example.mateusjose.newchatos.Objects.ItemBoutique;
import com.example.mateusjose.newchatos.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NavFavorites extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    boolean loggedIn=false;
    public static final String USER_EMAIL = "USER_EMAIL";
    public static final String USER_PASSWORD = "USER_PASSWORD";
    public static final String USER_ID = "USER_ID";

    public String EMAIL_LOGIN = null;
    public String PASSWORD_LOGIN = null;
    public String USER_ID_LOGIN = null;

    List<ItemBoutique> savedItemList;
    public int cont=0;
    ItemBoutique itemBoutique;

    FirebaseListAdapter<ItemBoutique> firebaseListAdapter;
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_clothes_saved);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // try to get user information from other activities.
        Intent i = getIntent();
        if(i.getStringExtra("loggedIn")!=null){
            if(i.getStringExtra("loggedIn").equals("loggedIn"))
                loggedIn=true;
            //Toast.makeText(this, "loggedIn", Toast.LENGTH_SHORT).show();
        }
        //get user information
        EMAIL_LOGIN = i.getStringExtra(USER_EMAIL);
        PASSWORD_LOGIN = i.getStringExtra(USER_PASSWORD);
        USER_ID_LOGIN = i.getStringExtra(USER_ID);



        if(!loggedIn){
            Toast.makeText(this, "login para ver as tuas roupas guardadas", Toast.LENGTH_SHORT).show();
        }
        else{
            loadClothes();
        }

    }


    public void loadClothes(){

        USER_ID_LOGIN="qTMOgIg5trYOQOD3Z20cgdu0fHA2";
        //get the list of all saved items
        Query query = mDatabase.child("SavedClothes").child(USER_ID_LOGIN).limitToLast(50);


        mDatabase.child("SavedClothes").child(USER_ID_LOGIN);

        savedItemList=new ArrayList<ItemBoutique>();


        //retrieve every item from the list
        for (int i = 0; i < savedItemList.size(); i++) {

            cont=i;
            /*mDatabase.child("ItemBoutique").child(savedItemList.get(i).getItemID()).addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // Get user value
                            itemBoutique = dataSnapshot.getValue(ItemBoutique.class);
//                            TextView price = (TextView) findViewById(R.id.tv_price);
//                            TextView title = (TextView) findViewById(R.id.tv_title);
//                            price.setText("Preco : "+Double.toString(itemBoutique.getPrice()));
//                            title.setText("Tilulo : "+itemBoutique.getTitle());
                            //location.setText();
                            //boutique.setText();
                            //openHours.setText();

                            ItemBoutique itemBoutique= new ItemBoutique();
                            savedItemList.get(cont).setFileImage(itemBoutique.getFileImage());
                            savedItemList.get(cont).setTitle(itemBoutique.getTitle());
                            //savedItemList.get(cont).setType("shoes");
                            savedItemList.get(cont).setSize(itemBoutique.getSize());
                            savedItemList.get(cont).setPrice(Double.valueOf(itemBoutique.getPrice());
                            savedItemList.get(cont).setStokeSize(itemBoutique.getStokeSize());
                            //itemBoutique.setImage(23);
                            savedItemList.get(cont).setGender(itemBoutique.getGender());
                            savedItemList.get(cont).setDescription(itemBoutique.getDescription());


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(NavFavorites.this, "erro de conexao a internet", Toast.LENGTH_SHORT).show();
                            //Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                        }
                    });*/


        }

        //set everything into the adaptor


    }

}
