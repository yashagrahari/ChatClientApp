package com.example.chatclientapp;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.DELETE;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {


    private List<Validation> mydata;
    public Context context;
    Gson gson=new Gson();

    public MessageAdapter(List<Validation> mydata,Context context) {
        this.mydata = mydata;
        this.context=context;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType==1) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem = layoutInflater.inflate(R.layout.dummy, parent, false);
            return new MessageAdapter.ViewHolder(listItem);
        }else {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem = layoutInflater.inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapter.ViewHolder(listItem);
        }

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

                                        api_interface.analysis_delete_api10(mydata.get(position).getId())
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .doOnSubscribe(a -> {
                                                })
                                                .doOnError(e -> Log.e("onError", gson.toJson(e)))
                                                .subscribe(response -> {
                                                    Delete s = response;

                                                });
                                        holder.createsendmessage.setVisibility(View.GONE);
                                                    holder.show_message.setVisibility(View.GONE);

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
            holder.getmessage.setText(mydata.get(position).getMsg());

            String time=mydata.get(position).getDtoi();
            try {
                String str = time.substring(11, 16);
                holder.creategetmessage.setText(str);
            }catch (Exception e){
                Log.e("time",time);

            }

        }
    }
    @Override
    public int getItemCount() {
            if(mydata.get(0).getName().equalsIgnoreCase("")){
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
        if (mydata.get(position).getName().equalsIgnoreCase("user")) {
            return 1;
        } else  {
            return 0;
        }

    }

}




