package com.identifyprotector.identifyprotector;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

//import com.bumptech.glide.Glide;

import java.util.List;

public class loginAdapter extends RecyclerView.Adapter<loginAdapter.loginViewHolder> {

    private Context mCtx;
    private List<logincredentials> loginList;
    private   logincredentials loginlistt;

    public loginAdapter(Context mCtx, List<logincredentials> loginList) {
        this.mCtx = mCtx;
        this.loginList = loginList;
    }

    @Override
    public loginViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R2.layout.login_item, null);
        return new loginViewHolder(view);
    }




    @Override
    public void onBindViewHolder(loginViewHolder holder, final int position) {
      //  logincredentials loginlistt = loginList.get(position);
         loginlistt = loginList.get(position);

        holder.listbut.setText(loginlistt.getNickname());

        holder.listbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mCtx, edit_logincredential.class);
                intent.putExtra("ID", loginList.get(position).getID());
                intent.putExtra("profile_name", loginList.get(position).getNickname());
                intent.putExtra("appname", loginList.get(position).getAppname());
                intent.putExtra("ActivateMonitoring", loginList.get(position).getActivateMonitoring());
                intent.putExtra("username", loginList.get(position).getUser());
                intent.putExtra("password", loginList.get(position).getPass());

                mCtx.startActivity(intent);
                ((Activity)mCtx).finish();

            }
        });

    }

    @Override
    public int getItemCount() {
        return loginList.size();
    }

    class loginViewHolder extends RecyclerView.ViewHolder {

        Button listbut;

        public loginViewHolder(View itemView) {
            super(itemView);

            listbut=itemView.findViewById(R2.id.login_list);


            listbut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }
    }


}
