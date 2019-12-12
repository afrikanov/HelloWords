package com.example.hellowords;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

public class RegistrationActivity extends AppCompatActivity {

    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;
    private static String selectSQL;
    Button signIn;
    Button signUp;
    EditText login;
    EditText password;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mDBHelper = new DBHelper(this, "usersData.db");

        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            //..
        }

        try {
            mDB = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        /*
        Cursor c = mDB.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name!='android_metadata' order by name", null);
        if (c.moveToFirst()) {
            while ( !c.isAfterLast() ) {
                Toast.makeText(RegistrationActivity.this, "Table Name=> "+c.getString(0), Toast.LENGTH_LONG).show();
                c.moveToNext();
            }
        }
        */
        signIn = findViewById(R.id.sign_in);
        signUp = findViewById(R.id.sign_up);
        login = findViewById(R.id.login);
        password = findViewById(R.id.password);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String loginFinal = login.getText().toString();
                final String passwordFinal = password.getText().toString();
                if (loginFinal.isEmpty() || passwordFinal.isEmpty()) {
                    Toast.makeText(RegistrationActivity.this, "Invalid data", Toast.LENGTH_SHORT).show();
                    return;
                }
                ContentValues newValue = new ContentValues();
                newValue.put("login", loginFinal);
                newValue.put("password", passwordFinal);
                mDB.insert("users", null, newValue);
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String loginFinal = login.getText().toString();
                final String passwordFinal = password.getText().toString();
                if (loginFinal.isEmpty() || passwordFinal.isEmpty()) {
                    Toast.makeText(RegistrationActivity.this, "Invalid data", Toast.LENGTH_SHORT).show();
                    return;
                }
                selectSQL = "SELECT * FROM users";
                try {
                    cursor = mDB.rawQuery(selectSQL, null);
                } catch (Exception e) {
                    System.out.println("EXCEPTION");
                }
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    String userLogin = cursor.getString(1);
                    String userPassword = cursor.getString(2);
                    if (loginFinal.equals(userLogin) && passwordFinal.equals(userPassword)) {
                        startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                    }
                    cursor.moveToNext();
                }
                Toast.makeText(RegistrationActivity.this, "User not found", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
