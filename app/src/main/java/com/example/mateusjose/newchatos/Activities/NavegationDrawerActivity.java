package com.example.mateusjose.newchatos.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mateusjose.newchatos.Nav_activities.NavFavorites;
import com.example.mateusjose.newchatos.Nav_activities.NavDefinitionsActivity;
import com.example.mateusjose.newchatos.Nav_activities.NavPaymentInformation;
import com.example.mateusjose.newchatos.Nav_activities.NavStores;
import com.example.mateusjose.newchatos.Objects.BoutiqueUser;
import com.example.mateusjose.newchatos.Objects.ConfigurationFirebase;
import com.example.mateusjose.newchatos.Objects.LoggedUserSingleton;
import com.example.mateusjose.newchatos.Objects.SingletonPatternForItemsSaved;
import com.example.mateusjose.newchatos.R;
import com.example.mateusjose.newchatos.TabsPager;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NavegationDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;


    public static final String users = "Users";



    public String EMAIL_LOGIN = null;
    public String PASSWORD_LOGIN = null;
    public String USER_ID_LOGIN = null;
    public Menu NavegationMenu=null;

    FirebaseUser user;
    BoutiqueUser logedBoutiqueUser;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;


    CircleImageView profileImage;
    TextView userName;
    TextView phoneNumber;

    NavigationView navigationView;

    // Choose authentication providers
    List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.EmailBuilder().build(),
            new AuthUI.IdpConfig.GoogleBuilder().build());
    public static final int RC_SIGN_IN = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navegation_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //setNavigationDrawerHeader( navigationView);
        LoggedUserSingleton.getInstance();
        SingletonPatternForItemsSaved.getInstance();
        //**************** for sliding menu

        TabLayout tabLayout=(TabLayout)findViewById(R.id.tabs);
        ViewPager viewPager=(ViewPager)findViewById(R.id.container);
        TabsPager tabsPager=new TabsPager(getSupportFragmentManager());

        viewPager.setAdapter(tabsPager);
        tabLayout.setupWithViewPager(viewPager);
        //********************** end of sliding menu


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

    }



    //******************************************** update the user information in the navigation header ************************************************

    public void setNavigationDrawerHeader(NavigationView navegationDrawerHeader){

        View header = navegationDrawerHeader.getHeaderView(0);
        profileImage = (CircleImageView)header.findViewById(R.id.iv_profile_image);
        phoneNumber = (TextView)header.findViewById(R.id.tv_user_phone_number);
        userName = (TextView)header.findViewById(R.id.tv_user_full_name);

        //set the user email and profile image in the navegation drawer
        user = FirebaseAuth.getInstance().getCurrentUser();



        if (user != null) {
            // check if the user has already an instance of the singleton.
            // if it has, there is no need to get the data from firebase again
            if (LoggedUserSingleton.getInstance().getBoutiqueUser() != null) {
                userName.setText(LoggedUserSingleton.getInstance().getBoutiqueUser().getFullName());

                phoneNumber.setText(LoggedUserSingleton.getInstance().getBoutiqueUser().getPhoneNumber());
                Glide.with(getBaseContext())
                        .load(LoggedUserSingleton.getInstance().getBoutiqueUser().getPhotoUrl())
                        .into(profileImage);
                //Toast.makeText(this, LoggedUserSingleton.getInstance().getBoutiqueUser().getPhotoUrl().toString(), Toast.LENGTH_SHORT).show();
            } // if there is not an instance, get the data from the firebase
            //else {
                /*logedBoutiqueUser = new BoutiqueUser();


                DatabaseReference database = ConfigurationFirebase.getDatabaseReference();
                DatabaseReference ref = database.child(users);

                //get the user data from the database
                ref.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        logedBoutiqueUser = dataSnapshot.getValue(BoutiqueUser.class);
                        //LoggedUserSingleton.getInstance().setBoutiqueUser(logedBoutiqueUser);
                        logedBoutiqueUser.setUserID(user.getUid());
                        //get the full name of the user
                        userName.setText(logedBoutiqueUser.getFullName());
                        phoneNumber.setText(logedBoutiqueUser.getPhoneNumber());

                        if (logedBoutiqueUser.getImagePath() != null) {
                            StorageReference storageReference = ConfigurationFirebase.getStorageReference().child(logedBoutiqueUser.getImagePath());
                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    //set the user profile image
                                    logedBoutiqueUser.setPhotoUrl(uri);
                                    Glide.with(getBaseContext())
                                            .load(logedBoutiqueUser.getPhotoUrl())
                                            .into(profileImage);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    //do something
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });*/
                //userName.setText("not very well done");
            //}
        }// if the user is not logged in
        else {
            userName.setText("anonimo usuario");
            phoneNumber.setText("+244-923-222-222");
            profileImage.setImageResource(R.drawable.user_general_image);
        }
    }

    //********************************************************************************************
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navegation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.it_login_sign_up) {
            //if the user if login
            if (FirebaseAuth.getInstance().getCurrentUser() != null) {

                //sign out
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                // ...
                                //update the navegation drawer
                                user=null;
                                Toast.makeText(NavegationDrawerActivity.this, "sign out", Toast.LENGTH_SHORT).show();
                            }
                        });

            } else {

                // No user is signed in
                //Toast.makeText(this, "Not signed in", Toast.LENGTH_SHORT).show();
                Intent main = new Intent(getBaseContext(), Login.class);
                startActivity(main);
                //check if the user logged in successefully before assigning the user variable
                if (FirebaseAuth.getInstance().getCurrentUser() != null)
                    user=FirebaseAuth.getInstance().getCurrentUser();

            }


            //update the navegation drawer
            setNavigationDrawerHeader(navigationView);
            return true;
        }
        if(id == R.id.it_cart){
            // start the saved items
            Toast.makeText(this, "SavedItems", Toast.LENGTH_SHORT).show();
            Intent main = new Intent(getBaseContext(), SavedItems.class);
            startActivity(main);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent main = new Intent(getBaseContext(), PostItem.class);
            startActivity(main);
        } else if (id == R.id.nav_favorites) {
            Intent main = new Intent(getBaseContext(), NavFavorites.class);
            startActivity(main);
        } else if (id == R.id.nav_stores) {
            Intent main = new Intent(getBaseContext(), NavStores.class);
            startActivity(main);
        } else if (id == R.id.nav_payment_information) {
            Intent main = new Intent(getBaseContext(), NavPaymentInformation.class);
            startActivity(main);
        } else if (id == R.id.nav_definitions) {
            Intent main = new Intent(getBaseContext(), NavDefinitionsActivity.class);
            startActivity(main);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onPause(){
        super.onPause();
        //setNavigationDrawerHeader( navigationView);
        //mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }
    @Override
    protected void onResume(){
        super.onResume();
        Toast.makeText(this, "voltamos", Toast.LENGTH_SHORT).show();
        setNavigationDrawerHeader( navigationView);
        //mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                BoutiqueUser boutiqueUser = new BoutiqueUser();
                boutiqueUser.setEmail(user.getEmail());
                boutiqueUser.setFullName(user.getDisplayName());
                //mDatabase.u
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }

    public void MyProfile(View view){
        Intent main = new Intent(getBaseContext(), ProfileActivity.class);
        startActivity(main);
    }

    public void SignIn(){


    }
}

