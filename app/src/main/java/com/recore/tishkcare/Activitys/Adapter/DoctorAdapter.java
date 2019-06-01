package com.recore.tishkcare.Activitys.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.recore.tishkcare.Activitys.Activtys.DoctorDetailActivity;
import com.recore.tishkcare.Activitys.Model.Doctor;
import com.recore.tishkcare.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.ViewHolder> {

    private Context mContext;
    private List<Doctor>mDoctorList;

    public DoctorAdapter(Context context, List<Doctor> doctorList) {
        mContext = context;
        mDoctorList = doctorList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.item_doctor_card,viewGroup,false);

        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.doctorName.setText(mDoctorList.get(i).getName());
        viewHolder.doctorMobile.setText(mDoctorList.get(i).getPhone());
        viewHolder.doctorCity.setText(mDoctorList.get(i).getLocation());
        viewHolder.doctorType.setText(mDoctorList.get(i).getSpeciality());
        viewHolder.doctorMail.setText(mDoctorList.get(i).getEmail());
        viewHolder.doctorSocial.setText(mDoctorList.get(i).getName());

        Glide.with(mContext).load(mDoctorList.get(i).getDoctorImg()).into(viewHolder.doctorImg);


    }

    @Override
    public int getItemCount() {
        return mDoctorList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView doctorName,doctorMobile,doctorType,doctorCity,doctorMail,doctorSocial;
        private CircleImageView doctorImg;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            doctorName=(TextView)itemView.findViewById(R.id.doctorNameCard);
            doctorMobile=(TextView)itemView.findViewById(R.id.doctorAddressPhoneCard);
            doctorType=(TextView)itemView.findViewById(R.id.doctorSpecialityCard);
            doctorCity=(TextView)itemView.findViewById(R.id.doctorAddressLocationCard);
            doctorImg=(CircleImageView)itemView.findViewById(R.id.doctorCardImg);
            doctorSocial=(TextView)itemView.findViewById(R.id.doctorSocial1);
            doctorMail=(TextView)itemView.findViewById(R.id.doctorSocial2);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position = getAdapterPosition();
                    Intent i = new Intent(mContext, DoctorDetailActivity.class);

                    i.putExtra("doctorId",mDoctorList.get(position).getDoctorId());
                    i.putExtra("doctorImg",mDoctorList.get(position).getDoctorImg());
                    i.putExtra("name",mDoctorList.get(position).getName());
                    i.putExtra("mail",mDoctorList.get(position).getEmail());
                    i.putExtra("phone",mDoctorList.get(position).getPhone());
                    i.putExtra("location",mDoctorList.get(position).getLocation());
                    i.putExtra("specialty",mDoctorList.get(position).getSpeciality());
                    i.putExtra("startHour",mDoctorList.get(position).getStartHour());
                    i.putExtra("endHour",mDoctorList.get(position).getEndHour());
                    i.putExtra("gender",mDoctorList.get(position).getGender());
                    i.putExtra("address",mDoctorList.get(position).getWork());
                    i.putExtra("openingHour",mDoctorList.get(position).getOpeningHour());



                    mContext.startActivity(i);

                }
            });

        }
    }

}
