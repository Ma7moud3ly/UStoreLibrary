# UStore - Fast&Simple local storage handler.
with UStore it's easy to store objects,lists,hashMaps and retrieve them with less effort and friendly code.

#### Getting Started
- Add UStore to your project.

in project build.gradle
```
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io"}
        ...
    }
}
```
in app build.gradle
```
dependencies {
    ....
    implementation 'com.github.ma7moud3ly:UStoreLibrary:v1.4'
}
```

#### ULite - SQLite Handler
- this class helps to create SQLite database/tables and insert/update/delete/search data.

import ULite..
```
import com.ma7moud3ly.ustore.ULite;
```
create a table
```
        Table table = new Table(String table_name);
        table.col(String column_name", String data_type);

        //create  table (students) with 3 columns name,age,height
        Table table = new Table("students");
        table.col("name", "TEXT");
        table.col("age", "INTEGER");
        table.col("height", "REAL");
```
create a database object.
```
        ULite ulite = new ULite(Context context,String database_name.db, Table table_name);
        
        ULite ulite = new ULite(this, "database.db", table);
```
create a table in the database
```     
         ulite.create(boolean override)
        // if table exists override it (create new one)   
        ulite.create(true);
        //create the table only if not exists
        ulite.create();
        or
        ulite.create(false);

```
push and insert.
```
        ulite.push(String column_name,Object value);

        //push values into a row
        ulite.push("name", "ahmed");
        ulite.push("age", 20);
        ulite.push("height", 1.8);
        
        //insert the row into the table
        ulite.insert();
        
        //push & insert again..
        ulite.push("name", "ali");
        ulite.push("age", 30);
        ulite.push("height", 1.76);
      
        ulite.insert();
```
retrieve data
```     
         //retrieve all the table.
         ulite.get();
         //retrieve by index.
          ulite.get(int row,int col);
         //retrieve single row.
         ulite.get(string row_name);
         //retrieve some columns.
         ulite.get(String[]{col1,col2,col...})
         //retrieve values by search
         ulite.get(String col, String search, Object where)
         //this brings all values in col=name where  age=15
         ulite.get("name", "age", 15)
```
update values in the table.
```
        //push new age value update.
        ulite.push("age", 90);
        //update the rows where name=ali with the new value age=90
        ulite.update("name", "ali");
```
delete the table.
```
        ulite.drop()
```
close the connection.
```
        ulite.close()
```

#### UPref - Shared Prefrences handler
- UPref helps to sotre/resotre values and lists in the Shared Preferences.

import UPref
```
import com.ma7moud3ly.ustore.UPref;
```
create an object
```
        UPref u = new UPref(Context context);

        UPref u = new UPref(this);
```
store objects..
```
        u.put(String key,Object data)
        
        u.put("message", "hello world");
        u.put("is_logged_in", true);
```        
store a string list.
```
        //create new string list
        List list = new ArrayList<>();
        list.add("my");
        list.add("age");
        list.add("is");
        list.add("25");
        
        //store the list
        u.putList("my_list", list);
```
retrieve the data.
```
         u.get(String key,Object default_value);
         
        //retrieve a string value
         u.get("message", "")
        //retrieve a list
        u.getList("my_list")
        
```

#### USon - JSON handler
- USon sotres/restores List/HashMap  in a json file.

import USon
```
import com.ma7moud3ly.ustore.USon;
```

create an object.
```
        USon uson = new USon(Context context, String json_file_path);
        USon uson = new USon(String json_file_path);
        USon uson = new USon(File json_file);
        
        //store the json file in the app local data directory with file.json name.
        //this does not require sotrage permission.
        USon uson = new USon(this, "file.json");
        
```
create a HashMap and put the values.
```
        HashMap map=new HashMap();
        map.put("name","ahmed");
        map.put("age",30);
        map.put("height",1.85);
```
store data.
```
        uson.putList(List list);
        uson.putMap(HashMap map);
```

store the hashMap (map) in file.json file.
```
        uson.putMap(map);
```
retrieve the stored map
```
       uson.getMap();
```
delete the json file.
```
uson.delete();
```
