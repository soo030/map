package edu.skku.cs.a2020314994_pa3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UpdateListActivity extends AppCompatActivity {
    Button numupbtn;
    Button mailupbtn;
    EditText oldnumET;
    EditText newnumET;
    EditText oldmailET;
    EditText newmailET;

    String getnum;
    String getmail;
    String oldnum;
    String newnum;
    String oldmail;
    String newmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_list);
        numupbtn = findViewById(R.id.tnumUpButton);
        mailupbtn = findViewById(R.id.tmailUpButton);

        oldnumET = findViewById(R.id.toldphoneeditText);
        newnumET = findViewById(R.id.tnewphoneeditText);
        oldmailET = findViewById(R.id.toldmaileditText);
        newmailET = findViewById(R.id.tnewmaileditText);

        Intent intentgetnum = getIntent();
        String tid = intentgetnum.getStringExtra(TeacherPage.EXT_TID);

        OkHttpClient client3 = new OkHttpClient();

        ContactModel contactdata = new ContactModel();
        contactdata.setTid(tid);

        Gson gson = new Gson();
        String json = gson.toJson(contactdata, ContactModel.class);

        HttpUrl.Builder urlBuilderget
                = HttpUrl.parse("https://mo5xfd8ow8.execute-api.ap-northeast-2.amazonaws.com/dev/contactview").newBuilder();

        String urlget = urlBuilderget.build().toString();
        Log.d("URL",urlget);
        Log.d("request", json);

        numupbtn.setOnClickListener(view -> {

            Request reqget = new Request.Builder().url(urlget)
                    .post(RequestBody.create(MediaType.parse("application/json"), json))
                    .build();

            client3.newCall(reqget).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    final String ContactResp = response.body().string();
                    Log.d("Resp",ContactResp);
                    Gson gson = new GsonBuilder().create();
                    final ContactModel cdata = gson.fromJson(ContactResp, ContactModel.class);
                    getmail = cdata.getEmail();
                    getnum = cdata.getPhonenum();

                    String oldnum1 = oldnumET.getText().toString();
                    String newnum1 = newnumET.getText().toString();

                    if (oldnum1.equals(getnum)) {

                        ContactModel contactnumdata = new ContactModel();
                        contactnumdata.setTid(tid);
                        contactnumdata.setPhonenum(newnum1);

                        String jsonnum = gson.toJson(contactnumdata, ContactModel.class);

                        HttpUrl.Builder urlBuildernum
                                = HttpUrl.parse("https://mo5xfd8ow8.execute-api.ap-northeast-2.amazonaws.com/dev/updatenum").newBuilder();

                        String url = urlBuildernum.build().toString();
                        Log.d("URL", url);
                        Log.d("request", jsonnum);

                        Request req = new Request.Builder().url(url)
                                .post(RequestBody.create(MediaType.parse("application/json"), jsonnum))
                                .build();

                        client3.newCall(req).enqueue(new Callback() {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                                final String espo = response.body().string();
                                Log.d("Resp",espo);
                                Gson gson = new GsonBuilder().create();

                                Log.d("success",espo);
                                UpdateListActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "번호를 수정했습니다", Toast.LENGTH_SHORT).show();
                                        oldnumET.setText("");
                                        newnumET.setText("");
                                    }
                                });

                            }
                        });

                    }

                    else {
                        UpdateListActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "기존 번호가 맞지 않습니다!", Toast.LENGTH_SHORT).show();
                                oldnumET.setText("");
                                newnumET.setText("");
                            }
                        });
                    }
                }
            });


        });

        mailupbtn.setOnClickListener(view -> {

            Request reqget = new Request.Builder().url(urlget)
                    .post(RequestBody.create(MediaType.parse("application/json"), json))
                    .build();

            client3.newCall(reqget).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    final String ContactResp = response.body().string();
                    Log.d("Resp",ContactResp);
                    Gson gson = new GsonBuilder().create();
                    final ContactModel cdata = gson.fromJson(ContactResp, ContactModel.class);
                    getmail = cdata.getEmail();
                    getnum = cdata.getPhonenum();
                    Log.d("open getmail", getmail);
                    Log.d("open getnum",getnum);


                    String oldmail1 = oldmailET.getText().toString();
                    String newmail1 = newmailET.getText().toString();

                    Log.d("btn oldnum1", oldmail1);
                    Log.d("btn newnum",newmail1);

                    if (oldmail1.equals(getmail)) {

                        Log.d("changing", "true");

                        ContactModel contactmdata = new ContactModel();
                        contactmdata.setTid(tid);
                        contactmdata.setEmail(newmail1);

                        Log.d("oldnum1", oldmail1);
                        Log.d("newnum",newmail1);
                        Log.d("tid", tid);

                        String jsonmail = gson.toJson(contactmdata, ContactModel.class);

                        HttpUrl.Builder urlBuildermail
                                = HttpUrl.parse("https://mo5xfd8ow8.execute-api.ap-northeast-2.amazonaws.com/dev/updateemail").newBuilder();

                        String url = urlBuildermail.build().toString();
                        Log.d("URL", url);
                        Log.d("request", jsonmail);

                        Request req = new Request.Builder().url(url)
                                .post(RequestBody.create(MediaType.parse("application/json"), jsonmail))
                                .build();

                        client3.newCall(req).enqueue(new Callback() {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                final String esp = response.body().string();
                                Log.d("Resp",esp);
                                Gson gson = new GsonBuilder().create();
                                final DataModel chan = gson.fromJson(esp, DataModel.class);
                                Log.d("success",esp);

                                UpdateListActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Log.d("changing", "finished");
                                        Toast.makeText(getApplicationContext(), "이메일을 수정했습니다", Toast.LENGTH_SHORT).show();
                                        oldmailET.setText("");
                                        newmailET.setText("");
                                    }
                                });
                            }
                        });

                    }

                    else {
                        UpdateListActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("changing", "false");
                                Log.d("oldnum1", oldmail1);
                                Log.d("newnum",newmail1);
                                Log.d("getmail", getmail);
                                Toast.makeText(getApplicationContext(), "기존 메일이 맞지 않습니다!", Toast.LENGTH_SHORT).show();
                                oldmailET.setText("");
                                newmailET.setText("");
                            }
                        });
                    }
                }
            });


        });
    }
}