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

import java.util.ArrayList;

class EmailAddress{
    public String ename;
    public String address;

    EmailAddress(String name, String address) {
        this.ename = name;
        this.address = address;
    }
}

public class EListAdapter extends BaseAdapter {
    Button sendbtn;
    private ArrayList<EmailAddress> eitems;
    private Context nContext;

    EListAdapter (ArrayList<EmailAddress> eitems, Context nContext) {
        this.nContext = nContext;
        this.eitems = eitems;
    }

    @Override
    public int getCount() {
        return eitems.size();
    }

    @Override
    public Object getItem(int i) {
        return eitems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view2, ViewGroup viewGroup) {
        LayoutInflater layoutInflater2 = (LayoutInflater) nContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view2 = layoutInflater2.inflate(R.layout.elistview, viewGroup, false);

        TextView en = view2.findViewById(R.id.enametextview);
        TextView addr = view2.findViewById(R.id.enumtextview);

        en.setText(eitems.get(i).ename);
        addr.setText(eitems.get(i).address);

        sendbtn = (Button) view2.findViewById(R.id.sendbutton);
        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendintent = new Intent(Intent.ACTION_SEND);
                sendintent.setType("plain/text");
                String ad =  addr.getText().toString();
                String[] adrs = {ad};
                Log.d("Email", ad);
                String receiver = "제목:" + en.getText().toString() + " 메일드립니다";
                sendintent.putExtra(Intent.EXTRA_EMAIL, adrs);
                sendintent.putExtra(Intent.EXTRA_SUBJECT, receiver);
                sendintent.putExtra(Intent.EXTRA_TEXT, "내용을 입력하세요");
                sendintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                nContext.startActivity(sendintent);
            }
        });

        this.notifyDataSetChanged();
        return view2;
    }
}

