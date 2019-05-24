package com.recore.tishkcare.Activitys.Activtys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.recore.tishkcare.Activitys.Model.Patient;
import com.recore.tishkcare.R;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingActivity extends AppCompatActivity {

    private Uri imageUri;
    private String myUrl;
    private StorageReference storageProfilePictureRef;
    private String checker ="";
    private StorageTask uploadTask;

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private String currentUserId;
    private Patient currentPateint;
    private CircleImageView profileImg;

    private Button btnUpdateProfile;
    private EditText edtMail,edtMobile,edtEducation,edtWork,edtBirthday;
    private MaterialSpinner spinnerBloodGroup,spinnerGender,spinnerMarriage,spinnerCity;
    private String bloodGroup="",gender="",marriage="",city="";
    private String bloodG="";
    private String genderG="";
    private String MarriageS="";
    private String CityS="";

    private static final String[] BloodGroup = {
            "blood Group",
            "A+",
            "A-",
            "B+",
            "B-",
            "AB+",
            "AB-",
            "O+",
            "O-"
    };

    private static final String[] Gender = {
            "Gender",
            "Male",
            "Female",
            "Rather not to say",

    };

    private static final String[] Marriage = {
            "Marriage",
            "Single",
            "Married",
            "Divorced",

    };
    private static final String[] City = {
            "City",
            "Hawler",
            "Sullaymanyah",
            "Duhok",

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Toolbar setting =(Toolbar)findViewById(R.id.setting_toolbar);
        setSupportActionBar(setting);
        ActionBar actionBar =getSupportActionBar();
        setting.setTitleTextColor(getColor(R.color.basePressColor));
        //actionBar.setDisplayHomeAsUpEnabled(true);

        storageProfilePictureRef= FirebaseStorage.getInstance().getReference().child("Patients_Profile_picture");

        mAuth= FirebaseAuth.getInstance();
        mCurrentUser=mAuth.getCurrentUser();
        currentUserId=mCurrentUser.getUid();

        spinnerBloodGroup=(MaterialSpinner)findViewById(R.id.spinnerBloodGroup);
        spinnerGender=(MaterialSpinner)findViewById(R.id.spinnerGender);
        spinnerMarriage=(MaterialSpinner)findViewById(R.id.spinnerMarriage);
        spinnerCity=(MaterialSpinner)findViewById(R.id.spinnerCity);

        btnUpdateProfile=(Button)findViewById(R.id.popupBtnUpdateProfile);
        edtMail=(EditText)findViewById(R.id.popupMail);
        edtMobile=(EditText)findViewById(R.id.popupMobile);
        edtEducation=(EditText)findViewById(R.id.popUpEducation);
        edtWork=(EditText)findViewById(R.id.popupWork);
        edtBirthday=(EditText)findViewById(R.id.popupBirthday);
        profileImg=(CircleImageView)findViewById(R.id.settingImg);

        userProfileDisplay(profileImg,edtMail,edtMobile,edtBirthday,edtEducation,edtWork);

        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checker="clicked";
                CropImage.activity(imageUri)
                        .setAspectRatio(1,1)
                        .start(SettingActivity.this);
            }
        });

        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checker.equals("clicked")){
                    userInfoSaved();
                }else{
                    updateOnlyUserInfo();
                }
            }
        });

        spinnerBloodGroup.setItems(BloodGroup);
        spinnerBloodGroup.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                bloodGroup=item;

            }
        });
        spinnerBloodGroup.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override public void onNothingSelected(MaterialSpinner spinner) {
                Snackbar.make(spinner, "Nothing selected", Snackbar.LENGTH_LONG).show();
                bloodGroup=bloodG;
            }
        });


        spinnerGender.setItems(Gender);
        spinnerGender.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                gender=item;
            }
        });
        spinnerGender.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override public void onNothingSelected(MaterialSpinner spinner) {
                Snackbar.make(spinner, "Nothing selected", Snackbar.LENGTH_LONG).show();
                gender=genderG;
            }
        });


        spinnerMarriage.setItems(Marriage);
        spinnerMarriage.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                marriage=item;
            }
        });
        spinnerMarriage.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override public void onNothingSelected(MaterialSpinner spinner) {
                Snackbar.make(spinner, "Nothing selected", Snackbar.LENGTH_LONG).show();
                marriage=MarriageS;
            }
        });



        spinnerCity.setItems(City);
        spinnerCity.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                city=item;
            }
        });
        spinnerCity.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override public void onNothingSelected(MaterialSpinner spinner) {
                Snackbar.make(spinner, "Nothing selected", Snackbar.LENGTH_LONG).show();
                city=CityS;
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting,menu);

        MenuItem signOut=menu.findItem(R.id.action_sign_out);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_sign_out:
                FirebaseAuth.getInstance().signOut();
                Intent loginActivity = new Intent(getApplicationContext(), SignUpInActivity.class);
                startActivity(loginActivity);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode== CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE&&resultCode==RESULT_OK&&data!=null){

            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri =result.getUri();
            profileImg.setImageURI(imageUri);


        }else{
            showMessage("Error, try again");
            //this only refresh the activity as the image was changed
            startActivity(new Intent(SettingActivity.this,SettingActivity.class));
            finish();
        }

    }

    private void showMessage(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();

    }

    private void updateOnlyUserInfo() {

        DatabaseReference databaseReference =FirebaseDatabase.getInstance().getReference().child("Patients");

        HashMap<String,Object>userMap= new HashMap<>();
        userMap. put("email", edtMail.getText().toString());
        userMap. put("phone", edtMobile.getText().toString());
        userMap. put("education", edtEducation.getText().toString());
        userMap.put("work",edtWork.getText().toString());
        userMap.put("dateOfBirth",edtBirthday.getText().toString());
        userMap.put("bloodGroup",bloodGroup);
        userMap.put("gender",gender);
        userMap.put("marriage",marriage);
        userMap.put("city",city);
        databaseReference.child(currentUserId).updateChildren(userMap);

        startActivity(new Intent(SettingActivity.this,MainActivity.class));
        showMessage("Profile info updated successfully");
        finish();


    }

    private void userInfoSaved() {
        if (TextUtils.isEmpty(edtMail.getText().toString()))
        {
            Toast.makeText(this, "Name is mandatory.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(edtMobile.getText().toString()))
        {
            Toast.makeText(this, "address is address.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(edtEducation.getText().toString()))
        {
            Toast.makeText(this, "phone is mandatory.", Toast.LENGTH_SHORT).show();
        }
        else if(checker.equals("clicked"))
        {
            uploadImage();
        }
    }

    private void uploadImage()
    {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Update Profile");
        progressDialog.setMessage("Please wait, while we are updating your account information");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        if (imageUri != null)
        {
            final StorageReference fileRef = storageProfilePictureRef
                    .child(currentUserId + ".jpg");

            uploadTask = fileRef.putFile(imageUri);

            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception
                {
                    if (!task.isSuccessful())
                    {
                        throw task.getException();
                    }

                    return fileRef.getDownloadUrl();
                }
            })
                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task)
                        {
                            if (task.isSuccessful())
                            {
                                Uri downloadUrl = task.getResult();
                                myUrl = downloadUrl.toString();

                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Patients");

                                HashMap<String, Object> userMap = new HashMap<>();
                                userMap. put("email", edtMail.getText().toString());
                                userMap. put("phone", edtMobile.getText().toString());
                                userMap. put("education", edtEducation.getText().toString());
                                userMap.put("work",edtWork.getText().toString());
                                userMap.put("dateOfBirth",edtBirthday.getText().toString());

                                userMap.put("bloodGroup",bloodGroup);
                                userMap.put("gender",gender);
                                userMap.put("marriage",marriage);
                                userMap.put("city",city);

                                userMap. put("image", myUrl);
                                ref.child(currentUserId).updateChildren(userMap);
                                progressDialog.dismiss();

                                startActivity(new Intent(SettingActivity.this, MainActivity.class));
                                Toast.makeText(SettingActivity.this, "Profile Info update successfully.", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else
                            {
                                progressDialog.dismiss();
                                Toast.makeText(SettingActivity.this, "Error.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else
        {
            Toast.makeText(this, "image is not selected.", Toast.LENGTH_SHORT).show();
        }
    }

    private void userProfileDisplay(final CircleImageView profileImageView, final EditText Mail, final EditText mobile,
                                    final EditText edtDateOfbirth,final EditText education,final  EditText work) {

        DatabaseReference UserRef = FirebaseDatabase.getInstance().getReference().child("Patients").child(currentUserId);
        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    if (dataSnapshot.child("image").exists()){

                        String image = dataSnapshot.child("image").getValue().toString();

                        Picasso.get().load(image).into(profileImageView);

                    }if (dataSnapshot.child("education").exists()){
                        String educationString = dataSnapshot.child("education").getValue().toString();
                        education.setText(educationString);
                    }if (dataSnapshot.child("dateOfBirth").exists()){
                        String dateOfBirth = dataSnapshot.child("dateOfBirth").getValue().toString();
                        edtDateOfbirth.setText(dateOfBirth);
                    } if (dataSnapshot.child("phone").exists()){
                        String phone = dataSnapshot.child("phone").getValue().toString();
                        mobile.setText(phone);
                    }  if (dataSnapshot.child("work").exists()){
                        String workString= dataSnapshot.child("work").getValue().toString();
                        work.setText(workString);
                    }  if (dataSnapshot.child("email").exists()){
                        String emailAddress = dataSnapshot.child("email").getValue().toString();
                        Mail.setText(emailAddress);
                    }  if (dataSnapshot.child("bloodGroup").exists()){
                        bloodG=dataSnapshot.child("bloodGroup").getValue().toString();
                        //  BloodGroup.setText(bloodG);
                    }  if (dataSnapshot.child("gender").exists()){
                        genderG=dataSnapshot.child("gender").getValue().toString();
                        //  Gender.setText(gender);
                    }  if (dataSnapshot.child("speciality").exists()){
                        MarriageS=dataSnapshot.child("speciality").getValue().toString();
                        //  Marriage.setText(MarriageS);
                    }  if (dataSnapshot.child("city").exists()){
                        CityS=dataSnapshot.child("city").getValue().toString();
                        //City.setText(CityS);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference patientRef =firebaseDatabase.getReference("Patients");
        currentUserId=mCurrentUser.getUid();

        patientRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(currentUserId)) {
                    currentPateint = dataSnapshot.child(currentUserId).getValue(Patient.class);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

}
