package edu.skku.cs.pa2;

import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GridViewAdapter extends BaseAdapter {
    private ArrayList<MazeWall> items2;
    private Context pContext;
    private int[] infor;
    GridViewAdapter (ArrayList<MazeWall> items2, Context pContext, int[] infor) {
        this.items2 = items2;
        this.pContext = pContext;
        this.infor = infor;
    }

    @Override
    public int getCount() {
        return items2.size();
    }

    @Override
    public Object getItem(int i) {
        return items2.get(i);
    }

    @Override
    public long getItemId(int i) { return i; }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if ( view == null ) {
            LayoutInflater layoutInflater2 = (LayoutInflater) pContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater2.inflate(R.layout.mazegridview, viewGroup, false);
        }

        ImageView img = view.findViewById(R.id.whiteimg);
        ImageView usrig = view.findViewById(R.id.userimg);
        ImageView hintig = view.findViewById(R.id.hintimg);
        ImageView goalig = view.findViewById(R.id.goalimg);

        FrameLayout.LayoutParams margin = (FrameLayout.LayoutParams) img.getLayoutParams();
        int c = Integer.parseInt(items2.get(0).col);

        String de = items2.get(i).col;

        String cl = items2.get(i).wall;
        char[] ch = new char[4];
        int[] wl = new int[4];

        for (int a = 0; a < cl.length(); a++) {
            ch[a] = cl.charAt(a);
        }

        int top = 0;
        int start = 0;
        int bot = 0;
        int end = 0;

        if (ch[0] == '1')
            top = 1;

        if (ch[1] == '1')
            start = 1;

        if (ch[2] == '1')
            bot = 1;

        if (ch[3] == '1')
            end = 1;

        float display = pContext.getResources().getDisplayMetrics().density;
        margin.height = (int) (350 * display / c) - (top + bot) * (int)(3*display);
        margin.width = (int) (350 * display / c) - (end + start) * (int)(3*display);
        margin.setMargins((int) (start * 3 * display), (int) (top * 3 * display), (int) (end * 3 * display), (int) (bot * 3 * display));
        img.setLayoutParams(margin);


        int column = Integer.parseInt(items2.get(i).col);

        if ( getItemId(i) == column*column -1) {
            goalig.setImageResource(R.drawable.goal);
            FrameLayout.LayoutParams margin1 = (FrameLayout.LayoutParams) goalig.getLayoutParams();
            goalig.setLayoutParams(margin);
            }

        if ( getItemId(i) == infor[0] ) {
            usrig.setImageResource(R.drawable.user);
            usrig.setRotation((float) infor[1]*90);
            FrameLayout.LayoutParams margin0 = (FrameLayout.LayoutParams) usrig.getLayoutParams();
            usrig.setLayoutParams(margin);
            if ( infor[0] == column * column - 1) {
                usrig.bringToFront();
            }
        }

        if (getItemId(i) == infor[2]) {
            if ( infor[2] == 1) {
                usrig.bringToFront();
            }
        }

        return view;
    }
}
