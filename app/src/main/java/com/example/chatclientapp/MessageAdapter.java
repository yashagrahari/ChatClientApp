package com.example.chatclientapp;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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

    private List<MessageData> mydata;
    public Context context;
    List<UserAdminmessagesandactivity3> responsedata;
    Gson gson = new Gson();
    int parentPosition;

    public MessageAdapter(List<MessageData> mydata, Context context, int parentPosition) {
        this.mydata = mydata;
        this.context = context;
        this.parentPosition = parentPosition;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == 1) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem = layoutInflater.inflate(R.layout.dummy, parent, false);
            return new MessageAdapter.ViewHolder(listItem);
        } else {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem = layoutInflater.inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapter.ViewHolder(listItem);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("hello", gson.toJson(mydata.get(position)));
            }
        });
        if (mydata.get(position).getName().equalsIgnoreCase("user")) {
            if (mydata.get(position).getStatus().equalsIgnoreCase("deleted")) {
                holder.show_message.setText("This message was deleted");
                holder.linearLayout.setBackgroundResource(R.color.transparent);

            } else {
                holder.show_message.setText(mydata.get(position).getMsg());

            }

            String time = mydata.get(position).getDtoi();

            try {
                String str = time.substring(11, 16);
                holder.createsendmessage.setText(str);
            } catch (Exception e) {

            }

            if (mydata.get(position).getStatus().equalsIgnoreCase("deleted")) {
                holder.show_message.setEnabled(false);
                Log.e("DELETED"+position,""+position);
            }
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
                                                responsedata = response;
                                                mydata.set(position,responsedata.get(parentPosition).getData().get(position));
                                                notifyItemChanged(position);
//                                                    mydata = responsedata.get(0).getData();
                                                SharedPreferences sharedPreferences1 = context.getSharedPreferences("mydata", MODE_PRIVATE);
                                                SharedPreferences.Editor editor = sharedPreferences1.edit();
                                                editor.putString("data", gson.toJson(responsedata));
                                                editor.apply();
                                                ParentAdapter_Message parentAdapter_message = new ParentAdapter_Message(responsedata, context);
                                                parentAdapter_message.notifyDataSetChanged();
//                                                mydata = responsedata.get(parentPosition).getData();
//                                                notifyDataSetChanged();
//                                                    notifyDataSetChanged();
                                            });
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
            Log.e("notUser","");
            holder.getmessage.setText(mydata.get(position).getMsg());

            String time = mydata.get(position).getDtoi();
            try {
                String str = time.substring(11, 16);
                holder.creategetmessage.setText(str);
            } catch (Exception e) {

            }

        }
    }

    @Override
    public int getItemCount() {
        if (mydata == null || mydata.size() == 0) {
            return 0;
        } else {
            return mydata.size();
        }

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView show_message, getmessage, creategetmessage, createsendmessage;
        public LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            show_message = itemView.findViewById(R.id.sendmessage);
            getmessage = itemView.findViewById(R.id.getmessage);
            linearLayout = itemView.findViewById(R.id.linear);
            creategetmessage = itemView.findViewById(R.id.createget);
            createsendmessage = itemView.findViewById(R.id.createsend);

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mydata.get(position).getName().equalsIgnoreCase("user")) {
            return 1;
        } else {
            return 0;
        }

    }


}




