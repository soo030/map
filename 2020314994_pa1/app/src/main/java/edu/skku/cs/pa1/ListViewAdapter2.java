package edu.skku.cs.pa1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class GreenCheck {
    public String lettergreen;
    public GreenCheck(String lettergreen){
        this.lettergreen = lettergreen;
    }
}

public class ListViewAdapter2 extends BaseAdapter {
    private ArrayList<GreenCheck> itemsgreen;
    private Context gContext;

    ListViewAdapter2 (ArrayList<GreenCheck> itemsgreen, Context gContext) {
        this.itemsgreen = itemsgreen;
        this.gContext = gContext;
    }

    @Override
    public int getCount() { return itemsgreen.size(); }

    @Override
    public Object getItem(int i) { return itemsgreen.get(i); }

    @Override
    public long getItemId(int i) { return i; }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if ( view == null ) {
            LayoutInflater layoutInflater = (LayoutInflater) gContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.greenlistview, viewGroup, false);
        }

        TextView textgreen = view.findViewById(R.id.greentextView);

        String chrsg = itemsgreen.get(i).lettergreen;
        textgreen.setText(chrsg.toUpperCase());
        this.notifyDataSetChanged();
        return view;
    }
}