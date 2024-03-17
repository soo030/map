package edu.skku.cs.pa1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class GrayCheck {
    public String lettergray;
    public GrayCheck(String lettergray){
        this.lettergray = lettergray;
    }
}

public class ListViewAdapter4 extends BaseAdapter {
    private ArrayList<GrayCheck> itemsgray;
    private Context grContext;

    ListViewAdapter4 (ArrayList<GrayCheck> itemsgray, Context grContext) {
        this.itemsgray = itemsgray;
        this.grContext = grContext;
    }

    @Override
    public int getCount() { return itemsgray.size(); }

    @Override
    public Object getItem(int i) { return itemsgray.get(i); }

    @Override
    public long getItemId(int i) { return i; }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if ( view == null ) {
            LayoutInflater layoutInflater = (LayoutInflater) grContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.graylistview, viewGroup, false);
        }
        TextView textgray = view.findViewById(R.id.graytextView);

        String chrg = itemsgray.get(i).lettergray;
        textgray.setText(chrg.toUpperCase());
        this.notifyDataSetChanged();
        return view;
    }
}