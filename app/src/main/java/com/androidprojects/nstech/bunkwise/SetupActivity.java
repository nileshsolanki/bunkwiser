package com.androidprojects.nstech.bunkwise;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.androidprojects.nstech.bunkwise.Utils.PrefHandler;
import com.ramotion.fluidslider.FluidSlider;

public class SetupActivity extends AppCompatActivity {

    FloatingActionButton fabSetup;
    ImageView girl, boy;
    EditText etUserName;
    FluidSlider fluidSlider;
    int selected = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PrefHandler pf = new PrefHandler(this);
        if(pf.getParamsUserName("userName") != null  && getIntent().getData() == null){

            startActivity(new Intent(this, SubjectsActivity.class));
            finish();

        }



        setContentView(R.layout.activity_setup);



        fabSetup = findViewById(R.id.fab_setup);
        girl = findViewById(R.id.iv_girl);
        boy = findViewById(R.id.iv_boy);
        etUserName = findViewById(R.id.et_userName);
        fluidSlider = findViewById(R.id.fluidSlider);

        fabSetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveOrUpdateAll();
            }
        });

        girl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selected == 0){
                    selected = 2;
                }
                if(selected == 1){

                    boy.setBackground(null);
                    boy.setAlpha((float) 0.5);
                    selected = 2;

                }
                girl.setBackground(getDrawable(R.drawable.bg_ll_photo_picker));
                girl.setAlpha((float)1);
            }
        });

        boy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selected == 0){

                    selected = 1;

                }

                if(selected == 2){

                    girl.setBackground(null);
                    girl.setAlpha((float) 0.5);
                    selected = 1;

                }
                boy.setAlpha((float) 1);
                boy.setBackground(getDrawable(R.drawable.bg_ll_photo_picker));
            }
        });


        if(getIntent().getData() != null){

            PrefHandler prefHandler = new PrefHandler(this);
            etUserName.setText(prefHandler.getParamsUserName("userName"));

            if(prefHandler.getParams("gender") == 1){

                boy.performClick();

            }
            else{

                girl.performClick();

            }
            fluidSlider.setPosition(prefHandler.getPercentage());


        }else{


            if(Build.VERSION.SDK_INT >= 21){

                getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

            }

        }


    }

    private void saveOrUpdateAll() {

        float target = (fluidSlider.getPosition());
        target = Float.parseFloat(String.format("%.2f", target));
        int gender = selected;
        String userName = etUserName.getText().toString().trim();

        PrefHandler pf = new PrefHandler(this);
        pf.putParams(gender, target, userName);

        Intent intent = new Intent(this, SubjectsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();



    }
}
