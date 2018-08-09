package com.example.mateusjose.newchatos.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mateusjose.newchatos.Objects.ConfigurationFirebase;
import com.example.mateusjose.newchatos.Objects.LoggedUserSingleton;
import com.example.mateusjose.newchatos.Objects.SingletonPatternForItemsSaved;
import com.example.mateusjose.newchatos.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {


    private FirebaseAuth mAuth;

    public static final String USER_EMAIL = "USER_EMAIL";
    public static final String USER_PASSWORD = "USER_PASSWORD";
    public static final String loggedIn = "loggedIn";
    public static final String USER_ID = "USER_ID";

    EditText etEmail;
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView tvEnter = (TextView) findViewById(R.id.TV_enter);
        TextView tvSignUp = (TextView) findViewById(R.id.TV_register);
        etEmail = (EditText) findViewById(R.id.ET_email);
        etPassword = (EditText) findViewById(R.id.ET_password);


        Toast.makeText(this, "first activity = Login", Toast.LENGTH_SHORT).show();
        if (LoggedUserSingleton.getInstance().getBoutiqueUser() != null) {
            LoggedUserSingleton.getInstance();
            SingletonPatternForItemsSaved.getInstance();
            Intent intent = new Intent(Login.this, NavegationDrawerActivity.class);
            startActivity(intent);
            finish();
        }
    }



    public void enter(View view) {
         final String email = String.valueOf(etEmail.getText());
         final String password = String.valueOf(etPassword.getText());

        if(email.equals("")||password.equals("")){
            Toast.makeText(this, "Email and password fields cannot be empty", Toast.LENGTH_SHORT).show();
        }else {

            mAuth = ConfigurationFirebase.getFirebaseAuth();
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                LoggedUserSingleton.getInstance();
                                SingletonPatternForItemsSaved.getInstance();
                                Intent intent = new Intent(Login.this, NavegationDrawerActivity.class);
                                startActivity(intent);
                                Toast.makeText(Login.this, "Login successful", Toast.LENGTH_SHORT).show();
                                //finish the intent and go back to navegation drawer
                                finish();

                            } else {
                                // If sign in fails, display a message to the user.
                                String exception="";
                                try {
                                    throw task.getException();
                                } catch (FirebaseAuthInvalidCredentialsException e) {
                                    exception = "palavra-passe errada";
                                } catch (FirebaseAuthInvalidUserException e) {
                                    exception ="conta com este email nao existe";
                                } catch (Exception e) {
                                    exception = "erro ao efetuar o login";
                                    e.printStackTrace();                                }
                                Toast.makeText(Login.this, "erro : " + exception, Toast.LENGTH_SHORT).show();

                            }
                            // ...
                        }
                    });
        }
    }


    public void register(View view) {
        Intent intent = new Intent(Login.this, SignUp.class);
        startActivity(intent);
        finish();
    }
}
