package com.recore.tishkcare.Activitys.fragments;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.recore.tishkcare.Activitys.Activtys.SettingActivity;
import com.recore.tishkcare.Activitys.Model.Patient;
import com.recore.tishkcare.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference patientRef;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private String currentUserId;
    private Patient currentPateint;
    private Dialog popupEditProfile;
    private ImageView edtProfile;

    private TextView txtName,txtMail,txtGender,txtBloodGroup,txtEducation,txtMobile,
            txtWork,txtMarriage,txtDateOfBirth,txtCity;
    private String bloodGroup="",education="",work="",gender="",marriage="",dateOfBirth,city="";
    private CircleImageView profileImg;




    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_profile, container, false);

        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mAuth=FirebaseAuth.getInstance();
        mCurrentUser=mAuth.getCurrentUser();
        patientRef=mFirebaseDatabase.getReference("Patients");

        txtName=(TextView)v.findViewById(R.id.name);
        txtMail=(TextView)v.findViewById(R.id.email);
        txtMobile=(TextView)v.findViewById(R.id.mobileNumber);

        txtBloodGroup=(TextView)v.findViewById(R.id.blood_group);
        txtEducation=(TextView)v.findViewById(R.id.education);
        txtWork=(TextView)v.findViewById(R.id.occupation);
        txtGender=(TextView)v.findViewById(R.id.gender);
        txtDateOfBirth=(TextView)v.findViewById(R.id.dob);
        txtMarriage=(TextView)v.findViewById(R.id.marriage);
        txtCity=(TextView)v.findViewById(R.id.location);
        edtProfile=(ImageView)v.findViewById(R.id.edit);

        profileImg=(CircleImageView)v.findViewById(R.id.profile);

        userProfileDisplay(profileImg,txtMail,txtMobile,txtDateOfBirth,txtEducation,txtWork,txtName,txtCity,txtBloodGroup,txtGender,txtMarriage);



        edtProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), SettingActivity.class);
                startActivity(i);
            }
        });

        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        currentUserId=mCurrentUser.getUid();

        patientRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(currentUserId)) {
                    currentPateint = dataSnapshot.child(currentUserId).getValue(Patient.class);

                    txtName.setText(currentPateint.getName());
                    txtMail.setText(currentPateint.getEmail());

                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    private void userProfileDisplay(final CircleImageView profileImageView, final TextView Mail, final TextView mobile,
                                    final TextView edtDateOfbirth,final TextView education,final  TextView work,final TextView name,final TextView City,
                                    final TextView BloodGroup,final TextView Gender,final TextView Marriage) {

        DatabaseReference patientRef = FirebaseDatabase.getInstance().getReference().child("Patients").child(mCurrentUser.getUid());
        patientRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                {
                    if (dataSnapshot.exists()) {
                        if (dataSnapshot.child("image").exists()) {

                            String image = dataSnapshot.child("image").getValue().toString();

                            Picasso.get().load(image).into(profileImageView);

                        }
                        if (dataSnapshot.child("education").exists()) {
                            String educationString = dataSnapshot.child("education").getValue().toString();
                            education.setText(educationString);
                        }
                        if (dataSnapshot.child("dateOfBirth").exists()) {
                            String dateOfBirth = dataSnapshot.child("dateOfBirth").getValue().toString();
                            edtDateOfbirth.setText(dateOfBirth);
                        }
                        if (dataSnapshot.child("phone").exists()) {
                            String phone = dataSnapshot.child("phone").getValue().toString();
                            mobile.setText(phone);
                        }
                        if (dataSnapshot.child("work").exists()) {
                            String workString = dataSnapshot.child("work").getValue().toString();
                            work.setText(workString);
                        }
                        if (dataSnapshot.child("email").exists()) {
                            String emailAddress = dataSnapshot.child("email").getValue().toString();
                            Mail.setText(emailAddress);
                        }
                        if (dataSnapshot.child("bloodGroup").exists()) {
                            String bloodG = dataSnapshot.child("bloodGroup").getValue().toString();
                            BloodGroup.setText(bloodG);
                        }
                        if (dataSnapshot.child("gender").exists()) {
                            String gender = dataSnapshot.child("gender").getValue().toString();
                            Gender.setText(gender);
                        }
                        if (dataSnapshot.child("marriage").exists()) {
                            String MarriageS = dataSnapshot.child("marriage").getValue().toString();
                            Marriage.setText(MarriageS);
                        }
                        if (dataSnapshot.child("city").exists()) {
                            String CityS = dataSnapshot.child("city").getValue().toString();
                            City.setText(CityS);
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
