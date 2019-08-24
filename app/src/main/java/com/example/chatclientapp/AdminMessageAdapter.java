package com.example.chatclientapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AdminMessageAdapter extends RecyclerView.Adapter<AdminMessageAdapter.ViewHolder> {


    private List<Validation> mydata;
    Gson gson=new Gson();
    Context context;

    public AdminMessageAdapter(List<Validation> mydata,Context context) {
        this.mydata = mydata;
        this.context=context;
        Log.e("json2",gson.toJson(mydata));
    }

    @NonNull
    @Override
    public AdminMessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType==1) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem = layoutInflater.inflate(R.layout.dummy, parent, false);
            return new AdminMessageAdapter.ViewHolder(listItem);
        }else{
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem = layoutInflater.inflate(R.layout.chat_item_right, parent, false);
            return new AdminMessageAdapter.ViewHolder(listItem);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull AdminMessageAdapter.ViewHolder holder, int position) {


        if (mydata.get(position).getName().equalsIgnoreCase("admin")) {

            holder.show_message.setText(mydata.get(position).getMsg());



            String time=mydata.get(position).getDtoi();

            try {
                String str = time.substring(11, 16);
                holder.createsendmessage.setText(str);

            }catch (Exception e){
                Log.e("time",time);

            }

            holder.show_message.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    android.app.AlertDialog.Builder builder=new AlertDialog.Builder(context);
                    builder.setCancelable(true)
                            .setTitle("Delete")
                            .setMessage("Are you sure you want to delete")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Authbody4 authBody4 = new Authbody4();
                                    authBody4.setId(mydata.get(position).getId());
                                    Api_interface api_interface = RetrofitClient.getClient(context).create(Api_interface.class);

                                    api_interface.analysis_delete_api10(authBody4)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .doOnSubscribe(a -> {
                                            })
                                            .doOnError(e -> Log.e("onError", gson.toJson(e)))
                                            .subscribe(response -> {
                                                Delete s = response;

                                            });
//                                    holder.createsendmessage.setText("This message was deleted");
                                    holder.show_message.setVisibility(View.GONE);
                                    holder.createsendmessage.setVisibility(View.GONE);
//                                    mydata.get(position).setMsg("This message was deleted");


                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).show();


                    return false;
                }
            });


        } else {

            if(mydata.get(position).getStatus().equalsIgnoreCase("inserted")){
                holder.getmessage.setText(mydata.get(position).getMsg());
            }else{
                holder.getmessage.setText("This message was deleted");
            }


            String time=mydata.get(position).getDtoi();
            try{
                String str = time.substring(11, 16);
                holder.creategetmessage.setText(str);
            }catch (Exception e){
                Log.e("time",time);

            }


        }
    }

    @Override
    public int getItemCount() {
        if(mydata.equals(null)){
            return 0;
        }else{
            return mydata.size();
        }
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

    @Override
    public int getItemViewType(int position) {
        if (mydata.get(position).getName().equalsIgnoreCase("admin")) {
            return 1;
        }else {
            return 0;
        }
    }

}




