package com.app.edu_sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class StudentUtility {


    private Context context;
    private MainActivity mainActivity;

    private StudentDatabaseUtility databaseUtility;
    private SQLiteDatabase sqLiteDatabase;


    // Wrapper class for the core DB.
    // House keeping work for the core DB will be done here..

    // 1. HOw to create a DB File
    public static final String DB_NAME = "student.db";   // This file will be created in the inetrnal file system

    // 2. How to create a Table and Whats it???

    public static final String TABLE_NAME = "studentss";

    public static final String COLUMN_ID = "_id";

    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_AGE = "age";

    // 4.  Db ver..

    public static final int DB_VERSION = 1;


    // 3. How can we create the fields for the table

    //  Pri_Key               Name        Age      ADDRESS.( VEr 2 )
    // 1                    Sathya      21
    // 2                    Mradul      20
    // 3                    Harsha      22
    // 4                    Mohan       19


    //  "create table studentss ( _id  integer primary key autoincrement, name text, age text);";
    public static final String TABLE_CREATE_QUERY =
            "create table " + TABLE_NAME + "(" + COLUMN_ID + " integer primary key autoincrement," + COLUMN_NAME + " text," +
                    COLUMN_AGE + " text);";

    public StudentUtility(MainActivity mainActivity) {

        this.context = mainActivity;
        databaseUtility = new StudentDatabaseUtility(context);
        // I have constructed the core DB Class from here


    }

    public void open() {
        // sqLiteDatabase  will be a pointer to the CRUD operations
        sqLiteDatabase = databaseUtility.getWritableDatabase();


    }


    /////////////////////////////////////  CRUD OPerations


    public void addStudent(Student student) {

        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, student.name);
        values.put(COLUMN_AGE, student.age);

        long id = sqLiteDatabase.insert(TABLE_NAME, null, values);

        if (id != -1) {
            Toast.makeText(context, "Insert Success " + id, Toast.LENGTH_SHORT).show();
        }
    }

///////////////////////////  Read records By toast

    public void listStudentsByToast() {

        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, null, null, null,
                null, null, null);


        if (cursor != null && cursor.moveToFirst()) {

            do {

                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                String age = cursor.getString(cursor.getColumnIndex(COLUMN_AGE));

                Toast.makeText(context, "Name :" + name + " Age : " + age, Toast.LENGTH_SHORT).show();


            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
    }


    ///////////////////////////////////////////////////////////////  Update

    public void UpdateStudent(Student student) {

        ContentValues values = new ContentValues();

        values.put(COLUMN_AGE, student.age);

        int numRowsUpdated = sqLiteDatabase.update(TABLE_NAME, values, COLUMN_NAME +
                "=?", new String[]{student.name});


        Toast.makeText(context, "Number of records Updated " + numRowsUpdated, Toast.LENGTH_SHORT).show();
    }


    /////////////// delete a student
    public void DeleteAStudent(Student student) {

        int numrowsDeleted = sqLiteDatabase.delete(TABLE_NAME, COLUMN_NAME +
                "=?", new String[]{student.name});

        Toast.makeText(context, "Number of records Deleted" + numrowsDeleted, Toast.LENGTH_SHORT).show();
    }


    // get number of rec Ex....

    public int getNumberOfRecords() {


    Cursor c = sqLiteDatabase.rawQuery("select * from " + TABLE_NAME, null);
    return c.getCount();
      }


      // delete all records

    public  void deleteAllStudents(){

        sqLiteDatabase.delete(TABLE_NAME,null,null);
        Toast.makeText(context, "Deleted all students...", Toast.LENGTH_SHORT).show();
    }

    public Cursor listStudents() {

        Cursor cursor = sqLiteDatabase.query(TABLE_NAME,null,null,null,
                null,null,null);

        return  cursor;

    }

    //////////// Core DB

    private class StudentDatabaseUtility extends SQLiteOpenHelper {


        public StudentDatabaseUtility(Context context) {
            super(context, DB_NAME, null, DB_VERSION);


        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

            sqLiteDatabase.execSQL(TABLE_CREATE_QUERY);

        }


       // Whenever the ver changes 1    2   3    4    5    6
        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

            Log.d("Contentdatabase",
                    "Upgrading database from version "
                            + oldVersion + " to " + newVersion
                            + ", which will destroy  old data");

            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);   // studentss

            onCreate(sqLiteDatabase);
        }
    }

    ////////////

}
