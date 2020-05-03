package com.app.edu_sqlite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener{

    //1 Declare all Edit and Button

    private ListView studentListVieww;
    EditText editTextName,editTextAge ;
    TextView textview;


      private StudentUtility studentUtility ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // 2. findViewbyID...

        textview = (TextView) findViewById(R.id.textView);

        textview.setText("SQLite CRUD Operations");


        editTextName        =( EditText)findViewById(R.id.editTextName);
        editTextAge         =( EditText)findViewById(R.id.editTextAge);

        studentListVieww    = (ListView) findViewById(R.id.studentListView);


        Button insertButton = (Button) findViewById(R.id.insertButton);
        Button readButton   = (Button) findViewById(R.id.readButton);
        Button updateButton = (Button) findViewById(R.id.updateButton);
        Button deleteButton = (Button) findViewById(R.id.deleteButton);


        // 3. list...

        insertButton.setOnClickListener(this);
        readButton.  setOnClickListener(this);
        updateButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);

        studentUtility = new StudentUtility(this);
        studentUtility.open();






   int recFound = studentUtility.getNumberOfRecords();
   textview.setText("Number of Records Found "+recFound);


    }


    // 4. onClick
    @Override
    public void onClick(View view) {
        switch( view.getId()){


            case R.id.insertButton:
                insertRecords();

                break;
            case R.id.readButton:
              // studentUtility.listStudentsByToast();
                readRecords();
                break;
            case R.id.updateButton:

                updateRecords();
                break;
            case R.id.deleteButton:

                //deleteRecords();  // delete a record
                 studentUtility.deleteAllStudents();

                break;
        }



        int recFound = studentUtility.getNumberOfRecords();
        textview.setText("Number of Records Found "+recFound);




    }

    private void deleteRecords() {
        studentUtility.DeleteAStudent((new Student(editTextName.getText().toString(),editTextAge.getText().toString() )));

    }

    private void updateRecords() {

        studentUtility.UpdateStudent((new Student(editTextName.getText().toString(),editTextAge.getText().toString() )));
    }

    private void readRecords() {

        // Bring in recycler view...
        //   OUTDATED WAY...
        studentUtility.listStudents();
        Cursor cursor = studentUtility.listStudents();

        // recycler view....

        if (cursor != null) {

            String[] from = new String[]{StudentUtility.COLUMN_NAME, StudentUtility.COLUMN_AGE};
            int[]      to = new int[]{android.R.id.text1, android.R.id.text2};

            SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(
                    this, android.R.layout.simple_list_item_2, cursor, from, to, 0);
            studentListVieww.setAdapter(simpleCursorAdapter);
        }





    }

    private void insertRecords() {

        //Toast.makeText(this, "Clicked on INSERT Button", Toast.LENGTH_SHORT).show();

        studentUtility.addStudent(new Student(editTextName.getText().toString(),editTextAge.getText().toString()));
    }

}
