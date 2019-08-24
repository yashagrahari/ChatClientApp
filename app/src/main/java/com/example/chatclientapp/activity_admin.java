package com.example.chatclientapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class activity_admin extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager lm;
    private AdminAdapter adapter;
    String name="ADMIN";
    List<UserAdminmessagesandactivity3> data;
    Gson gson=new Gson();
    SPreference sPreference=new SPreference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        androidx.appcompat.widget.Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        SharedPreferences sharedPreferences1 =getSharedPreferences("mydata",MODE_PRIVATE);
        String str2 = sharedPreferences1.getString("data","");
        Log.e("extra2",str2);
        Type type=new TypeToken<List<UserAdminmessagesandactivity3>>() {
        }.getType();
        data=new Gson().fromJson(str2,type);
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
                sPreference.setLoggedIn(getApplicationContext(), false);

                Intent b=new Intent(this,MainActivity.class);
                startActivity(b);
                finish();
                break;

            case R.id.refresh:

                Api_interface api_interface = RetrofitClient.getClient(activity_admin.this).create(Api_interface.class);

                api_interface.postInit3(name)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(a -> {
                        })
                        .doOnError(e -> Log.e("onError", gson.toJson(e)))
                        .subscribe(response2 -> {
                            data = response2;
                            String str = gson.toJson(response2);
                            Log.e("string", str);
                            SharedPreferences sharedPreferences1=getSharedPreferences("mydata",MODE_PRIVATE);
                            SharedPreferences.Editor editor=sharedPreferences1.edit();
                            editor.putString("data",gson.toJson(data));
                            editor.apply();
                            Intent a=new Intent(activity_admin.this,activity_admin.class);
                            startActivity(a);
                            finish();

                        });
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
