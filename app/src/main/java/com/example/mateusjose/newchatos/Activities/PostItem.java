package com.example.mateusjose.newchatos.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mateusjose.newchatos.Objects.ConfigurationFirebase;
import com.example.mateusjose.newchatos.Objects.ItemBoutique;
import com.example.mateusjose.newchatos.Objects.Shoes;
import com.example.mateusjose.newchatos.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostItem extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference mStorageRef;
    private StorageReference photoRef;
    private static final int PICK_IMAGE=100;
    private static final int RC_PHOTO_PICKER =  2;
    private Uri imageURI;
    private ItemBoutique itemBoutique;
    private Uri downloadUrl;
    private Uri pickedUri;


    EditText etTitle;
    EditText etDescription;
    Spinner spSize;
    EditText etPrice;
    EditText etStokeSize;
    Spinner dropdownSizes;
    Spinner dropdownGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_item);

        String[] sizes = new String[]{"S", "M", "L","XL"};
        Spinner dropdownSizes = (Spinner) findViewById(R.id.sp_size);
        ArrayAdapter<String> adapterSizes = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sizes);
        dropdownSizes.setAdapter(adapterSizes);

        String[] gender = new String[]{"M", "F", "unisex"};
        Spinner dropdownGender = (Spinner) findViewById(R.id.sp_gender);
        ArrayAdapter<String> adapterGender = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, gender);
        dropdownGender.setAdapter(adapterGender);

        storage = ConfigurationFirebase.getFirebaseStorage();
        mStorageRef = ConfigurationFirebase.getStorageReference().child("boutique_items");

    }




    public void postItem(View view){
        etTitle = (EditText) findViewById(R.id.et_title);
        etDescription=(EditText) findViewById(R.id.et_description);
        spSize=(Spinner) findViewById(R.id.sp_size);
        etPrice = (EditText) findViewById(R.id.et_price);
        etStokeSize = (EditText) findViewById(R.id.et_stoke_size);
        dropdownSizes = (Spinner) findViewById(R.id.sp_size);
        dropdownGender = (Spinner) findViewById(R.id.sp_gender);

        if(etTitle.getText().toString().length()<2){
            etTitle.setError("preenche o titulo");
        }
        else if(etDescription.getText().toString().length()<10){
            etDescription.setError("a descricao tem que ter mais de 10 letras");
        }
        else if(etPrice.getText().toString().length()<1){
            etPrice.setError("por favor coloque o valor de venda para o produto");
        }
        else if(etStokeSize.getText().toString().length()<1){
            etStokeSize.setError("por favor coloque a quantidade deste produto de disponivel para a venda");
        }
        else{

            //***********************
            //mAuth = FirebaseAuth.getInstance();
            //mDatabase= FirebaseDatabase.getInstance().getReference();

            mAuth = ConfigurationFirebase.getFirebaseAuth();
            mDatabase= ConfigurationFirebase.getDatabaseReference();

            itemBoutique= new ItemBoutique();
            String itemID=mDatabase.child("ItemBoutique").push().getKey();
            itemBoutique.setItemID(itemID);
            //upload image to the server before storing the refence of the image in the itemBoutique
            StorageReference photoRef = mStorageRef.child(itemBoutique.getItemID()).child(pickedUri.getLastPathSegment()+".jpg");
            photoRef.putFile(pickedUri).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    itemBoutique.setImagePath(taskSnapshot.getMetadata().getPath());
                    //taskSnapshot.getUploadSessionUri().get
                    sendBoutiqueItemToDatabase();

                }
            });


        }
    }


    public void sendBoutiqueItemToDatabase(){

        //store the values into an object

        //itemBoutique.setPhotoUrl(photoRef.);
        itemBoutique.setTitle(etTitle.getText().toString());
        itemBoutique.setType("shoes");
        itemBoutique.setSize(dropdownSizes.getSelectedItem().toString());
        itemBoutique.setPrice(Double.valueOf(etPrice.getText().toString()));
        itemBoutique.setStokeSize(Integer.valueOf(etStokeSize.getText().toString()));
        itemBoutique.setGender(dropdownGender.getSelectedItem().toString());
        itemBoutique.setDescription(etDescription.getText().toString());
        // stores the item in the database
        mDatabase.child("ItemBoutique").child(itemBoutique.getItemID()).setValue(itemBoutique);
        Toast.makeText(PostItem.this, "posted eee", Toast.LENGTH_SHORT).show();

    }


    public void getImage(View view){
        CircleImageView circleImageView = (CircleImageView)findViewById(R.id.ci_profile_image);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/jpeg");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK ){
            pickedUri = data.getData();
            //pickedUri.get
        }
    }
}




