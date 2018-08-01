package com.example.mateusjose.newchatos.Activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mateusjose.newchatos.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    CircleImageView circleImageView ;
    EditText tvFullName ;
    EditText tvBirthday ;
    EditText tvAddress ;
    EditText tvCellphoneNumber ;
    EditText tvEmail ;
    TextView tvEdit ;

    public FirebaseUser user;
    public boolean editProfile = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2);
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


        circleImageView = (CircleImageView) findViewById(R.id.ci_profile_image);
        tvFullName = (EditText) findViewById(R.id.tv_full_name);
        tvFullName.setEnabled(false);
        tvBirthday = (EditText) findViewById(R.id.tv_birthday);
        tvBirthday.setEnabled(false);
        tvAddress = (EditText) findViewById(R.id.tv_address);
        tvAddress.setEnabled(false);
        tvCellphoneNumber = (EditText) findViewById(R.id.tv_cellphone_number);
        tvCellphoneNumber.setEnabled(false);
        tvEmail = (EditText) findViewById(R.id.tv_email);
        tvEmail.setEnabled(false);
        tvEdit = (TextView) findViewById(R.id.tv_edit);


        // get the current user
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            tvFullName.setText(user.getDisplayName());
            tvEmail.setText(user.getEmail());
            Uri photoUrl = user.getPhotoUrl();
            circleImageView.setImageURI(photoUrl);


            //String uid = user.getUid();
        } else {
            // No user is signed in
            Toast.makeText(this, "Not signed in", Toast.LENGTH_SHORT).show();
            finish();
        }

    }


    public void EditPicture(View view){

        Toast.makeText(this, "edit picture", Toast.LENGTH_SHORT).show();

    }

    public void EditProfile(View view){
        Toast.makeText(this, "edit profile", Toast.LENGTH_SHORT).show();
        if(editProfile){
            tvFullName.setEnabled(true);
            tvBirthday.setEnabled(true);
            tvAddress.setEnabled(true);
            tvCellphoneNumber.setEnabled(true);
            tvEmail.setEnabled(true);
            editProfile = true;

        }else{
            tvFullName.setEnabled(false);
            tvBirthday.setEnabled(false);
            tvAddress.setEnabled(false);
            tvCellphoneNumber.setEnabled(false);
            tvEmail.setEnabled(false);
            editProfile = false;

            String fullName = tvFullName.getText().toString();
            String birthday = tvBirthday.getText().toString();
            String address = tvAddress.getText().toString();
            String cellphone = tvCellphoneNumber.getText().toString();
            String email = tvEmail.getText().toString();

            if(fullName!=null){

            }
            if(birthday!=null){

            }
            if(address!=null){

            }
            if(cellphone!=null){
                //user.updatePhoneNumber(cellphone);
            }
            if(email!=null){
                user.updateEmail(fullName);
            }


            /*String itemID=mDatabase.child("ItemBoutique").push().getKey();
            itemBoutique.setItemID(itemID);
            // stores the item in the database
            mDatabase.child("ItemBoutique").child(itemID).setValue(itemBoutique);
*/

        }

    }

}
