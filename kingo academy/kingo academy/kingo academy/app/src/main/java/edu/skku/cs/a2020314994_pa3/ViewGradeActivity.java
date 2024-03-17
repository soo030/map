package edu.skku.cs.a2020314994_pa3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

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

public class ViewGradeActivity extends AppCompatActivity {

    ListView gradelv;
    private ArrayList<GetGrade> grades;
    private GradeAdapter gradeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_grade);


        gradelv = findViewById(R.id.gradelistview);

        grades = new ArrayList<GetGrade>();
        gradeAdapter = new GradeAdapter(grades, getApplicationContext());
        gradelv.setAdapter(gradeAdapter);

        Intent intent = getIntent();
        String stnum = intent.getStringExtra(StudentPage.EXT_SPSWD);

        OkHttpClient client = new OkHttpClient();

        DataModel2 dataviewg = new DataModel2();
        dataviewg.setPasswd(stnum);
        Log.d("pswd", stnum);
        Gson gson = new Gson();
        String json = gson.toJson(dataviewg, DataModel2.class);

        HttpUrl.Builder urlBuilderviewgrade
                = HttpUrl.parse("https://mo5xfd8ow8.execute-api.ap-northeast-2.amazonaws.com/dev/gradeview").newBuilder();

        String url = urlBuilderviewgrade.build().toString();
        Log.d("URL",url);
        Log.d("request", json);

        Request req = new Request.Builder().url(url)
                .post(RequestBody.create(MediaType.parse("application/json"), json))
                .build();


        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String myResp = response.body().string();
                Log.d("Resp",myResp);
                Gson gson = new GsonBuilder().create();
                final GradeClass data = gson.fromJson(myResp, GradeClass.class);

                Log.d("data.getYourgrade" , data.getYourgrade());

                if (data.getYourgrade().equals("not yet")) {
                    grades.add(new GetGrade("조회할 성적이 없습니다"));
                }

                else {
                    grades.add(new GetGrade(data.getYourgrade()));
                }
                ViewGradeActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        gradeAdapter.notifyDataSetChanged();
                    }
                });

            }




        });
    }


}