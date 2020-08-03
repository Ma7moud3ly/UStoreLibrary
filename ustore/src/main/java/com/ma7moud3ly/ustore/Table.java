
package com.ma7moud3ly.ustore;

public class Table {
    private String name;
    //private boolean id = false;
    private String query = "";

    public Table(String name) {
        this.name = name;
    }

   /* public Table(String name, boolean id) {
        this.name = name;
        this.id = true;
    }*/

    //return table name
    public String getName() {
        return name;
    }

    //add new column name to the table
    public void col(String name, String type) {
        query += name + " " + type + ",";
    }

    //return sql table structure
    public String getStructure() {
        //if (id) query = "id INTEGER PRIMARY KEY," + query;
        if (query.endsWith(",")) query = query.substring(0, query.length() - 1);
        String table = "CREATE TABLE " + this.name + "(" + query + ")";
        return table;
    }
}
