package com.example.createupdatewithdelete_data;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText name, email, address, password, id;
    private Button create, update, delete, showAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        address = (EditText) findViewById(R.id.address);
        id = (EditText) findViewById(R.id.id);
        password = (EditText) findViewById(R.id.password);
        create = (Button) findViewById(R.id.save);
        update = (Button) findViewById(R.id.update);
        showAll = (Button) findViewById(R.id.show);
        delete = (Button) findViewById(R.id.delete);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModelClass modelClass = new ModelClass();
                MyDataBaseHelper myDataBaseHelper = new MyDataBaseHelper(getApplicationContext());
                modelClass.setName(name.getText().toString());
                modelClass.setEmail(email.getText().toString());
                modelClass.setAddress(address.getText().toString());
                modelClass.setPassword(password.getText().toString());
                long id = myDataBaseHelper.insert(modelClass);
                if (id == -1) {
                    Toast.makeText(MainActivity.this, "not save", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "save", Toast.LENGTH_LONG).show();
                }
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDataBaseHelper myDataBaseHelper = new MyDataBaseHelper(getApplicationContext());

                boolean isUpdate = myDataBaseHelper.update(id.getText().toString(), name.getText().toString(), email.getText().toString(), address.getText().toString(), password.getText().toString());
                if (isUpdate == true) {
                    Toast.makeText(MainActivity.this, "data is updated", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "data not updated", Toast.LENGTH_LONG).show();
                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDataBaseHelper myDataBaseHelper = new MyDataBaseHelper(getApplicationContext());
                int value = myDataBaseHelper.delete(id.getText().toString());
                if (value > 0) {
                    Toast.makeText(MainActivity.this, "Data id deleted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Data not deleted", Toast.LENGTH_LONG).show();
                }
            }
        });
        showAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDataBaseHelper myDataBaseHelper = new MyDataBaseHelper(getApplicationContext());
                Cursor cursor = myDataBaseHelper.showAll();
                if (cursor.getCount() == 0) {
                    showAllData("noData", "no any data in your Database");
                    Toast.makeText(MainActivity.this, "no data in your database", Toast.LENGTH_LONG).show();
                }
                StringBuffer stringBuffer = new StringBuffer();
                while (cursor.moveToNext()) {
                    stringBuffer.append("id = " + cursor.getString(0) + "\n" + "name = " + cursor.getString(1) + "\n"
                            + "email = " + cursor.getString(2) + "\n"
                            + "address = " + cursor.getString(3) + "\n"
                            + "password = " + cursor.getString(4) + "\n");
                    showAllData("all data", stringBuffer.toString());

                }
            }
        });
    }

    public void showAllData(String all_data, String databaseData) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(all_data).setMessage(databaseData).setCancelable(true).create().show();
    }
}
