package com.example.chatclientapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.gson.Gson;
import java.util.List;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    EditText username, password;
    Button buttonlogin;
    List<Validation0> responseData;
    Gson gson = new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        buttonlogin = findViewById(R.id.buttonlogin);

        if (SPreference.getLoggedStatus(getApplicationContext())) {

            SharedPreferences sharedPreferences1 = getSharedPreferences("mydata", MODE_PRIVATE);
            String str2 = sharedPreferences1.getString("data", "");

            if (str2.contains("unread")) {
                Intent a = new Intent(this, activity_admin.class);
                startActivity(a);
                finish();
            } else {
                Intent a = new Intent(this, User_chat.class);
                startActivity(a);
                finish();
            }

        }
        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                post();
            }
        });
    }


    private void post() {
        AuthBody authBody = new AuthBody();
        authBody.setUsername(username.getText().toString());
        authBody.setPassword(password.getText().toString());
        Log.e("ButtonP", gson.toJson(authBody));
        Api_interface api_interface = RetrofitClient.getClient(MainActivity.this).create(Api_interface.class);
        api_interface.postInit(authBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(a-> {})
                .doOnError(e -> Log.e("onError",gson.toJson(e)))
                .subscribe(response -> {
                    responseData = response;
                    String str=gson.toJson(response);
                    Log.e("string",str);
                    if (str.contains("date")) {

                        SPreference.setLoggedIn(getApplicationContext(),true);
                        SharedPreferences sharedPreferences=getSharedPreferences("mydata",MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("data",str);
                        editor.apply();
                         Intent a = new Intent(this, User_chat.class);
                         Log.e("string2",str);
                         a.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                         startActivity(a);
                         finish();

                     }
                     else if (str.contains("unread"))
                     {
                         SPreference.setLoggedIn(getApplicationContext(),true);
                         SharedPreferences sharedPreferences1=getSharedPreferences("mydata",MODE_PRIVATE);
                         SharedPreferences.Editor editor=sharedPreferences1.edit();
                         editor.putString("data",str);
                         editor.apply();
                         Intent a = new Intent(this, activity_admin.class);
//                         a.putExtra("Response", str);
                         Log.e("extra",str);
                         a.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                         startActivity(a);
                         finish();
                     }
                     else if (str.contains("kk"))
                         {

                         Toast.makeText(this,"Username invalid ",Toast.LENGTH_LONG).show();

                     }
                     else if (str.contains("password"))
                     {

                         Toast.makeText(this,"Password invalid ",Toast.LENGTH_LONG).show();

                     }


                    Log.e("response",gson.toJson(response));
                }, e-> {
                    e.printStackTrace();
                    Toast.makeText(this," Server under Maintenance",Toast.LENGTH_LONG).show();

                });


    }

}
