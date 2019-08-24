package com.example.chatclientapp;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static android.content.Context.MODE_PRIVATE;

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

            if(mydata.get(position).getStatus().equalsIgnoreCase("inserted")){
                holder.show_message.setText(mydata.get(position).getMsg());

            }else{
                holder.show_message.setText("This message was deleted");
            }

            String time=mydata.get(position).getDtoi();

                try {
                    String str = time.substring(11, 16);
                    holder.createsendmessage.setText(str);
                }catch (Exception e){

                    Log.e("time",time);
                }

            if(mydata.get(position).getStatus().equalsIgnoreCase("deleted")){
                holder.show_message.setEnabled(false);
            }
            else {

                holder.show_message.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setCancelable(true)
                                .setTitle("Delete")
                                .setMessage("Are you sure you want to delete")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Authbody4 authBody4 = new Authbody4();
                                        authBody4.setId(mydata.get(position).getId());
                                        authBody4.setUser1(mydata.get(position).link1__username);
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
                                        mydata.get(0).setMsg("This message was deleted");
                                        holder.show_message.setText("This message was deleted");
                                        holder.show_message.setEnabled(false);




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
            }
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
                if(mydata.equals(null)){
                    return 0;
                }else {
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

//    public  void updaterecyclerview(List<Validation> data1)
//    {
//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
//        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
//        Log.e("data",gson.toJson(data1));
//        linearLayoutManager.setStackFromEnd(true);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        MessageAdapter mAdapter = new MessageAdapter(data1,context);
//        mAdapter.notifyDataSetChanged();
//        recyclerView.setAdapter(mAdapter);
//    }

}




