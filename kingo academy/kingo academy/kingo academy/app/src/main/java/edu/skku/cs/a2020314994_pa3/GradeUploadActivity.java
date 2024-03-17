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
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GradeUploadActivity extends AppCompatActivity {
    public static final String EXT_LV = "Value";
    Button upload;
    EditText stdtn;
    EditText stgrade;
    ///

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_upload);

        Log.d("gradeupload activity", "start");
        stdtn = findViewById(R.id.stnumeditText);
        stgrade = findViewById(R.id.gradeeditText);
        upload = findViewById(R.id.tGradeUpButton);

        upload.setOnClickListener(view ->  {
            OkHttpClient clientgradet = new OkHttpClient();

            String stninput = stdtn.getText().toString();
            String gradeinput = stgrade.getText().toString();

            DataModel2 datatgrade = new DataModel2();
            datatgrade.setPasswd(stninput);
            datatgrade.setGrade(gradeinput);

            Gson gson = new Gson();
            String json = gson.toJson(datatgrade, DataModel2.class);

            HttpUrl.Builder urlBuilderlogin
                    = HttpUrl.parse("https://mo5xfd8ow8.execute-api.ap-northeast-2.amazonaws.com/dev/gradeupdate").newBuilder();

            String url = urlBuilderlogin.build().toString();
            Log.d("URL",url);

            Request reqtgrade = new Request.Builder().url(url)
                    .post(RequestBody.create(MediaType.parse("application/json"), json))
                    .build();

            clientgradet.newCall(reqtgrade).enqueue(new Callback() {
                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    final String myResp = response.body().string();
                    Log.d("Resp",myResp);
                    Gson gson = new GsonBuilder().create();
                    final DataModel datatgrade = gson.fromJson(myResp, DataModel.class);

                    if ( datatgrade.getSuccess()== true) {
                        Log.d("success", "true");
                        Log.d("grade add", gradeinput);
                        GradeUploadActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "학생번호 : " +stninput +"\n성적 : " + gradeinput +" 입력되었습니다.", Toast.LENGTH_SHORT).show();
                                stdtn.setText("");
                                stgrade.setText("");
                            }
                        });
                    }

                    else {
                        Log.d("success", "false");
                        GradeUploadActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "해당 번호의 학생이 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                                stdtn.setText("");
                                stgrade.setText("");
                            }
                        });
                    }
                }

                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    e.printStackTrace();
                }
            });

        });
    }
}