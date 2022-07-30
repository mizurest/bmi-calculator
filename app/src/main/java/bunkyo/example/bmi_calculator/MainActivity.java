package bunkyo.example.bmi_calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    myDBH helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.floatingActionButton).setOnClickListener(lis01); // FAB押下で新規登録画面に遷移

        helper = new myDBH(this);
        showDB();
    }

    // データを表示する処理
    public void showDB(){
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.query("sample", new String[]{"height", "weight", "bmi", "created_at"}, null, null, null, null, null);
        boolean d = c.moveToFirst();

        // 登録されているbmiの数だけ繰り返す
        int index = 0;
        while(d){
            if(index == 0){
                CoordinatorLayout wrap = findViewById(R.id.Wrap);
                TextView nrf = findViewById(R.id.nrf);
                wrap.removeView(nrf);
            }
            final String bmiStr =  c.getString(2);
            final String heightStr = c.getString(0);
            final String WeightStr = c.getString(1);
            final String DateStr = c.getString(3);

            // レイアウトの作成
            TextView bmiTv = new TextView(this);
            TextView hwTv = new TextView(this);
            TextView dateTv = new TextView(this);

            bmiTv.setText(bmiStr);
            hwTv.setText(heightStr + "cm / " + WeightStr + "kg");

            dateTv.setText(DateStr);

            LinearLayout targetLayout = (LinearLayout)findViewById(R.id.bmiListLayout); // ここにlayoutを追加していく

            LinearLayout cardLayout = new LinearLayout(this);
            cardLayout.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    showDetail(bmiStr, heightStr, WeightStr, DateStr);
                }
            });
            cardLayout.setOrientation(LinearLayout.HORIZONTAL); // 水平方向へ配置
            cardLayout.setBackgroundColor(Color.parseColor("#d9d9d9")); // 背景色を設定
            cardLayout.setPadding(16,32,16,32);

            bmiTv.setTextSize(32);
            bmiTv.setPadding(32,0,0,0);

            // BMIによって文字色を変更する
            double doubleBmi = Double.parseDouble(bmiStr);
            if(doubleBmi < 18.5){
                bmiTv.setTextColor(Color.parseColor("#65A1BD"));
            }else if(doubleBmi < 25){
                bmiTv.setTextColor(Color.parseColor("#4F8A4A"));
            }else if(doubleBmi < 30){
                bmiTv.setTextColor(Color.parseColor("#F9D648"));
            }else if(doubleBmi < 35){
                bmiTv.setTextColor(Color.parseColor("#E4985E"));
            }else{
                bmiTv.setTextColor(Color.parseColor("#D55C5B"));
            }

            hwTv.setTextSize(16);
            dateTv.setTextSize(16);

            LinearLayout verticalLayout = new LinearLayout(this);
            verticalLayout.setOrientation(LinearLayout.VERTICAL); // 垂直方向へ配置
            verticalLayout.setPadding(64,0,0,0);
            verticalLayout.addView(hwTv);
            verticalLayout.addView(dateTv);


            // 作成したtextを親レイアウトに
            cardLayout.addView(bmiTv);
            cardLayout.addView(verticalLayout);

            targetLayout.addView(cardLayout); // 作成したレイアウトを親レイアウトに

            index++;
            d = c.moveToNext();
        }
        c.close();
        db.close();
    }

    public void showDetail(String arg01, String arg02, String arg03, String arg04){
        Intent in = new Intent(getApplicationContext(), EditActivity.class);
        in.putExtra("BMI", arg01);
        in.putExtra("HEIGHT", arg02);
        in.putExtra("WEIGHT", arg03);
        in.putExtra("DATE", arg04);
        startActivity(in);
    }


    View.OnClickListener lis01 = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            Intent in = new Intent(getApplicationContext(), RegActivity.class);
            startActivity(in);
        }
    };
}