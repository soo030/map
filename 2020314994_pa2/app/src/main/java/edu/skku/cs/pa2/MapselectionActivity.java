package edu.skku.cs.pa2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MapselectionActivity extends AppCompatActivity {
    TextView textView;
    TextView textView2;
    TextView textView3;
    //Button sbutton;
    public static final String EXT_SIZE = "Size";

    private ListView listview;
    private ArrayList<MazeModel> items;
    private ListViewAdapter listViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapselection);

        Intent intent = getIntent();
        String name = intent.getStringExtra(MainActivity.EXT_NAME);

        textView = findViewById(R.id.userName);
        textView.setText(name);
        textView2 = findViewById(R.id.explain);
        listview = findViewById(R.id.mazeListView);
        items = new ArrayList<MazeModel>();
        listViewAdapter = new ListViewAdapter(items, getApplicationContext());

        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder  = HttpUrl.parse("http://115.145.175.57:10099/maps").newBuilder();

        String url = urlBuilder.build().toString();

        Request req = new Request.Builder().url(url).build();

        client.newCall(req).enqueue(new Callback(){
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String myResponse = response.body().string();

                try {
                    JSONArray jsonArray = new JSONArray(myResponse);
                    for (int i = 0 ; i < jsonArray.length() ; i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String mname = jsonObject.getString("name");
                        int msize = jsonObject.getInt("size");
                        items.add(new MazeModel(mname, msize));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                MapselectionActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        listview.setAdapter(listViewAdapter);

                    }
                });
            }
        });

    }
}