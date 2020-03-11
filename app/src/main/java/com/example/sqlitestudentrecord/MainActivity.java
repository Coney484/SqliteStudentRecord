package com.example.sqlitestudentrecord;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editTextID, editTextName, editTextEmail, editTextCC;
    Button buttonAdd, buttonGetDta, buttonUpdate, buttonDelete, buttonViewAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);

        editTextID = findViewById(R.id.editText_id);
        editTextName = findViewById(R.id.editText_name);
        editTextEmail = findViewById(R.id.editText_email);
        editTextCC = findViewById(R.id.editText_CC);

        buttonAdd = findViewById(R.id.button_add);
        buttonGetDta = findViewById(R.id.button_view);
        buttonUpdate = findViewById(R.id.button_update);
        buttonDelete = findViewById(R.id.button_delete);
        buttonViewAll = findViewById(R.id.button_viewAll);



//        showMessage("test","\n Testing Done");


        addButton();
        getData();
        viewAll();
        updateData();
        deleteData();
    }
    public void addButton() {
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDb.insertData(editTextName.getText().toString(), editTextEmail.getText().toString(), editTextCC.getText().toString());
                if (isInserted==true) {
                    Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Something went Wrong", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void getData() {
        buttonGetDta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = editTextID.getText().toString();

                if(id.equals(String.valueOf("")))
                {
                    editTextID.setError("Please Enter ID");
                    return;
                }

                Cursor cursor = myDb.getData(id);
                String data = null;

                if(cursor.moveToNext())
                {
                    data = "ID: " + cursor.getString(0) + "\n" +
                            "Name: " + cursor.getString(1) + "\n" +
                            "Email: " + cursor.getString(2) + "\n" +
                            "Course_Count: " + cursor.getString(3) + "\n";
                }
                showMessage("Data", data);
            }
        });
    }

    public void viewAll(){
        buttonViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = myDb.getAllData();
                if (cursor.getCount()==0)
                {
                    showMessage("Error","No Data has been Added onto Database");
                    return;
                }

                StringBuffer buffer = new StringBuffer();

                while (cursor.moveToNext())
                {
                    buffer.append("ID:"+cursor.getString(0)+"\n");
                    buffer.append("Name:"+cursor.getString(1)+"\n");
                    buffer.append("Email:"+cursor.getString(2)+"\n");
                    buffer.append("Course Count:"+cursor.getString(3)+"\n\n");
                }

                showMessage("All data",buffer.toString());

            }
        });
    }

    public void updateData(){
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate =  myDb.updateData(editTextID.getText().toString(),editTextName.getText().toString(),editTextEmail.getText().toString(),editTextCC.getText().toString());
                if (isUpdate == true){
                    Toast.makeText(MainActivity.this,"Data Updated Successfully",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this,"An Error Occurred",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void deleteData(){
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deleteRow = myDb.deleteData(editTextID.getText().toString());
                if (deleteRow > 0){
                    Toast.makeText(MainActivity.this,"Deleted Successfully",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this,"An Error Occurred",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void showMessage(String title, String message) {
        Context context;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.create();
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }


}
