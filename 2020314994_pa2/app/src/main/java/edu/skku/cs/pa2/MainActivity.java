package edu.skku.cs.pa2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button signbutton;
    int proceed;
    public static final String EXT_NAME = "Name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.signName);
        signbutton = findViewById(R.id.signin);

        signbutton.setOnClickListener(view -> {
            OkHttpClient client = new OkHttpClient();
            String user = editText.getText().toString();

            DataModel data = new DataModel();
            data.setUsername(user);

            Gson gson = new Gson();
            String json = gson.toJson(data, DataModel.class);

            HttpUrl.Builder urlBuilder =
                    HttpUrl.parse("http://115.145.175.57:10099/users").newBuilder();
            String url = urlBuilder.build().toString();

            Request req = new Request.Builder()
                    .url(url)
                    .post(RequestBody.create(MediaType.parse("application/json"),json))
                    .build();

            client.newCall(req).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    final String myResponse = response.body().string();

                    Gson gson = new GsonBuilder().create();
                    final DataModel data1 = gson.fromJson(myResponse, DataModel.class);
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                                String result = data1.getSuccess();
                                if ( result.equals("false") ) {
                                    Toast.makeText(getApplicationContext(), "Wrong User Name", Toast.LENGTH_SHORT).show();
                                    proceed = 1;
                                }

                                else {
                                    proceed = 2;
                                    //Toast.makeText(getApplicationContext(), "Correct "+ proceed, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(MainActivity.this, MapselectionActivity.class);
                                    intent.putExtra(EXT_NAME, user);
                                    startActivity(intent);
                                }
                        }
                    });
                }
            });
        });

    }



}