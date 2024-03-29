package com.example.chatclientapp;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.List;

public class ParentAdapter_Message extends RecyclerView.Adapter<ParentAdapter_Message.ViewHolder> {


    List<UserAdminmessagesandactivity3> data1;
    Context context;
    Gson gson=new Gson();

    public ParentAdapter_Message(List<UserAdminmessagesandactivity3> data1, Context context) {
        this.data1 = data1;
        this.context=context;
    }

    @NonNull
    @Override
    public ParentAdapter_Message.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.date, parent, false);
        return new ParentAdapter_Message.ViewHolder(listItem);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull ParentAdapter_Message.ViewHolder holder, int position) {
        Log.e("parent"+position,gson.toJson(data1.get(position)));
        holder.date.setText(data1.get(position).getDate());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        linearLayoutManager.setStackFromEnd(true);
        holder.recyclerView.setLayoutManager(linearLayoutManager);
        MessageAdapter mAdapter = new MessageAdapter(data1.get(position).getData(),context,position);
//        mAdapter.notifyDataSetChanged();
        holder.recyclerView.setAdapter(mAdapter);

    }

    public void refreshData (List<UserAdminmessagesandactivity3> data1) {
        this.data1 = data1;
        notifyDataSetChanged();
    }



    @Override
    public int getItemCount() {
        if(data1.size()==0)
        {
            return 0;
        }else
        {
            return data1.size();
        }

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView date;
        RecyclerView recyclerView;


        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            date=(TextView) itemView.findViewById(R.id.date);
            recyclerView=itemView.findViewById(R.id.messagerecycle);
        }
    }
}
