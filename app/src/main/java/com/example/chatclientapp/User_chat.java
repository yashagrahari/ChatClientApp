package com.example.chatclientapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
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
        String valid;
        int flag=0;
    String text;
    List<UserAdminmessagesandactivity3> responseData;
    List<Validation> v5=new ArrayList<Validation>();
    List<UserAdminmessagesandactivity3> responseData1=new ArrayList<UserAdminmessagesandactivity3>();
    SPreference sPreference=new SPreference();





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
        Type type1 = new TypeToken<List<UserAdminmessagesandactivity3>>() {
        }.getType();
        responseData = new Gson().fromJson(str2, type1);
        if(str2.contains("date")){
            List<Validation> v2=responseData.get(0).getData();
            valid=v2.get(0).link1__username;
            updatercyclerview(responseData);
        }else{
             valid=responseData.get(0).getCc();
             responseData.remove(0);
           updatercyclerview(responseData);

        }

        Log.e("st",gson.toJson(responseData1));







        imageButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {


                text=editText.getText().toString();
                text=text.trim();
                if(text.equals(""))
                {
                    return;
                }

                Authbody2 authBody = new Authbody2();
                authBody.setUsername(valid);
                authBody.setName(name2);
                authBody.setUser_message(text);
                Api_interface api_interface = RetrofitClient.getClient(User_chat.this).create(Api_interface.class);
                api_interface.postInit(authBody)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(a-> {})
                        .doOnError(e -> Log.e("onError",gson.toJson(e)))
                        .subscribe(response -> {
                            List<Success> s = response;
                            String str = gson.toJson(response);
                            Log.e("string", str);
                            Log.e("response",gson.toJson(response));

                            Log.e("Success",gson.toJson(s));
                            UserAdminmessagesandactivity3 v1= new UserAdminmessagesandactivity3("","","","","","","",null);
                            Log.e("list",gson.toJson(v1));
                            Validation v3=new Validation("","","","","","","","","","");
                            v3.setLink1__username(valid);
                            v3.setStatus("INSERTED");
                            v3.setDtoi(s.get(0).getTime());
                            v3.setName("user");
                            v3.setMsg(text);
                            v3.setId(s.get(0).getId());
                            v5.add(v3);

                            v1.setData(v5);
                            Log.e("list3",gson.toJson(v1));
                            Log.e("list2",gson.toJson(v5));
                            Log.e("list1",gson.toJson(v3));
                            Log.e("upar",gson.toJson(responseData1));
                            if(str.contains("date"))
                            {
                                v1.setDate("");
                                Log.e("uparyash1",gson.toJson(responseData1));

                            }else {
                                v1.setDate(s.get(0).getDate());
                            }

                            Log.e("list3",gson.toJson(v1));

                            if(str2.contains("cc")) {
                                responseData.clear();
                                responseData.add(v1);

                            }else {
                                if(responseData1.size()==0){
                                    Log.e("uparyash",gson.toJson(responseData1));
                                    Log.e("uparyash2",gson.toJson(v1));
                                    responseData1.add(v1);
                                    Log.e("yash",gson.toJson(responseData1));
                                }else{
                                    int l=gson.toJson(responseData1).length();
                                    Log.e("length",l+"");
                                    Log.e("yash",gson.toJson(responseData1));
                                    Log.e("yashg",gson.toJson(responseData1));

                                    responseData1.add(v1);
                                    Log.e("Afteradding",gson.toJson(responseData1));

                                }
                            }

                            SPreference.setLoggedIn(getApplicationContext(),true);
                            SharedPreferences sharedPreferences1=getSharedPreferences("mydata",MODE_PRIVATE);
                            SharedPreferences.Editor editor=sharedPreferences1.edit();
                            editor.putString("data",gson.toJson(responseData));
                            editor.apply();

                            if(str2.contains("cc")) {
                            updatercyclerview(responseData);
                            }
                            else {

                                responseData.get(0).getData().add(responseData1.get(0).getData().get(flag));
//                                responseData.get(0).setDate(s.get(0).getDate());
                                responseData1.clear();
                                flag=flag+1;
                                Log.e("harsh",gson.toJson(responseData1));
                                SPreference.setLoggedIn(getApplicationContext(),true);
                                 sharedPreferences1=getSharedPreferences("mydata",MODE_PRIVATE);
                                 editor=sharedPreferences1.edit();
                                editor.putString("data",gson.toJson(responseData));
                                editor.apply();
                                updatercyclerview(responseData);

                            }
                            Log.e("resesd",gson.toJson(responseData));

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
        switch (item.getItemId()) {
            case R.id.Sign_in:
                sPreference.setLoggedIn(getApplicationContext(), false);
                Log.e("sir",SPreference.getLoggedStatus(getApplicationContext())+"");

                Intent b = new Intent(this, MainActivity.class);
                startActivity(b);
                finish();
                break;

            case R.id.refresh:
                Api_interface api_interface = RetrofitClient.getClient(User_chat.this).create(Api_interface.class);

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

    public void updatercyclerview (List<UserAdminmessagesandactivity3> data){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(User_chat.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        Log.e("data",gson.toJson(data));
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        ParentAdapter_Message mAdapter = new ParentAdapter_Message(data,User_chat.this);
        mAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(mAdapter);
        editText.setText("");


    }

}