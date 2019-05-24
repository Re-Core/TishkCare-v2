package com.recore.tishkcare.Activitys.Activtys;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.recore.tishkcare.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorDetailActivity extends AppCompatActivity {

    private String docName,docPhone,docMail,docLocation,specilty,doctorId,doctorImg,docGender,docWork;
    private TextView txtDocName,txtDocPhone,txtDocSpecialty,txtLocation,txtMail,txtGender,txtWork;

    private Button btnAppointment;
    private CircleImageView doctorImgC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_detail);

        doctorId=getIntent().getStringExtra("doctorId");
        doctorImg=getIntent().getStringExtra("doctorImg");
        docName= getIntent().getStringExtra("name");
        docPhone= getIntent().getStringExtra("phone");
        docMail= getIntent().getStringExtra("mail");
        specilty=getIntent().getStringExtra("specialty");
        docLocation= getIntent().getStringExtra("location");
        docGender=getIntent().getStringExtra("gender");
        docWork=getIntent().getStringExtra("address");

        doctorImgC=(CircleImageView)findViewById(R.id.docProfile);

        txtDocName=(TextView)findViewById(R.id.name);
        txtLocation=(TextView)findViewById(R.id.location);
        txtDocSpecialty=(TextView)findViewById(R.id.education);
        txtDocPhone=(TextView)findViewById(R.id.mobileNumber);
        txtMail=(TextView)findViewById(R.id.email);
        txtGender=(TextView)findViewById(R.id.gender);
        txtWork=(TextView)findViewById(R.id.occupation);
        btnAppointment=(Button)findViewById(R.id.reqAppointment);


        Glide.with(getApplicationContext()).load(doctorImg).into(doctorImgC);

        txtDocSpecialty.setText(specilty);
        txtDocName.setText(docName);
        txtLocation.setText(docLocation);
        txtDocPhone.setText(docPhone);
        txtMail.setText(docMail);
        txtGender.setText(docGender);
        txtWork.setText(docWork);

        Toast.makeText(this, doctorId, Toast.LENGTH_SHORT).show();

        btnAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(DoctorDetailActivity.this,SetAppointmentActivity.class);
                i.putExtra("doctorId",doctorId);
                i.putExtra("specilty",specilty);
                startActivity(i);
            }
        });

    }

}
