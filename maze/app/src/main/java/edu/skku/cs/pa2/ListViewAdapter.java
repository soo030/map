package edu.skku.cs.pa2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
class MazeModel {
    public String mazename;
    public int mazesize;

    MazeModel(String mazename, int mazesize) {
        this.mazename = mazename;
        this.mazesize = mazesize;
    }
}

public class ListViewAdapter extends BaseAdapter {
    private ArrayList<MazeModel> items;
    private Context mContext;
    Button sbutton;

    public static final String EXT_MAZE = "Maze";

    ListViewAdapter (ArrayList<MazeModel> items, Context mContext) {
        this.mContext = mContext;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.maplistview, viewGroup, false);

        TextView mzname = view.findViewById(R.id.mazeName);
        TextView mzsize = view.findViewById(R.id.mazeSize);

        mzname.setText(items.get(i).mazename);
        int to = items.get(i).mazesize;
        String toi = Integer.toString(to);

        mzsize.setText(toi);
        sbutton = (Button) view.findViewById(R.id.startbutton);
        sbutton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(mContext, MazeActivity.class);
                String tn = mzname.getText().toString();
                intent2.putExtra(EXT_MAZE, tn);
                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent2);
            }

        });

        return view;
    }
}
