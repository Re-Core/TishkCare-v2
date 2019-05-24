package com.recore.tishkcare.Activitys.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.recore.tishkcare.Activitys.Adapter.AppointmentAdapter;
import com.recore.tishkcare.Activitys.Model.Appointment;
import com.recore.tishkcare.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppointmentListFragment extends Fragment {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private RecyclerView appointmentRecycler;
    private AppointmentAdapter mAppointmentAdapter;
    private List<Appointment> mAppointmentList;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String currentPatientId;

    public AppointmentListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_appointment, container, false);

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        currentPatientId=mUser.getUid();

        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mDatabaseReference=mFirebaseDatabase.getReference("Appointments").child("PatientAppointments");

        appointmentRecycler=(RecyclerView)v.findViewById(R.id.appointmentRv);
        appointmentRecycler.setLayoutManager(new LinearLayoutManager(getContext()));



        appointmentRecycler.setAdapter(mAppointmentAdapter);
        return v;
    }


    @Override
    public void onStart() {
        super.onStart();

        mDatabaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mAppointmentList = new ArrayList<>();

                for (DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    Appointment appointment =postSnapshot.getValue(Appointment.class);
                    mAppointmentList.add(appointment);


                }

                mAppointmentAdapter = new AppointmentAdapter(getActivity(),mAppointmentList);
                appointmentRecycler.setAdapter(mAppointmentAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
