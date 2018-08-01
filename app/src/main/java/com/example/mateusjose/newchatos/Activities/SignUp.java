package com.example.mateusjose.newchatos.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.mateusjose.newchatos.Objects.Boutique;
import com.example.mateusjose.newchatos.Objects.BoutiqueUser;
import com.example.mateusjose.newchatos.Objects.ConfigurationFirebase;
import com.example.mateusjose.newchatos.Objects.Person;
import com.example.mateusjose.newchatos.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class SignUp extends AppCompatActivity {



    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    BoutiqueUser boutiqueUser;

    EditText etFirstName, etLastName, etBirthDate, etCellNumber, etEmail, etConfirmEmail, etPassword, etConfirmPassword;
    Switch swIsCompany;
    Spinner spGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        init();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void init(){
        etFirstName = (EditText) findViewById(R.id.ET_FName);
        etLastName = (EditText) findViewById(R.id.ET_LName);
        etBirthDate = (EditText)findViewById(R.id.ET_Birthday);
        etCellNumber = (EditText)findViewById(R.id.ET_cellNumber);
        etEmail = (EditText)findViewById(R.id.ET_email);
        etConfirmEmail = (EditText)findViewById(R.id.ET_confirmEmail);
        etPassword =(EditText) findViewById(R.id.ET_password);
        etConfirmPassword = (EditText)findViewById(R.id.ET_passwordAgain);
        spGender = (Spinner) findViewById(R.id.SP_sex);
        swIsCompany = (Switch) findViewById(R.id.switch1);
    }

    public void Register(View view) {

        String TAG="error";
        // If the first and last names fields are empty, do not allow sign up.
//        if(firstName.getText().toString().matches("") || lastName.getText().toString().matches("")){
//            Toast.makeText(this, "Fill name fields", Toast.LENGTH_SHORT).show();
//        }
//        else if(birthDate.getText().toString().matches("")){
//            Toast.makeText(this, "Fill birthday fields", Toast.LENGTH_SHORT).show();
//        }
//        else if(!email.getText().toString().equals(confEmail.getText().toString())) {
//            Toast.makeText(this, "the emails does not match", Toast.LENGTH_SHORT).show();
//        }
//        else if( email.getText().toString().matches("") ||confEmail.getText().toString().matches("")){
//            Toast.makeText(this, "Fill emails fields", Toast.LENGTH_SHORT).show();
//
//        }
//        else if(!password.getText().toString().equals(confirmPassword.getText().toString())){
//            Toast.makeText(this, "the passwords does not match", Toast.LENGTH_SHORT).show();
//        }
//        else if(password.getText().toString().length()<8){
//            Toast.makeText(this, "password not long enough", Toast.LENGTH_SHORT).show();
//        }
//        else {

        mAuth = FirebaseAuth.getInstance();

        boutiqueUser = new BoutiqueUser();
        boutiqueUser.setEmail(String.valueOf(etEmail.getText()));
        boutiqueUser.setPassword(String.valueOf(etPassword.getText()));

        //make sure the authentication object is already initialized
        mAuth = ConfigurationFirebase.getFirebaseAuth();
        mAuth.createUserWithEmailAndPassword(boutiqueUser.getEmail(), boutiqueUser.getPassword())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //if the signup was succesfull
                            Toast.makeText(SignUp.this, "sucesso ao cadastrar o usuario", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = task.getResult().getUser();
                            boutiqueUser.setUserID(user.getUid());
                            boutiqueUser.saveBoutiqueUserOnFirebaseDatabase();
                            /*Intent intent = new Intent(getBaseContext(), NavegationDrawerActivity.class);
                            startActivity(intent);*/
                            finish();

                        } else {
                            String exception="";
                            try{
                                throw task.getException();
                            }  catch(FirebaseAuthWeakPasswordException e) {
                                exception = "digite uma senha mais forte contendo letras e numeros";
                            }  catch(FirebaseAuthInvalidCredentialsException e) {
                                exception = "o email digitado é invalido. digite um novo email";
                            } catch(FirebaseAuthUserCollisionException e) {
                                exception = "o email digitado ja esta em uso no applicativo";
                            }catch(Exception e) {
                                exception = "erro ao efetuar o cadastro";
                                e.printStackTrace();
                            }
                            Toast.makeText(SignUp.this, "erro :" + exception, Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
    public void BackToLogin(View view){

        Intent intent = new Intent(this,Login.class);
        startActivity(intent);
        finish();
    }
}
