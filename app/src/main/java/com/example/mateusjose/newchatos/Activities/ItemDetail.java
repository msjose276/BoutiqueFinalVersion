package com.example.mateusjose.newchatos.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mateusjose.newchatos.Objects.ItemBoutique;
import com.example.mateusjose.newchatos.Objects.Person;
import com.example.mateusjose.newchatos.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class ItemDetail extends AppCompatActivity {

    public static final String ITEM_ID_STRING = "ITEM_ID";
    public String ITEM_ID;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    ItemBoutique itemBoutique;
    Person posterPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        Intent i = getIntent();
        // get the item_id from the past activity
        ITEM_ID = i.getStringExtra(ITEM_ID_STRING);


        mAuth = FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();

        //ItemBoutique itemBoutique= new ItemBoutique();
        //Query query = mDatabase.child("ItemBoutique").orderByChild("itemID").equalTo(ITEM_ID).limitToLast(50);

        //get the information about the product
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


        //get the information about the poster's name........ itemBoutique.getPosterID()
        mDatabase.child("Users").child("qTMOgIg5trYOQOD3Z20cgdu0fHA2").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        posterPerson = dataSnapshot.getValue(Person.class);
                        TextView location = (TextView) findViewById(R.id.tv_location);
                        TextView boutique = (TextView) findViewById(R.id.tv_boutique);

                        //location.setText("Localizacao: "+ posterPerson.getAddress().getProvince() +", "+ posterPerson.getAddress().getMonicipio());
                        location.setText("Localizacao: Luanda, Benfica");
                        //boutique.setText("Boutique: "+posterPerson.getBoutiqueName());
                        boutique.setText("Boutique: Dabeleza");

                        //openHours.setText();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(ItemDetail.this, "erro de conexao a internet", Toast.LENGTH_SHORT).show();
                        //Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                    }
                });

        TextView back = (TextView) findViewById(R.id.tv_back);
        ImageView imageItem = (ImageView) findViewById(R.id.iv_image_item);
        TextView forward = (TextView) findViewById(R.id.tv_forward);

    }




    public void SaveItem(View view){

        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        if(firebaseUser==null){
            Toast.makeText(this, "para salvar roupas e acessorios precisa de estar login", Toast.LENGTH_SHORT).show();
        }
        else{
            //save item for each user
            mDatabase.child("ItemSaved").child(firebaseUser.getUid()).setValue(ITEM_ID);
        }
    }
}
