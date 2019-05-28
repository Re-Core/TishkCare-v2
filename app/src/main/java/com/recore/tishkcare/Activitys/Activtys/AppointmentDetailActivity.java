package com.recore.tishkcare.Activitys.Activtys;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.recore.tishkcare.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;




public class AppointmentDetailActivity extends AppCompatActivity {

    private CircleImageView imgDoctorProfile;
    private TextView txtDoctorName,txtDoctorSpeciality,txtPhone,txtMail,txtaddress,txtTime,txtDate;

    private String appointmentId,patientId;

    private FirebaseAuth mAuth;
    private FirebaseUser mPatient;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_detail);


        mAuth=FirebaseAuth.getInstance();
        mPatient=mAuth.getCurrentUser();
        patientId=mPatient.getUid();

        appointmentId=getIntent().getStringExtra("doctorId");

        //Toast.makeText(this, appointmentId, Toast.LENGTH_SHORT).show();testing doc id

        mFirebaseDatabase=FirebaseDatabase.getInstance();

        mRef=mFirebaseDatabase.getReference("Appointments").child("PatientAppointments").child(patientId)
                .child(appointmentId);

        imgDoctorProfile=(CircleImageView)findViewById(R.id.user_profile_photo);
        txtDoctorName=(TextView)findViewById(R.id.user_profile_name);
        txtDoctorSpeciality=(TextView)findViewById(R.id.user_profile_short_bio);

        txtPhone=(TextView)findViewById(R.id.txtPhone);
        txtMail=(TextView)findViewById(R.id.txtMail);
        txtDate=(TextView)findViewById(R.id.txtDate);
        txtTime=(TextView)findViewById(R.id.txtTime);
        txtaddress=(TextView)findViewById(R.id.txtAddress);

        getAppointmentData();

    }

    //this method load our appointment information and show it to our patient
    private void getAppointmentData(){

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    if (dataSnapshot.child("image").exists()) {
                        String image = dataSnapshot.child("image").getValue().toString();
                        Picasso.get().load(image).into(imgDoctorProfile);
                    }
                    if (dataSnapshot.child("specility").exists()) {
                        String speciality = dataSnapshot.child("specility").getValue().toString();
                        txtDoctorSpeciality.setText(speciality);
                    }
                    if (dataSnapshot.child("name").exists()) {
                        String dateOfBirth = dataSnapshot.child("name").getValue().toString();
                        txtDoctorName.setText(dateOfBirth);
                    }
                    if (dataSnapshot.child("email").exists()) {
                        String email = dataSnapshot.child("email").getValue().toString();
                        txtMail.setText(email);
                    }
                    if (dataSnapshot.child("work").exists()) {
                        String work = dataSnapshot.child("work").getValue().toString();
                        txtaddress.setText(work);
                    }
                    if (dataSnapshot.child("time").exists()) {
                        String time = dataSnapshot.child("time").getValue().toString();
                        txtTime.setText(time);
                    }
                    if (dataSnapshot.child("date").exists()) {
                        String date = dataSnapshot.child("date").getValue().toString();
                        txtDate.setText(date);
                    }
                    if (dataSnapshot.child("phone").exists()) {
                        String phone = dataSnapshot.child("phone").getValue().toString();
                        txtPhone.setText(phone);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
