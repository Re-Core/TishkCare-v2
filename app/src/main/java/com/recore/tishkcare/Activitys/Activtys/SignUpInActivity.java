package com.recore.tishkcare.Activitys.Activtys;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.recore.tishkcare.Activitys.Activtys.MainActivity;
import com.recore.tishkcare.Activitys.Model.Patient;
import com.recore.tishkcare.R;
import com.rengwuxian.materialedittext.MaterialEditText;

import dmax.dialog.SpotsDialog;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SignUpInActivity extends AppCompatActivity {

    private Button btnSignIn,btnRegister;
    private FirebaseAuth mAuth;
    private FirebaseDatabase db;
    private DatabaseReference usersRef;
    private RelativeLayout rootLayout;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CalligraphyConfig.initDefault(new CalligraphyConfig
                .Builder()
                .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                .build());
        setContentView(R.layout.activity_sign_up_in);

        //ini firebase
        mAuth=FirebaseAuth.getInstance();
        db=FirebaseDatabase.getInstance();
        usersRef=db.getReference("Patients");//a refrence to users node in the database



        btnSignIn=(Button)findViewById(R.id.btnSignIn);
        btnRegister=(Button)findViewById(R.id.btnRegister);
        rootLayout =(RelativeLayout)findViewById(R.id.root_layout);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRegisterDialog();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLogInDialog();
            }
        });

    }

    private void showLogInDialog() {

        AlertDialog.Builder logInDialog =  new AlertDialog.Builder(this);
        logInDialog.setTitle("SIGN IN");
        logInDialog.setMessage("Please use email to sign in");

        LayoutInflater inflater = LayoutInflater.from(this);

        View loginLayout = inflater.inflate(R.layout.layout_login,null);

        final MaterialEditText edtEmail =(MaterialEditText)loginLayout.findViewById(R.id.edtEmail);
        final MaterialEditText edtPassword =(MaterialEditText)loginLayout.findViewById(R.id.edtPassword);


        logInDialog.setView(loginLayout);
        logInDialog.setPositiveButton("SIGN IN", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                btnSignIn.setEnabled(false);


                if (TextUtils.isEmpty(edtEmail.getText().toString())){
                    Snackbar.make(rootLayout,"Please enter email address",Snackbar.LENGTH_LONG)
                            .show();
                    return;
                }

                if (TextUtils.isEmpty(edtPassword.getText().toString())){
                    Snackbar.make(rootLayout,"Please enter password",Snackbar.LENGTH_LONG)
                            .show();
                    return;
                }

                if (edtPassword.getText().toString().length()<6){
                    Snackbar.make(rootLayout,"password is too short",Snackbar.LENGTH_LONG)
                            .show();
                    return;
                }

                final Dialog waitingDialog = new SpotsDialog.Builder().setContext(SignUpInActivity.this).build();
                waitingDialog.show();

                mAuth.signInWithEmailAndPassword(edtEmail.getText().toString(),edtPassword.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                waitingDialog.dismiss();
                                startActivity(new Intent(SignUpInActivity.this, MainActivity.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        waitingDialog.dismiss();
                        Snackbar.make(rootLayout,"Failed "+e.getMessage(),Snackbar.LENGTH_LONG)
                                .show();
                        btnSignIn.setEnabled(true);

                    }
                });
            }
        });


        logInDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });

        logInDialog.show();

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
        }
    }


    private void showRegisterDialog() {

        AlertDialog.Builder registerDialog =  new AlertDialog.Builder(this);
        registerDialog.setTitle("REGISTER");
        registerDialog.setMessage("Please use email to register");

        LayoutInflater inflater = LayoutInflater.from(this);

        View registerLayout = inflater.inflate(R.layout.layout_sign_up,null);

        final MaterialEditText edtEmail =(MaterialEditText)registerLayout.findViewById(R.id.edtEmail);
        final MaterialEditText edtPassword =(MaterialEditText)registerLayout.findViewById(R.id.edtPassword);
        final MaterialEditText edtName =(MaterialEditText)registerLayout.findViewById(R.id.edtName);
        final MaterialEditText edtPhone =(MaterialEditText)registerLayout.findViewById(R.id.edtPhone);

        registerDialog.setView(registerLayout);
        registerDialog.setPositiveButton("REGISTER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

                if (TextUtils.isEmpty(edtEmail.getText().toString())){
                    Snackbar.make(rootLayout,"Please enter email address",Snackbar.LENGTH_LONG)
                            .show();
                    return;
                }

                if (TextUtils.isEmpty(edtPassword.getText().toString())){
                    Snackbar.make(rootLayout,"Please enter password",Snackbar.LENGTH_LONG)
                            .show();
                    return;
                }

                if (edtPassword.getText().toString().length()<6){
                    Snackbar.make(rootLayout,"password is too short",Snackbar.LENGTH_LONG)
                            .show();
                    return;
                }

                if (TextUtils.isEmpty(edtName.getText().toString())){
                    Snackbar.make(rootLayout,"Please enter name",Snackbar.LENGTH_LONG)
                            .show();
                    return;
                }

                if (TextUtils.isEmpty(edtPhone.getText().toString())){
                    Snackbar.make(rootLayout,"Please enter phone",Snackbar.LENGTH_LONG)
                            .show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(edtEmail.getText().toString(),edtPassword.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {

                                Patient user = new Patient();

                                user.setEmail(edtEmail.getText().toString());
                                user.setPassword(edtPassword.getText().toString());
                                user.setName(edtName.getText().toString());
                                user.setPhone(edtPhone.getText().toString());

                                //when a new user register make the email the key to that user
                                usersRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Snackbar.make(rootLayout,"Register Successfully",Snackbar.LENGTH_LONG)
                                                        .show();
                                                return;
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Snackbar.make(rootLayout,"Failed "+e.getMessage(),Snackbar.LENGTH_LONG)
                                                        .show();
                                                return;
                                            }
                                        });

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(rootLayout,"Failed "+e.getMessage(),Snackbar.LENGTH_LONG)
                                .show();
                        return;
                    }
                });
            }
        });

        registerDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        registerDialog.show();

    }
}
