package com.example.miniolx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SignInActivity extends AppCompatActivity {

    private int visibilityCounter = 0;
    private ProgressBar resetPasswordPB;
    private Button resetPasswordBtn;
    private TextInputEditText emailET;
    private TextInputEditText passwordET;
    private Button signInBtn;
    private ProgressBar loginPB;

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
                /*UserModel userModel = new UserModel();
                userModel.resetPassword(writtenEmail, new GeneralHandler() {
                    @Override
                    public <T> void onSuccess(Task<T> task) {
                        loginBinding.pbPasswordReset.setVisibility(View.INVISIBLE);
                        loginBinding.btnPasswordResetLink.setVisibility(View.VISIBLE);
                        Toast.makeText(getContext(), R.string.check_email, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Exception e) {
                        loginBinding.pbPasswordReset.setVisibility(View.INVISIBLE);
                        loginBinding.btnPasswordResetLink.setVisibility(View.VISIBLE);
                        Toast.makeText(getContext(), R.string.email_not_recorded, Toast.LENGTH_SHORT).show();
                        Log.d("json", "Error: " + e.getMessage());
                    }
                });*/
                break;
        }
    }

    public void signIn(View view) {
        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();

        Validation validation = new Validation();
        int validationResult = validation.validateFields(email, password);
        switch (validationResult) {
            case 0:
                Toast.makeText(this, R.string.missing_fileds, Toast.LENGTH_SHORT).show();
                break;
            case 1:
                loginPB.setVisibility(View.VISIBLE);
                signInBtn.setVisibility(View.INVISIBLE);
                /*UserModel userModel = new UserModel();
                userModel.loginWithFirebase(email, password, new LoginHandler() {
                    @Override
                    public <T> void onSuccess(Task<T> task) {
                        loginBinding.pbLogin.setVisibility(View.GONE);
                        loginBinding.btnSignin.setVisibility(View.VISIBLE);
                        if (task.isSuccessful()) {
                            Object authResultGeneric = task.getResult();
                            AuthResult authResult = AuthResult.class.cast(authResultGeneric);
                            FirebaseUser user = authResult.getUser();
                            Log.d("json", "Email found: " + user.getEmail());
                            if (user.isEmailVerified()) {
                                //Toast.makeText(getContext(), R.string.logged_successfully, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getContext(), HomeActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            } else
                                Toast.makeText(getContext(), R.string.verify_email, Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(getContext(), R.string.wrong_email_password, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Exception e) {
                        loginBinding.pbLogin.setVisibility(View.GONE);
                        loginBinding.btnSignin.setVisibility(View.VISIBLE);
                        Log.d("json", "Exception: " + e.getMessage());
                    }
                });
                break;*/
        }
    }

}