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

import java.util.List;

public class ParentAdminMessageAdapter extends RecyclerView.Adapter<ParentAdminMessageAdapter.ViewHolder> {


    List<Validation0> data1;
    Context context;

    public ParentAdminMessageAdapter(List<Validation0> data1,Context context) {
        this.data1 = data1;
        this.context=context;
    }

    @NonNull
    @Override
    public ParentAdminMessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.date, parent, false);
        return new ParentAdminMessageAdapter.ViewHolder(listItem);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull ParentAdminMessageAdapter.ViewHolder holder, int position) {



        holder.date.setText(data1.get(position).getDate());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        linearLayoutManager.setStackFromEnd(true);
        holder.recyclerView.setLayoutManager(linearLayoutManager);
        AdminMessageAdapter mAdapter = new AdminMessageAdapter(data1.get(position).getData());
        mAdapter.notifyDataSetChanged();
        holder.recyclerView.setAdapter(mAdapter);
//        editText.setText("");


    }

    @Override
    public int getItemCount() {
        return data1.size();
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
