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
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class Activity4 extends AppCompatActivity {


    private  Toolbar toolbar;
    private RecyclerView recyclerView;
    ImageButton imageButton;
    EditText editText;
    String name;
    String name2="admin";
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



        Intent intent = getIntent();

        String str2=intent.getStringExtra("Response"
        );
        Log.e("string",str2);
        Type type=new TypeToken<List<Validation0>>() {
        }.getType();
        responseData=new Gson().fromJson(str2,type);

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

                Log.e("Usermessage",text);
                Authbody3 authBody = new Authbody3();
                authBody.setUsername(name);
                authBody.setName(name2);
                authBody.setUser_message(text);
                Api_interface api_interface = RetrofitClient.getClient(Activity4.this).create(Api_interface.class);
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


    public void updatercyclerview (List<Validation0> mydata){


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Activity4.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        linearLayoutManager.setStackFromEnd(true);
        Log.e("json",gson.toJson(mydata));
        recyclerView.setLayoutManager(linearLayoutManager);
        ParentAdminMessageAdapter mAdapter = new ParentAdminMessageAdapter(responseData,getApplicationContext());
        mAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(mAdapter);
        editText.setText("");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        AuthBody authBody = new AuthBody();
        authBody.setUsername("admin");
        authBody.setPassword("12345");
        Log.e("ButtonP", gson.toJson(authBody));

        Api_interface api_interface = RetrofitClient.getClient(Activity4.this).create(Api_interface.class);

        api_interface.postInit(authBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(a -> {
                })
                .doOnError(e -> Log.e("onError", gson.toJson(e)))
                .subscribe(response -> {
                    responseData = response;
                    String str = gson.toJson(response);
                    Log.e("string", str);
                    SharedPreferences sharedPreferences1=getSharedPreferences("mydata",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences1.edit();
                    editor.putString("data",str);
                    editor.apply();
                    Intent a = new Intent(this, activity_admin.class);
//                    a.putExtra("Response", str);
                    Log.e("extra",str);
                    a.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(a);
                    finish();

                });

    }
}