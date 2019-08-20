package com.example.chatclientapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class activity_admin extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager lm;
    private AdminAdapter adapter;

    Gson gson=new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        androidx.appcompat.widget.Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        SharedPreferences sharedPreferences1 =getSharedPreferences("mydata",MODE_PRIVATE);
        String str2 = sharedPreferences1.getString("data","");
        Log.e("extra2",str2);
        Type type=new TypeToken<List<Validation0>>() {
        }.getType();
        List<Validation0> data=new Gson().fromJson(str2,type);
        Log.e("mydata2",gson.toJson(data));

        recyclerView = (RecyclerView) findViewById(R.id.recycle2);
        lm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lm);
        recyclerView.setHasFixedSize(true);

        adapter=new AdminAdapter(data,getBaseContext());
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.Sign_in:
                SPreference.setLoggedIn(getApplicationContext(), false);

                Intent b=new Intent(this,MainActivity.class);
                startActivity(b);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
