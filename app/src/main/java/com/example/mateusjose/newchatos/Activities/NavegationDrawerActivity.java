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

import com.example.mateusjose.newchatos.Nav_activities.NavFavorites;
import com.example.mateusjose.newchatos.Nav_activities.NavDefinitionsActivity;
import com.example.mateusjose.newchatos.Nav_activities.NavPaymentInformation;
import com.example.mateusjose.newchatos.Nav_activities.NavStores;
import com.example.mateusjose.newchatos.Objects.BoutiqueUser;
import com.example.mateusjose.newchatos.R;
import com.example.mateusjose.newchatos.TabsPager;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

public class NavegationDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    public static final String USER_EMAIL = "USER_EMAIL";
    public static final String USER_PASSWORD = "USER_PASSWORD";
    public static final String USER_ID = "USER_ID";

    public String EMAIL_LOGIN = null;
    public String PASSWORD_LOGIN = null;
    public String USER_ID_LOGIN = null;
    public Menu NavegationMenu=null;


    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //**************** for sliding menu

        TabLayout tabLayout=(TabLayout)findViewById(R.id.tabs);
        ViewPager viewPager=(ViewPager)findViewById(R.id.container);
        TabsPager tabsPager=new TabsPager(getSupportFragmentManager());

        viewPager.setAdapter(tabsPager);
        tabLayout.setupWithViewPager(viewPager);
        //********************** end of sliding menu


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();



        // check if the user is already sign in
        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(firebaseAuth.getCurrentUser() !=null){
                    Toast.makeText(NavegationDrawerActivity.this, "it is already login", Toast.LENGTH_SHORT).show();
                }else{

                    Intent main = new Intent(getBaseContext(), Login.class);
                    startActivity(main);

                    /*// Create and launch sign-in intent
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setAvailableProviders(providers)
                                    .build(),
                            RC_SIGN_IN);*/
                }
            }
        };

    }

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
                                Toast.makeText(NavegationDrawerActivity.this, "sign out", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {

                // No user is signed in
                //Toast.makeText(this, "Not signed in", Toast.LENGTH_SHORT).show();
                Intent main = new Intent(getBaseContext(), Login.class);
                startActivity(main);
            }
            return true;
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
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }
    @Override
    protected void onResume(){
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
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

