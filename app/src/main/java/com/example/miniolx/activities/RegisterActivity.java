package com.example.miniolx.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.miniolx.R;
import com.example.miniolx.data.UserModel;
import com.example.miniolx.data.Util;
import com.example.miniolx.data.Validation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {

    private String password;
    String firstName;
    String lastName;
    String username;
    String email;
    private TextInputEditText firstNameET;
    private TextInputEditText lastNameET;
    private TextInputEditText userNameET;
    private TextInputEditText emailET;
    private TextInputEditText passwordET;
    private TextInputLayout userNameContainer;
    private TextInputLayout passwordContainer;
    private ProgressBar signUpPB;
    private Button signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle(R.string.sign_up);

        firstNameET = findViewById(R.id.et_first_name);
        lastNameET = findViewById(R.id.et_last_name);
        userNameET = findViewById(R.id.et_username);
        emailET = findViewById(R.id.et_email);
        passwordET = findViewById(R.id.et_password);
        userNameContainer = findViewById(R.id.container_username_et);
        passwordContainer = findViewById(R.id.container_password_et);
        signUpPB = findViewById(R.id.pb_sign_up);
        signUpBtn = findViewById(R.id.btn_signup);

        userNameET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

                boolean hasUppercase = !editable.toString().equals(editable.toString().toLowerCase());
                boolean hasSpecial = !editable.toString().matches("[A-Za-z ]*");

                if (editable.length() < 4)
                    userNameContainer.setError(getResources().getString(R.string.username_small_error));
                else if (editable.length() > 20)
                    userNameContainer.setError(getResources().getString(R.string.username_big_error));
                else if (hasUppercase || hasSpecial)
                    userNameContainer.setError(getResources().getString(R.string.username_error));
                else
                    userNameContainer.setError(null);
            }
        });

        passwordET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                boolean hasLowercase = !editable.toString().equals(editable.toString().toUpperCase());
                boolean hasUppercase = !editable.toString().equals(editable.toString().toLowerCase());
                boolean hasSpecial = !editable.toString().matches("[A-Za-z0-9 ]*");

                if (editable.length() < 6)
                    passwordContainer.setError(getResources().getString(R.string.short_password_error));
                else if (!hasUppercase)
                    passwordContainer.setError(getResources().getString(R.string.upper_password_error));
                else if (!hasLowercase)
                    passwordContainer.setError(getResources().getString(R.string.lower_password_error));
                else if (!hasSpecial)
                    passwordContainer.setError(getResources().getString(R.string.special_password_error));
                else
                    passwordContainer.setError(null);
            }
        });

    }

    public void openSignIn(View view) {
        Intent i = new Intent(RegisterActivity.this, SignInActivity.class);
        startActivity(i);
        finish();
    }

    public void signUp(View view) {
        firstName = firstNameET.getText().toString();
        lastName = lastNameET.getText().toString();
        username = userNameET.getText().toString();
        email = emailET.getText().toString();
        password = passwordET.getText().toString();

        Validation validation = new Validation();
        int validationResult = validation.validateFields(firstName, lastName, username
                , email, password);
        switch (validationResult) {
            case 0:
                Toast.makeText(this, R.string.missing_fileds, Toast.LENGTH_SHORT).show();
                break;
            case 1:
                signUpPB.setVisibility(View.VISIBLE);
                signUpBtn.setVisibility(View.INVISIBLE);
                addNewUser();
                break;
        }
    }

    //Creating a new user with Firebase Authentication
    public void addNewUser() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Util.U_ID = task.getResult().getUser().getUid();
                            verifyEmailAddress(task.getResult().getUser());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this, R.string.user_failed, Toast.LENGTH_SHORT).show();
                        signUpPB.setVisibility(View.INVISIBLE);
                        signUpBtn.setVisibility(View.VISIBLE);
                    }
                });
    }

    //Verify email address after adding a new user with firebase authentication only
    public void verifyEmailAddress(FirebaseUser user) {
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                            createUserObject();
                    }
                });
    }

    private void createUserObject() {
        UserModel user = new UserModel(firstName, lastName, username, email);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db
                .collection("users")
                .document(Util.U_ID)
                .set(user)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, R.string.user_added, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(this, EmailVerificationActivity.class);
                    i.putExtra("Email", user.getEmail());
                    i.putExtra("Password", password);
                    startActivity(i);
                    finish();
                });
    }

}