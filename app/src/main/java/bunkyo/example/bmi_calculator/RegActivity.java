package bunkyo.example.bmi_calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Locale;

public class RegActivity extends AppCompatActivity {

    myDBH helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        helper = new myDBH(this);

        // 初期値
        Number height = 165;
        Number weight = 70;
        Number bmi = 25.71;

        findViewById(R.id.floatingActionButton2).setOnClickListener(lis01);

        EditText inputHeight = findViewById(R.id.inputHeight);
        inputHeight.setText(height.toString());

        EditText inputWeight = findViewById(R.id.inputWeight);
        inputWeight.setText(weight.toString());

        TextView btv = findViewById(R.id.bmiTextView);
        btv.setText(bmi.toString());

        // 身長用SeekBar
        SeekBar seekBarH = findViewById(R.id.seekBarHeight);
        seekBarH.setProgress(45); // 初期値
        seekBarH.setMax(90); // 最大値
        seekBarH.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    public void onProgressChanged(SeekBar seekBar,
                                                  int progress, boolean fromUser) {
                        // 値が変更されたとき
                        progress += 120;
                        String str = String.format(Locale.US, "%d",progress);
                        inputHeight.setText(str);

                        // BMIの計算
                        EditText inputWeight = findViewById(R.id.inputWeight);
                        String inputValue = inputWeight.getText().toString();
                        double progressDouble = progress;
                        double bmi = Double.parseDouble(inputValue) / ((progressDouble / 100) * (progressDouble / 100));
                        bmi = Math.floor(bmi * 100) / 100; // 小数第三位で切り捨て

                        btv.setText(Double.toString(bmi));
                    }

                    public void onStartTrackingTouch(SeekBar seekBar) {
                        // ツマミに触れたとき
                    }

                    public void onStopTrackingTouch(SeekBar seekBar) {
                        // ツマミを離したとき
                    }
                }
        );

        // 体重用SeekBar
        SeekBar seekBarW = findViewById(R.id.seekBarWeight);
        seekBarW.setProgress(40); // 初期値
        seekBarW.setMax(170); // 最大値
        seekBarW.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    public void onProgressChanged(SeekBar seekBar,
                                                  int progress, boolean fromUser) {
                        // 値が変更されたとき
                        progress += 30;
                        String str = String.format(Locale.US, "%d",progress);
                        inputWeight.setText(str);

                        // BMIの計算
                        EditText inputHeight = findViewById(R.id.inputHeight);
                        double inputValue = Double.parseDouble(inputHeight.getText().toString());
                        double bmi = progress / ((inputValue / 100) * (inputValue / 100));
                        bmi = Math.floor(bmi * 100) / 100; // 小数第三位で切り捨て

                        btv.setText(Double.toString(bmi));
                    }

                    public void onStartTrackingTouch(SeekBar seekBar) {
                        // ツマミに触れたとき
                    }

                    public void onStopTrackingTouch(SeekBar seekBar) {
                        // ツマミを離したとき
                    }
                }
        );

    }

    // FABクリック時
    View.OnClickListener lis01 = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            EditText inputHeight = findViewById(R.id.inputHeight);
            EditText inputWeight = findViewById(R.id.inputWeight);
            TextView displayBmi = findViewById(R.id.bmiTextView);

            insertDB(
                    Integer.parseInt(inputHeight.getText().toString()),
                    Integer.parseInt(inputWeight.getText().toString()),
                    Double.parseDouble(displayBmi.getText().toString()),
                    0
            );

            // 画面遷移
            Intent in = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(in);
        }
    };

    // DBにデータを登録する処理
    public void insertDB(int height, int weight ,double bmi, int created_at){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("height", height);
        cv.put("weight", weight);
        cv.put("bmi", bmi);
        cv.put("created_at", created_at);
        db.insert("sample", null, cv);
        db.close();
    }

}