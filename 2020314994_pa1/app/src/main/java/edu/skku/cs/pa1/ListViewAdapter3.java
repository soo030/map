package edu.skku.cs.pa1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class YellowCheck {

    public String letteryellow;
    public YellowCheck(String letteryellow){
        this.letteryellow = letteryellow;
    }
}

public class ListViewAdapter3 extends BaseAdapter {
    private ArrayList<YellowCheck> itemsyellow;
    private Context yContext;

    ListViewAdapter3 (ArrayList<YellowCheck> itemsyellow, Context yContext) {
        this.itemsyellow = itemsyellow;
        this.yContext = yContext;
    }

    @Override
    public int getCount() { return itemsyellow.size(); }

    @Override
    public Object getItem(int i) { return itemsyellow.get(i); }

    @Override
    public long getItemId(int i) { return i; }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if ( view == null ) {
            LayoutInflater layoutInflater = (LayoutInflater) yContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.yellowlistview, viewGroup, false);
        }
        TextView textyellow = view.findViewById(R.id.yellowtextView);

        String chry =itemsyellow.get(i).letteryellow;
        textyellow.setText(chry.toUpperCase());
        this.notifyDataSetChanged();

        return view;
    }
}
