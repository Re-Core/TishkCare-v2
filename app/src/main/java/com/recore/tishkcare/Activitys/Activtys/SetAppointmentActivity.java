package com.recore.tishkcare.Activitys.Activtys;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kd.dynamic.calendar.generator.ImageGenerator;
import com.recore.tishkcare.R;
import com.squareup.picasso.Picasso;

import java.time.Year;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

public class SetAppointmentActivity extends AppCompatActivity{

    /**
     * This class is the one that let you get an appointment
     * It send the doctor information to the patient appointment
     * and the patient information to the doctor app ( doctor appointment )
     *
     * */


    //the flowing are the component we used

    String doctorId;
    String patientId;
    String doctorType;
    //the firebase object
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference appointmentRef;

    //our data about the doctor
    private String doctorName,doctorimg,doctorWork,doctorCity,doctorEducation,doctorSpecilty,
            doctorGender,doctorAge,doctorMail,doctorPhone;
    //our data about the patient
    private String patientName,patientimg,patientWork,patientCity,patientEducation,patientBloodGroup,
            patientGender,patientAge,patientMail,patientPhone,patientMarriage;

    private String medicalBackground,currentProblem;

    private EditText edtMedicalBackground,edtCurrentProblem;

    private Button btnAppointment;
    //the appointment time and date
    private String appointmentDate;
    private String appointmentTime;
    private int dd,mm,yy,hh,mn;


    private TextView txtPatientName;
    private TextView txtTime;
    private CircleImageView imgPatient;
    private Calendar mCurrentDate;
    private Button btnDate;
    private Bitmap mGenerateIcon;
    private ImageGenerator mImageGenerator;
    private ImageView dispalyGenratedImg;
    private Button btnTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_appointment);

        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        ScrollView scrollView = (ScrollView)findViewById(R.id.rootlay);
        Snackbar.make(scrollView,"please select a date and time to proceed",Snackbar.LENGTH_LONG)
                .show();

        patientId=mUser.getUid();
        doctorId=getIntent().getStringExtra("doctorId");
        doctorType=getIntent().getStringExtra("specilty");

        getDoctorDetails();
        getPatientDetails();

        edtMedicalBackground=(EditText)findViewById(R.id.medicalBackgrount);
        edtCurrentProblem=(EditText)findViewById(R.id.currentProblem) ;

        txtPatientName =(TextView)findViewById(R.id.txtPatientName);
        imgPatient=(CircleImageView)findViewById(R.id.patientImg);
        txtTime=(TextView)findViewById(R.id.txtTime);
        btnAppointment =(Button)findViewById(R.id.reqAppointment);
        btnAppointment.setEnabled(false);

        mImageGenerator = new ImageGenerator(this);
        dispalyGenratedImg=(ImageView)findViewById(R.id.generatedImg);
        btnDate=(Button)findViewById(R.id.btnDate);
        btnTime=(Button)findViewById(R.id.btnTime);

        mImageGenerator.setIconSize(50,50);
        mImageGenerator.setDateSize(30);
        mImageGenerator.setMonthSize(10);
        mImageGenerator.setDatePosition(42);
        mImageGenerator.setMonthPosition(14);

        mImageGenerator.setDateColor(Color.parseColor("#3c6eaf"));
        mImageGenerator.setMonthColor(Color.WHITE);

        mImageGenerator.setStorageToSDCard(true);

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mCurrentDate = Calendar.getInstance();
                int year =mCurrentDate.get(Calendar.YEAR);
                int month =mCurrentDate.get(Calendar.MONTH);
                int day =mCurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(SetAppointmentActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int syear, int smonth, int sdayOfMonth) {
                        btnDate.setText(sdayOfMonth+"/"+smonth+"/"+syear);
                        mCurrentDate.set(syear,smonth,sdayOfMonth);
                        mGenerateIcon =mImageGenerator.generateDateImage(mCurrentDate,R.drawable.empty_calendar);

                        dd=mCurrentDate.get(Calendar.DAY_OF_MONTH);
                        mm=mCurrentDate.get(Calendar.MONTH);
                        yy=mCurrentDate.get(Calendar.YEAR);

                        Log.d("Date",dd+"/"+mm+"/"+yy);
                        dispalyGenratedImg.setImageBitmap(mGenerateIcon);
                        btnAppointment.setEnabled(true);

                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        btnTime.setOnClickListener(new View.OnClickListener() {

            Calendar mCalendar =Calendar.getInstance();
            int hour =mCalendar.get(Calendar.HOUR_OF_DAY);
            int minute =mCalendar.get(Calendar.MINUTE);

            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog =new TimePickerDialog(SetAppointmentActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        btnTime.setText(hourOfDay+":"+minute);
                        txtTime.setText(hourOfDay+":"+minute);
                        hh=hourOfDay;
                        mn=minute;

                    }
                },hour,minute,false);
                timePickerDialog.show();
            }
        });

        btnAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Date",dd+"/"+mm+"/"+yy);

                String month,day,year,minute,hour;
                month=Integer.toString(mm);
                day=Integer.toString(dd);
                year=Integer.toString(yy);

                minute=Integer.toString(mn);
                hour=Integer.toString(hh);

                appointmentTime=hour+":"+minute;
                appointmentDate=day+"/"+month+"/"+year;

                addAppointmentToDatabase();

                Intent i = new Intent(SetAppointmentActivity.this,MainActivity.class);
                Toast.makeText(SetAppointmentActivity.this, "Appointment was Set", Toast.LENGTH_SHORT).show();
                startActivity(i);
            }
        });




    }

    private void getDoctorDetails(){

        DatabaseReference doctorRef =mFirebaseDatabase.getReference("Doctors").child(doctorType).child(doctorId);

        doctorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (dataSnapshot.child("doctorImg").exists()) {

                        doctorimg = dataSnapshot.child("doctorImg").getValue().toString();

                    }
                    if (dataSnapshot.child("education").exists()) {
                        doctorEducation = dataSnapshot.child("education").getValue().toString();

                    }
                    if (dataSnapshot.child("dateOfBirth").exists()) {
                        doctorAge = dataSnapshot.child("dateOfBirth").getValue().toString();

                    }
                    if (dataSnapshot.child("phone").exists()) {
                        doctorPhone = dataSnapshot.child("phone").getValue().toString();

                    }
                    if (dataSnapshot.child("work").exists()) {
                        doctorWork = dataSnapshot.child("work").getValue().toString();

                    }
                    if (dataSnapshot.child("email").exists()) {
                        doctorMail = dataSnapshot.child("email").getValue().toString();

                    }
                    if (dataSnapshot.child("name").exists()) {
                        doctorName = dataSnapshot.child("name").getValue().toString();

                    }
                    if (dataSnapshot.child("gender").exists()) {
                        doctorGender = dataSnapshot.child("gender").getValue().toString();

                    }
                    if (dataSnapshot.child("speciality").exists()) {
                        doctorSpecilty = dataSnapshot.child("speciality").getValue().toString();

                    }
                    if (dataSnapshot.child("location").exists()) {
                        doctorCity = dataSnapshot.child("location").getValue().toString();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getPatientDetails(){

        DatabaseReference patientRef =mFirebaseDatabase.getReference("Patients").child(mAuth.getCurrentUser().getUid());

        patientRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    if (dataSnapshot.child("image").exists()) {

                        patientimg = dataSnapshot.child("image").getValue().toString();
                        Picasso.get().load(patientimg).into(imgPatient);
                    }
                    if (dataSnapshot.child("education").exists()) {
                        patientEducation = dataSnapshot.child("education").getValue().toString();

                    }
                    if (dataSnapshot.child("dateOfBirth").exists()) {
                        patientAge = dataSnapshot.child("dateOfBirth").getValue().toString();

                    }
                    if (dataSnapshot.child("phone").exists()) {
                        patientPhone = dataSnapshot.child("phone").getValue().toString();

                    }
                    if (dataSnapshot.child("work").exists()) {
                        patientWork = dataSnapshot.child("work").getValue().toString();

                    }
                    if (dataSnapshot.child("email").exists()) {
                        patientMail = dataSnapshot.child("email").getValue().toString();

                    }
                    if (dataSnapshot.child("bloodGroup").exists()) {
                        patientBloodGroup = dataSnapshot.child("bloodGroup").getValue().toString();

                    }
                    if (dataSnapshot.child("gender").exists()) {
                        patientGender = dataSnapshot.child("gender").getValue().toString();

                    }
                    if (dataSnapshot.child("marriage").exists()) {
                        patientMarriage = dataSnapshot.child("marriage").getValue().toString();

                    }
                    if (dataSnapshot.child("city").exists()) {
                        patientCity = dataSnapshot.child("city").getValue().toString();

                    }
                    if (dataSnapshot.child("name").exists()){
                        patientName=dataSnapshot.child("name").getValue().toString();
                        txtPatientName.setText(patientName);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void addAppointmentToDatabase(){

        appointmentRef =mFirebaseDatabase.getReference("Appointments");


        HashMap<String,Object>patientMap= new HashMap<>();

        patientMap.put("bloodGroup",patientBloodGroup);
        patientMap.put("city",patientCity);
        patientMap.put("dateOfBirth",patientAge);
        patientMap.put("education",patientEducation);
        patientMap.put("email",patientMail);
        patientMap.put("gender",patientGender);
        patientMap.put("image",patientimg);
        patientMap.put("marriage",patientMarriage);
        patientMap.put("name",patientName);
        patientMap.put("phone",patientPhone);
        patientMap.put("work",patientWork);
        patientMap.put("patientId",patientId);
        patientMap.put("date",appointmentDate);
        patientMap.put("time",appointmentTime);

        patientMap.put("medicalBackground",edtMedicalBackground.getText().toString());
        patientMap.put("currentProblem",edtCurrentProblem.getText().toString());

        appointmentRef.child("DoctorAppointments").child(doctorId).child(patientId)
                .updateChildren(patientMap);

        HashMap<String,Object>doctorMap=new HashMap<>();
        doctorMap.put("location",doctorCity);
        doctorMap.put("dateOfBirth",doctorAge);
        doctorMap.put("education",doctorEducation);
        doctorMap.put("email",doctorMail);
        doctorMap.put("gender",doctorGender);
        doctorMap.put("image",doctorimg);
        doctorMap.put("name",doctorName);
        doctorMap.put("phone",doctorPhone);
        doctorMap.put("work",doctorWork);
        doctorMap.put("doctorId",doctorId);
        doctorMap.put("specility",doctorSpecilty);
        doctorMap.put("date",appointmentDate);
        doctorMap.put("time",appointmentTime);

        appointmentRef.child("PatientAppointments").child(patientId).child(doctorId)
                .updateChildren(doctorMap);
    }

}
