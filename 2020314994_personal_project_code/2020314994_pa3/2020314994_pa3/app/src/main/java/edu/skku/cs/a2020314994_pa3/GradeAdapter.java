package edu.skku.cs.a2020314994_pa3;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

class GetGrade{
    public String grade;

    GetGrade(String grade) {
        this.grade = grade;
    }
}

public class GradeAdapter extends BaseAdapter {
    TextView examnumber;
    TextView gradescore;

    private ArrayList<GetGrade> grades;
    private Context kContext;

    GradeAdapter (ArrayList<GetGrade> grades, Context kContext) {
        this.kContext = kContext;
        this.grades = grades;
    }

    @Override
    public int getCount() {
        return grades.size();
    }

    @Override
    public Object getItem(int i) {
        return grades.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view4, ViewGroup viewGroup) {
        LayoutInflater layoutInflater4 = (LayoutInflater) kContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view4 = layoutInflater4.inflate(R.layout.gradelistview, viewGroup, false);

        TextView entv = view4.findViewById(R.id.examnum);
        TextView egtv = view4.findViewById(R.id.examgrade);

        entv.setText("금주 성적 : ");
        egtv.setText(grades.get(i).grade);

        this.notifyDataSetChanged();
        return view4;
    }
}
