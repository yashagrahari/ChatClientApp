package com.example.chatclientapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static android.content.Context.MODE_PRIVATE;

public class AdminMessageAdapter extends RecyclerView.Adapter<AdminMessageAdapter.ViewHolder> {


    private List<MessageData> mydata;
    List<UserAdminmessagesandactivity3> responsedata;

    Gson gson=new Gson();
    Context context;

    public AdminMessageAdapter(List<MessageData> mydata, Context context) {
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
                                    authBody4.setAdmin("ADMIN");
                                    authBody4.setUser2(mydata.get(0).getLink1__username());
                                    Api_interface api_interface = RetrofitClient.getClient(context).create(Api_interface.class);

                                    api_interface.analysis_delete_api10(authBody4)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .doOnSubscribe(a -> {
                                            })
                                            .doOnError(e -> Log.e("onError", gson.toJson(e)))
                                            .subscribe(response -> {
                                                responsedata = response;
                                                holder.show_message.setVisibility(View.GONE);
                                                holder.createsendmessage.setVisibility(View.GONE);
                                                Log.e("Mydata",gson.toJson(responsedata));
                                                SharedPreferences sharedPreferences1 = context.getSharedPreferences("mydata", MODE_PRIVATE);
                                                SharedPreferences.Editor editor = sharedPreferences1.edit();
                                                editor.putString("data", gson.toJson(responsedata));
                                                editor.apply();

//                                                notifyDataSetChanged();



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

            if(mydata.get(position).getStatus().equalsIgnoreCase("inserted")){
                holder.getmessage.setText(mydata.get(position).getMsg());
            }else{
                holder.getmessage.setText(mydata.get(position).getMsg());
                holder.linearLayout.setBackgroundResource(R.color.transparent);
                holder.getmessage.setBackgroundResource(R.color.transparent);

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
        if(mydata==null||mydata.size()==0){
            return 0;
        }else{
            return mydata.size();
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView show_message, getmessage,creategetmessage,createsendmessage;
        public LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            linearLayout=itemView.findViewById(R.id.linear1);
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




