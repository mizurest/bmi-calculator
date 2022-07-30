package bunkyo.example.bmi_calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Locale;

public class EditActivity extends AppCompatActivity {
    myDBH helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        helper = new myDBH(this);
        final SQLiteDatabase db = helper.getWritableDatabase();

        Intent in = getIntent();
        final String BmiStr = in.getStringExtra("BMI");
        final String HeightStr = in.getStringExtra("HEIGHT");
        final String WeightStr = in.getStringExtra("WEIGHT");
        final String DateStr = in.getStringExtra("WEIGHT");

        EditText inputHeight = findViewById(R.id.inputHeight);
        inputHeight.setText(HeightStr);

        EditText inputWeight = findViewById(R.id.inputWeight);
        inputWeight.setText(WeightStr);

        TextView btv = findViewById(R.id.bmiTextView);
        btv.setText(BmiStr);

        // BMIによって文字色を変更する
        double doubleBmi = Double.parseDouble(BmiStr);
        if(doubleBmi < 18.5){
            btv.setTextColor(Color.parseColor("#65A1BD"));
        }else if(doubleBmi < 25){
            btv.setTextColor(Color.parseColor("#4F8A4A"));
        }else if(doubleBmi < 30){
            btv.setTextColor(Color.parseColor("#F9D648"));
        }else if(doubleBmi < 35){
            btv.setTextColor(Color.parseColor("#E4985E"));
        }else{
            btv.setTextColor(Color.parseColor("#D55C5B"));
        }

        findViewById(R.id.floatingActionButton2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: データの更新
                ContentValues cv = new ContentValues();
                EditText inputHeight = findViewById(R.id.inputHeight);
                EditText inputWeight = findViewById(R.id.inputWeight);
                TextView btv = findViewById(R.id.bmiTextView);

                cv.put("height", String.valueOf(inputHeight.getText()));
                cv.put("weight", String.valueOf(inputWeight.getText()));
                cv.put("bmi", String.valueOf(btv.getText()));
                db.update("sample", cv, "height=" +HeightStr+ " AND weight="+WeightStr+ " AND bmi=" + BmiStr, null);

                // 画面遷移
                Intent in = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(in);
            }
        });

        findViewById(R.id.floatingActionButton3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: データの削除
                db.delete("sample", "height=? AND weight=? AND bmi=?",
                        new String[]{HeightStr, WeightStr, BmiStr});
                // 画面遷移
                Intent in = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(in);
            }
        });

        int defaultHeight = Integer.parseInt(HeightStr) - 120;
        int defaultWeight = Integer.parseInt(WeightStr) - 30;
        setHeightSeekBar(defaultHeight);
        setWeightSeekbar(defaultWeight);
    }

    public void setHeightSeekBar(int defaultHeight){
        SeekBar seekBarH = findViewById(R.id.seekBarHeight);
        seekBarH.setProgress(defaultHeight); // 初期値
        seekBarH.setMax(90); // 最大値
        seekBarH.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    public void onProgressChanged(SeekBar seekBar,
                                                  int progress, boolean fromUser) {
                        // 値が変更されたとき
                        progress += 120;
                        String str = String.format(Locale.US, "%d",progress);
                        EditText inputHeight = findViewById(R.id.inputHeight);
                        inputHeight.setText(str);

                        // BMIの計算
                        EditText inputWeight = findViewById(R.id.inputWeight);
                        String inputValue = inputWeight.getText().toString();
                        double progressDouble = progress;
                        double bmi = Double.parseDouble(inputValue) / ((progressDouble / 100) * (progressDouble / 100));
                        bmi = Math.floor(bmi * 100) / 100; // 小数第三位で切り捨て

                        TextView btv = findViewById(R.id.bmiTextView);
                        // BMIによって文字色を変更する
                        if(bmi < 18.5){
                            btv.setTextColor(Color.parseColor("#65A1BD"));
                        }else if(bmi < 25){
                            btv.setTextColor(Color.parseColor("#4F8A4A"));
                        }else if(bmi < 30){
                            btv.setTextColor(Color.parseColor("#F9D648"));
                        }else if(bmi < 35){
                            btv.setTextColor(Color.parseColor("#E4985E"));
                        }else{
                            btv.setTextColor(Color.parseColor("#D55C5B"));
                        }
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

    public void setWeightSeekbar(int defaultWeight){
        SeekBar seekBarW = findViewById(R.id.seekBarWeight);
        seekBarW.setProgress(defaultWeight); // 初期値
        seekBarW.setMax(170); // 最大値
        seekBarW.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    public void onProgressChanged(SeekBar seekBar,
                                                  int progress, boolean fromUser) {
                        // 値が変更されたとき
                        progress += 30;
                        String str = String.format(Locale.US, "%d",progress);
                        EditText inputWeight = findViewById(R.id.inputWeight);
                        inputWeight.setText(str);

                        // BMIの計算
                        EditText inputHeight = findViewById(R.id.inputHeight);
                        double inputValue = Double.parseDouble(inputHeight.getText().toString());
                        double bmi = progress / ((inputValue / 100) * (inputValue / 100));
                        bmi = Math.floor(bmi * 100) / 100; // 小数第三位で切り捨て

                        TextView btv = findViewById(R.id.bmiTextView);
                        // BMIによって文字色を変更する
                        if(bmi < 18.5){
                            btv.setTextColor(Color.parseColor("#65A1BD"));
                        }else if(bmi < 25){
                            btv.setTextColor(Color.parseColor("#4F8A4A"));
                        }else if(bmi < 30){
                            btv.setTextColor(Color.parseColor("#F9D648"));
                        }else if(bmi < 35){
                            btv.setTextColor(Color.parseColor("#E4985E"));
                        }else{
                            btv.setTextColor(Color.parseColor("#D55C5B"));
                        }
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
}