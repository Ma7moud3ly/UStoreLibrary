/*
 * USon .. a json calss to store/restore list&hashMap in json file.
 * By : Mahmoud Aly
 * engma7moud3l@gmail.com
 */
package com.ma7moud3ly.ustore;

import android.content.Context;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class USon {

    private File file;

    //create json file in app local directory
    public USon(Context c, String path) {
        path = c.getApplicationInfo().dataDir + "/" + path;
        file = new File(path);
        File dir = new File(file.getParent());
        if (!dir.exists()) dir.mkdir();
    }

    //create json file from string path...
    public USon(String path) {
        file = new File(path);
        File dir = new File(file.getParent());
        if (!dir.exists()) dir.mkdir();
    }
    //create json file from a file parameter
    public USon(File file) {
        this.file = file;
        File dir = new File(file.getParent());
        if (!dir.exists()) dir.mkdir();
    }

    //delete json file
    public void delete() {
        file.delete();
    }

    //store list
    public void putList(List list) {
        JSONArray json = new JSONArray();
        for (Object obj : list) json.put(obj);
        UFiles.write(file, json.toString());
    }

    //restore list
    public List getList() {
        String string = UFiles.read(file);
        List list = new ArrayList();
        try {
            JSONArray json = new JSONArray(string);
            for (int i = 0; i < json.length(); i++)
                list.add(json.get(i));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    //store hashMap
    public void putMap(HashMap map) {
        try {
            JSONObject json = new JSONObject();
            Set keys = map.keySet();
            for (Object key : keys) json.put(key.toString(), map.get(key));
            UFiles.write(file, json.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //restore hashMap
    public HashMap getMap() {
        HashMap map = new HashMap();
        try {
            String string = UFiles.read(file);
            JSONObject json = new JSONObject(string);
            Iterator<String> iterator = json.keys();
            while (iterator.hasNext()) {
                String key = iterator.next();
                Object value = json.get(key);
                map.put(key, value);
            }
        } catch (JSONException je) {
            je.printStackTrace();
        }
        return map;
    }
}

