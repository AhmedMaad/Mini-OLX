package com.example.miniolx.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.miniolx.R;
import com.example.miniolx.data.Util;
import com.example.miniolx.data.Validation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {

    private int visibilityCounter = 0;
    private ProgressBar resetPasswordPB;
    private Button resetPasswordBtn;
    private TextInputEditText emailET;
    private TextInputEditText passwordET;
    private Button signInBtn;
    private ProgressBar loginPB;
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        resetPasswordPB = findViewById(R.id.pb_password_reset);
        resetPasswordBtn = findViewById(R.id.btn_password_reset_link);
        emailET = findViewById(R.id.et_email);
        passwordET = findViewById(R.id.et_password);
        signInBtn = findViewById(R.id.btn_signin);
        loginPB = findViewById(R.id.pb_login);

    }

    public void openSignUp(View view) {
        Intent i = new Intent(SignInActivity.this, RegisterActivity.class);
        startActivity(i);
        finish();
    }

    public void forgetPassword(View view) {
        LinearLayout resetContainer = findViewById(R.id.ll_reset_container);
        if (visibilityCounter % 2 == 0)
            resetContainer.setVisibility(View.VISIBLE);
        else
            resetContainer.setVisibility(View.GONE);
        ++visibilityCounter;
    }

    public void signIn(View view) {
        email = emailET.getText().toString();
        password = passwordET.getText().toString();

        Validation validation = new Validation();
        int validationResult = validation.validateFields(email, password);
        switch (validationResult) {
            case 0:
                Toast.makeText(this, R.string.missing_fileds, Toast.LENGTH_SHORT).show();
                break;
            case 1:
                loginPB.setVisibility(View.VISIBLE);
                signInBtn.setVisibility(View.INVISIBLE);
                loginWithFirebase();
                break;
        }
    }

    public void loginWithFirebase() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        //TODO: Change the following line to this "mAuth.signInWithEmailAndPassword(email, password)"
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = task.getResult().getUser();
                            Log.d("trace", "Email found: " + user.getEmail());
                            if (user.isEmailVerified()) {
                                //Go to home page
                                Util.U_ID = user.getUid();
                                Log.d("trace", "User ID: " + Util.U_ID);
                                Intent intent = new Intent(SignInActivity.this
                                        , HomeActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Intent intent = new Intent(SignInActivity.this
                                        , EmailVerificationActivity.class);
                                intent.putExtra("Email", user.getEmail());
                                intent.putExtra("Password", password);
                                startActivity(intent);
                                finish();
                            }
                        } else
                            Toast.makeText(SignInActivity.this, R.string.wrong_email_password, Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loginPB.setVisibility(View.GONE);
                        signInBtn.setVisibility(View.VISIBLE);
                        Log.d("trace", "Exception: " + e.getMessage());
                    }
                });
    }

    public void sendPasswordResetLink(View view) {
        TextInputEditText emailET = findViewById(R.id.et_reset_email);
        String writtenEmail = emailET.getText().toString();
        Validation validation = new Validation();
        int validationResult = validation.validateFields(writtenEmail);
        switch (validationResult) {
            case 0:
                Toast.makeText(this, R.string.missing_fileds, Toast.LENGTH_SHORT).show();
                break;
            case 1:
                resetPasswordPB.setVisibility(View.VISIBLE);
                resetPasswordBtn.setVisibility(View.INVISIBLE);
                resetPassword(writtenEmail);
                break;
        }
    }

    //Reset password with Firebase authentication only
    public void resetPassword(String emailAddress) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            resetPasswordPB.setVisibility(View.INVISIBLE);
                            resetPasswordBtn.setVisibility(View.VISIBLE);
                            Toast.makeText(SignInActivity.this, R.string.check_email
                                    , Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        resetPasswordPB.setVisibility(View.INVISIBLE);
                        resetPasswordBtn.setVisibility(View.VISIBLE);
                        Toast.makeText(SignInActivity.this, R.string.email_not_recorded
                                , Toast.LENGTH_SHORT).show();
                        Log.d("json", "Error: " + e.getMessage());
                    }
                });

    }

}