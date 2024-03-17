package edu.skku.cs.a2020314994_pa3;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

class PhoneNumber{
    public String name;
    public String number;

    PhoneNumber(String name, String number) {
        this.name = name;
        this.number = number;
    }
}

public class NumListAdapter extends BaseAdapter {
    Button callbtn;
    private ArrayList<PhoneNumber> numitems;
    private Context mContext;

    NumListAdapter (ArrayList<PhoneNumber> numitems, Context mContext) {
        this.mContext = mContext;
        this.numitems = numitems;
    }

    @Override
    public int getCount() {
        return numitems.size();
    }

    @Override
    public Object getItem(int i) {
        return numitems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            LayoutInflater layoutInflater1 = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater1.inflate(R.layout.numlistview, viewGroup, false);
        }

        TextView n = view.findViewById(R.id.pnametextview);
        TextView num = view.findViewById(R.id.pnumtextview);

        n.setText(numitems.get(i).name);
        num.setText(numitems.get(i).number);

        callbtn = (Button) view.findViewById(R.id.callbutton);
        callbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phonenum = "tel:" + num.getText().toString();
                Log.d("phonenum", phonenum);
                Intent callintent = new Intent(Intent.ACTION_VIEW, Uri.parse(phonenum));
                callintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(callintent);
                Log.d("calling","finished");
            }
        });

        this.notifyDataSetChanged();
        return view;
    }
}
