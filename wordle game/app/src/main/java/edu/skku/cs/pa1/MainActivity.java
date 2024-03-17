package edu.skku.cs.pa1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ListView listViewGray;
    private ListView listViewGreen;
    private ListView listViewYellow;


    private ListViewAdapter listViewAdapter;
    private ListViewAdapter2 listViewAdapterGreen;
    private ListViewAdapter3 listViewAdapterYellow;
    private ListViewAdapter4 listViewAdapterGray;

    private ArrayList<TextSetting> texts;

    private ArrayList<GreenCheck> itemsgreen;
    private ArrayList<YellowCheck> itemsyellow;
    private ArrayList<GrayCheck> itemsgray;
    // yellow 아이템 담는 list
    public ArrayList<String> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 변수 설정
        EditText editText1 = (EditText) findViewById(R.id.editText);
        Button button = (Button) findViewById(R.id.button);

        // 파일 읽기
        InputStream wordtxt = null;
        try {
            wordtxt = getApplicationContext().getAssets().open("wordle_words.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 파일 string으로 바꾸기
        Scanner s = new Scanner(wordtxt).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";

        // string 쪼개서 array로 저장
        String[] words = result.split("\\s+");

        // 정답과 input을 adapter에 전달
        int number = words.length; //5757
        int pick;
        Random random = new Random();
        pick = random.nextInt(number); // 정답 랜덤으로 하나 선택
        String answer = words[pick];

        // strike면 3, ball이면 2, out면 1담기
        int[] alphabet = new int[26];
        int k;
        for ( k = 0 ; k < 26 ; k ++ ) {
            alphabet[k] = 0;
        }

        listView = findViewById(R.id.listview1);
        listViewGray = findViewById(R.id.graylist);
        listViewGreen = findViewById(R.id.greenlist);
        listViewYellow = findViewById(R.id.yellowlist);

        texts = new ArrayList<TextSetting>();
        listViewAdapter = new ListViewAdapter(texts, getApplicationContext());
        listView.setAdapter(listViewAdapter);

        itemsgreen = new ArrayList<GreenCheck>();
        itemsyellow = new ArrayList<YellowCheck>();
        itemsgray = new ArrayList<GrayCheck>();

        listViewAdapterGreen = new ListViewAdapter2(itemsgreen, getApplicationContext());
        listViewAdapterYellow = new ListViewAdapter3(itemsyellow, getApplicationContext());
        listViewAdapterGray = new ListViewAdapter4(itemsgray, getApplicationContext());

        listViewGreen.setAdapter(listViewAdapterGreen);
        listViewYellow.setAdapter(listViewAdapterYellow);
        listViewGray.setAdapter(listViewAdapterGray);
        //버튼 누르면 실행
        button.setOnClickListener(view -> {
            // 정답이 txt 파일에 있는지 확인
            String input = editText1.getText().toString(); // 사용자가 입력한 단어 input

            int i;
            int exist;
            exist = 0;
            //int match = 0;
            for (i = 0; i < number; i++) {
                // dictionary 안에 있는지 확인. 있을 때 exist ==1
                if ( (words[i]).equals(input)) {
                    editText1.setText("");
                    exist = 2;
                    //Toast.makeText(getApplicationContext(), answer + "", Toast.LENGTH_SHORT).show();
                    break;
                }
            }

            // 정답 없으면 에러 메세지 toast
            if (exist == 0)
                Toast.makeText(getApplicationContext(), "Word '" +input+"' not in dictionary!", Toast.LENGTH_SHORT).show();

            // 정답 있으면 실행
            if ( exist == 2) {
                texts.add(new TextSetting (input, answer));
                int countyellow = 0;
                char[] ch = new char[5];
                char[] an = new char[5];
                for (int f = 0; f < 5; f++) {
                    ch[f] = input.charAt(f);
                    an[f] = answer.charAt(f); }

                //input과 answer 비교
                for ( int z = 0 ; z < 5 ; z ++ ) {
                    int match = 0;
                    char a = ch[z];
                    int uni = a;
                    int ind = uni % 97;
                    for ( int w = 0 ; w < 5 ; w++) {
                        // strike
                        if ( ch[z] == an[z]) {
                            if ( alphabet[ind] == 2) {
                                alphabet[ind] =3;
                                match = 1;
                                itemsgreen.add(new GreenCheck(Character.toString(ch[z])));
                                int yellowsize = list.size();
                                for ( int l = 0 ; l <yellowsize; l++ ) {
                                    if ( (list.get(l)).equals(Character.toString(ch[z]))) {
                                        list.remove(l);
                                        itemsyellow.remove(l);
                                        break;
                                    }
                                }
                                break;
                            }

                            else if (alphabet[ind] == 0) {
                                alphabet[ind] = 3;
                                match = 1;
                                itemsgreen.add(new GreenCheck(Character.toString(ch[z])));
                            }
                        }

                        // ball
                        else if (ch[z] == an[w] ) {
                            if ( alphabet[ind] == 0) {
                                itemsyellow.add(new YellowCheck(Character.toString(ch[z])));
                                alphabet[ind] = 2;
                                match = 1;
                                list.add(Character.toString(ch[z]));
                                break;
                            }
                        }
                    }

                    //out
                    if ( match == 0) {
                        if ( alphabet[ind] == 0) {
                            itemsgray.add(new GrayCheck(Character.toString(ch[z])));
                            alphabet[ind] = 1;
                        }
                    }
                } // end of for */
            }
            listViewAdapter.notifyDataSetChanged();

            listViewAdapterGreen.notifyDataSetChanged();
            listViewAdapterYellow.notifyDataSetChanged();
            listViewAdapterGray.notifyDataSetChanged();
        });
    }
}