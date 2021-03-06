package com.kmu.dietapp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.ContentValues;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Meal extends AppCompatActivity {
    private DBHelper2 mydb;
    public CheckBox cb;
    SQLiteDatabase db;
    public int result;
    EditText edit_foodName,edit_infokcal,edit_incal;
    public String kcal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);

        mydb  = new DBHelper2(this);

        try {
            db = mydb.getWritableDatabase();
        } catch (SQLiteException ex) {
            db = mydb.getReadableDatabase();
        }

        edit_foodName = (EditText) findViewById(R.id.edit_foodName);
        edit_infokcal = (EditText) findViewById(R.id.info_kcal);
        edit_incal = findViewById(R.id.edit_incal);

        cb = (CheckBox)findViewById(R.id.checkBox);

    }

    public void search(View target) {
        //음식을 입력받아서 디비에 접근해서 입력받은 음식에 해당하는 칼로리 출력하기

        String ed_foodName = edit_foodName.getText().toString();
        Cursor cursor;
        cursor = db.rawQuery("SELECT * FROM calories WHERE foodname='"
                + ed_foodName + "';", null);

        //디비에 메뉴가 존재 할 경우 몇칼로리인지 표시 해주기
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            Toast.makeText(getApplicationContext(), "메뉴가 존재합니다.",
                    Toast.LENGTH_SHORT).show();

            kcal = cursor.getString(cursor.getColumnIndex(DBHelper2.CAL_COLUMN_KCAL));
            edit_infokcal.setText(kcal);
        }

        else{
            Toast.makeText(getApplicationContext(), "메뉴가 존재하지 않습니다.",
                    Toast.LENGTH_SHORT).show();
        }

    }

    public void register(View target){

        //추가 버튼이 클릭 되었으면 등록버튼을 눌렀을때 실행
        if(cb.isChecked())
        {
            Toast.makeText(getApplicationContext(), "메뉴가 추가되었습니다.",
                    Toast.LENGTH_SHORT).show();

        }else{
            String str = edit_incal.getText().toString();
            kcal = str;
            Toast.makeText(getApplicationContext(), "칼로리가 추가되었습니다.",
                    Toast.LENGTH_SHORT).show();
        }

        Intent resultIntent = new Intent();
        resultIntent.putExtra("result",kcal);
        setResult(RESULT_OK,resultIntent);
        finish();



    }



}







