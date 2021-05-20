package com.example.miniolx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {

    private String password;
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

    private void verifyEmail(/*final FirebaseUser user, final ProgressBar pbSignUp, final Button btnSignup*/) {
        /*UserModel userModel = new UserModel();
        userModel.verifyEmailAddress(user, new GeneralHandler() {
            @Override
            public <T> void onSuccess(Task<T> task) {
                pbSignUp.setVisibility(View.GONE);
                btnSignup.setVisibility(View.VISIBLE);
                Log.d("json", "Email sent to: " + user.getEmail());
                //Go to verification email activity
                Intent i = new Intent(getContext(), EmailVerificationActivity.class);
                i.putExtra("Email",user.getEmail());
                i.putExtra("Password", password);
                startActivity(i);
                getActivity().finish();
            }

            @Override
            public void onFailure(Exception e) {
                pbSignUp.setVisibility(View.GONE);
                btnSignup.setVisibility(View.VISIBLE);
                Log.d("json", "Cannot verify email: " + e.getMessage());
            }
        });*/
    }

    public void signUp(View view) {
        String firstName = firstNameET.getText().toString();
        String lastName = lastNameET.getText().toString();
        String username = userNameET.getText().toString();
        String email = emailET.getText().toString();
        password = passwordET.getText().toString();

        Validation validation = new Validation();
        int validationResult = validation.validateFields(firstName, lastName, username, email, password);
        switch (validationResult) {
            case 0:
                Toast.makeText(this, R.string.missing_fileds, Toast.LENGTH_SHORT).show();
                break;
            case 1:
                signUpPB.setVisibility(View.VISIBLE);
                signUpBtn.setVisibility(View.INVISIBLE);
                /*final UserModel userModel = new UserModel();
                userModel.addNewUser(email, password, new GeneralHandler() {
                    @Override
                    public <T> void onSuccess(Task<T> task) {
                        Object authResultGeneric = task.getResult();
                        AuthResult authResult = AuthResult.class.cast(authResultGeneric);
                        verifyEmail(authResult.getUser(), signupBinding.pbSignUp
                                , signupBinding.btnSignup);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        signupBinding.pbSignUp.setVisibility(View.GONE);
                        signupBinding.btnSignup.setVisibility(View.VISIBLE);
                        Toast.makeText(getContext(), R.string.user_failed, Toast.LENGTH_SHORT).show();
                        Log.d("json", "Error: " + e.getMessage());
                    }
                });
*/
                break;
        }
    }


}