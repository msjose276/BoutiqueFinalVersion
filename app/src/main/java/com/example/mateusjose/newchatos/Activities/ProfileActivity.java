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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mateusjose.newchatos.Objects.BoutiqueUser;
import com.example.mateusjose.newchatos.Objects.ConfigurationFirebase;
import com.example.mateusjose.newchatos.Objects.LoggedUserSingleton;
import com.example.mateusjose.newchatos.Objects.ProjStrings;
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
    //private static final String userNotLoged =  "o usuario nao esta logado";


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
        RelativeLayout cover = (RelativeLayout) findViewById(R.id.rl_bg_cover);

        //cover.setBackgroundResource(R.drawable.profile_page_bg);
        cover.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        // get the current user
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            //fill the page with the user information
            fillProfilePage();
        } else {
            // No user is signed in
            Toast.makeText(this, ProjStrings.UserNotLoged, Toast.LENGTH_SHORT).show();
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
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            LoggedUserSingleton.getInstance().getBoutiqueUser().setUserID(FirebaseAuth.getInstance().getCurrentUser().getUid());

            if(LoggedUserSingleton.getInstance().getBoutiqueUser().getFullName()!=null){
                tvFullName.setText("Name: "+LoggedUserSingleton.getInstance().getBoutiqueUser().getFullName());

            }else{
                tvFullName.setText("Name: "+ProjStrings.AnonimousUserName);
            }
            if(LoggedUserSingleton.getInstance().getBoutiqueUser().getEmail()!=null){
                tvEmail.setText("Email: "+LoggedUserSingleton.getInstance().getBoutiqueUser().getEmail());

            }else{
                tvEmail.setText("Email: "+ProjStrings.AnonimousUserEmail);
            }
            if(LoggedUserSingleton.getInstance().getBoutiqueUser().getPhoneNumber()!=null){
                tvCellphoneNumber.setText("Telefone: "+LoggedUserSingleton.getInstance().getBoutiqueUser().getPhoneNumber());

            }else{
                tvCellphoneNumber.setText("Telefone: "+ProjStrings.AnonimousUserPhonenumber);
            }

            if(LoggedUserSingleton.getInstance().getBoutiqueUser().getPhotoUrl()!=null){
                Glide.with(getBaseContext())
                        .load(LoggedUserSingleton.getInstance().getBoutiqueUser().getPhotoUrl())
                        .into(circleImageView);
            }else{
                circleImageView.setImageResource(R.drawable.user_general_image);
            }



        }
        else{
            Toast.makeText(this, ProjStrings.UserNotLoged, Toast.LENGTH_SHORT).show();
            finish();
        }


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

                logedBoutiqueUser.saveNewImagePicture(pickedUri);
                logedBoutiqueUser.saveBoutiqueUserOnFirebaseDatabase();

                LoggedUserSingleton.getInstance();
            }
            else{
                logedBoutiqueUser.saveBoutiqueUserOnFirebaseDatabase();
                //update the profile page
                fillProfilePage();
            }
            Toast.makeText(this, doneEdit, Toast.LENGTH_SHORT).show();
        }

    }

}
