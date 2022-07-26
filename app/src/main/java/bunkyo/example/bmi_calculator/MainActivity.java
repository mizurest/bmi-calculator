package bunkyo.example.bmi_calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    myDBH helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.floatingActionButton).setOnClickListener(lis01);

        helper = new myDBH(this);
//        insertDB(165, 70, 22.22, 0);
        showDB();
    }

//    //DBにデータを登録する処理
//    public void insertDB(int height, int weight ,double bmi, int created_at){
//        SQLiteDatabase db = helper.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put("height", height);
//        cv.put("weight", weight);
//        cv.put("bmi", bmi);
//        cv.put("created_at", created_at);
//        db.insert("sample", null, cv);
//        db.close();
//    }

    // データを表示する処理
    public void showDB(){
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.query("sample", new String[]{"height", "weight", "bmi", "created_at"}, null, null, null, null, null);
        boolean d = c.moveToFirst();

        while(d){

            TextView bmiTv = new TextView(this);
            TextView hwTv = new TextView(this);
            TextView dateTv = new TextView(this);

            bmiTv.setText(c.getString(2));
            hwTv.setText(c.getString(0) + "cm / " + c.getString(1) + "kg");
            dateTv.setText(c.getString(3));

            LinearLayout targetLayout = (LinearLayout)findViewById(R.id.bmiListLayout); // ここにlayoutを追加していく

            LinearLayout cardLayout = new LinearLayout(this);
            cardLayout.setOrientation(LinearLayout.HORIZONTAL); // 水平方向へ配置
            cardLayout.setBackgroundColor(Color.parseColor("#d9d9d9")); // 背景色を設定
            cardLayout.setPadding(5,5,5,5);

            bmiTv.setTextSize(32);
            hwTv.setTextSize(16);
            dateTv.setTextSize(16);

            LinearLayout verticalLayout = new LinearLayout(this);
            verticalLayout.setOrientation(LinearLayout.VERTICAL); // 垂直方向へ配置
            verticalLayout.addView(hwTv);
            verticalLayout.addView(dateTv);

            // 作成したtextを親レイアウトに
            cardLayout.addView(bmiTv);
            cardLayout.addView(verticalLayout);





            targetLayout.addView(cardLayout); // 作成したレイアウトを親レイアウトに


            d = c.moveToNext();
        }

        c.close();
        db.close();
    }


    View.OnClickListener lis01 = new View.OnClickListener() {
        @Override
        public void onClick(View v){

            Intent in = new Intent(getApplicationContext(), RegActivity.class);
            startActivity(in);
        }
    };

    View.OnClickListener lis02 = new View.OnClickListener() {
        @Override
        public void onClick(View v){

            Intent in = new Intent(getApplicationContext(), RegActivity.class);
            startActivity(in);
        }
    };
}