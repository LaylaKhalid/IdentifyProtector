package com.identifyprotector.identifyprotector;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

public class personalAdapter extends RecyclerView.Adapter<personalAdapter.personalViewHolder>{

    private Context mCtx;
    private List<personalinformation> personalList;
    private   personalinformation personallistt;

    public personalAdapter(Context mCtx, List<personalinformation> personalList) {
        this.mCtx = mCtx;
        this.personalList = personalList;
    }

    @Override
    public personalAdapter.personalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R2.layout.personal_item, null);
        return new personalAdapter.personalViewHolder(view);
    }




    @Override
    public void onBindViewHolder(personalAdapter.personalViewHolder holder, final int position) {
        //  logincredentials loginlistt = loginList.get(position);
        personallistt = personalList.get(position);



        holder.listbut.setText(personallistt.getNickname());
        holder.listbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mCtx, edit_personal.class);
                intent.putExtra("ID", personalList.get(position).getID());
                intent.putExtra("profile_name", personalList.get(position).getNickname());
                intent.putExtra("FirstName", personalList.get(position).getFirstName());
                intent.putExtra("MiddleMame", personalList.get(position).getMiddleMame());
                intent.putExtra("LastName", personalList.get(position).getLastName());
                intent.putExtra("ActivateMonitoring", personalList.get(position).getActivateMonitoring());
                intent.putExtra("DOB", personalList.get(position).getDOB());
                intent.putExtra("NationalID", personalList.get(position).getNationalID());
                intent.putExtra("PassportNumber", personalList.get(position).getPassportNumber());
                intent.putExtra("mail", personalList.get(position).getMail());
                intent.putExtra("PhoneNum", personalList.get(position).getPhoneNum());
                intent.putExtra("Country", personalList.get(position).getCountry());
                intent.putExtra("City", personalList.get(position).getCity());
                intent.putExtra("Street", personalList.get(position).getStreet());
                intent.putExtra("ZipCode", personalList.get(position).getZipCode());

                mCtx.startActivity(intent);
                ((Activity)mCtx).finish();
            }
        });


    }

    @Override
    public int getItemCount() {
        return personalList.size();
    }

    class personalViewHolder extends RecyclerView.ViewHolder {

        Button listbut;

        public personalViewHolder(View itemView) {
            super(itemView);

            listbut=itemView.findViewById(R2.id.peronal_list);


            listbut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });

        }
    }


}
