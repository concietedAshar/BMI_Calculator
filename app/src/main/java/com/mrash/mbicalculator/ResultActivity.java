package com.mrash.mbicalculator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.number.Precision;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.net.URI;
import java.text.DecimalFormat;

import static java.lang.Float.*;

public class ResultActivity extends AppCompatActivity {

    private static final String FILE_NAME = "BMI_Data.txt";
    TextView tvBMIRes, tvBMINoRes,tvMessage,tvNormal;
    Button btnBack;
    String saveText;
    private static final int WRITE_EXTERNAL_STORAGE_CODE  = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        init();

        String getName = getIntent().getStringExtra("keyName");
        String getGender = getIntent().getStringExtra("keyGender");
        int getAge = getIntent().getIntExtra("keyAge",-1);
        float getHeight = getIntent().getFloatExtra("keyHeight", -1);
        float getWeight = getIntent().getFloatExtra("keyWeight", -1);
        //calculate BMI using funcion
        float getBMI = calculateBMI(getWeight,getHeight);
        //Format It to 1 decimal place
        DecimalFormat df = new DecimalFormat("#.0");
        getBMI = valueOf(df.format( getBMI));

        // concatenate to store i file
        saveText = saveText(getName,getGender,getAge,getHeight,getWeight,getBMI);

        String result = ""+ getBMI;
        if(getBMI != -1)
        {
            tvBMINoRes.setText(result);
            tvMessage.setText(displayBMI(getBMI));
            String extra = "";

            if(compare(getBMI, 18.5f) <= 0 )
            {
                extra = "Normal BMI Range: 18Kg/m^2 - 25Kg/m^2 \n\n Try to Eat More";
            }
            else if(compare(getBMI, 25.0f) > 0)
            {
                extra = "Normal BMI Range: 18Kg/m^2 - 25Kg/m^2 \n\n Try to Exercise More";
            }
            tvNormal.setText(extra);
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createAndSaveFile();
                finish();


            }
        });
    }

    private float calculateBMI(float getWeight, float getHeight) {
        float getBMI =  getWeight / (getHeight * getHeight);
        return getBMI;
    }

    private String saveText(String getName, String getGender, int getAge, float getHeight, float getWeight, float getBMI) {

        String saveText = getName + " " + getGender + " " + getAge + " "
                + getHeight + " " + getWeight + " " +  getBMI;

        return saveText;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1)
        {
            if(resultCode == RESULT_OK)
            {
                try {
                    Uri uri = data.getData();
                    OutputStream outputStream = getContentResolver().openOutputStream(uri,"wa");
                    outputStream.write(saveText.getBytes());

                    outputStream.close();

                    Toast.makeText(this, "File Saved SuccessFully", Toast.LENGTH_SHORT).show();
                }catch (IOException e)
                {
                    Toast.makeText(this, "Fail to Save File", Toast.LENGTH_SHORT).show();
                }

            }else
            {
                Toast.makeText(this, "File Not Saved", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void createAndSaveFile() {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TITLE,FILE_NAME);
        startActivityForResult(intent, 1);
    }

    private String displayBMI(float getBMI) {
        String bmiLabel = "";
        if(compare(getBMI,18.5f) <= 0)
        {
            bmiLabel = getString(R.string.underWeight);
        }
        else if(compare(getBMI, 18.5f) > 0 && compare(getBMI, 24.9f) <= 0 )
        {
            bmiLabel = getString(R.string.normal);
        } else if(compare(getBMI, 25.0f) > 0 && compare(getBMI, 29.9f) <= 0 )
        {
            bmiLabel = getString(R.string.pre_obesity);
        } else if(compare(getBMI, 30.0f) > 0 && compare(getBMI, 34.9f) <= 0 )
        {
            bmiLabel = getString(R.string.obesity_1st_Degree);
        } else if(compare(getBMI, 35.0f) > 0 && compare(getBMI, 39.9f) <= 0 )
        {
            bmiLabel = getString(R.string.obesity_2nd_Degree);
        }
        else
        {
            bmiLabel = getString(R.string.obesity_3rd_Degree);
        }

        return bmiLabel;
    }

    private void init() {

        tvBMIRes = findViewById(R.id.tvBMIRes);
        tvBMINoRes = findViewById(R.id.tvBMINoRes);
        tvMessage = findViewById(R.id.tvMessage);
        tvNormal = findViewById(R.id.tvNormal);
        btnBack = findViewById(R.id.btnBack);

    }
}