package com.example.chatclientapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toolbar;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class User_chat extends AppCompatActivity {


    private  Toolbar toolbar;
    private RecyclerView recyclerView;
    ImageButton imageButton;
    EditText editText;
    String name;
    String name2="user";

    String text;
    List<Validation0> responseData;

    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chat);
        recyclerView = (RecyclerView) findViewById(R.id.recycle);
        androidx.appcompat.widget.Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        imageButton=findViewById(R.id.sent);
        editText=findViewById(R.id.messagenew);
        SharedPreferences sharedPreferences=getSharedPreferences("mydata",MODE_PRIVATE);
        String str2 = sharedPreferences.getString("data","");
        Log.e("Transfer", str2);
        Type type1 = new TypeToken<List<Validation0>>() {
        }.getType();
        responseData = new Gson().fromJson(str2, type1);
        updatercyclerview(responseData);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                text=editText.getText().toString();
                if(text.equals(""))
                {
                    return;
                }

                List<Validation> valid=responseData.get(0).getData();
                name=valid.get(0).getLink1__username();
                Log.e("name",name);


                Log.e("Usermessage",text);
                Authbody2 authBody = new Authbody2();
                authBody.setUsername(name);
                authBody.setName(name2);
                authBody.setUser_message(text);
                Api_interface api_interface = RetrofitClient.getClient(User_chat.this).create(Api_interface.class);
                api_interface.postInit(authBody)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(a-> {})
                        .doOnError(e -> Log.e("onError",gson.toJson(e)))
                        .subscribe(response -> {
                            responseData = response;
                            String str = gson.toJson(response);

                            Log.e("string", str);
                            Log.e("response",gson.toJson(response));


                            updatercyclerview(responseData);


                        });


            }
        });

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
                SPreference.setLoggedIn(getApplicationContext(),false);

                Intent b=new Intent(this,MainActivity.class);
                startActivity(b);
                finish();

        }
        return super.onOptionsItemSelected(item);
    }

    public void updatercyclerview (List<Validation0> data){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(User_chat.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        ParentAdapter_Message mAdapter = new ParentAdapter_Message(data,getApplicationContext());
        mAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(mAdapter);
        editText.setText("");

    }

}