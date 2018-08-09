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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mateusjose.newchatos.Objects.BoutiqueUser;
import com.example.mateusjose.newchatos.Objects.ConfigurationFirebase;
import com.example.mateusjose.newchatos.Objects.LoggedUserSingleton;
import com.example.mateusjose.newchatos.R;
import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    CircleImageView circleImageView ;
    EditText tvFullName ;
    EditText tvCellphoneNumber ;
    EditText tvEmail ;
    TextView tvEdit ;
    // variable for getting the profile picture
    private static final int RC_PHOTO_PICKER =  2;
    private Uri pickedUri=null;


    BoutiqueUser logedBoutiqueUser = new BoutiqueUser();
    public FirebaseUser user;
    public boolean editProfile = true;


    private static final String edit =  "edit perfil";
    private static final String doneEditing =  "salvar alteracoes";
    private static final String doneEdit =  "dados do perfil do usuario actualizados com sucesso";
    private static final String users =  "Users";
    private static final String userNotLoged =  "o usuario nao esta logado";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        circleImageView = (CircleImageView) findViewById(R.id.ci_profile_image);
        tvFullName = (EditText) findViewById(R.id.tv_full_name);
        tvFullName.setEnabled(false);
        tvCellphoneNumber = (EditText) findViewById(R.id.tv_cellphone_number);
        tvCellphoneNumber.setEnabled(false);
        tvEmail = (EditText) findViewById(R.id.tv_email);
        tvEmail.setEnabled(false);
        tvEdit = (TextView) findViewById(R.id.tv_edit);

        // get the current user
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            //fill the page with the user information
            fillProfilePage();
        } else {
            // No user is signed in
            Toast.makeText(this, userNotLoged, Toast.LENGTH_SHORT).show();
            finish();
        }

    }


    //********************************** function to change the user profile

    public void EditPicture(View view){
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
            circleImageView.setImageURI(pickedUri);
        }
    }

    //********************************** end of function to change the user profile



    //********************************** fill out the fields in the profile page

    public void fillProfilePage(){
        LoggedUserSingleton.getInstance().getBoutiqueUser().setUserID(FirebaseAuth.getInstance().getCurrentUser().getUid());
        tvFullName.setText(LoggedUserSingleton.getInstance().getBoutiqueUser().getFullName());
        tvEmail.setText(LoggedUserSingleton.getInstance().getBoutiqueUser().getEmail());
        tvCellphoneNumber.setText(LoggedUserSingleton.getInstance().getBoutiqueUser().getPhoneNumber());
        Glide.with(getBaseContext())
                .load(LoggedUserSingleton.getInstance().getBoutiqueUser().getPhotoUrl())
                .into(circleImageView);


    }

    //***************************** function called by the edit bottom
    // to edit the field in the profile page
    public void EditProfile(View view){
        if(editProfile){
            //change the editProfile variable to change the status of the fields
            editProfile = false;
            tvFullName.setEnabled(true);
            tvCellphoneNumber.setEnabled(true);
            tvEmail.setEnabled(true);
            circleImageView.setEnabled(true);
            tvEdit.setText(edit);
            Toast.makeText(this, edit, Toast.LENGTH_SHORT).show();

        }else{
            //change the editProfile variable to change the status of the fields
            editProfile = true;
            tvFullName.setEnabled(false);
            tvCellphoneNumber.setEnabled(false);
            tvEmail.setEnabled(false);
            circleImageView.setEnabled(false);
            tvEdit.setText(doneEditing);

            logedBoutiqueUser.setUserID(FirebaseAuth.getInstance().getCurrentUser().getUid());
            // check if the field are empty so that they can be updated
            if(tvFullName.getText().toString()!=null){ logedBoutiqueUser.setFullName(tvFullName.getText().toString()); }
            if(tvCellphoneNumber.getText().toString()!=null){ logedBoutiqueUser.setPhoneNumber(tvCellphoneNumber.getText().toString()); }
            if(tvEmail.getText().toString()!=null){ logedBoutiqueUser.setEmail(tvEmail.getText().toString()); }

            // check if the profile image has changed. if it has update the path
            if(pickedUri!=null){

                // save the path of the file that needs to be deleted after the update
                String fileToBeDeleted = logedBoutiqueUser.getImagePath();
                //upload image to the server before storing its reference to the user profile
                StorageReference photoRef = ConfigurationFirebase.getStorageReference().child("UsersProfile").child(logedBoutiqueUser.getUserID()).child(pickedUri.getLastPathSegment()+".jpg");
                photoRef.putFile(pickedUri).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        logedBoutiqueUser.setImagePath(taskSnapshot.getMetadata().getPath());
                        logedBoutiqueUser.saveBoutiqueUserOnFirebaseDatabase();
                        //update the profile page
                        fillProfilePage();
                    }
                });
                // Delete the profile picture of the user
                StorageReference photoRefDeletion = ConfigurationFirebase.getStorageReference().child(fileToBeDeleted);
                photoRefDeletion.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // File deleted successfully
                    }
                });
            }// else save everything into the database
            else{
                logedBoutiqueUser.saveBoutiqueUserOnFirebaseDatabase();
                //update the profile page
                fillProfilePage();
            }
            Toast.makeText(this, doneEdit, Toast.LENGTH_SHORT).show();
        }

    }

}
