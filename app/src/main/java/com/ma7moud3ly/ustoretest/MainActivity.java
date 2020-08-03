/*
 * UStore library examples
 * By : Mahmoud Aly
 * engma7moud3l@gmail.com
 */

package com.ma7moud3ly.ustoretest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.ma7moud3ly.ustore.Table;
import com.ma7moud3ly.ustore.ULite;
import com.ma7moud3ly.ustore.UPref;
import com.ma7moud3ly.ustore.USon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String MY_TAG = "MYTAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UPref_examples();
        ULit_examples();
        USon_examples();
    }

    private void UPref_examples() {
        //create object of UPref (shared preferences class)
        UPref upref = new UPref(this);
        //store any object..
        upref.put("message", "hello world");

        //create new string list
        List list = new ArrayList<>();
        list.add("my");
        list.add("age");
        list.add("is");
        list.add("25");
        //sotre  list the shared preferences.
        upref.putList("my_list", list);
        //add string value

        //retrieve a string value
        Log.i(MY_TAG, upref.get("message", ""));
        //retrieve a list
        Log.i(MY_TAG, upref.getList("my_list").toString());

    }

    private void ULit_examples() {

        //create table structure
        Table table = new Table("students");
        table.col("name", "TEXT");
        table.col("age", "INTEGER");
        table.col("height", "REAL");

        //create database object by database name & table name
        ULite ulite = new ULite(this, "database.db", table);
        //create table if not exists, and override it if exists
        ulite.create(true);
        //push values into a row
        ulite.push("name", "ahmed");
        ulite.push("age", 20);
        ulite.push("height", 1.8);
        //insert the row into the table
        ulite.insert();
        //insert again..
        ulite.push("name", "ali");
        ulite.push("age", 30);
        ulite.push("height", 1.76);
        ulite.insert();
        //retrieve all value in the table
        Log.i(MY_TAG, ulite.get().toString());
        //push age value to update
        ulite.push("age", 1000);
        //update the rows in which name=ali the new age value
        ulite.update("name", "ali");
        //retrieve all value in the table
        Log.i(MY_TAG, ulite.get().toString());
        //retrieve only column form the table
        Log.i(MY_TAG, ulite.get("name").toString());
    }

    private void USon_examples() {
        //create new json file to store hashMap
        USon uson = new USon(this, "file.json");
        HashMap map=new HashMap();
        map.put("name","ahmed");
        map.put("age",30);
        map.put("height",1.85);
        //store the map in file.json in app local storage path
        uson.putMap(map);
        //retrieve the stored map
        Log.i(MY_TAG, uson.getMap().toString());

    }
}