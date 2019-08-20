package com.example.chatclientapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.ViewHolder> {

    public List<Validation0> mydata2;
    public Context context;

    List<Validation0> responseData2;

    Gson gson = new Gson();

    public AdminAdapter(List<Validation0> mydata2, Context context) {
        this.mydata2 = mydata2;
        Log.e("mdata",gson.toJson(mydata2));
        this.context = context;
    }


    @NonNull
    @Override
    public AdminAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminAdapter.ViewHolder holder, int position) {

        holder.username.setText(mydata2.get(position).username);
        holder.mobileno.setText(mydata2.get(position).mobileno);
        holder.unread.setText(mydata2.get(position).unread);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Api_interface api_interface = RetrofitClient.getClient(context).create(Api_interface.class);

                api_interface.postInit2(mydata2.get(position).username)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(a-> {})
                        .doOnError(e -> Log.e("onError",gson.toJson(e)))
                        .subscribe(response2 -> {
                            responseData2=response2;
                            String str=gson.toJson(response2);
                            Log.e("string",str);


                            Intent a=new Intent(context,Activity4.class);
                            a.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                            a.putExtra("Response",str);
                            Log.e("response",gson.toJson(response2));
                            context.startActivity(a);
                            ((Activity)context).finish();



                        }, e-> {
                            e.printStackTrace();

                        });
            }
        });

    }

    @Override
    public int getItemCount() {
        return mydata2.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView username,mobileno,unread;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username=itemView.findViewById(R.id.username);
            mobileno=itemView.findViewById(R.id.mobileno);
            unread=itemView.findViewById(R.id.status);
            cardView=itemView.findViewById(R.id.card);

        }
    }
}
