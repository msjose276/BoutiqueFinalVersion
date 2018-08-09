package com.example.mateusjose.newchatos.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mateusjose.newchatos.Objects.Boutique;
import com.example.mateusjose.newchatos.Objects.BoutiqueUser;
import com.example.mateusjose.newchatos.Objects.ConfigurationFirebase;
import com.example.mateusjose.newchatos.Objects.LoggedUserSingleton;
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
    BoutiqueUser boutiqueUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    public void register(View view) {

        EditText fullName = (EditText) findViewById(R.id.tv_full_name);
        EditText cellphone = (EditText) findViewById(R.id.et_cellphone);
        EditText email = (EditText) findViewById(R.id.et_email);
        EditText password = (EditText) findViewById(R.id.et_password);
        TextView registar = (TextView) findViewById(R.id.tv_registar);

         //If the first and last names fields are empty, do not allow sign up.
        if(fullName.getText().toString().matches("")){
            fullName.setError("por favor, escreva o seu nome completo");
            Toast.makeText(this, "por favor, escreva o seu nome completo", Toast.LENGTH_SHORT).show();
        }
        else if(email.getText().toString().matches("")) {
            email.setError("por favor, escreva o seu email aqui");
            Toast.makeText(this, "por favor, escreva o seu email aqui", Toast.LENGTH_SHORT).show();
        }
        else if(cellphone.getText().toString().equals("") || cellphone.getText().toString().length()<8){
            cellphone.setError("este campo nao pode estar vazio. por favor escreva o seu numero de tefone");
            Toast.makeText(this, "por favor escreva o seu numero de tefone", Toast.LENGTH_SHORT).show();
        }
        else if(password.getText().toString().length()<8){
            password.setError("por favor, coloque uma palavra-passe com pelo menos 8 caracteres");
            Toast.makeText(this, "password not long enough", Toast.LENGTH_SHORT).show();
        }
        else {

            mAuth = FirebaseAuth.getInstance();

            boutiqueUser = new BoutiqueUser();
            boutiqueUser.setFullName(String.valueOf(fullName.getText()));
            boutiqueUser.setEmail(String.valueOf(email.getText()));
            boutiqueUser.setPassword(String.valueOf(password.getText()));
            boutiqueUser.setPhoneNumber(String.valueOf(cellphone.getText()));

            //make sure the authentication object is already initialized
            mAuth = ConfigurationFirebase.getFirebaseAuth();
            mAuth.createUserWithEmailAndPassword(boutiqueUser.getEmail(), boutiqueUser.getPassword())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //if the signup was succesfull
                                Toast.makeText(SignUp.this, "sucesso ao cadastrar o usuario", Toast.LENGTH_SHORT).show();
                                // get the user created, store its uid and save the the boutique user into the firebase
                                FirebaseUser user = task.getResult().getUser();
                                boutiqueUser.setUserID(user.getUid());
                                boutiqueUser.saveBoutiqueUserOnFirebaseDatabase();

                                LoggedUserSingleton.getInstance();
                                // finish and create a new activity
                                Intent intent = new Intent(getBaseContext(), NavegationDrawerActivity.class);
                                startActivity(intent);
                                finish();

                            } else {
                                String exception = "";
                                try {
                                    throw task.getException();
                                } catch (FirebaseAuthWeakPasswordException e) {
                                    exception = "digite uma senha mais forte contendo letras e numeros";
                                } catch (FirebaseAuthInvalidCredentialsException e) {
                                    exception = "o email digitado é invalido. digite um novo email";
                                } catch (FirebaseAuthUserCollisionException e) {
                                    exception = "o email digitado ja esta em uso no applicativo";

                                } catch (Exception e) {
                                    exception = "erro ao efetuar o cadastro";
                                    e.printStackTrace();
                                }
                                Toast.makeText(SignUp.this, "erro :" + exception, Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }
    }
    public void login(View view){

        Intent intent = new Intent(this,Login.class);
        startActivity(intent);
        finish();
    }
}
