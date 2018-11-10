package com.identifyprotector.identifyprotector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

public class creditAdapter extends RecyclerView.Adapter<creditAdapter.creditViewHolder> {

    private Context mCtx;
    private List<creditcard> creditList;
    private creditcard creditlistt;


    public creditAdapter(Context mCtx, List<creditcard> creditList) {
        this.mCtx = mCtx;
        this.creditList = creditList;
    }

    @Override
    public creditAdapter.creditViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R2.layout.credit_item, null);
        return new creditAdapter.creditViewHolder(view);
    }

    @Override
    public void onBindViewHolder(creditAdapter.creditViewHolder holder, final int position) {
        //  logincredentials loginlistt = loginList.get(position);
        creditlistt = creditList.get(position);

        holder.listbut.setText(creditlistt.getNickname());
        holder.listbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mCtx, edit_credit.class);
                intent.putExtra("ID", creditList.get(position).getID());
                intent.putExtra("profile_name", creditList.get(position).getNickname());
                intent.putExtra("CreditNum", creditList.get(position).getCreditNum());
                intent.putExtra("ActivateMonitoring", creditList.get(position).getActivateMonitoring());
                intent.putExtra("SecureCode", creditList.get(position).getSecureCode());
                intent.putExtra("CardOwner", creditList.get(position).getCardOwner());
                intent.putExtra("ExpDate", creditList.get(position).getExpDate());
                intent.putExtra("PhoneNum", creditList.get(position).getPhoneNum());
                intent.putExtra("Country", creditList.get(position).getCountry());
                intent.putExtra("City", creditList.get(position).getCity());
                intent.putExtra("Street", creditList.get(position).getStreet());
                intent.putExtra("ZipCode", creditList.get(position).getZipCode());

                mCtx.startActivity(intent);
                ((Activity)mCtx).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return creditList.size();
    }

    class creditViewHolder extends RecyclerView.ViewHolder {

        Button listbut;

        public creditViewHolder(View itemView) {
            super(itemView);

            listbut=itemView.findViewById(R2.id.credit_list);


            listbut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Intent i = new Intent(mCtx, setting_page2.class);
                    // mCtx.startActivity(i);
                  //  Toast.makeText(mCtx, creditlistt.getNickname(), Toast.LENGTH_SHORT).show();

                }
            });

        }
    }
}
