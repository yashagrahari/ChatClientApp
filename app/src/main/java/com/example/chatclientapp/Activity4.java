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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class Activity4 extends AppCompatActivity {


     RecyclerView recyclerView;
    ImageButton imageButton;
    EditText editText;
    String name;
    String name2="admin";
    String text;
    List<UserAdminmessagesandactivity3> responseData;
    SPreference sPreference = new SPreference();
    SharedPreferences sharedPreferences;
    String str2;



    Gson gson = new Gson();
    String valid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chat);
        recyclerView = (RecyclerView) findViewById(R.id.recycle);

        androidx.appcompat.widget.Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageButton=findViewById(R.id.sent);
        editText=findViewById(R.id.messagenew);

         sharedPreferences=getSharedPreferences("mydata",MODE_PRIVATE);
         str2 = sharedPreferences.getString("data","");
        Log.e("Transfer", str2);
        Type type1 = new TypeToken<List<UserAdminmessagesandactivity3>>() {
        }.getType();
        responseData = new Gson().fromJson(str2, type1);
        if(str2.contains("date")){
            List<MessageData> v2=responseData.get(0).getData();
            valid=v2.get(0).link1__username;
            updatercyclerview(responseData);
        }else{
            valid=responseData.get(0).getCc();
            responseData.remove(0);
            SharedPreferences sharedPreferences1 = getSharedPreferences("mydata", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences1.edit();
            editor.putString("data", gson.toJson(responseData));
            editor.apply();
            updatercyclerview(responseData);
        }



        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                sharedPreferences=getSharedPreferences("mydata",MODE_PRIVATE);
                str2 = sharedPreferences.getString("data","");
                Log.e("Transfer", str2);
                Type type1 = new TypeToken<List<UserAdminmessagesandactivity3>>() {
                }.getType();
                responseData = new Gson().fromJson(str2, type1);
                text=editText.getText().toString();
                if(text.equals(""))
                {
                    return;
                }



                Log.e("Usermessage",text);
                Authbody3 authBody = new Authbody3();
                authBody.setUsername(valid);
                authBody.setName(name2);
                authBody.setUser_message(text);
                Api_interface api_interface = RetrofitClient.getClient(Activity4.this).create(Api_interface.class);
                api_interface.postInit(authBody)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(a-> {})
                        .doOnError(e -> Log.e("onError",gson.toJson(e)))
                        .subscribe(response -> {
                            List<Success> s = response;
                            if(responseData.size()==0){
                                List<MessageData> mData = new ArrayList<>();
                                MessageData message = new MessageData(text,name2,valid,"","","","",s.get(0).getTime(),s.get(0).getId(),"INSERTED");
                                mData.add(message);
                                UserAdminmessagesandactivity3 msgDataList = new UserAdminmessagesandactivity3("","","","","","",response.get(0).getDate(),mData);
                                responseData.add(msgDataList);
                                SharedPreferences sharedPreferences1 = getSharedPreferences("mydata", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences1.edit();
                                editor.putString("data", gson.toJson(responseData));
                                editor.apply();
                                updatercyclerview(responseData);
                            }
                            else{
                                if(responseData.get(responseData.size()-1).getDate().equals(s.get(0).getDate())){
                                    List<MessageData> mData = new ArrayList<>();
                                    MessageData message = new MessageData(text,name2,"",valid,"","","",s.get(0).getTime(),s.get(0).getId(),"INSERTED");
                                    mData.add(message);
                                    responseData.get(responseData.size() - 1).getData().add(message);
                                    SharedPreferences sharedPreferences1 = getSharedPreferences("mydata", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences1.edit();
                                    editor.putString("data", gson.toJson(responseData));
                                    editor.apply();
                                    updatercyclerview(responseData);
                                }else{
                                    Api_interface api_interface1 = RetrofitClient.getClient(Activity4.this).create(Api_interface.class);

                                    api_interface1.postInit3(valid)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .doOnSubscribe(a -> {
                                            })
                                            .doOnError(e -> Log.e("onError", gson.toJson(e)))
                                            .subscribe(response2 -> {
                                                responseData = response2;
                                                String str1 = gson.toJson(response2);
                                                Log.e("string", str1);
                                                SharedPreferences sharedPreferences1 = getSharedPreferences("mydata", MODE_PRIVATE);
                                                SharedPreferences.Editor editor = sharedPreferences1.edit();
                                                editor.putString("data", gson.toJson(responseData));
                                                editor.apply();
                                                updatercyclerview(responseData);
                                                return;
                                            });
                                }
                            }


                        });


            }
        });

    }


    public void updatercyclerview (List<UserAdminmessagesandactivity3> mydata){


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Activity4.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        linearLayoutManager.setStackFromEnd(true);
        Log.e("json",gson.toJson(mydata));
        recyclerView.setLayoutManager(linearLayoutManager);
        ParentAdminMessageAdapter mAdapter = new ParentAdminMessageAdapter(responseData,Activity4.this);
        mAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(mAdapter);
        editText.setText("");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        AuthBody authBody = new AuthBody();
        authBody.setUsername("ADMIN");
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
                    Log.e("extra",str);
                    a.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(a);
                    finish();

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
        switch (item.getItemId()) {

            case R.id.refresh:
                Api_interface api_interface = RetrofitClient.getClient(Activity4.this).create(Api_interface.class);

                api_interface.postInit2(valid)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(a -> {
                        })
                        .doOnError(e -> Log.e("onError", gson.toJson(e)))
                        .subscribe(response2 -> {
                            responseData = response2;
                            String str = gson.toJson(response2);
                            Log.e("string", str);
                            SharedPreferences sharedPreferences1=getSharedPreferences("mydata",MODE_PRIVATE);
                            SharedPreferences.Editor editor=sharedPreferences1.edit();
                            editor.putString("data",gson.toJson(responseData));
                            editor.apply();

                            updatercyclerview(responseData);
                        });
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}