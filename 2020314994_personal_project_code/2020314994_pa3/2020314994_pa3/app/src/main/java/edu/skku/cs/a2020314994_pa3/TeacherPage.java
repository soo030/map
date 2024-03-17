package edu.skku.cs.a2020314994_pa3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class TeacherPage extends AppCompatActivity {

    public static final String EXT_TID = "TID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_page);

        Intent intent2 = getIntent();
        String name = intent2.getStringExtra(MainActivity.EXT_NAME);
        String tid = intent2.getStringExtra(MainActivity.EXT_PASSWD);

        TextView teachername = findViewById(R.id.TeacherNameTextView);
        teachername.setText(name+" 선생님");

        Button tgradeBtn = findViewById(R.id.tGradeButton);
        Button tnumBtn = findViewById(R.id.tnumnButton);

        // post로 db에 grade 입력
        tgradeBtn.setOnClickListener(view -> {
            Intent intentgradet = new Intent(TeacherPage.this, GradeUploadActivity.class);
            startActivity(intentgradet);
        });


        // 연락처 listview 관리
        tnumBtn.setOnClickListener(view -> {
            Intent intentnumt = new Intent(TeacherPage.this, UpdateListActivity.class);
            intentnumt.putExtra(EXT_TID, tid);
            startActivity(intentnumt);
        });



    }
}