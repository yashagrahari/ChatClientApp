package com.example.chatclientapp;


import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {


    private List<Validation> mydata;

    public MessageAdapter(List<Validation> mydata) {
        this.mydata = mydata;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.chat_item_right, parent, false);
        return new MessageAdapter.ViewHolder(listItem);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {




        if (mydata.get(position).getName().equalsIgnoreCase("user")) {
            holder.show_message.setText(mydata.get(position).getMsg());

            String time=mydata.get(position).getDtoi();

                try {
                    String str = time.substring(11, 16);
                    holder.createsendmessage.setText(str);
                }catch (Exception e){

                    Log.e("time",time);
                }






            holder.getmessage.setVisibility(View.GONE);
            holder.creategetmessage.setVisibility(View.GONE);
            Log.e("msg", mydata.get(position).getMsg());

        } else {
            holder.getmessage.setText(mydata.get(position).getMsg());

            String time=mydata.get(position).getDtoi();
            try {
                String str = time.substring(11, 16);
                holder.creategetmessage.setText(str);
            }catch (Exception e){
                Log.e("time",time);

            }


            holder.show_message.setVisibility(View.GONE);
            holder.createsendmessage.setVisibility(View.GONE);

            Log.e("msg", mydata.get(position).getMsg());

        }
    }

    @Override
    public int getItemCount() {
        return mydata.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView show_message, getmessage,creategetmessage,createsendmessage;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
                show_message = itemView.findViewById(R.id.sendmessage);
                getmessage = itemView.findViewById(R.id.getmessage);
                creategetmessage=itemView.findViewById(R.id.createget);
                createsendmessage=itemView.findViewById(R.id.createsend);

        }
    }

}




