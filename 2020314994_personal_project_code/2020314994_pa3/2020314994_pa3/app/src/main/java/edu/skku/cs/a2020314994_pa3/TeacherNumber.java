package edu.skku.cs.a2020314994_pa3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TeacherNumber extends AppCompatActivity {

    private ListView numlist;
    private ListView elist;
    private ArrayList<PhoneNumber> numitems;
    private ArrayList<EmailAddress> eitems;
    private NumListAdapter listViewAdapternumber;
    private EListAdapter elistViewAdapter;

    String getmail1;
    String getmail2;
    String getnum1;
    String getnum2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_number);

        numlist = findViewById(R.id.numlistview);
        elist = findViewById(R.id.emaillistview);
        numitems = new ArrayList<PhoneNumber>();
        eitems = new ArrayList<EmailAddress>();

        listViewAdapternumber = new NumListAdapter(numitems, getApplicationContext());
        numlist.setAdapter(listViewAdapternumber);

        elistViewAdapter = new EListAdapter(eitems, getApplicationContext());
        elist.setAdapter(elistViewAdapter);

        numitems.add(new PhoneNumber("학원데스크", "02-9999-8888"));
        numitems.add(new PhoneNumber("학원상담실", "02-7777-6666"));
        numitems.add(new PhoneNumber("학원교재구매", "02-3333-6666"));
        numitems.add(new PhoneNumber("학원고객센터", "02-5555-4444"));

        OkHttpClient client = new OkHttpClient();

        ContactModel contactlist1 = new ContactModel();
        contactlist1.setTid("202020");

        Gson gson = new Gson();
        String  json1 = gson.toJson(contactlist1, ContactModel.class);

        HttpUrl.Builder urlBuilderlist1
                = HttpUrl.parse("https://mo5xfd8ow8.execute-api.ap-northeast-2.amazonaws.com/dev/contactview").newBuilder();

        String urllist1 = urlBuilderlist1.build().toString();
        Log.d("URL",urllist1);
        Log.d("request", json1);

        Request reqlist1 = new Request.Builder().url(urllist1)
                .post(RequestBody.create(MediaType.parse("application/json"), json1))
                .build();

        client.newCall(reqlist1).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String CResp = response.body().string();
                Log.d("Resp",CResp);
                Gson gson = new GsonBuilder().create();
                final ContactModel c1data = gson.fromJson(CResp, ContactModel.class);
                getmail1 = c1data.getEmail();
                getnum1 = c1data.getPhonenum();
                Log.d("getmail1", getmail1);
                Log.d("getnum1",getnum1);
                numitems.add(new PhoneNumber("김민규선생님", getnum1));
                eitems.add(new EmailAddress("김민규선생님", getmail1));

                TeacherNumber.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listViewAdapternumber.notifyDataSetChanged();
                        elistViewAdapter.notifyDataSetChanged();
                    }
                });
            }
        });

        ContactModel contactlist2 = new ContactModel();
        contactlist2.setTid("212121");

        Gson gson2 = new Gson();
        String  json2 = gson2.toJson(contactlist2, ContactModel.class);

        HttpUrl.Builder urlBuilderlist2
                = HttpUrl.parse("https://mo5xfd8ow8.execute-api.ap-northeast-2.amazonaws.com/dev/contactview").newBuilder();

        String urllist2 = urlBuilderlist2.build().toString();
        Log.d("URL",urllist2);
        Log.d("request", json2);

        Request reqlist2 = new Request.Builder().url(urllist2)
                .post(RequestBody.create(MediaType.parse("application/json"), json2))
                .build();

        client.newCall(reqlist2).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String CResp = response.body().string();
                Log.d("Resp",CResp);
                Gson gson = new GsonBuilder().create();
                final ContactModel c1data = gson.fromJson(CResp, ContactModel.class);
                getmail2 = c1data.getEmail();
                getnum2 = c1data.getPhonenum();
                Log.d("getmail2", getmail2);
                Log.d("getnum2",getnum2);

                numitems.add(new PhoneNumber("이지은선생님", getnum2));
                Log.d("add 이지은쌤 phone num", "success");
                eitems.add(new EmailAddress("이지은선생님", getmail2));


                TeacherNumber.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listViewAdapternumber.notifyDataSetChanged();
                        elistViewAdapter.notifyDataSetChanged();
                    }
                });
            }
        });


    }
}