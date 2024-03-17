package edu.skku.cs.pa2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MazeActivity extends AppCompatActivity {
    Button upbtn;
    Button downbtn;
    Button leftbtn;
    Button rightbtn;
    TextView textView;
    Button hint;
    ImageView userpic;
    ImageView hintpic;
    ImageView goalpic;
    int count;
    int hintcount;
    int goalpos;
    private GridView gridView;
    private ArrayList<MazeWall> items2;
    private GridViewAdapter gridViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maze);

        gridView = findViewById(R.id.mazegrid);
        items2 = new ArrayList<MazeWall>();
        int infor[] = new int[3];
        gridViewAdapter = new GridViewAdapter(items2, getApplicationContext(), infor);
        Intent intent = getIntent();


        upbtn = findViewById(R.id.upbutton);
        downbtn = findViewById(R.id.downbutton);
        leftbtn = findViewById(R.id.leftbutton);
        rightbtn = findViewById(R.id.rightbutton);

        textView = findViewById(R.id.turncount);
        hint = findViewById(R.id.hintbutton);

        String binary[] = new String[101];
        String mname = intent.getStringExtra(ListViewAdapter.EXT_MAZE);
        OkHttpClient client2 = new OkHttpClient();
        HttpUrl.Builder urlBuilder2 = HttpUrl.parse("http://115.145.175.57:10099/maze/map").newBuilder();
        urlBuilder2.addQueryParameter("name",mname);

        String url = urlBuilder2.build().toString();
        Request req2 = new Request.Builder().url(url).build();

        client2.newCall(req2).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Gson gson = new GsonBuilder().create();
                final String myMaze = response.body().string();
                final MazeValue data1 = gson.fromJson(myMaze, MazeValue.class);
                String[] num = data1.getMaze().split("\\s+");

                int[] numlist = new int[num.length];
                String[] binaryString = new String[num.length];
                for ( int k = 0 ; k < num.length ; k++) {
                    numlist[k] = Integer.parseInt(num[k]);
                }

                for ( int l = 1 ; l < numlist.length ; l++) {
                    binaryString[l] = Integer.toBinaryString(numlist[l]);
                    if (binaryString[l].length() < 4) {
                        int len = binaryString[l].length();
                        for (int u = 0; u < 4 - len; u++) {
                            binaryString[l] = "0" + binaryString[l];
                        }
                    }
                    binary[l] = binaryString[l];
                    goalpos = Integer.parseInt(num[0]);
                    items2.add(new MazeWall(binaryString[l], num[0]));
                }

                MazeActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        gridView.setNumColumns(numlist[0]);
                        gridView.setAdapter(gridViewAdapter);
                    }
                });
            }
        });

        int pos[] = new int[2];
        pos[0] = 1;
        pos[1] = 1;

        count = 0;
        hintcount = 1;

        upbtn.setOnClickListener(view -> {
            int find = (pos[0] - 1) * goalpos + pos[1];
            String findwall = binary[find];
            if (findwall.charAt(0) == '0') {
                pos[0] = pos[0] - 1;
                count += 1;
                textView.setText("Turn : " + count);
                infor[0] = (pos[0] - 1) * goalpos + pos[1]-1;
                infor[1] = 0;
                gridViewAdapter = new GridViewAdapter(items2, getApplicationContext(), infor);
                gridView.setAdapter(gridViewAdapter);
            }
            if ( pos[0] == goalpos && pos[1] ==goalpos) {
                Toast.makeText(getApplicationContext(), "Finish!",Toast.LENGTH_SHORT).show();
            }
        });

        downbtn.setOnClickListener(view -> {
            int find = (pos[0] - 1) * goalpos + pos[1];
            String findwall = binary[find];
            if (findwall.charAt(2) == '0') {
                pos[0] = pos[0] + 1;
                count += 1;
                textView.setText("Turn : " + count);
                infor[0] = (pos[0] - 1) * goalpos + pos[1] -1;
                infor[1] = 2;
                gridViewAdapter = new GridViewAdapter(items2, getApplicationContext(), infor);
                gridView.setAdapter(gridViewAdapter);
            }
            if ( pos[0] == goalpos && pos[1] ==goalpos) {
                Toast.makeText(getApplicationContext(), "Finish!",Toast.LENGTH_SHORT).show();
            }
        });

        leftbtn.setOnClickListener(view -> {
            int find = (pos[0] - 1) * goalpos + pos[1];
            String findwall = binary[find];
            if (findwall.charAt(1) == '0') {
                pos[1] = pos[1] - 1;
                count += 1;
                textView.setText("Turn : " + count);
                infor[0] = (pos[0] - 1) * goalpos + pos[1] -1;
                infor[1] = 3;
                gridViewAdapter = new GridViewAdapter(items2, getApplicationContext(), infor);
                gridView.setAdapter(gridViewAdapter);
            }
            if ( pos[0] == goalpos && pos[1] ==goalpos) {
                Toast.makeText(getApplicationContext(), "Finish!",Toast.LENGTH_SHORT).show();
            }
        });

        rightbtn.setOnClickListener(view -> {
            int find = (pos[0] - 1) * goalpos + pos[1];
            String findwall = binary[find];
            if (findwall.charAt(3) == '0') {
                pos[1] = pos[1] + 1;
                count += 1;
                textView.setText("Turn : " + count);
                infor[0] = (pos[0] - 1) * goalpos + pos[1]-1;
                infor[1] = 1;
                gridViewAdapter = new GridViewAdapter(items2, getApplicationContext(), infor);
                gridView.setAdapter(gridViewAdapter);
            }
            if ( pos[0] == goalpos && pos[1] ==goalpos) {
                Toast.makeText(getApplicationContext(), "Finish!",Toast.LENGTH_SHORT).show();
            }
        });

        hint.setOnClickListener(view -> {
            if (hintcount == 1) {
                hintcount = 0;
            }
        });

        gridViewAdapter.notifyDataSetChanged();


    }
}