package com.example.mateusjose.newchatos.Activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
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



    FirebaseUser user;


    CircleImageView profileImage;
    TextView userName;
    TextView phoneNumber;

    NavigationView navigationView;
    public static final int RC_SIGN_IN = 1;

    final String users = "Users";
    final String itemBoutique = "ItemBoutique";
    DatabaseReference database = ConfigurationFirebase.getDatabaseReference();
    DatabaseReference refForItemBoutique = database.child(itemBoutique);

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


        SearchBar();

    }



    public void SearchBar(){
        EditText searchBar = (EditText) findViewById(R.id.et_search_bar);

        searchBar.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0){
                    Intent main = new Intent(getBaseContext(), SearchItems.class);
                    startActivity(main);

                }
            }
        });
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
                //set the navegationDrawer header with the user information. if some is missing, it will be replace with the general one
                if(LoggedUserSingleton.getInstance().getBoutiqueUser().getFullName()!=null){
                    userName.setText(LoggedUserSingleton.getInstance().getBoutiqueUser().getFullName());
                }
                else {
                    userName.setText("anonimo");
                }
                if(LoggedUserSingleton.getInstance().getBoutiqueUser().getPhoneNumber()!=null){
                    phoneNumber.setText(LoggedUserSingleton.getInstance().getBoutiqueUser().getPhoneNumber());
                }
                else{
                    phoneNumber.setText("***-***-***");
                }
                if(LoggedUserSingleton.getInstance().getBoutiqueUser().getPhotoUrl()!=null){
                    Glide.with(getBaseContext())
                            .load(LoggedUserSingleton.getInstance().getBoutiqueUser().getPhotoUrl())
                            .into(profileImage);
                }
                else{
                    profileImage.setImageResource(R.drawable.user_general_image);
                }
            }

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
                FirebaseAuth.getInstance().signOut();
                LoggedUserSingleton.getInstance().setBoutiqueUser(null);


            } else {
                // No user is signed in, then it has to log in
                Intent main = new Intent(getBaseContext(), Login.class);
                startActivity(main);
                finish();
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

        if (id == R.id.nav_boutiques) {
            Intent main = new Intent(getBaseContext(), PostItem.class);
            startActivity(main);
        } else if (id == R.id.nav_favorites) {
            Intent main = new Intent(getBaseContext(), SavedItems.class);
            startActivity(main);
        } else if (id == R.id.nav_settings) {
            Intent main = new Intent(getBaseContext(), NavStores.class);
            startActivity(main);
        } else if (id == R.id.nav_about_the_application) {
            Intent main = new Intent(getBaseContext(), AboutTheApplication.class);
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
    }
    @Override
    protected void onResume(){
        super.onResume();
        Toast.makeText(this, "voltamos", Toast.LENGTH_SHORT).show();
        setNavigationDrawerHeader( navigationView);
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


}

