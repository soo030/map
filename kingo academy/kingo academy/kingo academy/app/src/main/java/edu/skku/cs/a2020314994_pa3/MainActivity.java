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

public class MainActivity extends AppCompatActivity {

    public static final String EXT_NAME = "Name";
    public static final String EXT_PASSWD = "Password";

    Button loginBtn;
    Button registerBtn;
    EditText idText;
    EditText passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        idText = findViewById(R.id.ideditText);
        passwordText = findViewById(R.id.pswdeditText);
        loginBtn = findViewById(R.id.loginbutton);
        registerBtn = findViewById(R.id.registerbutton);

        loginBtn.setOnClickListener(view -> {
            OkHttpClient client = new OkHttpClient();

            String idinput = idText.getText().toString();
            String pswdinput = passwordText.getText().toString();

            DataModel2 data2 = new DataModel2();
            data2.setName(idinput);
            data2.setPasswd(pswdinput);

            Gson gson = new Gson();
            String json = gson.toJson(data2, DataModel2.class);

            HttpUrl.Builder urlBuilderlogin
                    = HttpUrl.parse("https://mo5xfd8ow8.execute-api.ap-northeast-2.amazonaws.com/dev/login").newBuilder();

            String url = urlBuilderlogin.build().toString();
            Log.d("URL",url);
            Log.d("request", json);

            Request req = new Request.Builder().url(url)
                    .post(RequestBody.create(MediaType.parse("application/json"), json))
                    .build();

            client.newCall(req).enqueue(new Callback() {
                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    final String myResp = response.body().string();
                    Log.d("Resp",myResp);
                    Gson gson = new GsonBuilder().create();
                    final DataModel data = gson.fromJson(myResp, DataModel.class);

                    if ( data.getSuccess()== true) {
                        Log.d("success", "true");
                        int st = Integer.parseInt(pswdinput);

                        // stdt
                        if ( (st /100000) ==1 ) {
                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), idinput + "학생 환영합니다!", Toast.LENGTH_SHORT).show();
                                    idText.setText("");
                                    passwordText.setText("");
                                }
                            });
                            Intent intent = new Intent(MainActivity.this, StudentPage.class);
                            intent.putExtra(EXT_NAME, idinput);
                            intent.putExtra(EXT_PASSWD, pswdinput);
                            startActivity(intent);
                        }

                        //teacherㅎ
                        else {
                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), idinput + "선생님 환영합니다!", Toast.LENGTH_SHORT).show();
                                    idText.setText("");
                                    passwordText.setText("");
                                }
                            });

                            Intent intent2 = new Intent(MainActivity.this, TeacherPage.class);
                            intent2.putExtra(EXT_NAME, idinput);
                            intent2.putExtra(EXT_PASSWD, pswdinput);
                            startActivity(intent2);
                        }
                    }
                    else {
                        Log.d("success", "false");
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "로그인 정보가 잘못되었거나\n가입이 필요합니다.", Toast.LENGTH_SHORT).show();
                                idText.setText("");
                                passwordText.setText("");
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

        registerBtn.setOnClickListener(view -> {
            OkHttpClient client2 = new OkHttpClient();

            String idinput2 = idText.getText().toString();
            String pswdinput2 = passwordText.getText().toString();

            int ss = Integer.parseInt(pswdinput2);
            Log.d("ss % 100000", Integer.toString((int)ss/100000));
            if ( (int) (ss / 100000) ==2 ) {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"선생님 계정은 오프라인에서만\n생성 가능합니다.", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            else if ( (int) (ss/100000) == 0 ) {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"고유번호가 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                        idText.setText("");
                        passwordText.setText("");
                    }
                });
            }

            else {
                DataModel2 data3 = new DataModel2();
                data3.setName(idinput2);
                data3.setPasswd(pswdinput2);
                String initgrade = "None";
                data3.setGrade(initgrade);
                Gson gson2 = new Gson();
                String json = gson2.toJson(data3, DataModel2.class);
                Log.d("json", json);

                HttpUrl.Builder urlBuilderregister
                        = HttpUrl.parse("https://mo5xfd8ow8.execute-api.ap-northeast-2.amazonaws.com/dev/adduser").newBuilder();

                String urlreg = urlBuilderregister.build().toString();
                Log.d("URL", urlreg);

                Request reqreg = new Request.Builder().url(urlreg)
                        .post(RequestBody.create(MediaType.parse("application/json"), json))
                        .build();

                client2.newCall(reqreg).enqueue(new Callback() {
                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response responsereg) throws IOException {
                        final String myResp2 = responsereg.body().string();
                        Gson gson = new GsonBuilder().create();
                        Log.d("myresp", myResp2);
                        final DataModel data = gson.fromJson(myResp2, DataModel.class);

                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // 계정없을때
                                if (data.getSuccess() == true) {

                                    Log.d("register", "true");
                                    int st = Integer.parseInt(pswdinput2);

                                    Toast.makeText(getApplicationContext(), idinput2 + "학생 가입을 축하합니다!\n다시 로그인해주세요.", Toast.LENGTH_LONG).show();
                                    idText.setText("");
                                    passwordText.setText("");
                                } else {
                                    Log.d("register", "account already exists");
                                    Toast.makeText(getApplicationContext(), "이미 계정이 존재합니다.", Toast.LENGTH_SHORT).show();
                                    idText.setText("");
                                    passwordText.setText("");
                                }
                            }
                        });
                    }

                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        });


    }
}