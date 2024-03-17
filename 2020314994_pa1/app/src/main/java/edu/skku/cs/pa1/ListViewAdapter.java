package edu.skku.cs.pa1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.io.InputStream;
import java.util.ArrayList;

class TextSetting {
    public String letter;
    public String answ;

    public TextSetting(String letter, String answ){
        this.letter = letter;
        this.answ = answ;
    }
}

public class ListViewAdapter extends BaseAdapter {

    private ArrayList<TextSetting> texts;
    private Context mContext;

    ListViewAdapter (ArrayList<TextSetting> texts, Context mContext) {
        this.texts = texts;
        this.mContext = mContext;
    }

    @Override
    public int getCount() { return texts.size(); }

    @Override
    public Object getItem(int i) { return texts.get(i); }

    @Override
    public long getItemId(int i) { return i; }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if ( view == null ) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.listview, viewGroup, false);
        }

        TextView text1 = view.findViewById(R.id.textView1);
        TextView text2 = view.findViewById(R.id.textView2);
        TextView text3 = view.findViewById(R.id.textView3);
        TextView text4 = view.findViewById(R.id.textView4);
        TextView text5 = view.findViewById(R.id.textView5);

        //
        int bgStrike = ContextCompat.getColor(mContext.getApplicationContext(),R.color.background_strike);
        int txtStrike = ContextCompat.getColor(mContext.getApplicationContext(), R.color.text_strike);
        int bgBall = ContextCompat.getColor(mContext.getApplicationContext(), R.color.background_ball);
        int txtOut = ContextCompat.getColor(mContext.getApplicationContext(), R.color.text_out);
        int bgOut = ContextCompat.getColor(mContext.getApplicationContext(), R.color.background_out);

        //
        char[] ch = new char[5];
        char[] an = new char[5];
        for (int f = 0; f < 5; f++) {
            ch[f] = texts.get(i).letter.charAt(f);
            an[f] = texts.get(i).answ.charAt(f); }

        //
        for ( int a = 0 ; a < 1 ; a ++ ) {
            int match = 0;
            for ( int b = 1 ; b < 5 ; b++) {
                // 위치와 존재 모두 맞을 때
                if ( ch[0] == an[0]) {
                    match = 1;
                    text1.setBackgroundColor(bgStrike);
                    text1.setTextColor(txtStrike);
                    break;

                }

                else if (ch[a] == an[b] ) {
                    text1.setBackgroundColor(bgBall);
                    text1.setTextColor(txtStrike);
                    match = 1;
                    break;
                }
            }

            if ( match == 0) {
                text1.setBackgroundColor(bgOut);
                text1.setTextColor(txtOut);
            }

            String chr = Character.toString(ch[0]);
            text1.setText(chr.toUpperCase());
        } // end of for */

        for ( int a = 1 ; a < 2 ; a ++ ) {
            int match = 0;
            for ( int b = 0 ; b < 5 ; b++) {
                // 위치와 존재 모두 맞을 때
                if ( ch[1] == an[1]) {
                    match = 1;
                    text2.setBackgroundColor(bgStrike);
                    text2.setTextColor(txtStrike);
                    break;
                }

                else if ( ch[a] == an[b]) {
                    text2.setBackgroundColor(bgBall);
                    text2.setTextColor(txtStrike);
                    match = 1;
                    break;
                }
            }

            if ( match == 0) {
                text2.setBackgroundColor(bgOut);
                text2.setTextColor(txtOut);
            }

            String chr = Character.toString(ch[1]);
            text2.setText(chr.toUpperCase());
        } // end of for */

        for ( int a = 2 ; a < 3 ; a ++ ) {
            int match = 0;
            for ( int b = 0 ; b < 5 ; b++) {
                // 위치와 존재 모두 맞을 때
                if ( ch[2] == an[2] ) {
                    match = 1;
                    text3.setBackgroundColor(bgStrike);
                    text3.setTextColor(txtStrike);
                    break;
                }

                else if ( ch[a] == an[b] ) {
                    text3.setBackgroundColor(bgBall);
                    text3.setTextColor(txtStrike);
                    match = 1;
                    break;
                }
            }

            if ( match == 0) {
                text3.setBackgroundColor(bgOut);
                text3.setTextColor(txtOut);
            }
            String chr = Character.toString(ch[2]);
            text3.setText(chr.toUpperCase());
        }

        for ( int a = 3 ; a < 4 ; a ++ ) {
            int match = 0;
            for ( int b = 0 ; b < 5 ; b++) {
                // 위치와 존재 모두 맞을 때
                if ( ch[3] == an[3] ) {
                    match = 1;
                    text4.setBackgroundColor(bgStrike);
                    text4.setTextColor(txtStrike);
                    break;
                }

                else if ( ch[a] == an[b] ) {
                    text4.setBackgroundColor(bgBall);
                    text4.setTextColor(txtStrike);
                    match = 1;
                    break;
                }
            }

            if ( match == 0) {
                text4.setBackgroundColor(bgOut);
                text4.setTextColor(txtOut);
            }
            String chr = Character.toString(ch[3]);
            text4.setText(chr.toUpperCase());
        }

        for ( int a = 4 ; a < 5 ; a ++ ) {
            int match = 0;
            for ( int b = 0 ; b < 5 ; b++) {
                // 위치와 존재 모두 맞을 때
                if ( ch[4] == an[4]) {
                    match = 1;
                    text5.setBackgroundColor(bgStrike);
                    text5.setTextColor(txtStrike);
                    break;
                }

                else if ( ch[a] == an[b] ) {
                    text5.setBackgroundColor(bgBall);
                    text5.setTextColor(txtStrike);
                    match = 1;
                    break;
                }
            }

            if ( match == 0) {
                text5.setBackgroundColor(bgOut);
                text5.setTextColor(txtOut);
            }
            String chr = Character.toString(ch[4]);
            text5.setText(chr.toUpperCase());
        }
        this.notifyDataSetChanged();
        return view;
    }
}
