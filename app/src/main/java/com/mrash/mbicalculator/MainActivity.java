package com.mrash.mbicalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    EditText etPersonName,etGender,etAge,etHeight,etWeight;
    Button btnCalculate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    String name = etPersonName.getText().toString().trim();
                    String gender = etGender.getText().toString().trim();
                    int age = Integer.parseInt(etAge.getText().toString());
                    float height = Float.parseFloat(etHeight.getText().toString());
                    float weight = Float.parseFloat(etWeight.getText().toString());

                    height = (float) (height / 100f);




                    Intent intent = new Intent(MainActivity.this,com.mrash.mbicalculator.ResultActivity.class);
                    intent.putExtra("keyName",name); //Name Send to result activity
                    intent.putExtra("keyGender",gender);
                    intent.putExtra("keyAge",age);
                    intent.putExtra("keyHeight",height);
                    intent.putExtra("keyWeight",weight);
                    startActivity(intent);
                    finish();

                }//if end
            }//onclick view end
        });//set on clickListener End
                }

                private boolean validate() {

                    boolean flag = true;
                    if (etPersonName.getText().toString().isEmpty()) {
                        etPersonName.setError("Enter Your Name");
                        flag = false;
                    }
                    if (etGender.getText().toString().isEmpty()) {
                        etGender.setError("Enter Your Gender");
                        flag = false;
                    }

                    if (etAge.getText().toString().isEmpty()) {
                        etAge.setError("Age Can't be Empty");
                        flag = false;

                    }
                    else if (Integer.parseInt(etAge.getText().toString()) == 0) {
                        etAge.setError("Age must be greater than 0");
                        flag = false;
                    }

                    if (etHeight.getText().toString().isEmpty()) {
                        etHeight.setError("Height can't be empty");
                        flag = false;

                    }
                    else if (Float.parseFloat(etHeight.getText().toString()) == 0f) {
                        etHeight.setError("Height can't be zero");
                        flag = false;
                    }

                    if (etWeight.getText().toString().isEmpty()) {
                        etWeight.setError("Weight Must Not be Empty");
                        flag = false;
                    }
                    else if (Float.parseFloat(etWeight.getText().toString()) == 0f) {
                            etWeight.setError("Weight can't be zero");
                            flag = false;
                        }
                    return flag;
                }


                public void init()
                {
                    etPersonName = findViewById(R.id.etPersonName);
                    etGender = findViewById(R.id.etGender);
                    etAge = findViewById(R.id.etAge);
                    etHeight = findViewById(R.id.etHeight);
                    etWeight = findViewById(R.id.etWeight);
                    btnCalculate = findViewById(R.id.btnCalculate);
                }
}