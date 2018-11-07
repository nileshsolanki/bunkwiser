package com.androidprojects.nstech.bunkwise;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SubjectDialog extends Dialog implements View.OnClickListener {
    Context context;
    InputMethodManager imm;
    EditText et_sub_name;
    ArrayAdapter adapter;
    static int index = 0;
    public SubjectDialog(@NonNull Context context, ArrayAdapter adapter) {
        super(context);
        this.context = context;
        this.adapter = adapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_subject_add);

        this.getWindow().setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(android.R.color.transparent)));

        Button btn_add = findViewById(R.id.btn_add_sub);
        btn_add.setOnClickListener(this);

        et_sub_name = findViewById(R.id.et_sub_name);

        imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm != null){

            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        }


    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.btn_add_sub) {

            String subName = et_sub_name.getText().toString().trim();
            for (int i = 0; i < adapter.getCount(); i++) {

                if (adapter.getItem(i).equals(subName)) {

                    Toast.makeText(context, subName + " Already added", Toast.LENGTH_LONG).show();
                    this.dismiss();
                    return;
                }

            }
            adapter.add(subName);
            index++;
            if(imm != null){

                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

            }
            this.dismiss();
        }

    }
}
