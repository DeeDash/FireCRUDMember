package com.deedash.firecrud;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    //creating variables for edit text and textview, firebase auth, button and progress bar.
    private TextInputEditText userNameEdt, passwordEdt, confirmPwdEdt;
    private FirebaseAuth mAuth;
    private ProgressBar loadingPB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //initializing all our variables.
        userNameEdt = findViewById(R.id.idEdtUserName);
        passwordEdt = findViewById(R.id.idEdtPassword);
        loadingPB = findViewById(R.id.idPBLoading);
        confirmPwdEdt = findViewById(R.id.idEdtConfirmPassword);
        TextView loginTV = findViewById(R.id.idTVLoginUser);
        Button registerBtn = findViewById(R.id.idBtnRegister);
        mAuth = FirebaseAuth.getInstance();

        //adding on click for login tv.
        loginTV.setOnClickListener(v -> {
            //opening a login activity on clicking login text.
            Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(i);
        });
        //adding click listener for register button.
        registerBtn.setOnClickListener(v -> {
            //hiding our progress bar.
            loadingPB.setVisibility(View.VISIBLE);
            //getting data fro =m our edit text.
            String userName = Objects.requireNonNull(userNameEdt.getText()).toString();
            String pwd = Objects.requireNonNull(passwordEdt.getText()).toString();
            String cnfPwd = Objects.requireNonNull(confirmPwdEdt.getText()).toString();
            //checking if the password and confirm password is equal or not.
            if (!pwd.equals(cnfPwd)) {
                Toast.makeText(RegisterActivity.this, "Please check both having same password..", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(userName) && TextUtils.isEmpty(pwd) && TextUtils.isEmpty(cnfPwd)) {
                //checking if the text fields are empty or not.
                Toast.makeText(RegisterActivity.this, "Please enter your credentials..", Toast.LENGTH_SHORT).show();
            } else {
                //on below line we are creating a new user by passing email and password.
                mAuth.createUserWithEmailAndPassword(userName, pwd).addOnCompleteListener(task -> {
                    //on below line we are checking if the task is success or not.
                    if (task.isSuccessful()) {
                        //in on success method we are hiding our progress bar and opening a login activity.
                        loadingPB.setVisibility(View.GONE);
                        Toast.makeText(RegisterActivity.this, "User Registered..", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        //in else condition we are displaying a failure toast message.
                        loadingPB.setVisibility(View.GONE);
                        Toast.makeText(RegisterActivity.this, "Fail to register user..", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}