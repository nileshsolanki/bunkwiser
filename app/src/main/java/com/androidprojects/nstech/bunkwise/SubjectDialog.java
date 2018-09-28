package com.androidprojects.nstech.bunkwise;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class SubjectDialog extends Dialog implements View.OnClickListener {
    Context context;
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

        Button btn_add = findViewById(R.id.btn_add_sub);
        btn_add.setOnClickListener(this);

        et_sub_name = findViewById(R.id.et_sub_name);
    }

    @Override
    public void onClick(View view) {

        String subName = et_sub_name.getText().toString().trim();
        adapter.add(subName);
        index ++;
        this.dismiss();

    }
}
