package edu.skku.cs.a2020314994_pa3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class StudentPage extends AppCompatActivity {

    public static final String EXT_SPSWD = "Spassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_page);

        Intent intent = getIntent();
        String name = intent.getStringExtra(MainActivity.EXT_NAME);
        String sid = intent.getStringExtra(MainActivity.EXT_PASSWD);

        TextView stdtname = findViewById(R.id.stdtNameTextView);
        stdtname.setText(name+" 학생");

        Button sgradeBtn = findViewById(R.id.sGradeButton);
        Button snumBtn = findViewById(R.id.sNumberButton);

        // 연락처 조회
        snumBtn.setOnClickListener(view-> {
            Intent intenttn = new Intent(StudentPage.this, TeacherNumber.class);
            startActivity(intenttn);
        });

        // 성적 조회
        sgradeBtn.setOnClickListener(view->{
            Intent intentsg = new Intent (StudentPage.this, ViewGradeActivity.class);
            intentsg.putExtra(EXT_SPSWD, sid);
            startActivity(intentsg);
        });


    }
}