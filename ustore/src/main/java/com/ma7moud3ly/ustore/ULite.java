/*
 * ULite .. SQLite handler
 * By : Mahmoud Aly
 * engma7moud3l@gmail.com
 */
package com.ma7moud3ly.ustore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class ULite {

    private Context context;
    private Table table;
    private SQLiteDatabase db;
    private ContentValues values;

    public ULite(Context context, String database, Table table) {
        this.table = table;
        this.context = context;
        db = new DBHelper(context, database).getWritableDatabase();
        values = new ContentValues();
    }

    public <DATA> void push(String key, DATA val) {
        if (val.getClass() == Integer.class)
            values.put(key, (Integer) val);
        else if (val.getClass() == Long.class)
            values.put(key, (Long) val);
        else if (val.getClass() == Float.class)
            values.put(key, (Float) val);
        else if (val.getClass() == Double.class)
            values.put(key, (Double) val);
        else if (val.getClass() == Boolean.class)
            values.put(key, (Boolean) val);
        else if (val.getClass() == String.class)
            values.put(key, (String) val);
    }

    //recreate either it was exists
    public void create(boolean override) {
        if (override) {
            drop();
            create();
        } else {
            create();
        }
    }

    //create new table if not exist
    public void create() {
        try {
            db.execSQL(table.getStructure());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //delete existing table
    public void drop() {
        db.execSQL("DROP TABLE IF EXISTS " + table.getName());
    }

    public long insert() {
        long r = db.insert(table.getName(), null, values);
        return r;
    }

    //get column
    public List get(String col_name) {
        List col = new ArrayList<>();
        try {
            Cursor cursor = db.query(table.getName(), new String[]{col_name}, null, null, null, null, null);
            int index = cursor.getColumnIndex(col_name);
            while (cursor.moveToNext()) {
                col.add(cursor.getString(index));
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return col;
    }

    //get columns
    public List get(String[] cols) {
        List all_table = new ArrayList<>();
        try {
            Cursor cursor = db.query(table.getName(), cols, null, null, null, null, null);
            while (cursor.moveToNext()) {
                List row = new ArrayList();
                for (int i = 0; i < cols.length; i++)
                    row.add(cursor.getString(i));
                all_table.add(row);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return all_table;
    }

    //get all table
    public List get() {
        Cursor cursor = db.query(table.getName(), null, null, null, null, null, null);
        List table = new ArrayList<>();
        int cols = cursor.getColumnCount();//cols
        while (cursor.moveToNext()) {
            List row = new ArrayList();
            for (int i = 0; i < cols; i++)
                row.add(cursor.getString(i));
            table.add(row);
        }
        cursor.close();
        return table;
    }

    //get by index
    public Object get(int row, int col) {
        Cursor cursor = db.query(table.getName(), null, null, null, null, null, null);
        int rows = cursor.getCount();//rows
        int cols = cursor.getColumnCount();//cols
        if (row >= rows || col >= cols) return "";
        for (int i = 0; i < rows; i++) {
            cursor.moveToNext();
            if (i == row) {
                String val = cursor.getString(col);
                cursor.close();
                return val;
            }
        }
        return "";
    }

    //get row
    public List get(int row) {
        Cursor cursor = db.query(table.getName(), null, null, null, null, null, null);
        int rows = cursor.getCount();//rows
        if (row >= rows) return new ArrayList<>();
        int cols = cursor.getColumnCount();//cols
        for (int i = 0; i < rows; i++) {
            cursor.moveToNext();
            if (i == row) {
                List list = new ArrayList();
                for (int j = 0; j < cols; j++)
                    list.add(cursor.getString(j));
                cursor.close();
                return list;
            }
        }
        return new ArrayList<>();
    }

    //get col  where
    public List get(String col, String search, Object where) {
        try {
            Cursor cursor = db.query(table.getName(), new String[]{col}, search + " = ?", new String[]{where.toString()}, null, null, null);
            List table = new ArrayList<>();
            int cols = cursor.getColumnCount();//cols
            while (cursor.moveToNext()) {
                for (int i = 0; i < cols; i++)
                    table.add(cursor.getString(i));
            }
            cursor.close();
            return table;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList();
        }
    }

    //get cols  where
    public List get(String[] col, String search, Object where) {
        try {
            Cursor cursor = db.query(table.getName(), col, search + " = ?", new String[]{where.toString()}, null, null, null);
            List table = new ArrayList<>();
            int cols = cursor.getColumnCount();//cols
            while (cursor.moveToNext()) {
                List row = new ArrayList();
                for (int i = 0; i < cols; i++)
                    row.add(cursor.getString(i));
                table.add(row);
            }
            cursor.close();
            return table;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList();
        }
    }

    //get all where
    public List get(String search, Object where) {
        try {
            Cursor cursor = db.query(table.getName(), null, search + " = ?", new String[]{where.toString()}, null, null, null);
            List table = new ArrayList<>();
            int cols = cursor.getColumnCount();//cols
            while (cursor.moveToNext()) {
                List row = new ArrayList();
                for (int i = 0; i < cols; i++)
                    row.add(cursor.getString(i));
                table.add(row);
            }
            cursor.close();
            cursor.close();
            return table;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList();
        }
    }

    //delete rows in where search occurs
    public long delete(String search, Object where) {
        return db.delete(table.getName(), search + " LIKE ?", new String[]{where.toString()});
    }

    //update push values  where search occurs
    public int update(String search, Object where) {
        return db.update(table.getName(), values, search + " LIKE ?", new String[]{where.toString()});
    }

    //close database
    public void close() {
        db.close();
    }

}

class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;

    public DBHelper(Context context, String database) {
        super(context, database, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}

