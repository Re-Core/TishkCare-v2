package com.recore.tishkcare.Activitys.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.recore.tishkcare.Activitys.Adapter.DoctorAdapter;
import com.recore.tishkcare.Activitys.Model.Doctor;
import com.recore.tishkcare.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorListFragment extends Fragment {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private RecyclerView doctorRecycler;
    private DoctorAdapter mDoctorAdapter;
    private List<Doctor>mDoctorList;
    private String doctorType="Surgeon";
    private MaterialSpinner categorySpinner;

    private static final String[] category = {
            "doctor type",
            "Surgeon",
            "Psychiatrist",
            "dentist",
            "Heart"
    };

    public DoctorListFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_doctor_list, container, false);

        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mDatabaseReference=mFirebaseDatabase.getReference("Doctors").child(doctorType);


        doctorRecycler=(RecyclerView)v.findViewById(R.id.doctorRv);
        doctorRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        categorySpinner=(MaterialSpinner)v.findViewById(R.id.category);

        LinearLayout rootLay =(LinearLayout)v.findViewById(R.id.root_layout);
        Snackbar.make(getActivity().findViewById(R.id.content_pan), "select a category ", Snackbar.LENGTH_LONG).show();


        categorySpinner.setItems(category);
        categorySpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Selected " + item, Snackbar.LENGTH_LONG).show();
                mDatabaseReference=mFirebaseDatabase.getReference("Doctors").child(item);
                onStart();
            }
        });



        doctorRecycler.setAdapter(mDoctorAdapter);


        return v;
    }


    @Override
    public void onStart() {
        super.onStart();
        //a change made here
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mDoctorList = new ArrayList<>();

                for (DataSnapshot postSnapshot:dataSnapshot.getChildren()){

                    Doctor doctor =postSnapshot.getValue(Doctor.class);
                    mDoctorList.add(doctor);


                }

                mDoctorAdapter = new DoctorAdapter(getActivity(),mDoctorList);
                doctorRecycler.setAdapter(mDoctorAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



}
